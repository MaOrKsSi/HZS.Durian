package org.hzs.安全;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DIGEST {

    public static String SHA_1 = "SHA-1", SHA_256 = "SHA-256", SHA_512 = "SHA-512", MD2 = "MD2", MD5 = "MD5";

    /**
     * @param ci生成方式方式_s SHA-1、SHA-256、SHA-512、MD2、MD5
     */
    public static byte[] i摘要_byteArray(byte[] ci_byteArray, String ci生成方式方式_s) throws NoSuchAlgorithmException {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance(ci生成方式方式_s);
            md.update(ci_byteArray);
            return md.digest();
        } finally {
            md = null;
        }
    }
}
