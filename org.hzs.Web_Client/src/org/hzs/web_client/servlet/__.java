package org.hzs.web_client.servlet;

import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class __ {

//    private static final org.hzs.安全.RSA rsa = new org.hzs.安全.RSA(5);
//    private static java.security.PublicKey i公钥 = null;
    protected java.util.TreeMap<String, byte[]> d资源缓冲 = null;
    public String i本地服务网址_s = null;

    public __(final int ci端口_i, final org.hzs.logging.error ci_error) throws java.security.NoSuchAlgorithmException, java.security.spec.InvalidKeySpecException, java.io.UnsupportedEncodingException {
//        if (i公钥 == null) {
//            i公钥 = org.hzs.安全.RSA.i公钥(org.hzs.编码.Base64.i解码_byteArray("MIIBYjANBgkqhkiG9w0BAQEFAAOCAU8AMIIBSgKCAUEAjs8nSOSz/Cd38p9Skn4HOdeboqz3C9Q7gRG/gXLwA4nX3GQeG9kkAbmXc03Ly47xRSnA3sqGpbRMgq+EdAH0KbUyfRUMJhSt4dMCPUSF+WvvCl7cqr+lM+gwnC6Z6U6ntPEga1DMj4PLfiG5GF8YYddcZHJ7KwAIN8YOH8g5ZIWHa2LTX/huNtLDs7WjxIny7vgdODPmLzp8B/iZPEBwt7RP2CjZUOjq6AL1bPgIK7mSOfQH7WB+cTPFPxoREpom6lVBjFGoqJtEQnczBs+96lfqv6UJbUv+IIWEqb9Z+qFARx0H2it0YiNFFoUlcUcazlgpvoW+/vxcJrDPnOckIdAMhpnwT9nFo2x/xTXzqzvSY2KyHV7K7g4yES8THxFnaYwFj1qoKq0i6XoXNCsRQ5tE7qvy1ONEVDw4NZPd9GsCAwEAAQ==".getBytes("UTF-8")));
//        }
        //建立服务器ip
        if (ci端口_i == 80) {
            i本地服务网址_s = "http://127.0.0.1/";
        } else {
            i本地服务网址_s = "http://127.0.0.1:" + ci端口_i + "/";
        }
    }

    protected void g缓冲资源(final String ci文档_s) {
        // <editor-fold defaultstate="collapsed" desc="自用">
        class 自用 {

            java.io.File d文档 = null;
            java.io.FileInputStream in1 = null;
            java.io.ObjectInputStream oin = null;
            java.util.TreeMap<String, byte[]> d资源缓冲 = null;
            byte[] i_byteArray = null;

            void close() {
                if (oin != null) {
                    try {
                        oin.close();
                    } catch (java.io.IOException ex) {
                    }
                }
                d资源缓冲 = null;
                d文档 = null;
            }
        }// </editor-fold>
        自用 ji自用 = null;
        try {
            ji自用 = new 自用();
            //构建缓冲
            ji自用.d文档 = new java.io.File(org.hzs.web_client.Property.i工作路径_s + ci文档_s);
            ji自用.i_byteArray = new byte[(int) ji自用.d文档.length()];
            ji自用.in1 = new java.io.FileInputStream(ji自用.d文档);
            ji自用.in1.read(ji自用.i_byteArray);
            ji自用.i_byteArray = org.hzs.压缩解压.Gzip.i解压_byteArray(ji自用.i_byteArray);
            ji自用.oin = new java.io.ObjectInputStream(new java.io.ByteArrayInputStream(ji自用.i_byteArray));
            ji自用.d资源缓冲 = (java.util.TreeMap<String, byte[]>) ji自用.oin.readObject(); // 没有强制转换到Person类型
            for (String ji键_s : ji自用.d资源缓冲.keySet()) {
                d资源缓冲.put(ji键_s, ji自用.d资源缓冲.get(ji键_s));
            }
        } catch (java.io.IOException | ClassNotFoundException ex) {
            Logger.getLogger(__.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (ji自用 != null) {
                ji自用.close();
                ji自用 = null;
            }
        }
    }
}
