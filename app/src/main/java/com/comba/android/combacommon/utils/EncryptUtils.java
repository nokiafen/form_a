package com.comba.android.combacommon.utils;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by liangchunfeng on 2017/4/24.
 */

public class EncryptUtils {

    private static final String SHA1PRNG = "SHA1PRNG"; // SHA1PRNG 强随机种子算法, 要区别4.2以上版本的调用方法
    private static final String IV = "qws871bz73msl9x8";
    private static final String AES = "AES"; //AES 加密
    private static final String CIPHERMODE = "AES/CBC/PKCS5Padding"; //algorithm/mode/padding

    /**
     * 对字符串进行 MD5 加密
     * @param str   需要加密的字符串
     * @return  MD5加密后的字符串
     */
    public static String MD5Encode(String str){
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
        char[] charArray = str.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for(int i=0 ; i<charArray.length ; i++){
            byteArray[i] = (byte)charArray[i];
        }

        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexVal = new StringBuffer();

        for(int i=0 ; i<md5Bytes.length ; i++){
            int val = ((int)md5Bytes[i]) & 0xff;
            if(val <16 ){
                hexVal.append("0");
            }
            hexVal.append(Integer.toHexString(val));
        }
        return hexVal.toString();
    }


    /**
     * 字节数组转换为16进制字符串
     * @param byteArray
     * @return
     */
    public static String byteArrayToHex(byte[] byteArray){
        //首先初始化一个字符数组，用来存放每个16进制字符
        char[] hexDigits = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
        //new一个字符数组，这个就是用来组成结果字符串的（解释一下：一个byte是八位二进制，也就是2位十六进制字符）
        char[] resultCharArray = new char[byteArray.length*2];
        //遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中去
        int index = 0;
        for(byte b : byteArray){
            resultCharArray[index++] = hexDigits[b>>> 4 & 0xf];
            resultCharArray[index++] = hexDigits[b& 0xf];
        }

        //字符数组组合成字符串返回
        return new String(resultCharArray);
    }


