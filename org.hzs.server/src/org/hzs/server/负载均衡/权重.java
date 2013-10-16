/**
 * 保留线程：2<BR>
 * 线程“清理超时或访问频率过快的session”负责：定时清理过期的客户端(包括session1、session)。<BR>
 * 线程“接收其他服务器报告”负责接收其他服务器传过来的权重。<BR>
 */
package org.hzs.server.负载均衡;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hzs.logging.error;

public final class 权重 extends __ {

    private static int i负载均衡端口_i;

    protected static void init_() {
        if (i集群内服务器列表.size() <= 0) {
            return;
        }
        i负载均衡端口_i = i中心端口_i - 1;
        org.hzs.server.负载均衡.Property.d线程池.execute(new 清理超时或访问频率过快的session());
        i内总权重_i = i权重_i;
        i外总权重_i = i权重_i;
        if (i公网IP_s != null) {
            i外服务器列表.put(i内网IP_s, i权重_i);
            if (org.hzs.server.负载均衡.Property.i业务服务_b) {
                i内服务器列表.put(i内网IP_s, i权重_i);
            }
        }
        org.hzs.server.负载均衡.Property.d线程池.execute(new 接收其他服务器报告());
        jg向其他服务器报告及索取报告();
    }

    /**
     * 接收其他服务器的使用情况。假设处理速度很快，不再增设其他线程
     */
    private static class 接收其他服务器报告 implements Runnable {

