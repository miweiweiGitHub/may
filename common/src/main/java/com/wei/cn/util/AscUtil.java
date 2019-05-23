package com.wei.cn.util;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Slf4j
public class AscUtil {

    public static String md5(String plainText) {
        byte[] secretBytes = null;
        try {
            secretBytes = MessageDigest.getInstance("md5").digest(
                    plainText.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("没有md5这个算法！");
        }
        String md5code = new BigInteger(1, secretBytes).toString(16);
        // 如果生成数字未满32位，需要前面补0
        int md5Len = md5code.length();
        log.info("md5Len:{}",md5Len);
        for (int i = 0; i < 32 - md5Len; i++) {
            md5code = "0" + md5code;
        }
        return md5code;
    }

    // 加密
    public static String encrypt(String sSrc, String sKey) {
        if (sKey == null) {
            System.out.print("Key为空null");
            return null;
        }
        // 判断Key是否为32位
        if (sKey.length() != 32) {
            System.out.print("Key长度不是32位"+sKey.length());
            return null;
        }
        byte[] encrypted = null ;
        try {

            byte[] raw = sKey.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//"算法/模式/补码方式"
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));

        } catch (UnsupportedEncodingException e) {
            log.error("UnsupportedEncodingException e:{}",e);
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            log.error("NoSuchAlgorithmException e:{}",e);
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            log.error("InvalidKeyException e:{}",e);
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            log.error("NoSuchPaddingException e:{}",e);
            e.printStackTrace();
        } catch (BadPaddingException e) {
            log.error("BadPaddingException e:{}",e);
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            log.error("IllegalBlockSizeException e:{}",e);
            e.printStackTrace();
        }
        //此处使用BASE64做转码功能，同时能起到2次加密的作用。
        //return new Base64().encodeToString(encrypted);
        return Base64.encode(encrypted);
    }

    // 解密
    public static String decrypt(String sSrc, String sKey){
        try {
            // 判断Key是否正确
            if (sKey == null) {
                System.out.print("Key为空null");
                return null;
            }
            // 判断Key是否为32位
            if (sKey.length() != 32) {
                System.out.print("Key长度不是32位"+sKey.length());
                return null;
            }
            byte[] raw = sKey.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            //先用base64转码
            //byte[] encrypted1 = new Base64().decode(sSrc);
            byte[] encrypted1 = Base64.decode(sSrc);
            try {
                byte[] original = cipher.doFinal(encrypted1);
                String originalString = new String(original, "utf-8");
                return originalString;
            } catch (Exception e) {
                System.out.println(e.toString());
                return null;
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }


    public static void main(String[] args) {
        String soundai = md5("s1plat-ximalaya-soundai");

        String encrypt = encrypt("http://lxcipcas.cmread.com", soundai);

        String decrypt = decrypt(encrypt, soundai);
        log.info("soundai:{},encrypt:{},decrypt:{}",soundai,encrypt,decrypt);

    }
}
