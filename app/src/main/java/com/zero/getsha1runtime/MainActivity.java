package com.zero.getsha1runtime;

import android.annotation.SuppressLint;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSignature();
    }

    private void getSignature() {

        try {
            @SuppressLint("WrongConstant") final PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNING_CERTIFICATES);
            final MessageDigest md = MessageDigest.getInstance("SHA");

            if (Build.VERSION.SDK_INT >= 28) {
                Signature[] signatures = packageInfo.signingInfo.getApkContentsSigners();

                if (signatures == null) {
                    signatures = packageInfo.signingInfo.getSigningCertificateHistory();
                }

                for (Signature signature : signatures) {
                    md.update(signature.toByteArray());
                    final String signatureBase64 = new String(Base64.encode(md.digest(), Base64.DEFAULT));
                    Log.d("Signature Base64 1", signatureBase64);
                    Log.i("Signature 1", ": " + SHA1(signatureBase64));
                }

            } else {
                Signature[] sigs = getPackageManager().getPackageInfo(getApplication().getPackageName(), PackageManager.GET_SIGNATURES).signatures;
                for (Signature signature : sigs) {
                    md.update(signature.toByteArray());
                    final String signatureBase64 = new String(Base64.encode(md.digest(), Base64.DEFAULT));
                    Log.i("myLogSignature 2", ": " + signatureBase64);

//                    byte[] sha1 = {
//                            0x3B, (byte) 0xDA, (byte) 0xA0, 0x5B, 0x4F, 0x35, 0x71, 0x02, 0x4E, 0x27, 0x22, (byte) 0xB9, (byte) 0xAc, (byte) 0xB2, 0x77, 0x2F, (byte) 0x9D, (byte) 0xA9, (byte) 0x9B, (byte) 0xD9
//                    };

                    byte[] sha1_2 = {
                            (byte) 0x90, (byte) 0xB3, (byte) 0x3A, 0x3D, 0x13, 0x42, (byte) 0xD1, 0x6F, 0x34, 0x41, 0x3D, (byte) 0x19, (byte) 0x37, (byte) 0x0E, 0x0E, 0x3B, (byte) 0x57, (byte) 0x43, (byte) 0x9C, (byte) 0x3B
                    };

                    Log.i("myLogKeyhash", Base64.encodeToString(sha1_2, Base64.NO_WRAP));

                    //Terminal echo 90:B3:3A:3D:13:42:D1:6F:34:41:3D:19:37:0E:0E:3B:57:43:9C:3B | xxd -r -p | openssl base64


                }
            }
        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException e) {
            Log.e("Signature Base64", "getSignature: " + e.getMessage());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    private static String convertToHex(byte[] data) {
        StringBuilder buf = new StringBuilder();
        for (byte b : data) {
            int halfbyte = (b >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                buf.append((0 <= halfbyte) && (halfbyte <= 9) ? (char) ('0' + halfbyte) : (char) ('a' + (halfbyte - 10)));
                halfbyte = b & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }

    public static String SHA1(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] textBytes = text.getBytes("iso-8859-1");
        md.update(textBytes, 0, textBytes.length);
        byte[] sha1hash = md.digest();
        return convertToHex(sha1hash);
    }

    private String byteArray2Hex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        return formatter.toString();
    }


}
