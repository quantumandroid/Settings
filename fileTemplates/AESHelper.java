import android.content.Context;
import android.util.Base64;
import android.widget.Toast;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

public class AESHelper {

    private final String PASSWORD = "12345";
    private Context context;
    private SecretKeySpec sks = null;

    public AESHelper(Context context) {
        this.context = context;
        generateSks();
    }

    private void generateSks() {
        // Set up secret key spec for 128-bit AES encryption and decryption

        try {
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
            sr.setSeed(PASSWORD.getBytes());
            KeyGenerator kg = KeyGenerator.getInstance("AES");
            kg.init(128, sr);
            sks = new SecretKeySpec((kg.generateKey()).getEncoded(), "AES");
        } catch (Exception e) {
            Toast.makeText(context, "AES secret key spec error", Toast.LENGTH_LONG).show();

        }
    }

    public String encrypt(String string) {
        
        // Encode the original data with AES
        byte[] encodedBytes = null;
        try {
            Cipher c = Cipher.getInstance("AES");
            c.init(Cipher.ENCRYPT_MODE, sks);
            encodedBytes = c.doFinal(string.getBytes());
        } catch (Exception e) {
            Toast.makeText(context, "AES encryption error", Toast.LENGTH_LONG).show();

        }


        return Base64.encodeToString(encodedBytes, Base64.DEFAULT);

    }

    public String decrypt(String str) {

        byte[] encodedBytes = Base64.decode(str, Base64.DEFAULT);

        // Decode the encoded data with AES
        byte[] decodedBytes = null;
        try {
            Cipher c = Cipher.getInstance("AES");
            c.init(Cipher.DECRYPT_MODE, sks);
            decodedBytes = c.doFinal(encodedBytes);
        } catch (Exception e) {
            Toast.makeText(context, "AES decryption error", Toast.LENGTH_LONG).show();

        }

        return new String(decodedBytes);
    }
}
