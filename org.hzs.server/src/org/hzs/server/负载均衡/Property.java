package org.hzs.server.负载均衡;

public class Property {

    public static java.util.concurrent.ExecutorService d线程池 = null;
    public static org.hzs.安全.RSA rsa = null;
    public static byte i集群号_byte;
    public static int i中心端口_i = 8080;
    public static byte[] i公钥_byteArray = null;
    public static int i频率上限_i = 36;//访问频率用来控制客户端的恶意访问。比如客户自己写一个软件自动访问，进而窃取公司数据。访问频率的限制能降低数据泄密速度，但不能禁止。默认36次/5″
    public static int i接收缓冲区容量_i = 10 * 1024, i发送缓冲区容量_i = 5 * 1024 * 1024;//接收默认10K，发送默认5M。
    public static boolean i负载服务_b = true, i业务服务_b = true;
    public static org.hzs.json.JSONArray i集群内服务器列表_ArrayJSON = null;

    public static void init(final int ci均衡负载个數_i) throws java.security.NoSuchAlgorithmException, java.security.spec.InvalidKeySpecException, java.io.UnsupportedEncodingException {
        /*线程数量：
         权重：静态1个
         均衡：静态2个、动态3个、预留业务处理1个*/
        int ji最少线程數量_i = 3 + ci均衡负载个數_i * 4;
        int ji线程數量_i = Runtime.getRuntime().availableProcessors();//根据硬件线程數计算线程池的线程數
        if (ji线程數量_i <= ji最少线程數量_i) {
            ji线程數量_i = ji最少线程數量_i;
        }
        d线程池 = java.util.concurrent.Executors.newFixedThreadPool(ji线程數量_i);
        rsa = new org.hzs.安全.RSA(5);
        rsa.g生成();
        i公钥_byteArray = rsa.i公钥_byteArray();
    }
}
