package org.hzs.安全;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wchun
 *
 * AES128 算法，加密模式为ECB，填充模式为 pkcs7（实际就是pkcs5）
 *
 *
 */
public class AES {

    private static final String i加密模式_s = "AES/ECB/PKCS5Padding";
//    public static final String i加密算法_s = "AES";
//    public static final int i密钥长度_i = 128;//二进制位数
    private static javax.crypto.Cipher d加密容器 = null;
//    static boolean isInited = false;

//    //初始化
//    private static void init() {
////        //初始化keyGen
////        try {
////            keyGen = KeyGenerator.getInstance("AES");
////        } catch (NoSuchAlgorithmException e) {
////            // TODO Auto-generated catch block
////            
////        }
////        keyGen.init(i密钥长度_i);
//
//        //初始化cipher
//        try {
//            d加密容器 = Cipher.getInstance(i加密模式_s);
//        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
//            // TODO Auto-generated catch block
//
//        }
//
//        isInited = true;
//    }
//    public static byte[] GenKey() {
//        if (!isInited)//如果没有初始化过,则初始化
//        {
//            init();
//        }
//        return keyGen.generateKey().getEncoded();
//    }
    public static byte[] i加密_byteArray(final byte[] content, final java.security.Key key) {
        byte[] encryptedText = null;
        try {
            //初始化
            if (d加密容器 == null) {
                d加密容器 = javax.crypto.Cipher.getInstance(i加密模式_s);
            }
//        Key key = new SecretKeySpec(keyBytes, i加密算法_s);
            d加密容器.init(javax.crypto.Cipher.ENCRYPT_MODE, key);
            encryptedText = d加密容器.doFinal(content);
            return encryptedText;
        } catch (java.security.NoSuchAlgorithmException | javax.crypto.NoSuchPaddingException | java.security.InvalidKeyException | javax.crypto.IllegalBlockSizeException | javax.crypto.BadPaddingException ex) {
            Logger.getLogger(AES.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            encryptedText = null;
        }
    }

    //解密为byte[]
    public static byte[] i解密_byteArray(final byte[] content, final java.security.Key key) {
        byte[] originBytes = null;
        try {
            //初始化
            if (d加密容器 == null) {
                d加密容器 = javax.crypto.Cipher.getInstance(i加密模式_s);
            }
//            Key key = new SecretKeySpec(keyBytes, i加密算法_s);
            d加密容器.init(javax.crypto.Cipher.DECRYPT_MODE, key);
            originBytes = d加密容器.doFinal(content);
            return originBytes;
        } catch (java.security.NoSuchAlgorithmException | javax.crypto.NoSuchPaddingException | java.security.InvalidKeyException | javax.crypto.IllegalBlockSizeException | javax.crypto.BadPaddingException ex) {
            Logger.getLogger(AES.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            originBytes = null;
        }
    }
}
