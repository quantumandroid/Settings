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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ScreenShot {

    private Context context;
    private File IMAGE_FILE;
    private File DIRECTORY;
    private String IMAGE_NAME;
    
    public ScreenShot(Context context, View layout) {
        this.context = context;
         DIRECTORY = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "SL18");
        IMAGE_NAME = new SimpleDateFormat("ddMMyyyy_HHmmss", Locale.US).format(Calendar.getInstance().getTime());
        IMAGE_NAME = "IMG-" + IMAGE_NAME + ".jpg";



        Bitmap bitmap = loadBitmapFromView(layout, layout.getWidth(), layout.getHeight());
       IMAGE_FILE = saveBitmap(bitmap);

    }
    
    private File saveBitmap(Bitmap bitmap) {
        File imagePath = null;
        imagePath = new File(DIRECTORY, IMAGE_NAME);

        FileOutputStream fos;
        try {

            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            //Uri selected_image_uri = Uri.fromFile(imagePath);

            //shareImage(imagePath);

            //Log.e("ImageSave", "Saveimage");
        } catch (FileNotFoundException e) {
            //Log.e("GREC", e.getMessage(), e);
        } catch (IOException e) {
            // Log.e("GREC", e.getMessage(), e);
        }
        return imagePath;
    }

    private static Bitmap loadBitmapFromView(View view, int width, int height) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        return bitmap;
    }

    public void share() {
        final Uri uri = FileProvider.getUriForFile(context, "my.file.provider", IMAGE_FILE);
        new AlertDialog.Builder(context)
                .setTitle("Success!")
                .setMessage("ScreenShot saved Successfully...")
                .setPositiveButton("SHARE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent shareIntent = new Intent();
                        shareIntent.setAction(Intent.ACTION_SEND);
                        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        shareIntent.setType("image/*");
                        context.startActivity(Intent.createChooser(shareIntent, "Share image via:"));
                    }
                })
                .setNegativeButton("Cancel", null)
                .setCancelable(false)
                .create()
                .show();

    }

}
