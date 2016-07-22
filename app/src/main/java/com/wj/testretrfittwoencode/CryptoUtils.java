package com.wj.testretrfittwoencode;


import android.util.Base64;
import android.util.Log;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/*
 *Created by wangjing on 2016/7/5.
 * 加密算法
 */
public class CryptoUtils {

    static {
        System.loadLibrary("NativeLib");
    }

    public static String EncryptWithNoPadding(String s) {
        return Encrypt(s, "NoPadding");
    }

    public static String EncryptWithPadding(String s) {
        return Encrypt(s, "PKCS7Padding");
    }

    private static String Encrypt(String s, String paddingMode) {
        try {
            Log.e("sssss", getKey().toString());
            Log.e("111111", getNativeString());
            Log.e("iiiiiiiii", getIv().toString());
            SecretKeySpec key = new SecretKeySpec(getKey(), "AES");
            IvParameterSpec iv = new IvParameterSpec(getIv());
            Cipher cipher = Cipher.getInstance("AES/CBC/" + paddingMode);
            cipher.init(Cipher.ENCRYPT_MODE, key, iv);
            byte[] result = cipher.doFinal(s.getBytes("utf-8"));
            return Base64.encodeToString(result, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String DecryptWithNoPadding(String s) {
        return Decrypt(s, "NoPadding");
    }

    public static String DecryptWithPadding(String s) {
        return Decrypt(s, "PKCS7Padding");
    }

    private static String Decrypt(String s, String paddingMode) {
        try {
            SecretKeySpec key = new SecretKeySpec(getKey(), "AES");
            IvParameterSpec iv = new IvParameterSpec(getIv());
            Cipher cipher = Cipher.getInstance("AES/CBC/" + paddingMode);
            cipher.init(Cipher.DECRYPT_MODE, key, iv);
            byte[] result = cipher.doFinal(Base64.decode(s, Base64.DEFAULT));
            return new String(result, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static native String getNativeString();

    public static native byte[] getKey();

    public static native byte[] getIv();
}