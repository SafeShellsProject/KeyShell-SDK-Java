package com.keyShell.api.Utils;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.util.Date;

public class TokenUtil {

    public static String encrypt(byte[] data, Key key) {
        try {
            IvParameterSpec iv = new IvParameterSpec(new byte[16]);
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            cipher.init(cipher.ENCRYPT_MODE, key, iv);
            byte[] result = cipher.doFinal(data);

            String tokenResult = ByteUtil.toHexString(result);
            tokenResult = tokenResult.substring(tokenResult.length() - 32, tokenResult.length() - 16);
            return tokenResult;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] decrypt(byte[] data, SecretKey secretKey) {
        try {
            IvParameterSpec iv = new IvParameterSpec(new byte[16]);
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
            byte[] result = cipher.doFinal(data);
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] sha256(byte[] value) {
        byte[] hashValue = null;
        try {
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
            hashValue = sha256.digest(value);
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Hash失败.");
        }
        return hashValue;
    }

    public static boolean compareTime(Date oldTime, Date newTime) {
        long time = newTime.getTime() - oldTime.getTime();
        double result = time * 1.0 /(1000 * 60 * 60);
        if (result < 24) {
            return true;
        } else {
            return false;
        }
    }

    public static String validToken(String token, String validData) {
        SecretKey key = new SecretKeySpec(ByteUtil.toBytes(token), "AES");
        byte[] macData = sha256(ByteUtil.toBytes(validData));
        String macValid = encrypt(macData, key);
        return macValid;
    }
}
