package org.hzs.安全;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public final class RSA {

    private int 密钥长度_i = 512;
    private final int 密文最长度_i;
    private final int 明文最长度_i;
    private static final String 算法_s = "RSA";
    private java.security.PrivateKey i私钥 = null;
    private java.security.PublicKey i公钥 = null;

//    public RSA() {
//        密文最长度_i = 密钥长度_i / 8;
//        明文最长度_i = 密文最长度_i - 11;
//    }

    /**
     * @param ci密钥长度_i 密钥长度=ci密钥长度_i*512
     */
    public RSA(final int ci密钥长度_i) {
        密钥长度_i = ci密钥长度_i * 512;
        密文最长度_i = 密钥长度_i / 8;
        明文最长度_i = 密文最长度_i - 11;
    }

    /**
     * *
     * 生成密钥对
     *
     * @throws NoSuchAlgorithmException 指定的算法无效
     */
    public void g生成() throws java.security.NoSuchAlgorithmException {
        java.security.KeyPairGenerator keyPairGen = null;
        java.security.KeyPair jd密钥对 = null;
        try {
            keyPairGen = java.security.KeyPairGenerator.getInstance(算法_s);
            keyPairGen.initialize(密钥长度_i, new java.security.SecureRandom());
            jd密钥对 = keyPairGen.genKeyPair();
            i私钥 = jd密钥对.getPrivate();
            i公钥 = jd密钥对.getPublic();
        } finally {
            keyPairGen = null;
            jd密钥对 = null;
        }
    }

    /**
     * <p>
     * 获取公钥 </p>
     *
     * @return 返回公钥数组
     */
    public byte[] i公钥_byteArray() {
        return i公钥.getEncoded();
    }

    /**
     * <p>
     * 获取私钥，仅供服务器用 </p>
     *
     * @return 返回私钥数组
     */
    public byte[] i私钥_byteArray() {
        return i私钥.getEncoded();
    }

    /**
     * <p>
     * 置入私钥,仅供服务器使用 </p>
     *
     * @param ci私钥_byteArray
     * @throws NoSuchAlgorithmException 指定算法无效
     * @throws InvalidKeySpecException
     */
    public void g置私钥(final byte[] ci私钥_byteArray) throws NoSuchAlgorithmException, InvalidKeySpecException {
        java.security.spec.PKCS8EncodedKeySpec PKCS8KeySpec = null;
        java.security.KeyFactory keyFactory = null;
        try {
            PKCS8KeySpec = new java.security.spec.PKCS8EncodedKeySpec(ci私钥_byteArray);
            keyFactory = java.security.KeyFactory.getInstance(算法_s);
            i私钥 = keyFactory.generatePrivate(PKCS8KeySpec);
        } finally {
            PKCS8KeySpec = null;
            keyFactory = null;
        }
    }

    /**
     * <p>
     * 获取公钥 </p>
     *
     * @param ci公钥_byteArray
     * @return 返回公钥对象
     * @throws NoSuchAlgorithmException 指定算法无效
     * @throws InvalidKeySpecException
     */
    public static java.security.PublicKey i公钥(final byte[] ci公钥_byteArray) throws NoSuchAlgorithmException, InvalidKeySpecException {
        java.security.spec.X509EncodedKeySpec x509KeySpec = null;
        java.security.KeyFactory keyFactory = null;
        java.security.PublicKey publicK = null;
        try {
            x509KeySpec = new java.security.spec.X509EncodedKeySpec(ci公钥_byteArray);
            keyFactory = java.security.KeyFactory.getInstance(算法_s);
            publicK = keyFactory.generatePublic(x509KeySpec);
            return publicK;
        } finally {
            x509KeySpec = null;
            keyFactory = null;
            publicK = null;
        }
    }

    /**
     * <p>
     * 置入公钥 </p>
     *
     * @param ci公钥_byteArray
     * @throws NoSuchAlgorithmException 指定算法无效
     * @throws InvalidKeySpecException
     */
    public void g置公钥(final byte[] ci公钥_byteArray) throws NoSuchAlgorithmException, InvalidKeySpecException {
        i公钥 = i公钥(ci公钥_byteArray);
    }

    /**
     * <p>
     * 公钥加密 </p>
     *
     * @param cd待加密數據_InputStream
     * @param cd加密後數據_OutputStream
     * @param ci公钥
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IOException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    private void g用公钥加密(final java.io.InputStream cd待加密數據_InputStream, final java.io.OutputStream cd加密後數據_OutputStream, java.security.PublicKey ci公钥) throws java.security.NoSuchAlgorithmException, javax.crypto.NoSuchPaddingException, java.security.InvalidKeyException, java.io.IOException, javax.crypto.IllegalBlockSizeException, javax.crypto.BadPaddingException {
        int ji待加密數據长度_i;
        java.security.KeyFactory keyFactory = null;
        javax.crypto.Cipher cipher = null;
        byte[] ji待加密數據_byteArray = null, cache = null;
        try {
            ji待加密數據_byteArray = new byte[明文最长度_i];
            keyFactory = java.security.KeyFactory.getInstance(算法_s);
            cipher = javax.crypto.Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, ci公钥);
            // 对数据分段加密
            while ((ji待加密數據长度_i = cd待加密數據_InputStream.read(ji待加密數據_byteArray)) > 0) {
                cache = cipher.doFinal(ji待加密數據_byteArray, 0, ji待加密數據长度_i);
                cd加密後數據_OutputStream.write(cache, 0, cache.length);
            }
        } finally {
            keyFactory = null;
            cipher = null;
            ji待加密數據_byteArray = null;
            cache = null;
        }
    }

    /**
     * <p>
     * 公钥加密 </p>
     *
     * @param ci待加密數據_byteArray 源数据
     * @return 加密後數據（字节组）
     * @throws NoSuchAlgorithmException
     * @throws javax.crypto.NoSuchPaddingException
     * @throws java.security.InvalidKeyException
     * @throws javax.crypto.IllegalBlockSizeException
     * @throws javax.crypto.BadPaddingException
     * @throws java.io.IOException
     */
    public byte[] i用公钥加密_byteArray(final byte[] ci待加密數據_byteArray) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException {
        int offSet = 0, i = 0;
        java.security.KeyFactory keyFactory = null;
        javax.crypto.Cipher cipher = null;
        java.io.ByteArrayOutputStream out = null;
        byte[] cache = null, encryptedData = null;
        try {
            keyFactory = java.security.KeyFactory.getInstance(算法_s);
            cipher = javax.crypto.Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, i公钥);
            out = new java.io.ByteArrayOutputStream();
            // 对数据分段加密
            while (ci待加密數據_byteArray.length - offSet > 0) {
                if (ci待加密數據_byteArray.length - offSet > 明文最长度_i) {
                    cache = cipher.doFinal(ci待加密數據_byteArray, offSet, 明文最长度_i);
                } else {
                    cache = cipher.doFinal(ci待加密數據_byteArray, offSet, ci待加密數據_byteArray.length - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * 明文最长度_i;
            }
            encryptedData = out.toByteArray();
            out.close();
            return encryptedData;
        } finally {
            keyFactory = null;
            cipher = null;
            out = null;
            cache = null;
            encryptedData = null;
        }
    }

    /**
     * <p>
     * 公钥加密 </p>
     *
     * @param ci待加密數據_byteArray 源数据
     * @param ci公钥
     * @return 加密後數據（字节组）
     * @throws NoSuchAlgorithmException
     * @throws javax.crypto.NoSuchPaddingException
     * @throws java.security.InvalidKeyException
     * @throws javax.crypto.IllegalBlockSizeException
     * @throws javax.crypto.BadPaddingException
     * @throws java.io.IOException
     */
    public byte[] i用公钥加密_byteArray(final byte[] ci待加密數據_byteArray, final java.security.PublicKey ci公钥) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException {
        int offSet = 0, i = 0;
        java.security.KeyFactory keyFactory = null;
        javax.crypto.Cipher cipher = null;
        java.io.ByteArrayOutputStream out = null;
        byte[] cache = null, encryptedData = null;
        try {
            keyFactory = java.security.KeyFactory.getInstance(算法_s);
            cipher = javax.crypto.Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, ci公钥);
            out = new java.io.ByteArrayOutputStream();
            // 对数据分段加密
            while (ci待加密數據_byteArray.length - offSet > 0) {
                if (ci待加密數據_byteArray.length - offSet > 明文最长度_i) {
                    cache = cipher.doFinal(ci待加密數據_byteArray, offSet, 明文最长度_i);
                } else {
                    cache = cipher.doFinal(ci待加密數據_byteArray, offSet, ci待加密數據_byteArray.length - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * 明文最长度_i;
            }
            encryptedData = out.toByteArray();
            out.close();
            return encryptedData;
        } finally {
            keyFactory = null;
            cipher = null;
            out = null;
            cache = null;
            encryptedData = null;
        }
    }

    /**
     * <P>
     * 私钥解密 </p>
     *
     * @param ci待解密數據_byteArray
     * @return 明文（字节组）
     * @throws NoSuchAlgorithmException
     * @throws javax.crypto.NoSuchPaddingException
     * @throws java.security.InvalidKeyException
     * @throws javax.crypto.IllegalBlockSizeException
     * @throws javax.crypto.BadPaddingException
     * @throws java.io.IOException
     */
    public byte[] i用公钥解密_byteArray(final byte[] ci待解密數據_byteArray) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException {
        int offSet = 0, i = 0;
        java.security.KeyFactory keyFactory = null;
        javax.crypto.Cipher cipher = null;
        java.io.ByteArrayOutputStream out = null;
        byte[] cache = null, decryptedData = null;
        try {
            keyFactory = java.security.KeyFactory.getInstance(算法_s);
            cipher = javax.crypto.Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(javax.crypto.Cipher.DECRYPT_MODE, i公钥);
            int inputLen = ci待解密數據_byteArray.length;
            out = new java.io.ByteArrayOutputStream();
            // 对数据分段解密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > 密文最长度_i) {
                    cache = cipher.doFinal(ci待解密數據_byteArray, offSet, 密文最长度_i);
                } else {
                    cache = cipher.doFinal(ci待解密數據_byteArray, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * 密文最长度_i;
            }
            decryptedData = out.toByteArray();
            out.close();
            return decryptedData;
        } finally {
            keyFactory = null;
            cipher = null;
            out = null;
            cache = null;
            decryptedData = null;
        }
    }

    /**
     * <P>
     * 私钥解密 </p>
     *
     * @param ci待解密數據_byteArray
     * @param ci公钥
     * @return 明文（字节组）
     * @throws NoSuchAlgorithmException
     * @throws javax.crypto.NoSuchPaddingException
     * @throws java.security.InvalidKeyException
     * @throws javax.crypto.IllegalBlockSizeException
     * @throws javax.crypto.BadPaddingException
     * @throws java.io.IOException
     */
    public byte[] i用公钥解密_byteArray(final byte[] ci待解密數據_byteArray, final java.security.PublicKey ci公钥) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException {
        int offSet = 0, i = 0;
        java.security.KeyFactory keyFactory = null;
        javax.crypto.Cipher cipher = null;
        java.io.ByteArrayOutputStream out = null;
        byte[] cache = null, decryptedData = null;
        try {
            keyFactory = java.security.KeyFactory.getInstance(算法_s);
            cipher = javax.crypto.Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(javax.crypto.Cipher.DECRYPT_MODE, ci公钥);
            int inputLen = ci待解密數據_byteArray.length;
            out = new java.io.ByteArrayOutputStream();
            // 对数据分段解密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > 密文最长度_i) {
                    cache = cipher.doFinal(ci待解密數據_byteArray, offSet, 密文最长度_i);
                } else {
                    cache = cipher.doFinal(ci待解密數據_byteArray, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * 密文最长度_i;
            }
            decryptedData = out.toByteArray();
            out.close();
            return decryptedData;
        } finally {
            keyFactory = null;
            cipher = null;
            out = null;
            cache = null;
            decryptedData = null;
        }
    }

    /**
     * <p>
     * 公钥加密 </p>
     *
     * @param ci待加密數據_byteArray 源数据
     * @return 加密後數據（字节组）
     * @throws NoSuchAlgorithmException
     * @throws javax.crypto.NoSuchPaddingException
     * @throws java.security.InvalidKeyException
     * @throws javax.crypto.IllegalBlockSizeException
     * @throws javax.crypto.BadPaddingException
     * @throws java.io.IOException
     */
    public byte[] i用私钥加密_byteArray(final byte[] ci待加密數據_byteArray) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException {
        int offSet = 0, i = 0;
        java.security.KeyFactory keyFactory = null;
        javax.crypto.Cipher cipher = null;
        java.io.ByteArrayOutputStream out = null;
        byte[] cache = null, encryptedData = null;
        try {
            keyFactory = java.security.KeyFactory.getInstance(算法_s);
            cipher = javax.crypto.Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, i私钥);
            out = new java.io.ByteArrayOutputStream();
            // 对数据分段加密
            while (ci待加密數據_byteArray.length - offSet > 0) {
                if (ci待加密數據_byteArray.length - offSet > 明文最长度_i) {
                    cache = cipher.doFinal(ci待加密數據_byteArray, offSet, 明文最长度_i);
                } else {
                    cache = cipher.doFinal(ci待加密數據_byteArray, offSet, ci待加密數據_byteArray.length - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * 明文最长度_i;
            }
            encryptedData = out.toByteArray();
            out.close();
            return encryptedData;
        } finally {
            keyFactory = null;
            cipher = null;
            out = null;
            cache = null;
            encryptedData = null;
        }
    }

    private void g用私钥解密(final java.io.InputStream cd待解密數據_InputStream, final java.io.OutputStream cd解密後數據_OutputStream) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IOException, IllegalBlockSizeException, BadPaddingException {
        int ji待解密數據长度_i;
        java.security.KeyFactory keyFactory = null;
        javax.crypto.Cipher cipher = null;
        byte[] ji待解密數據_byteArray = null, cache = null;
        try {
            keyFactory = java.security.KeyFactory.getInstance(算法_s);
            cipher = javax.crypto.Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(javax.crypto.Cipher.DECRYPT_MODE, i私钥);
            ji待解密數據_byteArray = new byte[密文最长度_i];
            // 对数据分段解密
            while ((ji待解密數據长度_i = cd待解密數據_InputStream.read(ji待解密數據_byteArray)) > 0) {
                cache = cipher.doFinal(ji待解密數據_byteArray, 0, ji待解密數據长度_i);
                cd解密後數據_OutputStream.write(cache, 0, cache.length);
            }
        } finally {
            keyFactory = null;
            cipher = null;
            ji待解密數據_byteArray = null;
            cache = null;
        }
    }

    /**
     * <P>
     * 私钥解密 </p>
     *
     * @param ci待解密數據_byteArray
     * @return 明文（字节组）
     * @throws NoSuchAlgorithmException
     * @throws javax.crypto.NoSuchPaddingException
     * @throws java.security.InvalidKeyException
     * @throws javax.crypto.IllegalBlockSizeException
     * @throws javax.crypto.BadPaddingException
     * @throws java.io.IOException
     */
    public byte[] i用私钥解密_byteArray(final byte[] ci待解密數據_byteArray) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException {
        int offSet = 0, i = 0;
        java.security.KeyFactory keyFactory = null;
        javax.crypto.Cipher cipher = null;
        java.io.ByteArrayOutputStream out = null;
        byte[] cache = null, decryptedData = null;
        try {
            keyFactory = java.security.KeyFactory.getInstance(算法_s);
            cipher = javax.crypto.Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(javax.crypto.Cipher.DECRYPT_MODE, i私钥);
            int inputLen = ci待解密數據_byteArray.length;
            out = new java.io.ByteArrayOutputStream();
            // 对数据分段解密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > 密文最长度_i) {
                    cache = cipher.doFinal(ci待解密數據_byteArray, offSet, 密文最长度_i);
                } else {
                    cache = cipher.doFinal(ci待解密數據_byteArray, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * 密文最长度_i;
            }
            decryptedData = out.toByteArray();
            out.close();
            return decryptedData;
        } finally {
            keyFactory = null;
            cipher = null;
            out = null;
            cache = null;
            decryptedData = null;
        }
    }

    private byte[] i用私钥簽名_byteArray(final java.io.InputStream cd待簽名數據_InputStream) throws IOException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        int ji缓冲长度_i;
        byte[] ji缓冲_byteArray = null;
        java.io.ByteArrayOutputStream ji字节流_ByteArrayOutputStream = null;
        try {
            ji字节流_ByteArrayOutputStream = new java.io.ByteArrayOutputStream();
            ji缓冲_byteArray = new byte[密文最长度_i];
            while ((ji缓冲长度_i = cd待簽名數據_InputStream.read(ji缓冲_byteArray)) > 0) {
                ji字节流_ByteArrayOutputStream.write(ji缓冲_byteArray, 0, ji缓冲长度_i);
            }
            return i用私钥簽名_byteArray(ji字节流_ByteArrayOutputStream.toByteArray());
        } finally {
            ji缓冲_byteArray = null;
            ji字节流_ByteArrayOutputStream = null;
        }
    }

    /**
     * <p>
     * 用私钥对信息生成数字签名 </p>
     *
     * @param ci待簽名數據_byteArray
     * @return 簽名（字节组）
     * @throws NoSuchAlgorithmException
     * @throws java.security.InvalidKeyException
     * @throws java.security.SignatureException
     */
    public byte[] i用私钥簽名_byteArray(final byte[] ci待簽名數據_byteArray) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        java.security.Signature signature = null;
        try {
            signature = java.security.Signature.getInstance("MD5withRSA");
            signature.initSign(i私钥);
            signature.update(ci待簽名數據_byteArray);
            return signature.sign();
        } finally {
            signature = null;
        }
    }

    private boolean i用公钥驗证簽名_b(final java.io.InputStream cd待驗证數據_InputStream, final byte[] ci簽名_byteArray, final java.security.PublicKey ci公钥) throws java.security.NoSuchAlgorithmException, java.security.InvalidKeyException, java.security.SignatureException, java.io.IOException {
        int ji缓冲长度_i;
        byte[] ji缓冲_byteArray = null;
        java.io.ByteArrayOutputStream ji字节流_ByteArrayOutputStream = null;
        try {
            ji字节流_ByteArrayOutputStream = new java.io.ByteArrayOutputStream();
            ji缓冲_byteArray = new byte[密文最长度_i];
            while ((ji缓冲长度_i = cd待驗证數據_InputStream.read(ji缓冲_byteArray)) > 0) {
                ji字节流_ByteArrayOutputStream.write(ji缓冲_byteArray, 0, ji缓冲长度_i);
            }
            return i用公钥驗证簽名_b(ji字节流_ByteArrayOutputStream.toByteArray(), ci簽名_byteArray, ci公钥);
        } finally {
            ji缓冲_byteArray = null;
            ji字节流_ByteArrayOutputStream = null;
        }
    }

    /**
     * @param ci待驗证數據_byteArray
     * @param ci簽名_byteArray
     * @param ci公钥
     * @return true 數據与签名符合；false 數據与签名不符
     * @throws java.security.NoSuchAlgorithmException
     * @throws java.security.InvalidKeyException
     * @throws java.security.SignatureException
     */
    public static boolean i用公钥驗证簽名_b(final byte[] ci待驗证數據_byteArray, final byte[] ci簽名_byteArray, final java.security.PublicKey ci公钥) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        java.security.Signature signetcheck = null;
        try {
            signetcheck = java.security.Signature.getInstance("MD5withRSA");
            signetcheck.initVerify(ci公钥);
            signetcheck.update(ci待驗证數據_byteArray);
            return signetcheck.verify(ci簽名_byteArray);
        } finally {
            signetcheck = null;
        }
    }
}
