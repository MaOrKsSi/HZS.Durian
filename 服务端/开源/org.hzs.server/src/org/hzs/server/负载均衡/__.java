/**
 * 保留线程：0<BR>
 */
package org.hzs.server.负载均衡;

import java.io.IOException;
import java.net.SocketException;

public abstract class __ {

    protected static volatile java.util.TreeMap<Integer, org.hzs.server.负载均衡.Session> session_集合 = new java.util.TreeMap<>();
    protected static org.hzs.server.业务.__ d业务 = null;
    protected static int i中心端口_i = org.hzs.server.负载均衡.Property.i中心端口_i;
    protected static volatile int i内总权重_i = 0, i外总权重_i = 0;
    protected static int i权重_i = 0;
    protected static String i内网IP_s = null;
    protected static volatile String i公网IP_s = null;
    protected static volatile java.util.TreeMap<String, Integer> i外服务器列表 = null;//<IP,权重>
    protected static volatile java.util.TreeMap<String, Integer> i内服务器列表 = null;//<IP,权重>
    protected static final java.util.LinkedList<String> i集群内服务器列表 = new java.util.LinkedList<>();

    public static void init() throws SocketException, IOException {
        //希望有人提供好的对cpu测速方法，要求能够抵御优化，不能被优化掉哦
        init1();
        if (org.hzs.server.负载均衡.Property.i负载服务_b) {
            init2();
        }

        int ji硬线程数量_i = Runtime.getRuntime().availableProcessors();
        int ji剩余内存_i = (int) (Runtime.getRuntime().freeMemory() / 1024 / 1024);
        i权重_i = ji硬线程数量_i * ji剩余内存_i;
        权重.init_();
        均衡.init_();
    }

    private static void init1() throws SocketException {
        java.util.Enumeration<java.net.NetworkInterface> mEnumeration = null;
        try {
            mEnumeration = java.net.NetworkInterface.getNetworkInterfaces();
            while (mEnumeration.hasMoreElements()) {
                java.net.NetworkInterface intf = mEnumeration.nextElement();
                for (java.util.Enumeration<java.net.InetAddress> enumIPAddr = intf.getInetAddresses(); enumIPAddr.hasMoreElements();) {
                    java.net.InetAddress inetAddress = enumIPAddr.nextElement();
                    // 如果不是回环地址  
                    if (!inetAddress.isLoopbackAddress()) {
                        // 直接返回本地IP地址  
                        i内网IP_s = inetAddress.getHostAddress();
                        break;
                    }
                }
            }
            //
            int ji1_i = org.hzs.server.负载均衡.Property.i集群内服务器列表_ArrayJSON.size();
            for (int ji2_i = 0; ji2_i < ji1_i; ji2_i++) {
                i集群内服务器列表.push(org.hzs.server.负载均衡.Property.i集群内服务器列表_ArrayJSON.getString(ji2_i));
            }
            if (i集群内服务器列表.size() > 0) {
                if (i内网IP_s == null) {
                    i内网IP_s = "127.0.0.1";
                }
            }
        } finally {
            mEnumeration = null;
        }
    }

    private static void init2() throws SocketException {
        java.util.Enumeration<java.net.NetworkInterface> netInterfaces = null;
        java.net.InetAddress ip = null;
        java.util.Enumeration<java.net.InetAddress> address = null;
        try {
            i外服务器列表 = new java.util.TreeMap<String, Integer>();//<IP,权重>
            i内服务器列表 = new java.util.TreeMap<String, Integer>();//<IP,权重>
            netInterfaces = java.net.NetworkInterface.getNetworkInterfaces();
            while (netInterfaces.hasMoreElements()) {
                java.net.NetworkInterface ni = netInterfaces.nextElement();
                address = ni.getInetAddresses();
                while (address.hasMoreElements()) {
                    ip = address.nextElement();
                    if (!ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && !ip.getHostAddress().contains(":")) {// 外网IP
                        i公网IP_s = ip.getHostAddress();
                        return;
                    }
                }
            }
            if (i公网IP_s == null) {
                i公网IP_s = i内网IP_s;
            }
        } finally {
            netInterfaces = null;
            ip = null;
            address = null;
        }
    }

}
