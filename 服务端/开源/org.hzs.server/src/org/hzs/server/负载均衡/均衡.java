/**
 * 每个客户端只允许串行访问、
 * 保留线程：5<BR>
 * 8080：第一层负载对外端口 8079：负载均衡端口 8081：开始业务端口 8080 - 偏移 - 1：内部业务端口 8080 + 偏移 + 1：对外业务端口 线程1负责：1、对客户的请求作出响应；2、将请求交给压力最小的服务器处理。为客户提供服务。直接对外<BR>
 * 线程2负责对其他节点或本节点的请求，提供访问权限：session1。也就是对第一层负载提供rsa公钥，客户可持公钥到本服务器申请服务。与第一层的线程3对话<BR>
 * 线程3负责接收其他服务器传过来的请求，并将处理结果传回给对方。与线程1对话 功能：负责将客户端的接引平均分配到各服务器<BR>
 * 线程负责响应客户请求，向指定服务器递交客户端公钥并取得服务器公钥，并转交给客户：公钥、IP。与第二层负载的线程2对话。
 */
package org.hzs.server.负载均衡;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import org.hzs.logging.error;

public class 均衡 extends __ {

    private int i内部端口_i, i外部端口_i;
    private static int i业务处理端口_i;

    protected static void init() throws SocketException {
        if (i公网IP_s == null) {
            return;
        }
        i业务处理端口_i = i中心端口_i + 1;
        org.hzs.server.Property.d线程池.execute(new 请求开始业务());
    }

    public 均衡(final int ci端口偏移_i, final org.hzs.server.业务.__ cd业务) throws SocketException {
        if (!i业务服务_b) {
            return;
        }
        d业务 = cd业务;
        i内部端口_i = i中心端口_i - ci端口偏移_i - 1;
        i外部端口_i = i中心端口_i + ci端口偏移_i + 1;
        org.hzs.server.Property.d线程池.execute(new 监聽对内业务());
        if (i公网IP_s != null) {
            org.hzs.server.Property.d线程池.execute(new 监聽对外业务());
            org.hzs.server.Property.d线程池.execute(new 监聽请求开始业务());
        }
    }

    private class 监聽对外业务 implements Runnable {

