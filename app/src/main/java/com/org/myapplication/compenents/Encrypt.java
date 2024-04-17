package com.org.myapplication.compenents;

import android.util.Base64;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Encrypt {

    private static final String CBC_PKCS5_PADDING = "AES/CBC/PKCS5Padding";
    private static final String AES = "AES";
    private static String IvParameter = "";
    private static String key = "";

    public static String encrypt(String data) {
        return encryptString(data);
    }

    public static String encrypt(int data) {
        return encryptString(Integer.toString(data));
    }

    public static String encrypt(Long data) {
        return encryptString(data.toString());
    }

    public static String encrypt(CharSequence data) {
        return encryptString(data.toString());
    }

    public static String encrypt() {
        return "";
    }

    private static String encryptString(String data) {
        try {
            byte[] raw = key.getBytes();
            SecretKeySpec skeySpec = new SecretKeySpec(raw, AES);
            Cipher cipher = Cipher.getInstance(CBC_PKCS5_PADDING);
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, new IvParameterSpec(IvParameter.getBytes()));
            byte[] cipherText = cipher.doFinal(data.getBytes());
            return Base64.encodeToString(cipherText, Base64.DEFAULT)
                    .replaceAll(Objects.requireNonNull(System.getProperty("line.separator")), "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String decrypt() {
        return "";
    }

    public static String decrypt(String data) {
        try {
            byte[] raw = key.getBytes(StandardCharsets.US_ASCII);
            SecretKeySpec skeySpec = new SecretKeySpec(raw, AES);
            Cipher cipher = Cipher.getInstance(CBC_PKCS5_PADDING);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, new IvParameterSpec(IvParameter.getBytes()));
            return new String(cipher.doFinal(Base64.decode(data, Base64.DEFAULT)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void initUtil() {
        Random random = new Random();
        StringBuilder key_cache = new StringBuilder(), IvParameter_cache = new StringBuilder();
        String text = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (int i = 0; i < 16; i++)
            key_cache.append(text.charAt(random.nextInt(text.length())));
        for (int i = 0; i < 16; i++)
            IvParameter_cache.append(text.charAt(random.nextInt(text.length())));
        IvParameter = IvParameter_cache.toString();
        key = key_cache.toString();
    }

    public static void setSecretKey(String secretKey) {
        key = secretKey;
    }

    public static void setIvParameter(String ivParameter) {
        IvParameter = ivParameter;
    }

    public static String getSecretKey() {
        return key;
    }

    public static String getIvParameter() {
        return IvParameter;
    }
}
