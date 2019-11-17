
import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

public class PermissionActivity extends AppCompatActivity {
// first you must need add permissions in AndroidManifest.xml fil
// Requesting Permissions at Run Time (Android-6.0 (API-23) and up)
// Dangerous Permissions.


/*todo   Permission Group               Permissions

         1) CALENDAR                READ_CALENDAR
                                    WRITE_CALENDAR
         2) CAMERA                  CAMERA
         3) CONTACTS                READ_CONTACTS
                                    WRITE_CONTACTS
                                    GET_ACCOUNTS
         4) LOCATION                ACCESS_FINE_LOCATION
                                    ACCESS_COARSE_LOCATION
         5) MICROPHONE              RECORD_AUDIO
         6) PHONE                   READ_PHONE_STATE
                                    CALL_PHONE
                                    READ_CALL_LOG
                                    WRITE_CALL_LOG
                                    ADD_VOICEMAIL
                                    USE_SIP
                                    PROCESS_OUTGOING_CALLS
         7) SENSORS                 BODY_SENSORS

         8) SMS                     SEND_SMS
                                    RECEIVE_SMS
                                    READ_SMS
                                    RECEIVE_WAP_PUSH
                                    RECEIVE_MMS
         9) STORAGE                 READ_EXTERNAL_STORAGE
                                    WRITE_EXTERNAL_STORAGE
                                    */

    // all are custom integers
    public static final String READ_CALENDAR = Manifest.permission.READ_CALENDAR;
    public static final String WRITE_CALENDAR   = Manifest.permission.WRITE_CALENDAR;
    public static final String CAMERA = Manifest.permission.CAMERA;
    public static final String READ_CONTACTS = Manifest.permission.READ_CONTACTS;
    public static final String WRITE_CONTACTS = Manifest.permission.WRITE_CONTACTS;
    public static final String GET_ACCOUNTS = Manifest.permission.GET_ACCOUNTS;
    public static final String ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    public static final String ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    public static final String RECORD_AUDIO = Manifest.permission.RECORD_AUDIO;
    public static final String READ_PHONE_STATE = Manifest.permission.READ_PHONE_STATE;
    public static final String CALL_PHONE = Manifest.permission.CALL_PHONE;
    public static final String READ_CALL_LOG = Manifest.permission.READ_CALL_LOG;
    public static final String WRITE_CALL_LOG = Manifest.permission.WRITE_CALL_LOG;
    public static final String ADD_VOICEMAIL = Manifest.permission.ADD_VOICEMAIL;
    public static final String USE_SIP = Manifest.permission.USE_SIP;
    public static final String PROCESS_OUTGOING_CALLS = Manifest.permission.PROCESS_OUTGOING_CALLS;
    public static final String BODY_SENSORS = Manifest.permission.BODY_SENSORS;
    public static final String SEND_SMS = Manifest.permission.SEND_SMS;
    public static final String RECEIVE_SMS = Manifest.permission.RECEIVE_SMS;
    public static final String READ_SMS = Manifest.permission.READ_SMS;
    public static final String RECEIVE_WAP_PUSH = Manifest.permission.RECEIVE_WAP_PUSH;
    public static final String RECEIVE_MMS = Manifest.permission.RECEIVE_MMS;
    public static final String READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;
    public static final String WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;

    private static final int PERMISSION_REQUEST_CODE = 999;
   // private String PERMISSION_STRING = "";


    public boolean checkPermission(String [] permission_strings) {
        ArrayList<String> arrayList = new ArrayList<String>();

        int final_result = 0;


        for (String PERMISSION_STRING : permission_strings) {
            int result = ContextCompat.checkSelfPermission(this, PERMISSION_STRING);
            if (result == PackageManager.PERMISSION_DENIED) {

                arrayList.add(PERMISSION_STRING);

            }
            final_result += result;
        }
        String [] DENIED_STRINGS = new String[arrayList.size()];
        DENIED_STRINGS = arrayList.toArray(DENIED_STRINGS);

        if (final_result == PackageManager.PERMISSION_GRANTED){
            return true;
        }else {
            requestPermission(DENIED_STRINGS);
            return false;
        }



    }

    private void requestPermission(String[] denied_strings) {
        ActivityCompat.requestPermissions(this, denied_strings, PERMISSION_REQUEST_CODE);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean first = true;
        String p_string = "";
        ArrayList<String> arrayList = new ArrayList<String>();
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {
                    for (int i =0 ; i < grantResults.length;i++){
                        boolean permissionAccepted = grantResults[i] == PackageManager.PERMISSION_GRANTED;
                        if (!permissionAccepted && first){
                            first = false;
                            p_string = permissions[i];
                            arrayList.add(p_string);

                        }else if (!permissionAccepted){
                            arrayList.add(permissions[i]);
                        }
                    }

                    String[] mStringArray = new String[arrayList.size()];
                    mStringArray = arrayList.toArray(mStringArray);

                    if (!p_string.equals("")) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(p_string)) {
                                final String[] finalMStringArray = mStringArray;
                                showMessageOKCancel("You need to allow access to the permissions.",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(finalMStringArray, PERMISSION_REQUEST_CODE);
                                                }
                                            }
                                        });
                                return;
                            }
                        }
                    }

                }

                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setTitle("Permission Denied.!!")
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

}


/*todo how to use*/
/*
 if (checkPermission(new String[]{WRITE_EXTERNAL_STORAGE,CAMERA})) {
            Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "please grant permission request", Toast.LENGTH_SHORT).show();
        }
* */

