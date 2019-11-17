package com.softlogic1.sl18_02.utility;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    public static final String dmy = "dd-MM-yyyy";
    public static final String ydm = "yyyy-MM-dd";

    public static String getCurrentTime(String simple_date_format) {

        Calendar c = Calendar.getInstance();

        @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat(simple_date_format);

        return df.format(c.getTime());
    }

    public static String convert(String date_str, String from_format, String to_format) {

        @SuppressLint("SimpleDateFormat") SimpleDateFormat from = new SimpleDateFormat(from_format);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat to = new SimpleDateFormat(to_format);

        try {
            Date date = from.parse(date_str);

            return to.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date_str;
    }
     public static void setDate(Context context, final EditText editText){
         final Calendar c = Calendar.getInstance();

         DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                 new DatePickerDialog.OnDateSetListener() {

                     @Override
                     public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                         String str = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                         editText.setText(str);

                         @SuppressLint("SimpleDateFormat") SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
                         try {
                             Date date = fmt.parse(str);

                             @SuppressLint("SimpleDateFormat") SimpleDateFormat fmt2 = new SimpleDateFormat("dd-MM-yyyy");
                             String s = fmt2.format(date);
                             editText.setText(s);


                         } catch (ParseException e) {
                             e.printStackTrace();
                         }





                     }
                 }, c.get(Calendar.YEAR),
                    c.get(Calendar.MONTH),
                    c.get(Calendar.DAY_OF_MONTH));
         
         datePickerDialog.show();
     }
private String getDateString(String date_str) {

        if (date_str.equals("0000-00-00"))
            return "";

        @SuppressLint("SimpleDateFormat") SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = fmt.parse(date_str);
            @SuppressLint("SimpleDateFormat") SimpleDateFormat fmt2 = new SimpleDateFormat("dd-MM-yyyy");
            return fmt2.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date_str;
    }

}
