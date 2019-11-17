import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SmsManager;

public class Sms {

    private static final String SMS_SENT_ACTION = "com.sms.SMS_SENT_ACTION";
    private static final String SMS_DELIVERED_ACTION = "com.sms.SMS_DELIVERED_ACTION";

    private OnResponseListener listener;
    private Context context;

    public Sms(Context context, String PHONE, String MESSAGE) {

        listener = null;
        this.context = context;

        registerReceivers(context);

        sendMessage(PHONE, MESSAGE);
    }

    private void registerReceivers(Context context) {
        /*
         * Sent Receiver
         * */

        context.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String message = "";
                boolean sent = false;
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        sent = true;
                        message = "Message Sent Successfully !";
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        sent = false;
                        message = "Error.";
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        sent = false;
                        message = "Error: No service.";
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        sent = false;
                        message = "Error: Null PDU.";
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        sent = false;
                        message = "Error: Radio off.";
                        break;
                }

                if (listener != null) {
                    listener.onSent(sent, message);
                }

            }
        }, new IntentFilter(SMS_SENT_ACTION));

        /*
         * Delivery Receiver
         * */

        context.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                if (listener != null) {
                    listener.onDelivered();
                }

            }
        }, new IntentFilter(SMS_DELIVERED_ACTION));
    }

    private void sendMessage(String phone, String message) {

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phone, null, message,
                    PendingIntent.getBroadcast(context, 0, new Intent(SMS_SENT_ACTION), 0),
                    PendingIntent.getBroadcast(context, 0, new Intent(SMS_DELIVERED_ACTION), 0));
        } catch (Exception e) {
            e.printStackTrace();
            if (listener != null) {
                listener.onSent(false, e.toString());
            }
        }

    }

    public void setOnResponseListener(OnResponseListener listener) {
        this.listener = listener;
    }

    public interface OnResponseListener {

        public void onSent(boolean SENT, String msg);

        public void onDelivered();
    }

}