    /**
     * 对文件进行 MD5 加密
     * @param filePath  需要加密的文件路径
     * @param bufferSize    自定义缓冲区大小
     * @return  加密后的字符
     */
    public static String fileMD5Encode(String filePath,int bufferSize){
        FileInputStream fileInputStream = null;
        DigestInputStream digestInputStream = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            // 使用DigestInputStream

            fileInputStream = new FileInputStream(filePath);

            digestInputStream = new DigestInputStream(fileInputStream,messageDigest);

            // read的过程中进行MD5处理，直到读完文件

            byte[] buffer =new byte[bufferSize];

            while (digestInputStream.read(buffer) > 0);

            // 获取最终的MessageDigest

            messageDigest= digestInputStream.getMessageDigest();

            byte[] resultByteArray = messageDigest.digest();

            // 把字节数组转换成字符串
            return byteArrayToHex(resultByteArray);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally {
            try {
                digestInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
                try {
                    fileInputStream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }


//    /**
//     * 对字符串进行 BASE64 编码
//     * @param source
//     * @return
//     */
//    public static String Base64Encode(String source){
//        if(source == null)
//            return "";
//        return Base64.encodeToString(source.getBytes(),Base64.DEFAULT);
//    }
//
//
//    /**
//     * 对 base64 编码后的字符串进行解码
//     * @return
//     */
//    public static String Base64Decode(String base64Str){
//        if(base64Str == null)
//            return "";
//        return new String(Base64.decode(base64Str.getBytes(),Base64.DEFAULT));
//    }

    /**
     * 将图片转换成bitmap并编码
     * @param path
     * @return
     */
    private String encode(String path) {
        //decode to bitmap
        Bitmap bitmap = BitmapFactory.decodeFile(path);
//        Log.d(TAG, "bitmap width: " + bitmap.getWidth() + " height: " + bitmap.getHeight());
        //convert to byte array
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes = baos.toByteArray();

        //base64 encode
        byte[] encode = Base64.encode(bytes,Base64.DEFAULT);
        String encodeString = new String(encode);
        return encodeString;
    }

    /**
     * 解码成图片
     * @param encodeString
     * @return
     */
    public Bitmap decode(String encodeString) {
        byte[] decode = Base64.decode(encodeString,Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(decode, 0, decode.length);

        return bitmap;
    }

    /**
     * save to image on sdcard
     * @param bitmap
     * @param path
     */
    private void saveBitmap(Bitmap bitmap,String path) {
        try {
//            Log.d("linc","path is "+path);
            OutputStream stream = new FileOutputStream(path);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream);
            stream.close();
//            Log.e("linc","jpg okay!");
        } catch (IOException e) {
            e.printStackTrace();
//            Log.e("linc","failed: "+e.getMessage());
        }
    }

    /**
     * 对字符串进行SHA1加密
     * @param str
     * @return
     */
    public static String encryptSha1(String str){
        if(str==null||str.length()==0){
            return null;
        }
        char hexDigits[] = {'0','1','2','3','4','5','6','7','8','9',
                'a','b','c','d','e','f'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(str.getBytes("UTF-8"));

            byte[] md = mdTemp.digest();
            int j = md.length;
            char buf[] = new char[j*2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(buf);
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }
    }


    /**
     * 将字节数组进行 SHA1 加密
     * @param data
     * @return
     */
    public static byte[] encryptSha1(byte[] data){
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(data);
            return mdTemp.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 针对字符串进行 AES 加密
     * @param key   约定的秘钥
     * @param text  需要加密的字符串
     * @return
     */
    public static String encryptAes(String key, String text) {
        if (TextUtils.isEmpty(text)) {
            return text;
        }
        try {
            byte[] result = encryptAes(key, text.getBytes());
            return parseByte2HexStr(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 针对字节数组进行 AES 加密
     * @param key
     * @param text
     * @return
     */
    public static byte[] encryptAes(String key, byte[] text) {
        byte[] raw = new byte[0];
        try {
            raw = getRawKey(key.getBytes());
            SecretKeySpec skeySpec = new SecretKeySpec(raw, AES);
            Cipher cipher = Cipher.getInstance(CIPHERMODE);
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, new IvParameterSpec(new byte[cipher.getBlockSize()]));
            byte[] encrypted = cipher.doFinal(text);
            return encrypted;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 解密 AES 串
     * @param key
     * @param encrypted
     * @return
     */
    public static String decryptAes(String key, String encrypted) {
        if (TextUtils.isEmpty(encrypted)) {
            return encrypted;
        }
        try {
            byte[] enc = parseHexStr2Byte(encrypted);
            byte[] result = decryptAes(key, enc);
            return new String(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密
     */
    public static byte[] decryptAes(String key, byte[] encrypted) throws Exception {
        byte[] raw = getRawKey(key.getBytes());
        SecretKeySpec skeySpec = new SecretKeySpec(raw, AES);
        Cipher cipher = Cipher.getInstance(CIPHERMODE);
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, new IvParameterSpec(new byte[cipher.getBlockSize()]));
        byte[] decrypted = cipher.doFinal(encrypted);
        return decrypted;
    }

    /**
     * 生成随机数，可以当做动态的密钥
     * 加密和解密的密钥必须一致，不然将不能解密
     */
    public static String generateKey() {
        try {
            SecureRandom secureRandom = SecureRandom.getInstance(SHA1PRNG);
            byte[] key = new byte[20];
            secureRandom.nextBytes(key);
            return toHex(key);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 对密钥进行处理
     */
    public static byte[] getRawKey(byte[] seed) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance(AES);
        //for android
        SecureRandom sr = null;
        // 在4.2以上版本中，SecureRandom获取方式发生了改变
        if (android.os.Build.VERSION.SDK_INT >= 17) {
            sr = SecureRandom.getInstance(SHA1PRNG, "Crypto");
        } else {
            sr = SecureRandom.getInstance(SHA1PRNG);
        }
        // for Java
        // secureRandom = SecureRandom.getInstance(SHA1PRNG);
        sr.setSeed(seed);
        kgen.init(128, sr); //256 bits or 128 bits,192bits
        //AES中128位密钥版本有10个加密循环，192比特密钥版本有12个加密循环，256比特密钥版本则有14个加密循环。
        SecretKey skey = kgen.generateKey();
        byte[] raw = skey.getEncoded();
        return raw;
    }

    /**
     * 二进制转字符
     */
    public static String toHex(byte[] buf) {
        if (buf == null)
            return "";
        StringBuffer result = new StringBuffer(2 * buf.length);
        for (int i = 0; i < buf.length; i++) {
            appendHex(result, buf[i]);
        }
        return result.toString();
    }

    private static void appendHex(StringBuffer sb, byte b) {
        sb.append(IV.charAt((b >> 4) & 0x0f)).append(IV.charAt(b & 0x0f));
    }

    /**
     * 将二进制转换成16进制
     *
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将16进制转换为二进制
     *
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2),
                    16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

}
