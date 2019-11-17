import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.Toast;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

//TODO use libitext5-5.5.12
public class PdfHelper{

    private Context context;
    private File PDF_FILE = null;

    private File DIRECTORY;
    private String PDF_NAME;

    public PdfHelper(Context context, View layout) {
        this.context = context;

        PDF_NAME = new SimpleDateFormat("ddMMyyyy_HHmmss", Locale.US).format(Calendar.getInstance().getTime());
        PDF_NAME = PDF_NAME + ".pdf";


        DIRECTORY = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator + "SL18");

        DIRECTORY.mkdirs();

            File image_file = layoutToImage(layout);
            PDF_FILE = imageToPDF(image_file);

    }

    private File imageToPDF(File image_file) {
        File pdf_file = null;

        //String dirpath;
        try {
            Document document = new Document();

            pdf_file = new File(DIRECTORY, PDF_NAME);

            // dirpath = android.os.Environment.getExternalStorageDirectory().toString();
            PdfWriter.getInstance(document, new FileOutputStream(pdf_file)); //  Change pdf's name.
            document.open();
            Image img = Image.getInstance(image_file.getAbsolutePath());
            //float scaler = ((document.getPageSize().getWidth() - document.leftMargin() - document.rightMargin() - 0) / img.getHeight()) * 100;
            float scaler = ((document.getPageSize().getHeight() - document.topMargin() - document.bottomMargin() - 0) / img.getHeight()) * 100;

            img.scalePercent(scaler);

            img.setAlignment(Image.ALIGN_CENTER | Image.ALIGN_TOP);
            document.add(img);
            document.close();
            Toast.makeText(context, "PDF Generated successfully!..", Toast.LENGTH_SHORT).show();
            image_file.delete();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();

        }
        return pdf_file;
    }

    private File layoutToImage(View layout) {

        Bitmap bitmap = loadBitmapFromView(layout, layout.getWidth(), layout.getHeight());
        // saveBitmap(bitmap1);


        File directory = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        directory.mkdirs();

        String image_name = new SimpleDateFormat("ddMMyyyy_HHmmss", Locale.US).format(Calendar.getInstance().getTime());

        File image_file = new File(directory, image_name + ".jpg");


        // File imagePath = new File(Environment.getExternalStorageDirectory() + File.separator + "image.jpg");
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(image_file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();

            //Log.e("ImageSave", "Saveimage");
        } catch (FileNotFoundException e) {
            //Log.e("GREC", e.getMessage(), e);
        } catch (IOException e) {
            // Log.e("GREC", e.getMessage(), e);
        }

        return image_file;

    }
    private static Bitmap loadBitmapFromView(View view, int width, int height) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        return bitmap;
    }


    public void sharePdf() {

        if (PDF_FILE == null){
            Toast.makeText(context, "Error in share pdf", Toast.LENGTH_SHORT).show();
            return;
        }

        final Uri uri = FileProvider.getUriForFile(context, "my.file.provider", PDF_FILE);
        new AlertDialog.Builder(context)
                .setTitle("Success!")
                .setMessage("Pdf generated Successfully...")
                .setPositiveButton("SHARE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent shareIntent = new Intent();
                        shareIntent.setAction(Intent.ACTION_SEND);
                        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        shareIntent.setType("application/pdf");
                        context.startActivity(Intent.createChooser(shareIntent, "Share pdf via:"));
                    }
                })
                .setNegativeButton("Cancel", null)
                .setCancelable(false)
                .create()
                .show();
    }

}
