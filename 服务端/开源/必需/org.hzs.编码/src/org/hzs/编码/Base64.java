package org.hzs.编码;

public class Base64 {

    private final static java.util.Base64.Decoder base64解码 = java.util.Base64.getDecoder();
    private final static java.util.Base64.Encoder base64编码 = java.util.Base64.getEncoder();

    public static byte[] i编码_byteArray(final byte[] ci待编码_BASE64byteArray) {
        return base64编码.encode(ci待编码_BASE64byteArray);
    }

    public static byte[] i解码_byteArray(final byte[] ci待解码_BASE79byteArray) {
        return base64解码.decode(ci待解码_BASE79byteArray);
    }
}
