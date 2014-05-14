package org.hzs.server;

public class Property {

    public static java.util.concurrent.ExecutorService d线程池 = null;
    public static org.hzs.安全.RSA rsa = null;
    public static byte i集群号_byte;
    public static int i中心端口_i = 8080;
    public static byte[] i數據密钥_byteArray = null, i公钥_byteArray = null;
    public static String i數據库连接_s = null, i數據库用户_s = null, i數據库口令_s = null;
    public static final java.util.Random d不确定數 = new java.util.Random();

    public static void init(final int ci第二层负载个數_i) throws java.security.NoSuchAlgorithmException, java.security.spec.InvalidKeySpecException, java.io.UnsupportedEncodingException {
        int ji最少线程數量_i = 5 + 8 * ci第二层负载个數_i;//线程数量：第一层负载2个、第二层负载6个（还需再预留2个空线程）、公共的2个、数据库1个
        int ji线程數量_i = Runtime.getRuntime().availableProcessors() * 2;//根据硬件线程數计算线程池的线程數
        if (ji线程數量_i <= ji最少线程數量_i) {
            ji线程數量_i = ji最少线程數量_i;
        }
        d线程池 = java.util.concurrent.Executors.newFixedThreadPool(ji线程數量_i);
        rsa = new org.hzs.安全.RSA(5);
        rsa.g生成();
    }
}
