

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Util {

    //Constant fields
    public static String DateFormat_DATE = "yyyy-MM-dd";
    public static String DateFormat_DATETIME = "yyyy-MM-dd HH:mm:ss";
    public static String DateFormat_TIME = "HH:mm:ss";
    public static int YESTERDAY = 0;
    public static int TODAY = 0;

    public String getTime(String simple_date_format) {

        Calendar c = Calendar.getInstance();

        @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat(simple_date_format);

        return df.format(c.getTime());
    }
    
    //  String pdf_name = new SimpleDateFormat("ddMMyyyy_HHmmss", Locale.US).format(Calendar.getInstance().getTime());
    public String getCurrentTime(String simple_date_format){
        return new SimpleDateFormat(simple_date_format, Locale.US).format(Calendar.getInstance().getTime());
    }

    public String getTime(String day, String simple_date_format) {

        Calendar c = Calendar.getInstance();

        @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat(simple_date_format);

        return df.format(c.getTime());
    }
}
