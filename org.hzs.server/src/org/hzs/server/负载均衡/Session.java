package org.hzs.server.负载均衡;

public class Session {

    public final static long 时限_l = 60_000;//在规定时间段内未进行会晤，则认为客户端已经断开，清除所有相关资源。默认1′
    public java.security.Key AES_Key = null;
    public long i最近使用时间_l = Long.MAX_VALUE;
    public String iIP_s = null;
    public boolean i未用_b = true, i正在服务_b = false;
    public int i访问频率_i = 0;//访问频率用来控制客户端的恶意访问。比如客户自己写一个软件自动访问，进而窃取公司数据。访问频率的限制能降低数据泄密速度，但不能禁止

    public void close() {
        iIP_s = null;
        AES_Key = null;
    }
}