        @Override
        public void run() {
            java.net.ServerSocket d通信服务 = null;
            Runnable_ jd通信 = null;
            for (;;) {
                try {
                    d通信服务 = new java.net.ServerSocket(i外部端口_i);//注意监听端口
                    for (;;) {
                        // <editor-fold defaultstate="collapsed" desc="利用对象复制初始化过程内部变量">
                        jd通信 = (Runnable_) org.hzs.常用.d对象池.get(Runnable_.class.getName());
                        if (jd通信 == null) {
                            jd通信 = new Runnable_();
                            org.hzs.常用.d对象池.put(Runnable_.class.getName(), jd通信);
                        }
                        jd通信 = jd通信.d副本(d通信服务.accept());
                        // </editor-fold>
                        org.hzs.server.Property.d线程池.execute(jd通信);
                    }
                } catch (java.io.IOException | CloneNotSupportedException ex) {
                    Logger.getLogger(均衡.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    if (d通信服务 != null) {
                        try {
                            d通信服务.close();
                        } catch (java.io.IOException ex) {
                        }
                        d通信服务 = null;
                    }
                }
            }
        }

        private class Runnable_ implements Runnable, Cloneable {

            private java.net.Socket d通信链 = null;

            @Override
            public void run() {
                // <editor-fold defaultstate="collapsed" desc="自用">
                class 自用 implements Cloneable {

                    byte[] i缓冲_byteArray = new byte[1024], session_byteArray = null, i_byteArray = null;
                    int sessionid_i;
                    org.hzs.server.Session session = null;
                    org.hzs.json.JSONObject i_JSON = null;
                    java.util.Calendar i当前时间 = null;

                    自用 d副本() throws CloneNotSupportedException {
                        自用 jd = (自用) super.clone();
                        jd.i缓冲_byteArray = jd.i缓冲_byteArray.clone();
                        return jd;
                    }

                    void close() {
                        i当前时间 = null;
                        i缓冲_byteArray = null;
                        session_byteArray = null;
                        i_byteArray = null;
                        session = null;
                    }
                }// </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="会晤">
                class 会晤 implements Cloneable {

                    byte[] i_byteArray = null;
                    java.io.InputStream d读入 = null;
                    java.io.OutputStream d写出 = null;
                    java.net.Socket d通信链 = null;
                    java.net.InetAddress address = null;

                    会晤 d副本() throws CloneNotSupportedException {
                        return (会晤) super.clone();
                    }

                    void close() {
                        address = null;
                        i_byteArray = null;
                        if (d读入 != null) {
                            try {
                                d读入.close();
                            } catch (IOException ex) {
                            }
                            d读入 = null;
                        }
                        if (d写出 != null) {
                            try {
                                d写出.close();
                            } catch (IOException ex) {
                            }
                            d写出 = null;
                        }
                    }
                }// </editor-fold>
                自用 ji自用 = null;
                会晤 ji会晤 = null;
                org.hzs.logging.error ji_error = null;
                boolean ji未捕获错误_b = true;
                try {
                    // <editor-fold defaultstate="collapsed" desc="利用对象复制初始化过程内部变量">
                    ji自用 = (自用) org.hzs.常用.d对象池.get(自用.class.getName());
                    if (ji自用 == null) {
                        ji自用 = new 自用();
                        org.hzs.常用.d对象池.put(自用.class.getName(), ji自用);
                        ji会晤 = new 会晤();
                        org.hzs.常用.d对象池.put(会晤.class.getName(), ji会晤);
                    } else {
                        ji会晤 = (会晤) org.hzs.常用.d对象池.get(会晤.class.getName());
                    }
                    ji自用 = ji自用.d副本();
                    ji会晤 = ji会晤.d副本();
                    ji_error = org.hzs.logging.error.d副本();
                    // </editor-fold>
                    d通信链.setSoTimeout(3_000);//设置最大连接时长为3000毫秒分钟，防止长连接攻击
                    //判断软件使用权是否到期
                    ji自用.i当前时间 = java.util.Calendar.getInstance();
                    if (org.hzs.server.Property.i软件到期时间_L != null && org.hzs.server.Property.i软件到期时间_L.compareTo(ji自用.i当前时间.getTimeInMillis() - ji自用.i当前时间.getTimeZone().getRawOffset()) < 0) {
                        d通信链.shutdownInput();
                        ji会晤.d写出 = d通信链.getOutputStream();
                        ji会晤.d写出.write("{success:false,_:'软件使用权到期，请续费！'}".getBytes("UTF-8"));
                        ji未捕获错误_b = false;
                        return;
                    }
                    ji会晤.d读入 = d通信链.getInputStream();
                    //获取外连接数据
                    ji自用.i缓冲_byteArray = new byte[1024];
                    ji会晤.i_byteArray = new byte[0];
                    int ji_i;
                    while ((ji_i = ji会晤.d读入.read(ji自用.i缓冲_byteArray)) > 0) {
                        ji会晤.i_byteArray = java.util.Arrays.copyOf(ji会晤.i_byteArray, ji会晤.i_byteArray.length + ji_i);
                        System.arraycopy(ji自用.i缓冲_byteArray, 0, ji会晤.i_byteArray, ji会晤.i_byteArray.length - ji_i, ji_i);
                    }
                    if (ji会晤.i_byteArray.length <= 0) {
                        ji未捕获错误_b = false;
                        return;
                    }
                    {//解析出session并传递给业务处理模块
                        //***取会晤号
                        ji自用.i_byteArray = new byte[4];
                        System.arraycopy(ji会晤.i_byteArray, 0, ji自用.i_byteArray, 0, 4);
                        ji自用.sessionid_i = org.hzs.lang.转换.byteArray_2_int(ji自用.i_byteArray);
                        System.arraycopy(ji会晤.i_byteArray, 4, ji会晤.i_byteArray, 0, ji会晤.i_byteArray.length - 4);
                        ji会晤.i_byteArray = java.util.Arrays.copyOf(ji会晤.i_byteArray, ji会晤.i_byteArray.length - 4);
                        //处理内部session
                        ji自用.session = session_集合.get(ji自用.sessionid_i);
                        if (ji自用.session.i正在服务_b) {
                            return;
                        }
                        ji自用.session.i正在服务_b = true;
                        ji会晤.i_byteArray = org.hzs.安全.AES.i解密_byteArray(ji会晤.i_byteArray, ji自用.session.AES_Key);
                        //取得session，
                        ji自用.i_byteArray = d业务.getSession(ji自用.sessionid_i).toString(ji_error).getBytes("UTF-8");//取得业务的session
                        ji自用.i_byteArray = org.hzs.压缩解压.Gzip.i压缩_byteArray(ji自用.i_byteArray);
                        ji自用.session_byteArray = org.hzs.lang.转换.short_2_byteArray((short) ji自用.i_byteArray.length);
                        ji自用.session_byteArray = java.util.Arrays.copyOf(ji自用.session_byteArray, ji自用.i_byteArray.length + 2);
                        System.arraycopy(ji自用.i_byteArray, 0, ji自用.session_byteArray, 2, ji自用.i_byteArray.length);
                        //拼接session与客户数据
                        ji会晤.i_byteArray = java.util.Arrays.copyOf(ji会晤.i_byteArray, ji会晤.i_byteArray.length + ji自用.session_byteArray.length);
                        System.arraycopy(ji会晤.i_byteArray, 0, ji会晤.i_byteArray, ji自用.session_byteArray.length, ji会晤.i_byteArray.length - ji自用.session_byteArray.length);
                        System.arraycopy(ji自用.session_byteArray, 0, ji会晤.i_byteArray, 0, ji自用.session_byteArray.length);

                    }
                    //随机选择业务服务器，不仅能降低网路压力，而且确保每个业务节点的压力相仿。
                    for (;;) {
                        String ji键_s = null;
                        int ji权重_i = org.hzs.server.Property.d不确定數.nextInt(org.hzs.server.负载均衡.__.i内总权重_i);
                        int ji下限权重_i = 0, ji上限权重_i = 0;
                        for (String ji键_sArray : i内服务器列表.keySet()) {
                            ji上限权重_i += i内服务器列表.get(ji键_sArray);
                            if (ji下限权重_i <= ji权重_i && ji上限权重_i >= ji权重_i) {
                                ji键_s = ji键_sArray;
                            }
                        }
                        ji会晤.address = java.net.InetAddress.getByName(ji键_s);//ping this IP 
                        if (!(ji会晤.address instanceof java.net.Inet4Address) && !(ji会晤.address instanceof java.net.Inet6Address)) {//非IP4或IP6，提示错误
                            //若不连通，说明该服务器出现故障或被人为停止，反正不能工作，所以在列表中删除此服务器。
                            org.hzs.server.负载均衡.__.i内总权重_i -= i内服务器列表.get(ji键_s);
                            i内服务器列表.remove(ji键_s);
                            continue;
                        }
                        //将服务专向寻得的务器；如果不能连通，则移除此服务器，并继续寻工作量最小的服务器
                        try {
                            ji会晤.d通信链 = new java.net.Socket(ji键_s, i内部端口_i);
                        } catch (java.io.IOException ex) {
                            ////若不连通，说明该服务器出现故障或被人为停止，反正不能工作，所以在列表中删除此服务器。
                            org.hzs.server.负载均衡.__.i内总权重_i -= i内服务器列表.get(ji键_s);
                            i内服务器列表.remove(ji键_s);
                            continue;
                        }
                        ////如果出现转交，则访问频率增加，否则不增加，以防非黑客捣乱带来影响。
                        ji自用.session.i最近使用时间_l = ji自用.i当前时间.getTimeInMillis();
                        ji自用.session.i访问频率_i++;
                        ////将客户请求转交
                        ji会晤.d写出 = ji会晤.d通信链.getOutputStream();
                        ji会晤.d写出.write(ji会晤.i_byteArray);
                        ji会晤.d通信链.shutdownOutput();
                        ji会晤.d读入 = ji会晤.d通信链.getInputStream();
                        ji会晤.i_byteArray = new byte[0];
                        while ((ji_i = ji会晤.d读入.read(ji自用.i缓冲_byteArray)) > 0) {
                            ji会晤.i_byteArray = java.util.Arrays.copyOf(ji会晤.i_byteArray, ji会晤.i_byteArray.length + ji_i);
                            System.arraycopy(ji自用.i缓冲_byteArray, 0, ji会晤.i_byteArray, ji会晤.i_byteArray.length - ji_i, ji_i);
                        }
                        ji会晤.d通信链.close();
                        break;
                    }
                    //处理返回结果
                    try {
                        ji自用.i_byteArray = org.hzs.压缩解压.Gzip.i解压_byteArray(ji会晤.i_byteArray);
                        if (ji自用.i_byteArray != null) {
                            ji会晤.i_byteArray = ji自用.i_byteArray;
                        }

                        ji自用.i_JSON = org.hzs.json.JSONObject.d副本();
                        ////回写session
                        ji自用.i_JSON.set(new String(ji会晤.i_byteArray, "UTF-8"), ji_error);
                        d业务.getSession(ji自用.sessionid_i).set(ji自用.i_JSON.getString("$", ji_error), ji_error);
                        //将服务结果交给客户
                        ji会晤.i_byteArray = ji自用.i_JSON.getString("_", ji_error).getBytes("UTF-8");
                        ji自用.i_byteArray = org.hzs.压缩解压.Gzip.i压缩_byteArray(ji会晤.i_byteArray);
                        if (ji自用.i_byteArray.length < ji会晤.i_byteArray.length) {
                            ji会晤.i_byteArray = ji自用.i_byteArray;
                        }
                    } catch (error | UnsupportedEncodingException ex) {
                        Logger.getLogger(均衡.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    ji会晤.i_byteArray = org.hzs.安全.AES.i加密_byteArray(ji会晤.i_byteArray, ji自用.session.AES_Key);
                    ji会晤.d写出 = d通信链.getOutputStream();
                    ji会晤.d写出.write(ji会晤.i_byteArray);
                    ji自用.session.i正在服务_b = false;
                    ji未捕获错误_b = false;
                } catch (IOException | CloneNotSupportedException | error ex) {
                    Logger.getLogger(均衡.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    // <editor-fold defaultstate="collapsed" desc="释放资源">
                    if (ji自用 != null) {
                        ji自用.close();
                        ji自用 = null;
                    }
                    if (d通信链 != null) {
                        if (ji会晤 != null) {
                            ji会晤.close();
                            ji会晤 = null;
                        }
                        try {
                            d通信链.close();
                        } catch (IOException ex) {
                        }
                        d通信链 = null;
                    }
                    // </editor-fold>
                    if (ji未捕获错误_b) {
                    }
                }
            }

            Runnable_ d副本(final java.net.Socket cd通信链) throws CloneNotSupportedException {
                Runnable_ jd = (Runnable_) super.clone();
                jd.d通信链 = cd通信链;
                return jd;
            }
        }
    }

    private class 监聽对内业务 implements Runnable {

        @Override
        public void run() {
            java.net.ServerSocket d通信服务 = null;
            Runnable_ jd通信 = null;
            for (;;) {
                try {
                    d通信服务 = new java.net.ServerSocket(i内部端口_i);//注意监听端口
                    for (;;) {
                        // <editor-fold defaultstate="collapsed" desc="利用对象复制初始化过程内部变量">
                        jd通信 = (Runnable_) org.hzs.常用.d对象池.get(Runnable_.class.getName());
                        if (jd通信 == null) {
                            jd通信 = new Runnable_();
                            org.hzs.常用.d对象池.put(Runnable_.class.getName(), jd通信);
                        }
                        jd通信 = jd通信.d副本(d通信服务.accept());
                        // </editor-fold>
                        org.hzs.server.Property.d线程池.execute(jd通信);
                    }
                } catch (java.io.IOException | CloneNotSupportedException ex) {
                    Logger.getLogger(均衡.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    if (d通信服务 != null) {
                        try {
                            d通信服务.close();
                        } catch (java.io.IOException ex) {
                        }
                        d通信服务 = null;
                    }
                }
            }
        }

        private class Runnable_ implements Runnable, Cloneable {

            private java.net.Socket d通信链 = null;

            @Override
            public void run() {
                // <editor-fold defaultstate="collapsed" desc="自用">
                class 自用 implements Cloneable {

                    byte[] i缓冲_byteArray = new byte[1024], session = null;
                    org.hzs.json.JSONObject i_JSON = null;

                    自用 d副本() throws CloneNotSupportedException {
                        自用 jd = (自用) super.clone();
                        jd.i缓冲_byteArray = jd.i缓冲_byteArray.clone();
                        return jd;
                    }

                    void close() {
                        i缓冲_byteArray = null;
                        session = null;
                    }
                }// </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="会晤">
                class 会晤 implements Cloneable {

                    byte[] i_byteArray = null;
                    java.io.InputStream d读入 = null;
                    java.io.OutputStream d写出 = null;
                    java.net.Socket d通信链 = null;
                    java.net.InetAddress address = null;

                    会晤 d副本() throws CloneNotSupportedException {
                        return (会晤) super.clone();
                    }

                    void close() {
                        address = null;
                        i_byteArray = null;
                        if (d读入 != null) {
                            try {
                                d读入.close();
                            } catch (IOException ex) {
                            }
                            d读入 = null;
                        }
                        if (d写出 != null) {
                            try {
                                d写出.close();
                            } catch (IOException ex) {
                            }
                            d写出 = null;
                        }
                    }
                }// </editor-fold>
                自用 ji自用 = null;
                会晤 ji会晤 = null;
                try {
                    // <editor-fold defaultstate="collapsed" desc="利用对象复制初始化过程内部变量">
                    ji自用 = (自用) org.hzs.常用.d对象池.get(自用.class.getName());
                    if (ji自用 == null) {
                        ji自用 = new 自用();
                        org.hzs.常用.d对象池.put(自用.class.getName(), ji自用);
                        ji会晤 = new 会晤();
                        org.hzs.常用.d对象池.put(会晤.class.getName(), ji会晤);
                    } else {
                        ji会晤 = (会晤) org.hzs.常用.d对象池.get(会晤.class.getName());
                    }
                    ji自用 = ji自用.d副本();
                    ji会晤 = ji会晤.d副本();
                    // </editor-fold>
                    //校验访问者是否为集群内服务器，一定程度的防止恶意访问
                    if (i集群内服务器列表.indexOf(d通信链.getInetAddress().getHostAddress()) < 0) {
                        return;
                    }
                    //
                    ji会晤.d读入 = d通信链.getInputStream();
                    //获取外连接数据
                    ji会晤.i_byteArray = new byte[0];
                    int ji_i;
                    while ((ji_i = ji会晤.d读入.read(ji自用.i缓冲_byteArray)) > 0) {
                        ji会晤.i_byteArray = java.util.Arrays.copyOf(ji会晤.i_byteArray, ji会晤.i_byteArray.length + ji_i);
                        System.arraycopy(ji自用.i缓冲_byteArray, 0, ji会晤.i_byteArray, ji会晤.i_byteArray.length - ji_i, ji_i);
                    }
                    //***取会晤
                    ji自用.session = new byte[2];
                    ji自用.session[0] = ji会晤.i_byteArray[0];
                    ji自用.session[1] = ji会晤.i_byteArray[1];
                    ji自用.session = new byte[org.hzs.lang.转换.byteArray_2_short(ji自用.session)];
                    System.arraycopy(ji会晤.i_byteArray, 2, ji自用.session, 0, ji自用.session.length);
                    //***截掉会晤并解压，取得客户传过来的数据
                    System.arraycopy(ji会晤.i_byteArray, ji自用.session.length + 2, ji会晤.i_byteArray, 0, ji会晤.i_byteArray.length - ji自用.session.length - 2);
                    ji会晤.i_byteArray = java.util.Arrays.copyOf(ji会晤.i_byteArray, ji会晤.i_byteArray.length - ji自用.session.length - 2);
                    ji自用.session = org.hzs.压缩解压.Gzip.i解压_byteArray(ji自用.session);
                    ji自用.i缓冲_byteArray = org.hzs.压缩解压.Gzip.i解压_byteArray(ji会晤.i_byteArray);
                    if (ji自用.i缓冲_byteArray != null) {
                        ji会晤.i_byteArray = ji自用.i缓冲_byteArray;
                    }
                    //交给业务处理模块
                    ji会晤.i_byteArray = d业务.g处理请求(new String(ji自用.session, "UTF-8"), ji会晤.i_byteArray);
                    if (ji会晤.i_byteArray != null) {
                        //将结果压缩，回交给接待服务
                        ji自用.i缓冲_byteArray = org.hzs.压缩解压.Gzip.i压缩_byteArray(ji会晤.i_byteArray);
                        if (ji会晤.i_byteArray.length > ji自用.i缓冲_byteArray.length) {
                            ji会晤.i_byteArray = ji自用.i缓冲_byteArray;
                        }
                        //将服务结果交给客户
                        ji会晤.d写出 = d通信链.getOutputStream();
                        ji会晤.d写出.write(ji会晤.i_byteArray);
                    }
                } catch (IOException | CloneNotSupportedException ex) {
                    Logger.getLogger(均衡.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    // <editor-fold defaultstate="collapsed" desc="释放资源">
                    if (ji自用 != null) {
                        ji自用.close();
                        ji自用 = null;
                    }
                    if (d通信链 != null) {
                        if (ji会晤 != null) {
                            ji会晤.close();
                            ji会晤 = null;
                        }
                        try {
                            d通信链.close();
                        } catch (IOException ex) {
                        }
                        d通信链 = null;
                    }
                    // </editor-fold>
                }
            }

            Runnable_ d副本(final java.net.Socket cd通信链) throws CloneNotSupportedException {
                Runnable_ jd = (Runnable_) super.clone();
                jd.d通信链 = cd通信链;
                return jd;
            }
        }
    }

    private static class 请求开始业务 implements Runnable {

        @Override
        public void run() {
            java.net.ServerSocket d通信服务 = null;
            Runnable_ jd通信 = null;
            for (;;) {
                try {
                    d通信服务 = new java.net.ServerSocket(i中心端口_i);//注意监听端口
                    for (;;) {
                        // <editor-fold defaultstate="collapsed" desc="利用对象复制初始化过程内部变量">
                        jd通信 = (Runnable_) org.hzs.常用.d对象池.get(Runnable_.class.getName());
                        if (jd通信 == null) {
                            jd通信 = new Runnable_();
                            org.hzs.常用.d对象池.put(Runnable_.class.getName(), jd通信);
                        }
                        jd通信 = jd通信.d副本(d通信服务.accept());
                        // </editor-fold>
                        org.hzs.server.Property.d线程池.execute(jd通信);
                    }
                } catch (java.io.IOException | CloneNotSupportedException ex) {
                    Logger.getLogger(均衡.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    if (d通信服务 != null) {
                        try {
                            d通信服务.close();
                        } catch (java.io.IOException ex) {
                        }
                        d通信服务 = null;
                    }
                }
            }
        }

        private class Runnable_ implements Runnable, Cloneable {

            private java.net.Socket d通信链 = null;

            @Override
            public void run() {
                // <editor-fold defaultstate="collapsed" desc="自用">
                class 自用 implements Cloneable {

                    byte[] i缓冲_byteArray = new byte[1024];
                    String[] i键_sArray = null;
                    long i最小客户量_l, i客户量_l;
                    java.util.Calendar i当前时间 = null;

                    自用 d副本() throws CloneNotSupportedException {
                        自用 jd = (自用) super.clone();
                        jd.i缓冲_byteArray = jd.i缓冲_byteArray.clone();
                        return jd;
                    }

                    void close() {
                        i当前时间 = null;
                        i缓冲_byteArray = null;
                        i键_sArray = null;
                    }
                }// </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="会晤">
                class 会晤 implements Cloneable {

                    byte[] i_byteArray = null;
                    java.io.InputStream d读入 = null;
                    java.io.OutputStream d写出 = null;
                    java.net.Socket d通信链 = null;
                    java.net.InetAddress address = null;

                    会晤 d副本() throws CloneNotSupportedException {
                        return (会晤) super.clone();
                    }

                    void close() {
                        address = null;
                        i_byteArray = null;
                        if (d读入 != null) {
                            try {
                                d读入.close();
                            } catch (IOException ex) {
                            }
                            d读入 = null;
                        }
                        if (d写出 != null) {
                            try {
                                d写出.close();
                            } catch (IOException ex) {
                            }
                            d写出 = null;
                        }
                    }
                }// </editor-fold>
                自用 ji自用 = null;
                会晤 ji会晤 = null;
                try {
                    // <editor-fold defaultstate="collapsed" desc="利用对象复制初始化过程内部变量">
                    ji自用 = (自用) org.hzs.常用.d对象池.get(自用.class.getName());
                    if (ji自用 == null) {
                        ji自用 = new 自用();
                        org.hzs.常用.d对象池.put(自用.class.getName(), ji自用);
                        ji会晤 = new 会晤();
                        org.hzs.常用.d对象池.put(会晤.class.getName(), ji会晤);
                    } else {
                        ji会晤 = (会晤) org.hzs.常用.d对象池.get(会晤.class.getName());
                    }
                    ji自用 = ji自用.d副本();
                    ji会晤 = ji会晤.d副本();
                    // </editor-fold>
                    d通信链.setSoTimeout(3000);//设置最大连接时长为3″，防止长连接攻击
                    //判断软件使用权是否到期
                    ji自用.i当前时间 = java.util.Calendar.getInstance();
                    if (org.hzs.server.Property.i软件到期时间_L != null && org.hzs.server.Property.i软件到期时间_L.compareTo(ji自用.i当前时间.getTimeInMillis() - ji自用.i当前时间.getTimeZone().getRawOffset()) < 0) {
                        d通信链.shutdownInput();
                        ji会晤.d写出 = d通信链.getOutputStream();
                        ji会晤.d写出.write("{success:false,_:'软件使用权到期，请续费！'}".getBytes("UTF-8"));
                        return;
                    }
                    //
                    ji会晤.d读入 = d通信链.getInputStream();
                    //接收客户端密钥或递出服务器公钥
                    {
                        ji会晤.i_byteArray = new byte[0];
                        int ji_i;
                        while ((ji_i = ji会晤.d读入.read(ji自用.i缓冲_byteArray)) > 0) {
                            ji会晤.i_byteArray = java.util.Arrays.copyOf(ji会晤.i_byteArray, ji会晤.i_byteArray.length + ji_i);
                            System.arraycopy(ji自用.i缓冲_byteArray, 0, ji会晤.i_byteArray, ji会晤.i_byteArray.length - ji_i, ji_i);
                        }
                        if (ji会晤.i_byteArray.length <= 0) {
                            //客户端未传送数据，说明是取得公钥
                            ji会晤.d写出 = d通信链.getOutputStream();
                            ji会晤.d写出.write(org.hzs.server.Property.rsa.i公钥_byteArray());
                            return;
                        }
                    }
                    //随机选择负载服务器，不仅能降低网路压力，而且确保每个业务节点的压力相仿。基于管理软的客户总是需要退出，暂未考虑每台负载节点的所负责的客户端数量。
                    for (;;) {
                        String ji键_s = null;
                        int ji权重_i = org.hzs.server.Property.d不确定數.nextInt(org.hzs.server.负载均衡.__.i外总权重_i);
                        int ji下限权重_i = 0, ji上限权重_i = 0;
                        for (String ji键1_s : i外服务器列表.keySet()) {
                            ji上限权重_i += i外服务器列表.get(ji键1_s);
                            if (ji下限权重_i <= ji权重_i && ji上限权重_i >= ji权重_i) {
                                ji键_s = ji键1_s;
                            }
                        }
                        ji会晤.address = java.net.InetAddress.getByName(ji键_s);//ping this IP 
                        if (!(ji会晤.address instanceof java.net.Inet4Address) && !(ji会晤.address instanceof java.net.Inet6Address)) {//非IP4或IP6，提示错误
                            //若不连通，说明该服务器出现故障或被人为停止，反正不能工作，所以在列表中删除此服务器。
                            org.hzs.server.负载均衡.__.i外总权重_i -= org.hzs.server.负载均衡.__.i外服务器列表.get(ji键_s);
                            org.hzs.server.负载均衡.__.i外服务器列表.remove(ji键_s);
                            continue;
                        }
                        //将服务专向寻得的最小服务器
                        try {
                            ji会晤.d通信链 = new java.net.Socket(ji键_s, i业务处理端口_i);
                        } catch (java.io.IOException ex) {
                            org.hzs.server.负载均衡.__.i外总权重_i -= org.hzs.server.负载均衡.__.i外服务器列表.get(ji键_s);
                            org.hzs.server.负载均衡.__.i外服务器列表.remove(ji键_s);
                            continue;
                        }
                        {
                            ji会晤.d写出 = ji会晤.d通信链.getOutputStream();
                            ji会晤.d写出.write(ji会晤.i_byteArray);//递交客户端密钥
                            ji会晤.d通信链.shutdownOutput();
                            ji会晤.d读入 = ji会晤.d通信链.getInputStream();//取得会晤号，对应的公网IP、端口
                            ji会晤.i_byteArray = new byte[0];
                            int ji_i;
                            while ((ji_i = ji会晤.d读入.read(ji自用.i缓冲_byteArray)) > 0) {
                                ji会晤.i_byteArray = java.util.Arrays.copyOf(ji会晤.i_byteArray, ji会晤.i_byteArray.length + ji_i);
                                System.arraycopy(ji自用.i缓冲_byteArray, 0, ji会晤.i_byteArray, ji会晤.i_byteArray.length - ji_i, ji_i);
                            }
                        }
                        ji会晤.d通信链.close();
                        break;
                    }
                    //将服务结果交给客户
                    ji会晤.d写出 = d通信链.getOutputStream();
                    ji会晤.d写出.write(ji会晤.i_byteArray);//将取得的數据递交给客户端
                } catch (IOException | CloneNotSupportedException ex) {
                    Logger.getLogger(均衡.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    // <editor-fold defaultstate="collapsed" desc="释放资源">
                    if (ji自用 != null) {
                        ji自用.close();
                        ji自用 = null;
                    }
                    if (d通信链 != null) {
                        if (ji会晤 != null) {
                            ji会晤.close();
                            ji会晤 = null;
                        }
                        try {
                            d通信链.close();
                        } catch (IOException ex) {
                        }
                        d通信链 = null;
                    }
                    // </editor-fold>
                }
            }

            Runnable_ d副本(final java.net.Socket cd通信链) throws CloneNotSupportedException {
                Runnable_ jd = (Runnable_) super.clone();
                jd.d通信链 = cd通信链;
                return jd;
            }
        }
    }

    private class 监聽请求开始业务 implements Runnable {

        @Override
        public void run() {
            java.net.ServerSocket d通信服务 = null;
            Runnable_ jd通信 = null;
            for (;;) {
                try {
                    d通信服务 = new java.net.ServerSocket(i业务处理端口_i);//注意监听端口
                    for (;;) {
                        // <editor-fold defaultstate="collapsed" desc="利用对象复制初始化过程内部变量">
                        jd通信 = (Runnable_) org.hzs.常用.d对象池.get(Runnable_.class.getName());
                        if (jd通信 == null) {
                            jd通信 = new Runnable_();
                            org.hzs.常用.d对象池.put(Runnable_.class.getName(), jd通信);
                        }
                        jd通信 = jd通信.d副本(d通信服务.accept());
                        // </editor-fold>
                        org.hzs.server.Property.d线程池.execute(jd通信);
                    }
                } catch (java.io.IOException | CloneNotSupportedException ex) {
                    Logger.getLogger(均衡.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    if (d通信服务 != null) {
                        try {
                            d通信服务.close();
                        } catch (java.io.IOException ex) {
                        }
                        d通信服务 = null;
                    }
                }
            }
        }

        private class Runnable_ implements Runnable, Cloneable {

            private java.net.Socket d通信链 = null;

            @Override
            public void run() {
                // <editor-fold defaultstate="collapsed" desc="自用">
                class 自用 implements Cloneable {

                    byte[] i缓冲_byteArray = new byte[1024];
                    long i最小工作量_l, i工作量_l;
                    org.hzs.json.JSONObject i_JSON = null;
                    org.hzs.server.Session session = null;
                    int i会晤号_i;

                    自用 d副本() throws CloneNotSupportedException {
                        自用 jd = (自用) super.clone();
                        jd.i缓冲_byteArray = jd.i缓冲_byteArray.clone();
                        return jd;
                    }

                    void close() {
                        i缓冲_byteArray = null;
                        session = null;
                    }
                }// </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="会晤">
                class 会晤 implements Cloneable {

                    byte[] i_byteArray = null;
                    java.io.InputStream d读入 = null;
                    java.io.OutputStream d写出 = null;

                    会晤 d副本() throws CloneNotSupportedException {
                        return (会晤) super.clone();
                    }

                    void close() {
                        i_byteArray = null;
                        if (d读入 != null) {
                            try {
                                d读入.close();
                            } catch (IOException ex) {
                            }
                            d读入 = null;
                        }
                        if (d写出 != null) {
                            try {
                                d写出.close();
                            } catch (IOException ex) {
                            }
                            d写出 = null;
                        }
                    }
                }// </editor-fold>
                自用 ji自用 = null;
                会晤 ji会晤 = null;
                try {
                    // <editor-fold defaultstate="collapsed" desc="利用对象复制初始化过程内部变量">
                    ji自用 = (自用) org.hzs.常用.d对象池.get(自用.class.getName());
                    if (ji自用 == null) {
                        ji自用 = new 自用();
                        org.hzs.常用.d对象池.put(自用.class.getName(), ji自用);
                        ji会晤 = new 会晤();
                        org.hzs.常用.d对象池.put(会晤.class.getName(), ji会晤);
                    } else {
                        ji会晤 = (会晤) org.hzs.常用.d对象池.get(会晤.class.getName());
                    }
                    ji自用 = ji自用.d副本();
                    ji会晤 = ji会晤.d副本();
                    // </editor-fold>
                    ji会晤.d读入 = d通信链.getInputStream();
                    //获取外连接数据，客户端密钥
                    ji会晤.i_byteArray = new byte[0];
                    int ji_i;
                    while ((ji_i = ji会晤.d读入.read(ji自用.i缓冲_byteArray)) > 0) {
                        ji会晤.i_byteArray = java.util.Arrays.copyOf(ji会晤.i_byteArray, ji会晤.i_byteArray.length + ji_i);
                        System.arraycopy(ji自用.i缓冲_byteArray, 0, ji会晤.i_byteArray, ji会晤.i_byteArray.length - ji_i, ji_i);
                    }
                    ji会晤.i_byteArray = org.hzs.server.Property.rsa.i用私钥解密_byteArray(ji会晤.i_byteArray);
                    //建立系统内部会晤
                    ji自用.session = new org.hzs.server.Session();
                    ji自用.session.AES_Key = new SecretKeySpec(ji会晤.i_byteArray, "AES");
                    ////生成会晤号
                    do {
                        ji自用.i会晤号_i = org.hzs.server.Property.d不确定數.nextInt();
                    } while (session_集合.get(ji自用.i会晤号_i) != null);
                    session_集合.put(ji自用.i会晤号_i, ji自用.session);
                    //
                    ji自用.i_JSON = org.hzs.json.JSONObject.d副本();
                    ji自用.i_JSON.put("IP_s", i公网IP_s);
                    ji自用.i_JSON.put("端口_i", i外部端口_i);
                    ji自用.i_JSON.put("会晤号_i", ji自用.i会晤号_i);
                    ji会晤.i_byteArray = ji自用.i_JSON.toString(null).getBytes("UTF-8");
                    ji会晤.i_byteArray = org.hzs.压缩解压.Gzip.i压缩_byteArray(ji会晤.i_byteArray);
                    ji会晤.i_byteArray = org.hzs.安全.AES.i加密_byteArray(ji会晤.i_byteArray, ji自用.session.AES_Key);
                    //将服务结果交给客户
                    ji会晤.d写出 = d通信链.getOutputStream();
                    ji会晤.d写出.write(ji会晤.i_byteArray);
                } catch (IOException | CloneNotSupportedException | NoSuchAlgorithmException | error | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
                    Logger.getLogger(均衡.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    // <editor-fold defaultstate="collapsed" desc="释放资源">
                    if (ji自用 != null) {
                        ji自用.close();
                        ji自用 = null;
                    }
                    if (d通信链 != null) {
                        if (ji会晤 != null) {
                            ji会晤.close();
                            ji会晤 = null;
                        }
                        try {
                            d通信链.close();
                        } catch (IOException ex) {
                        }
                        d通信链 = null;
                    }
                    // </editor-fold>
                }
            }

            Runnable_ d副本(final java.net.Socket cd通信链) throws CloneNotSupportedException {
                Runnable_ jd = (Runnable_) super.clone();
                jd.d通信链 = cd通信链;
                return jd;
            }
        }
    }
}