        @Override
        public void run() {
            // <editor-fold defaultstate="collapsed" desc="自用">
            class 自用 extends org.hzs.Close implements Cloneable {

                byte[] i缓冲_byteArray = new byte[128];
                org.hzs.json.JSONObject i_JSON = null;
                String IP_s = null;
                Integer i权重_I;

                自用 d副本() throws CloneNotSupportedException {
                    自用 jd = (自用) super.clone();
                    jd.i缓冲_byteArray = jd.i缓冲_byteArray.clone();
                    return jd;
                }
            }// </editor-fold>
            // <editor-fold defaultstate="collapsed" desc="会晤">
            class 会晤 extends org.hzs.Close implements Cloneable {

                byte[] i_byteArray = null;
                java.io.InputStream d读入 = null;
                java.io.OutputStream d写出 = null;
                java.net.Socket d通信链 = null;

                会晤 d副本() throws CloneNotSupportedException {
                    return (会晤) super.clone();
                }
            }// </editor-fold>
            org.hzs.logging.error ji_error = null;
            java.net.ServerSocket d通信服务 = null;
            自用 ji自用 = new 自用();
            会晤 ji会晤 = new 会晤();
            for (;;) {
                try {
                    ji_error = org.hzs.logging.error.d副本();
                    d通信服务 = new java.net.ServerSocket(i负载均衡端口_i);
                    for (;;) {
                        ji会晤.d通信链 = d通信服务.accept();
                        //校验访问者是否为集群内服务器，一定程度的防止恶意访问
                        if (i集群内服务器列表.indexOf(ji会晤.d通信链.getInetAddress().getHostAddress()) < 0) {
                            ji会晤.d通信链.close();
                            return;
                        }
                        if (i公网IP_s != null) {
                            ji会晤.d读入 = ji会晤.d通信链.getInputStream();
                            ji会晤.i_byteArray = new byte[0];
                            int ji_i;
                            while ((ji_i = ji会晤.d读入.read(ji自用.i缓冲_byteArray)) > 0) {
                                ji会晤.i_byteArray = java.util.Arrays.copyOf(ji会晤.i_byteArray, ji会晤.i_byteArray.length + ji_i);
                                System.arraycopy(ji自用.i缓冲_byteArray, 0, ji会晤.i_byteArray, ji会晤.i_byteArray.length - ji_i, ji_i);
                            }
                            if (ji会晤.i_byteArray.length <= 0) {
                                ji会晤.close();
                                continue;
                            }
                            ji自用.i_JSON = org.hzs.json.JSONObject.d副本();
                            ji自用.i_JSON.set(new String(ji会晤.i_byteArray, "UTF-8"), ji_error);
                            //
                            ji自用.IP_s = ji自用.i_JSON.getString("$", ji_error);
                            if (ji自用.IP_s == null) {
                                continue;
                            }
                            ji自用.i权重_I = ji自用.i_JSON.getInt("权重_i", ji_error);
                            if (ji自用.i权重_I == null) {
                                continue;
                            }
                            if (ji自用.i_JSON.get("外") != null) {
                                i外服务器列表.put(ji自用.IP_s, ji自用.i权重_I);
                                i外总权重_i += ji自用.i权重_I;
                            }
                            i内服务器列表.put(ji自用.IP_s, ji自用.i权重_I);
                            i内总权重_i += ji自用.i权重_I;
                        }
                        ji自用.i_JSON = org.hzs.json.JSONObject.d副本();
                        ji自用.i_JSON.put("$", i内网IP_s);
                        ji自用.i_JSON.put("权重_i", i权重_i);
                        if (i公网IP_s != null) {
                            ji自用.i_JSON.put("外", 1);
                        }
                        ji会晤.d写出 = ji会晤.d通信链.getOutputStream();
                        ji会晤.d写出.write(ji自用.i_JSON.toString(ji_error).getBytes("UTF-8"));
                        ji会晤.close();
                    }
                } catch (java.io.IOException | CloneNotSupportedException | org.hzs.logging.error ex) {
                    Logger.getLogger(__.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    if (ji会晤 != null) {
                        ji会晤.close();
                        ji会晤 = null;
                    }
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
    }

    private static class 清理超时或访问频率过快的session implements Runnable {

        @Override
        public void run() {
            // <editor-fold defaultstate="collapsed" desc="自用">
            class 自用 implements Cloneable {

                org.hzs.json.JSONObject i_JSON = null;
                Object[] sessionid_Array = null;
                org.hzs.server.负载均衡.Session session = null;
                long i当前时间_l;

                void close() {
                    if (i_JSON != null) {
                        i_JSON.clear();
                        i_JSON = null;
                    }
                }
            }// </editor-fold>
            自用 ji自用 = null;
            try {
                ji自用 = new 自用();
                //
                for (;;) {
                    try {
                        java.lang.Thread.sleep(5000);
                    } catch (InterruptedException ex) {
                    }
                    ji自用.i当前时间_l = java.util.Calendar.getInstance().getTimeInMillis();
                    if (org.hzs.server.负载均衡.__.session_集合.size() <= 0) {
                        continue;
                    }
                    Set<Integer> keys = org.hzs.server.负载均衡.__.session_集合.keySet();
                    for (Integer jjkey_i : keys) {
                        ji自用.session = org.hzs.server.负载均衡.__.session_集合.get(jjkey_i);
                        //移除超时session 或 访问过于频繁的session
                        if (ji自用.i当前时间_l - ji自用.session.i最近使用时间_l > org.hzs.server.负载均衡.Session.时限_l
                                || ji自用.session.i访问频率_i > org.hzs.server.负载均衡.Property.i频率上限_i) {
                            keys.remove(jjkey_i);
                            if (d业务 != null) {
                                d业务.removeSession(jjkey_i);
                            }
                        } else {
                            ji自用.session.i访问频率_i = 0;//重置访问频率
                        }
                    }
//                    ji自用.sessionid_Array = org.hzs.server.负载均衡.__.session_集合.keySet().toArray();
//                    for (int ji_i = ji自用.sessionid_Array.length - 1; ji_i >= 0; ji_i--) {
//                        ji自用.session = session_集合.get(ji自用.sessionid_Array[ji_i]);
//                        //移除超时session 或 访问过于频繁的session
//                        if (ji自用.i当前时间_l - ji自用.session.i最近使用时间_l > org.hzs.server.负载均衡.Session.时限_l
//                                || ji自用.session.i访问频率_i > org.hzs.server.负载均衡.Property.i频率上限_i) {
//                            session_集合.remove(ji自用.sessionid_Array[ji_i]);
//                            if (d业务 != null) {
//                                d业务.removeSession(ji_i);
//                            }
//                        } else {
//                            ji自用.session.i访问频率_i = 0;//重置访问频率
//                        }
//                    }
                }
            } finally {
                if (ji自用 != null) {
                    ji自用.close();
                    ji自用 = null;
                }
            }
        }
    }

    private static void jg向其他服务器报告及索取报告() {
        // <editor-fold defaultstate="collapsed" desc="自用">
        class 自用 implements Cloneable {

            org.hzs.json.JSONObject i_JSON = null;
            byte[] i缓冲_byteArray = new byte[128];
            byte[] i_byteArray = null;
            String IP_s = null;
            Integer i权重_I = null, i客户量_I = null;

            void close() {
                i客户量_I = null;
                i权重_I = null;
                IP_s = null;
                i缓冲_byteArray = null;
                i_byteArray = null;
                if (i_JSON != null) {
                    i_JSON.clear();
                    i_JSON = null;
                }
            }
        }// </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="会晤">
        class 会晤 implements Cloneable {

            byte[] i_byteArray = null;
            java.io.OutputStream d写出 = null;
            java.io.InputStream d读入 = null;
            java.net.Socket d通信链 = null;
            java.net.InetAddress address = null;
            org.hzs.json.JSONObject i_JSON = null;

            void close() {
                address = null;
                i_byteArray = null;
                if (i_JSON != null) {
                    i_JSON.clear();
                    i_JSON = null;
                }
                if (d通信链 != null) {
                    if (d写出 != null) {
                        try {
                            d写出.close();
                        } catch (IOException ex) {
                        }
                        d写出 = null;
                    }
                    if (d读入 != null) {
                        try {
                            d读入.close();
                        } catch (IOException ex) {
                        }
                        d读入 = null;
                    }
                    try {
                        d通信链.close();
                    } catch (IOException ex) {
                    }
                    d通信链 = null;
                }
            }
        }// </editor-fold>
        org.hzs.logging.error ji_error = null;
        自用 ji自用 = null;
        会晤 ji会晤 = null;
        try {
            ji自用 = new 自用();
            ji会晤 = new 会晤();
            //
            ji_error = org.hzs.logging.error.d副本();
            ji自用.i_JSON = org.hzs.json.JSONObject.d副本();
            ji自用.i_JSON.put("$", i内网IP_s);
            if (i公网IP_s != null) {
                ji自用.i_JSON.put("外", 1);
            }
            ji自用.i_JSON.put("权重_i", i权重_i);
            ji自用.i_byteArray = ji自用.i_JSON.toString(ji_error).getBytes("UTF-8");
            for (String ji内网IP_s : i集群内服务器列表) {
                if (ji内网IP_s.equals("127.0.0.1") || ji内网IP_s.equals(i内网IP_s)) {
                    continue;
                }
                ji会晤.address = java.net.InetAddress.getByName(ji内网IP_s); //ping this IP 
                if (!(ji会晤.address instanceof java.net.Inet4Address) && !(ji会晤.address instanceof java.net.Inet6Address)) {//非IP4或IP6，提示错误
                    continue;
                }
                try {
                    ji会晤.d通信链 = new java.net.Socket(ji内网IP_s, i负载均衡端口_i);
                } catch (java.io.IOException ex) {
                    continue;
                }
                ji会晤.d写出 = ji会晤.d通信链.getOutputStream();
                ji会晤.d写出.write(ji自用.i_byteArray);
                ji会晤.d通信链.shutdownOutput();
                if (i公网IP_s != null) {
                    ji会晤.d读入 = ji会晤.d通信链.getInputStream();
                    ji会晤.i_byteArray = new byte[0];
                    int ji_i;
                    while ((ji_i = ji会晤.d读入.read(ji自用.i缓冲_byteArray)) > 0) {
                        ji会晤.i_byteArray = java.util.Arrays.copyOf(ji会晤.i_byteArray, ji会晤.i_byteArray.length + ji_i);
                        System.arraycopy(ji自用.i缓冲_byteArray, 0, ji会晤.i_byteArray, ji会晤.i_byteArray.length - ji_i, ji_i);
                    }
                    if (ji会晤.i_byteArray.length <= 0) {
                        ji会晤.close();
                        continue;
                    }
                    ji会晤.i_JSON = org.hzs.json.JSONObject.d副本();
                    ji会晤.i_JSON.set(new String(ji会晤.i_byteArray, "UTF-8"), ji_error);
                    //
                    ji自用.IP_s = ji会晤.i_JSON.getString("$", ji_error);
                    if (ji自用.IP_s == null) {
                        continue;
                    }
                    ji自用.i权重_I = ji会晤.i_JSON.getInt("权重_i", ji_error);
                    if (ji自用.i权重_I == null) {
                        continue;
                    }
                    if (ji会晤.i_JSON.get("外") != null) {
                        i外服务器列表.put(ji自用.IP_s, ji自用.i权重_I);
                        i外总权重_i += ji自用.i权重_I;
                    }
                    i内服务器列表.put(ji自用.IP_s, ji自用.i权重_I);
                    i内总权重_i += ji自用.i权重_I;
                }
                ji会晤.d通信链.close();
            }
        } catch (UnknownHostException | UnsupportedEncodingException | CloneNotSupportedException | error ex) {
            Logger.getLogger(__.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(__.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (ji自用 != null) {
                ji自用.close();
                ji自用 = null;
            }
            if (ji会晤 != null) {
                ji会晤.close();
                ji会晤 = null;
            }
        }
    }
}
