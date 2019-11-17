
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import static com.softlogic1.sl18_02.utility.Strings.C2;
import static com.softlogic1.sl18_02.utility.Strings.FROM;
import static com.softlogic1.sl18_02.utility.Strings.SUM;
import static com.softlogic1.sl18_02.utility.Strings.TO;

//TODO use lib jexcelapi_2_6_12
public class ExcelHelper {

    private Context context;
    private File EXCEL_FILE;

    public ExcelHelper(Context context, String JSON_STRING) {

        this.context = context;

        String EXCEL_NAME = new SimpleDateFormat("ddMMyyyy_HHmmss", Locale.US).format(Calendar.getInstance().getTime());
        EXCEL_NAME = "XL-" + EXCEL_NAME + ".xls";

        File DIRECTORY = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "SL18");
        DIRECTORY.mkdirs();

        EXCEL_FILE = new File(DIRECTORY, EXCEL_NAME);

        writeExcel(EXCEL_FILE, getJsonData(JSON_STRING));

    }

    private ArrayList<HashMap<String, String>> getJsonData(String json_string) {

        ArrayList<HashMap<String, String>> list = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(json_string);
            JSONArray result = jsonObject.getJSONArray(Strings.TAG_JSON_ARRAY);

            for (int i = 0; i < result.length(); i++) {

                HashMap<String, String> hashMap1 = new HashMap<>();

                JSONObject jo = result.getJSONObject(i);

                hashMap1.put("0", jo.getString(Strings.KEY_ID1));
                hashMap1.put("1", jo.getString(Strings.KEY_T1));
                hashMap1.put("2", jo.getString(Strings.KEY_D1));
                hashMap1.put("3", jo.getString(Strings.KEY_T2));
                hashMap1.put("4", jo.getString(Strings.KEY_D2));
                hashMap1.put("5", jo.getString(Strings.KEY_C1));
                hashMap1.put("6", jo.getString(Strings.KEY_C2));
                hashMap1.put("7", jo.getString(Strings.KEY_T8));
                hashMap1.put("8", jo.getString(Strings.KEY_C3));
                hashMap1.put("9", jo.getString(Strings.KEY_T3));
                hashMap1.put("10", jo.getString(Strings.KEY_T4));
                hashMap1.put("11", jo.getString(Strings.KEY_D3));
                hashMap1.put("12", jo.getString(Strings.KEY_T5));
                hashMap1.put("13", jo.getString(Strings.KEY_T6));
                hashMap1.put("14", jo.getString(Strings.KEY_TM1));


                list.add(hashMap1);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }

    private void writeExcel(File file, ArrayList<HashMap<String, String>> data_list) {
        Toast.makeText(context, data_list.size() + "", Toast.LENGTH_SHORT).show();

        int EXTRA_ROWS = 5;
        int TOTAL_ROWS = data_list.size() + EXTRA_ROWS;
        int TOTAL_COLUMNS = data_list.get(0).size();

        WorkbookSettings wbSettings = new WorkbookSettings();

        wbSettings.setLocale(new Locale("en", "EN"));

        WritableWorkbook workbook;

        try {

            workbook = Workbook.createWorkbook(file, wbSettings);

            WritableSheet sheet = workbook.createSheet("First Sheet", 0);

            for (int column = 0; column < TOTAL_COLUMNS; column++) {

                sheet.setColumnView(column, 12);
            }

            sheet.mergeCells(0, 0, TOTAL_COLUMNS, 0);
            sheet.mergeCells(0, 1, TOTAL_COLUMNS, 0);
            sheet.mergeCells(0, 2, TOTAL_COLUMNS, 0);


            for (int row = 0; row < TOTAL_ROWS; row++) {
                for (int column = 0; column < TOTAL_COLUMNS; column++) {

                    if (row == 0) {
                        Label lable0 = new Label(0, 0, "SL18", getCenterFormat());
                        sheet.addCell(lable0);

                    } else if (row == 1) {
                        Label lable1 = new Label(0, row, "C2 :- " + C2, getLeftFormat());
                        sheet.addCell(lable1);

                    } else if (row == 2) {
                        String str;
                        if (FROM.isEmpty() || TO.isEmpty()) {
                            str = "D1 :- All";
                        } else {
                            str = "D1 :- FROM: " + FROM + "  TO: " + TO;
                        }
                        Label lable2 = new Label(0, row, str, getLeftFormat());
                        sheet.addCell(lable2);

                    } else if (row == 3) {

                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put("0", "ID1");
                        hashMap.put("1", "T1");
                        hashMap.put("2", "D1");
                        hashMap.put("3", "T2");
                        hashMap.put("4", "D2");
                        hashMap.put("5", "C1");
                        hashMap.put("6", "C2");
                        hashMap.put("7", "T8");
                        hashMap.put("8", "C3");
                        hashMap.put("9", "T3");
                        hashMap.put("10", "T4");
                        hashMap.put("11", "D3");
                        hashMap.put("12", "T5");
                        hashMap.put("13", "T6");
                        hashMap.put("14", "TM1");

                        for (Map.Entry<String, String> entry : hashMap.entrySet()) {

                            int cl = Integer.parseInt(entry.getKey());
                            String value = entry.getValue();
                            Label label = new Label(cl, row, value, getCenterFormat());
                            sheet.addCell(label);

                        }
                    } else if (row == TOTAL_ROWS - 1) {
                        Label label = new Label(12, row, "TOTAL", getCenterFormat());
                        sheet.addCell(label);

                        Label label2 = new Label(13, row, SUM, getLeftFormat());
                        sheet.addCell(label2);

                    } else {
                        HashMap<String, String> hashMap = data_list.get(row - 4);

                        for (Map.Entry<String, String> entry : hashMap.entrySet()) {


                            int cl = Integer.parseInt(entry.getKey());
                            String value = entry.getValue();
                            Label label = new Label(cl, row, value);
                            sheet.addCell(label);

                        }

                    }

                }

            }

            workbook.write();

            workbook.close();

        } catch (IOException | WriteException e) {
            e.printStackTrace();
        }

    }

    private WritableCellFormat getLeftFormat() {
        WritableCellFormat cFormat = new WritableCellFormat();

        try {
            WritableFont font = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
            Alignment alignment = Alignment.LEFT;
            Colour colour = Colour.GREY_25_PERCENT;
            cFormat.setFont(font);
            cFormat.setAlignment(alignment);
            cFormat.setBackground(colour);
        } catch (WriteException e) {
            e.printStackTrace();
        }
        return cFormat;
    }

    private WritableCellFormat getCenterFormat() {

        WritableCellFormat titleFormat = new WritableCellFormat();

        try {
            WritableFont title_font = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
            Alignment title_alignment = Alignment.CENTRE;
            Colour title_colour = Colour.GRAY_25;
            titleFormat.setFont(title_font);
            titleFormat.setAlignment(title_alignment);
            titleFormat.setBackground(title_colour);
        } catch (WriteException e) {
            e.printStackTrace();
        }
        return titleFormat;
    }

    public void shareFile() {
        if (!EXCEL_FILE.exists())
            return;

        new AlertDialog.Builder(context)
                .setMessage("File Created Successfully.")
                .setPositiveButton("SHARE", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        // Uri uri = Uri.fromFile(file);
                        final Uri uri = FileProvider.getUriForFile(context, "my.file.provider2", EXCEL_FILE);
                        Intent share = new Intent();
                        share.setAction(Intent.ACTION_SEND);
                        share.setType("application/vnd.ms-excel");
                        share.putExtra(Intent.EXTRA_STREAM, uri);
                        share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        context.startActivity(share);


                        dialog.cancel();// it used to cancel dialog box
                    }
                })
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }


}
