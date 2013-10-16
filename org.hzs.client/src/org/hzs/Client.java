package org.hzs;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {

    private static final String i类名_s = "org.hzs.Client";
    private static org.hzs.安全.RSA RSA = null;
    public static java.awt.Toolkit d蜂鸣 = null;

    private final class i {

        byte[] iAES密钥_byteArray = null;
        java.security.Key AES_Key = null;
        byte[] i会晤号_byteArray = new byte[4];//4字节支持40亿用户，够用
        int i会晤次数_i = 0;

        java.nio.channels.Selector selector = null;
        java.nio.channels.SocketChannel socketChannel = null;
        final java.nio.ByteBuffer i接收缓冲区 = java.nio.ByteBuffer.allocate(5 * 1024 * 1024), i发送缓冲区 = java.nio.ByteBuffer.allocate(10 * 1024);//发送默认10K，接收默认5M。

        维持连接 i维持连接 = null;
    }
    i i = null;

    public Client() {
        i = new i();
        if (RSA == null) {
            RSA = new org.hzs.安全.RSA(5);
        }
        if (d蜂鸣 == null) {
            d蜂鸣 = (new java.awt.Frame()).getToolkit();
        }
        i.iAES密钥_byteArray = new byte[16];
    }

    private void jg握手(final String ci服务器IP_s, final Integer ci服务器端口_i, final org.hzs.logging.error ci_error) throws org.hzs.logging.error {
        final String ji过程名_s = "jg握手";
        // <editor-fold defaultstate="collapsed" desc="自用">
        class 自用 extends org.hzs.Close {

            java.net.InetSocketAddress i服务器端地址 = null;
            java.util.Set<java.nio.channels.SelectionKey> selectionKeys = null;
            java.util.Iterator<java.nio.channels.SelectionKey> iterator = null;
            java.nio.channels.SelectionKey selectionKey = null;
            java.nio.channels.SocketChannel client = null;

            自用 d副本() throws CloneNotSupportedException {
                return (自用) super.clone();
            }
        }// </editor-fold>
        自用 ji自用 = null;
        try {
            // <editor-fold defaultstate="collapsed" desc="初始化变量">
            ji自用 = (自用) org.hzs.常用.d对象池.get(自用.class.getName());
            if (ji自用 == null) {
                ji自用 = new 自用();
            }
            // </editor-fold>
            ji自用.i服务器端地址 = new java.net.InetSocketAddress(ci服务器IP_s, ci服务器端口_i);
            if (i.socketChannel == null) {
                i.socketChannel = java.nio.channels.SocketChannel.open();// 打开socket通道
                i.socketChannel.configureBlocking(false);// 设置为非阻塞方式
            }
            // 打开选择器
            if (i.selector != null) {
                i.selector.close();
            }
            i.selector = java.nio.channels.Selector.open();
            i.socketChannel.register(i.selector, java.nio.channels.SelectionKey.OP_CONNECT);//注册连接服务端socket动作
            i.socketChannel.connect(ji自用.i服务器端地址);//连接

            //连接服务器
            while (true) {
                //选择一组键，其相应的通道已为 I/O 操作准备就绪。  
                //此方法执行处于阻塞模式的选择操作。  
                i.selector.select();
                //返回此选择器的已选择键集。  
                ji自用.selectionKeys = i.selector.selectedKeys();
                //System.out.println(selectionKeys.size());  
                ji自用.iterator = ji自用.selectionKeys.iterator();
                while (ji自用.iterator.hasNext()) {
                    ji自用.selectionKey = ji自用.iterator.next();
                    if (ji自用.selectionKey.isConnectable()) {
                        ji自用.client = (java.nio.channels.SocketChannel) ji自用.selectionKey.channel();
                        // 判断此通道上是否正在进行连接操作。  
                        // 完成套接字通道的连接过程。  
                        if (ji自用.client.isConnectionPending()) {
                            ji自用.client.finishConnect();
                            i.i发送缓冲区.clear();
                            i.i发送缓冲区.put("1".getBytes());
                            //将缓冲区各标志复位,因为向里面put了数据标志被改变要想从中读取数据发向服务器,就要复位  
                            i.i发送缓冲区.flip();
                            ji自用.client.write(i.i发送缓冲区);
                        }
                        ji自用.client.register(i.selector, java.nio.channels.SelectionKey.OP_READ);//客户端先写后读
                    } else if (ji自用.selectionKey.isReadable()) {
                        i.i发送缓冲区.clear();
                        ji自用.client = (java.nio.channels.SocketChannel) ji自用.selectionKey.channel();
                        ji自用.client.read(i.i发送缓冲区);
                        ji自用.client.register(i.selector, java.nio.channels.SelectionKey.OP_WRITE);
                        i.i维持连接 = new 维持连接();
                        i.i维持连接.start();
                        return;//连接成功，则退出
                    }
                }
                ji自用.selectionKeys.clear();
            }
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            // <editor-fold defaultstate="collapsed" desc="抛出错误">
            ci_error.g添加错误信息("{类:'" + i类名_s + "',"
                    + "过程:'" + ji过程名_s + "',"
                    + "序号:''"
                    + "错误类型:'捕获',"
                    + "错误:'" + ex.getMessage() + "'}");
            throw ci_error;
            // </editor-fold>
        } finally {
            if (ji自用 != null) {
                ji自用.close();
                ji自用 = null;
            }
        }
    }

    public void g握手(final String ci服务器IP_s, final Integer ci服务器端口_i, final int ci端口偏移_i, final org.hzs.logging.error ci_error) throws org.hzs.logging.error {
        final String ji过程名_s = "g握手";
        // <editor-fold defaultstate="collapsed" desc="自用">
        class 自用 {

            int i服务器端口_i, i端口偏移_i, i打印端口_i, i本地服务端口_i;
            String i服务器IP_s = null;
            java.security.PublicKey i服务器公钥 = null;

            org.hzs.json.JSONObject i_JSON = null;
            byte[] i密文_byteArray = null, i明文_byteArray = null;
            java.nio.channels.Selector selector = null;
            java.nio.channels.SocketChannel socketChannel = null;
            java.nio.ByteBuffer i接收缓冲区 = java.nio.ByteBuffer.allocate(5 * 1024 * 1024), i发送缓冲区 = java.nio.ByteBuffer.allocate(10 * 1024);//发送默认10K，接收默认5M。
            java.net.InetSocketAddress i服务器端地址 = null;

            java.util.Set<java.nio.channels.SelectionKey> selectionKeys = null;
            java.util.Iterator<java.nio.channels.SelectionKey> iterator = null;
            java.nio.channels.SelectionKey selectionKey = null;
            java.nio.channels.SocketChannel client = null;
            int cont;

            void close() {
                if (i_JSON != null) {
                    i_JSON.clear();
                    i_JSON = null;
                }
                i密文_byteArray = null;
                i明文_byteArray = null;
                if (selector != null) {
                    try {
                        selector.close();
                    } catch (java.io.IOException ex) {
                    }
                    selector = null;
                }
                if (socketChannel != null) {
                    try {
                        socketChannel.close();
                    } catch (java.io.IOException ex) {
                    }
                    socketChannel = null;
                }
                i服务器端地址 = null;

                if (selectionKeys != null) {
                    selectionKeys.clear();
                    selectionKeys = null;
                }
                iterator = null;
                selectionKey = null;
                if (client != null) {
                    try {
                        client.close();
                    } catch (java.io.IOException ex) {
                    }
                    client = null;
                }
            }
        }// </editor-fold>
        自用 ji自用 = null;
        try {
            // <editor-fold defaultstate="collapsed" desc="初始化变量">
            ji自用 = (自用) org.hzs.常用.d对象池.get(自用.class.getName());
            if (ji自用 == null) {
                ji自用 = new 自用();
            }
            // </editor-fold>
            if (i.i维持连接 != null) {
                i.i维持连接.interrupt();
                i.i维持连接 = null;
            }
            //
            (new java.util.Random()).nextBytes(i.iAES密钥_byteArray);
            i.AES_Key = new javax.crypto.spec.SecretKeySpec(i.iAES密钥_byteArray, "AES");
            //
            ji自用.i服务器端地址 = new java.net.InetSocketAddress(ci服务器IP_s, ci服务器端口_i);
            // 打开socket通道  
            ji自用.socketChannel = java.nio.channels.SocketChannel.open();
            // 设置为非阻塞方式  
            ji自用.socketChannel.configureBlocking(false);
            // 打开选择器  
            ji自用.selector = java.nio.channels.Selector.open();
            // 注册连接服务端socket动作  
            ji自用.socketChannel.register(ji自用.selector, java.nio.channels.SelectionKey.OP_CONNECT);
            // 连接  
            ji自用.socketChannel.connect(ji自用.i服务器端地址);

            while (true) {
                //选择一组键，其相应的通道已为 I/O 操作准备就绪。  
                //此方法执行处于阻塞模式的选择操作。  
                if (ji自用.selector.select(1000) <= 0) {
                    throw ci_error;//链接超时，则认为网路不通
                }
                //返回此选择器的已选择键集。  
                ji自用.selectionKeys = ji自用.selector.selectedKeys();
                //System.out.println(selectionKeys.size());  
                ji自用.iterator = ji自用.selectionKeys.iterator();
                if (ji自用.iterator.hasNext()) {
                    ji自用.selectionKey = ji自用.iterator.next();
                    if (ji自用.selectionKey.isConnectable()) {
                        ji自用.client = (java.nio.channels.SocketChannel) ji自用.selectionKey.channel();
                        // 判断此通道上是否正在进行连接操作。  
                        // 完成套接字通道的连接过程。  
                        if (ji自用.client.isConnectionPending()) {
                            ji自用.i发送缓冲区.clear();
                            ji自用.i发送缓冲区.put("1".getBytes());
                            //将缓冲区各标志复位,因为向里面put了数据标志被改变要想从中读取数据发向服务器,就要复位  
                            ji自用.i发送缓冲区.flip();
                            ji自用.client.finishConnect();
                            ji自用.client.write(ji自用.i发送缓冲区);
                        }
                        ji自用.client.register(ji自用.selector, java.nio.channels.SelectionKey.OP_READ);
                    } else if (ji自用.selectionKey.isReadable()) {
                        ji自用.client = (java.nio.channels.SocketChannel) ji自用.selectionKey.channel();
                        //将缓冲区清空以备下次读取  
                        ji自用.i接收缓冲区.clear();
                        //读取服务器发送来的数据到缓冲区中
                        ji自用.cont = ji自用.client.read(ji自用.i接收缓冲区);
                        ji自用.i密文_byteArray = ji自用.i接收缓冲区.array();
                        ji自用.i密文_byteArray = java.util.Arrays.copyOf(ji自用.i密文_byteArray, ji自用.cont);
                        if (ji自用.i服务器公钥 == null) {
                            ji自用.i服务器公钥 = org.hzs.安全.RSA.i公钥(ji自用.i密文_byteArray);
                            ji自用.client.register(ji自用.selector, java.nio.channels.SelectionKey.OP_WRITE);
                        } else {
                            ji自用.i明文_byteArray = org.hzs.安全.AES.i解密_byteArray(ji自用.i密文_byteArray, i.AES_Key);
                            ji自用.i明文_byteArray = org.hzs.压缩解压.Gzip.i解压_byteArray(ji自用.i明文_byteArray);
                            ji自用.i_JSON = org.hzs.json.JSONObject.d副本();
                            ji自用.i_JSON.set(new String(ji自用.i明文_byteArray, "UTF-8"), ci_error);
                            //解析
                            i.i会晤号_byteArray = org.hzs.lang.转换.int_2_byteArray(ji自用.i_JSON.getInt("会晤号_i", ci_error));
                            ji自用.i服务器IP_s = ji自用.i_JSON.getString("IP_s", ci_error);
                            ji自用.i服务器端口_i = ci服务器端口_i + ci端口偏移_i + 1;
                            ji自用.client.close();
                            break;
                        }
                    } else if (ji自用.selectionKey.isWritable()) {
                        ji自用.client = (java.nio.channels.SocketChannel) ji自用.selectionKey.channel();
                        if (ji自用.i服务器公钥 != null) {
                            ji自用.i密文_byteArray = RSA.i用公钥加密_byteArray(i.iAES密钥_byteArray, ji自用.i服务器公钥);
                        } else {
                            ji自用.i密文_byteArray = new byte[0];
                        }
                        ji自用.i发送缓冲区.clear();
                        ji自用.i发送缓冲区.put(ji自用.i密文_byteArray);
                        //将缓冲区各标志复位,因为向里面put了数据标志被改变要想从中读取数据发向服务器,就要复位  
                        ji自用.i发送缓冲区.flip();
                        ji自用.client.write(ji自用.i发送缓冲区);
                        ji自用.client.register(ji自用.selector, java.nio.channels.SelectionKey.OP_READ);
                    }
                }
                ji自用.selectionKeys.clear();
            }
            jg握手(ji自用.i服务器IP_s, ji自用.i服务器端口_i, ci_error);
        } catch (java.io.IOException | java.security.NoSuchAlgorithmException | javax.crypto.NoSuchPaddingException | java.security.InvalidKeyException | javax.crypto.IllegalBlockSizeException | javax.crypto.BadPaddingException | java.security.spec.InvalidKeySpecException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            // <editor-fold defaultstate="collapsed" desc="抛出错误">
            ci_error.g添加错误信息("{类:'" + i类名_s + "',"
                    + "过程:'" + ji过程名_s + "',"
                    + "序号:''"
                    + "错误类型:'捕获',"
                    + "错误:'" + ex.getMessage() + "'}");
            throw ci_error;
            // </editor-fold>
        } catch (org.hzs.logging.error ex) {
            // <editor-fold defaultstate="collapsed" desc="抛出错误">
            ex.g添加错误信息("{类:'" + i类名_s + "',"
                    + "过程:'" + ji过程名_s + "',"
                    + "序号:''"
                    + "错误类型:'捕获',"
                    + "错误:''}");
            throw ex;
            // </editor-fold>
        } finally {
            if (ji自用 != null) {
                ji自用.close();
                ji自用 = null;
            }
        }
    }

    /**
     * @param ci指令_s
     * @param ci参數_s
     * @param ci_error
     * @return
     * @throws org.hzs.logging.error
     */
    public String g会晤服务器(final String ci指令_s, final String ci参數_s, org.hzs.logging.error ci_error) throws org.hzs.logging.error {
        final String ji过程名_s = "g会晤服务器";
        // <editor-fold defaultstate="collapsed" desc="自用">
        class 自用 extends org.hzs.Close implements Cloneable {

            org.hzs.json.JSONObject i_JSON = null, i1_JSON = null;
            byte[] i密文_byteArray = null, i压缩前明文_byteArray = null, i压缩後明文_byteArray = null;

            public 自用 d副本() throws CloneNotSupportedException {
                return (自用) super.clone();
            }
        }// </editor-fold>
        自用 ji自用 = null;
        boolean ji未捕获错误_b = true;
        try {
            ji自用 = (自用) org.hzs.常用.d对象池.get(自用.class.getName());
            if (ji自用 == null) {
                ji自用 = new 自用();
                org.hzs.常用.d对象池.put(自用.class.getName(), ji自用);
            }
            ji自用 = ji自用.d副本();
            //
            try {//签名、加密，生成需要传送的数据
                /*
                 *$:{
                 *   指令:用户  （系统自动填入）
                 *   指令0:     （传递过来）
                 *   ...
                 * },
                 * _:{
                 *    参數:
                 * }
                 */
                ji自用.i_JSON = org.hzs.json.JSONObject.d副本();
                ji自用.i1_JSON = org.hzs.json.JSONObject.d副本();
                ji自用.i1_JSON.set(ci指令_s, ci_error);
                ji自用.i_JSON.put("$", ji自用.i1_JSON, ci_error);
                ji自用.i1_JSON = org.hzs.json.JSONObject.d副本();
                ji自用.i1_JSON.set(ci参數_s, ci_error);
                ji自用.i_JSON.put("_", ji自用.i1_JSON, ci_error);
                //整理明文并加密
                ji自用.i_JSON.toString(ci_error);
                ji自用.i压缩前明文_byteArray = ji自用.i_JSON.toString(ci_error).getBytes("UTF-8");
                ji自用.i压缩後明文_byteArray = org.hzs.压缩解压.Gzip.i压缩_byteArray(ji自用.i压缩前明文_byteArray);//压缩明文
                if (ji自用.i压缩後明文_byteArray.length > ji自用.i压缩前明文_byteArray.length) {
                    ji自用.i密文_byteArray = org.hzs.安全.AES.i加密_byteArray(ji自用.i压缩前明文_byteArray, i.AES_Key);
                } else {
                    ji自用.i密文_byteArray = org.hzs.安全.AES.i加密_byteArray(ji自用.i压缩後明文_byteArray, i.AES_Key);
                }
                //在密文头部加入会晤号
                ji自用.i密文_byteArray = java.util.Arrays.copyOf(ji自用.i密文_byteArray, ji自用.i密文_byteArray.length + 4);
                System.arraycopy(ji自用.i密文_byteArray, 0, ji自用.i密文_byteArray, 4, ji自用.i密文_byteArray.length - 4);
                System.arraycopy(i.i会晤号_byteArray, 0, ji自用.i密文_byteArray, 0, 4);
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                ji未捕获错误_b = false;
                // <editor-fold defaultstate="collapsed" desc="抛出错误">
                ci_error.g添加错误信息("{类:'" + i类名_s + "',"
                        + "过程:'" + ji过程名_s + "',"
                        + "序号:'1'"
                        + "错误类型:'捕获',"
                        + "错误:'" + ex.getMessage() + "',"
                        + "错误1:'本地错误'}");
                throw ci_error;
                // </editor-fold>
            } catch (org.hzs.logging.error ex) {
                ji未捕获错误_b = false;
                // <editor-fold defaultstate="collapsed" desc="抛出错误">
                ex.g添加错误信息("{类:'" + i类名_s + "',"
                        + "过程:'" + ji过程名_s + "',"
                        + "序号:'1'"
                        + "错误类型:'捕获',"
                        + "错误:'',"
                        + "错误1:'本地错误'}");
                throw ex;
                // </editor-fold>
            }
            try {//传送给服务器并取回结果
                java.util.Set<java.nio.channels.SelectionKey> selectionKeys;
                java.util.Iterator<java.nio.channels.SelectionKey> iterator;
                java.nio.channels.SelectionKey selectionKey;
                java.nio.channels.SocketChannel client;
                while (true) {
                    //选择一组键，其相应的通道已为 I/O 操作准备就绪。  
                    //此方法执行处于阻塞模式的选择操作。  
                    if (i.selector.select(1000) < 1) {
                        break;
                    }
                    //返回此选择器的已选择键集。  
                    selectionKeys = i.selector.selectedKeys();
                    //System.out.println(selectionKeys.size());  
                    iterator = selectionKeys.iterator();
                    if (iterator.hasNext()) {
                        selectionKey = iterator.next();
                        if (selectionKey.isReadable()) {
                            client = (java.nio.channels.SocketChannel) selectionKey.channel();
                            //将缓冲区清空以备下次读取  
                            i.i接收缓冲区.clear();
                            //读取服务器发送来的数据到缓冲区中  
                            int cont = client.read(i.i接收缓冲区);
                            ji自用.i密文_byteArray = i.i接收缓冲区.array();
                            ji自用.i密文_byteArray = java.util.Arrays.copyOf(ji自用.i密文_byteArray, cont);
                            client.register(i.selector, java.nio.channels.SelectionKey.OP_WRITE);
                            break;
                        } else if (selectionKey.isWritable()) {
                            i.i发送缓冲区.clear();
                            client = (java.nio.channels.SocketChannel) selectionKey.channel();
                            i.i发送缓冲区.put(ji自用.i密文_byteArray);
                            //将缓冲区各标志复位,因为向里面put了数据标志被改变要想从中读取数据发向服务器,就要复位  
                            i.i发送缓冲区.flip();
                            client.write(i.i发送缓冲区);
                            client.register(i.selector, java.nio.channels.SelectionKey.OP_READ);
                        }
                    }
                    selectionKeys.clear();
                }
            } catch (java.io.IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                ji未捕获错误_b = false;
                // <editor-fold defaultstate="collapsed" desc="抛出错误">
                ci_error.g添加错误信息("{类:'" + i类名_s + "',"
                        + "过程:'" + ji过程名_s + "',"
                        + "序号:'2'"
                        + "错误类型:'捕获',"
                        + "错误:'" + ex.getMessage() + "',"
                        + "错误1:'与服务器通讯中断，请联系机房管理员！'}");
                throw ci_error;
                // </editor-fold>
            }
            try {//解密、验证签名，取得返回结果
                 /*
                 * success:
                 * _:内容
                 */
                ji自用.i压缩後明文_byteArray = org.hzs.安全.AES.i解密_byteArray(ji自用.i密文_byteArray, i.AES_Key);
                ji自用.i压缩前明文_byteArray = org.hzs.压缩解压.Gzip.i解压_byteArray(ji自用.i压缩後明文_byteArray);
                ji自用.i_JSON = org.hzs.json.JSONObject.d副本();
                if (ji自用.i压缩前明文_byteArray == null) {
                    ji自用.i_JSON.set(new String(ji自用.i压缩後明文_byteArray, "UTF-8"), ci_error);
                } else {
                    ji自用.i_JSON.set(new String(ji自用.i压缩前明文_byteArray, "UTF-8"), ci_error);
                }
                ji未捕获错误_b = false;
                i.i会晤次数_i = 11;
                return ji自用.i_JSON.toString(ci_error);
            } catch (java.io.UnsupportedEncodingException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                ji未捕获错误_b = false;
                // <editor-fold defaultstate="collapsed" desc="抛出错误">
                ci_error.g添加错误信息("{类:'" + i类名_s + "',"
                        + "过程:'" + ji过程名_s + "',"
                        + "序号:'3'"
                        + "错误类型:'捕获',"
                        + "错误:'" + ex.getMessage() + "',"
                        + "错误1:'网路有可能被攻击或您的电脑中了木马、病毒！'}");
                throw ci_error;
                // </editor-fold>
            } catch (org.hzs.logging.error ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                ji未捕获错误_b = false;
                // <editor-fold defaultstate="collapsed" desc="抛出错误">
                ex.g添加错误信息("{类:'" + i类名_s + "',"
                        + "过程:'" + ji过程名_s + "',"
                        + "序号:'4'"
                        + "错误类型:'捕获',"
                        + "错误:'',"
                        + "错误1:'网路有可能被攻击或您的电脑中了木马、病毒！'}");
                throw ex;
                // </editor-fold>
            }
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            ji未捕获错误_b = false;
            // <editor-fold defaultstate="collapsed" desc="抛出错误">
            ci_error.g添加错误信息("{类:'" + i类名_s + "',"
                    + "过程:'" + ji过程名_s + "',"
                    + "序号:'5'"
                    + "错误类型:'',"
                    + "错误:'" + ex.getMessage() + "',"
                    + "错误1:'本地错误'}");
            throw ci_error;
            // </editor-fold>
        } finally {
            // <editor-fold defaultstate="collapsed" desc="释放资源">
            if (ji自用 != null) {
                ji自用.close();
                ji自用 = null;
            }
            ci_error = null;
            // </editor-fold>
            if (ji未捕获错误_b) {
                // <editor-fold defaultstate="collapsed" desc="抛出错误">
                ci_error.g添加错误信息("{类:'" + i类名_s + "',"
                        + "过程:'" + ji过程名_s + "',"
                        + "序号:''"
                        + "错误类型:'未捕获',"
                        + "错误:'',"
                        + "错误1:'已断开！'}");
                throw ci_error;
                // </editor-fold>
            }
        }
    }

    public void g退出() {
        if (i == null || i.selector == null) {
            return;
        }
        // <editor-fold defaultstate="collapsed" desc="自用">
        class 自用 implements Cloneable {

            public java.net.Socket d通信链 = null;
            public java.io.OutputStream d写出 = null;
            public byte[] i密文_byteArray = null;

            public 自用 d副本() throws CloneNotSupportedException {
                return (自用) super.clone();
            }

            public void close() {
                if (d写出 != null) {
                    try {
                        d写出.close();
                    } catch (java.io.IOException ex) {
                    }
                    d写出 = null;
                }
                if (d通信链 != null) {
                    try {
                        d通信链.close();
                    } catch (java.io.IOException ex) {
                    }
                    d通信链 = null;
                }
                i密文_byteArray = null;
            }
        }// </editor-fold>
        自用 ji自用 = null;
        try {
            ji自用 = new 自用();

            ji自用.i密文_byteArray = org.hzs.安全.AES.i加密_byteArray(i.i会晤号_byteArray, i.AES_Key);
            //在密文头部加入会晤号
            ji自用.i密文_byteArray = java.util.Arrays.copyOf(ji自用.i密文_byteArray, ji自用.i密文_byteArray.length + 4);
            System.arraycopy(ji自用.i密文_byteArray, 0, ji自用.i密文_byteArray, 4, ji自用.i密文_byteArray.length - 4);
            System.arraycopy(i.i会晤号_byteArray, 0, ji自用.i密文_byteArray, 0, 4);
            //
            java.util.Set<java.nio.channels.SelectionKey> selectionKeys;
            java.util.Iterator<java.nio.channels.SelectionKey> iterator;
            java.nio.channels.SelectionKey selectionKey;
            java.nio.channels.SocketChannel client;
            while (true) {
                if (i.selector.select(1000) < 1) {
                    break;
                }
                //返回此选择器的已选择键集。  
                selectionKeys = i.selector.selectedKeys();
                //System.out.println(selectionKeys.size());  
                iterator = selectionKeys.iterator();
                if (iterator.hasNext()) {
                    selectionKey = iterator.next();
                    if (selectionKey.isWritable()) {
                        i.i发送缓冲区.clear();
                        client = (java.nio.channels.SocketChannel) selectionKey.channel();
                        i.i发送缓冲区.put(ji自用.i密文_byteArray);
                        i.i发送缓冲区.flip();
                        client.write(i.i发送缓冲区);
                        client.close();
                    }
                }
            }
        } catch (java.io.IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (ji自用 != null) {
                ji自用.close();
                ji自用 = null;
            }
        }
    }

    private class 维持连接 extends Thread {

        private boolean i继续_b = true;

        @Override
        public void interrupt() {
            i继续_b = false;
            super.interrupt();
        }

        @Override
        public void run() {
            org.hzs.logging.error ji_error = null;
            try {
                ji_error = org.hzs.logging.error.d副本();
                while (i继续_b) {
                    try {
                        Thread.sleep(5_000);//5″循环一次，当会晤次数小于等于零，则会晤服务器，确保服务器不失效
                    } catch (InterruptedException ex) {
                    }
                    i.i会晤次数_i--;
                    if (i.i会晤次数_i <= 0) {
                        try {
                            g会晤服务器("{}", "{}", ji_error);
                        } catch (org.hzs.logging.error ex) {
                            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                ji_error = null;
            }
        }
    }
}
