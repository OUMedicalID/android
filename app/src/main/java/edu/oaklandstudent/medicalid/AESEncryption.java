package edu.oaklandstudent.medicalid;
// We can implement regular java and call it from the android classes such as Home or MainActivity for example.
// We could have chosen to implement import java.util.Base64 but we will implement the android version as it has more benefits.


import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Random;


public class AESEncryption {

    public static String MEDICAL_ID_DOMAIN = "https://medicalidou.com";
    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();

    public static String encrypt(String value, String key) {

        if(value == null) return "";

        String initVector = generateRandomString(16);

        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(value.getBytes());
            return  bytesToHex(encrypted) + ":" + initVector;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }


    public static String encryptEmail(String value, String key) {

        if(value == null) return "";

        String initVector = rightPadZeros(hexadecimal(value, "utf-8"), 16).substring(0, 16);

        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(value.getBytes());
            return  bytesToHex(encrypted);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static String decrypt(String encrypted, String key) {

        if(encrypted == null || encrypted.equals("")) return "";

        String[] data = encrypted.split(":");


        try {
            IvParameterSpec iv = new IvParameterSpec(data[1].getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] original = cipher.doFinal(hexStringToByteArray(data[0]));

            return new String(original);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }



    public static String getSHA512(String input){
        String toReturn = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            digest.reset();
            digest.update(input.getBytes("utf8"));
            toReturn = String.format("%0128x", new BigInteger(1, digest.digest()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return toReturn;
    }


    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }


    public static String hexadecimal(String input, String charsetName){
        try{
            return asHex(input.getBytes(charsetName));
        }catch(IOException e) {
            return "";
        }
    }

    private static final char[] HEX_CHARS = "0123456789ABCDEF".toCharArray();

    public static String asHex(byte[] buf)
    {
        char[] chars = new char[2 * buf.length];
        for (int i = 0; i < buf.length; ++i)
        {
            chars[2 * i] = HEX_CHARS[(buf[i] & 0xF0) >>> 4];
            chars[2 * i + 1] = HEX_CHARS[buf[i] & 0x0F];
        }
        return new String(chars);
    }


    public static String convertHexToStringValue(String hex) {
        if(hex == null)return null;
        StringBuilder stringbuilder = new StringBuilder();
        char[] hexData = hex.toCharArray();
        for (int count = 0; count < hexData.length - 1; count += 2) {
            int firstDigit = Character.digit(hexData[count], 16);
            int lastDigit = Character.digit(hexData[count + 1], 16);
            int decimal = firstDigit * 16 + lastDigit;
            stringbuilder.append((char)decimal);
        }
        return stringbuilder.toString();
    }


    public static String generateRandomString(int length) {
        // You can customize the characters that you want to add into
        // the random strings
        String CHAR = "ABCDEF";
        String NUMBER = "0123456789";

        String DATA_FOR_RANDOM_STRING = CHAR + NUMBER;
        SecureRandom random = new SecureRandom();

        if (length < 1) throw new IllegalArgumentException();

        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            // 0-62 (exclusive), random returns 0-61
            int rndCharAt = random.nextInt(DATA_FOR_RANDOM_STRING.length());
            char rndChar = DATA_FOR_RANDOM_STRING.charAt(rndCharAt);

            sb.append(rndChar);
        }

        return sb.toString();
    }

    public static String rightPadZeros(String str, int num) {
        return String.format("%1$-" + num + "s", str).replace(' ', '0');
    }
}



