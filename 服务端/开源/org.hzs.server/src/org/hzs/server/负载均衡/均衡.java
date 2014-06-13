package org.hzs.server.负载均衡;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class 均衡 extends __ {

    private static final java.util.Random d不确定數 = new java.util.Random();
    private int i内部端口_i, i外部端口_i;
    private static int i业务处理端口_i;

    protected static void init_() throws java.net.SocketException, java.io.IOException {
        if (i公网IP_s == null) {
            return;
        }
        i业务处理端口_i = i中心端口_i + 1;
        org.hzs.server.负载均衡.Property.d线程池.execute(new 请求开始业务(i中心端口_i));
        org.hzs.server.负载均衡.Property.d线程池.execute(new 监聽请求开始业务());
    }

    public 均衡(final int ci端口偏移_i, final org.hzs.server.业务.__ cd业务) throws java.net.SocketException, java.io.IOException {
        if (!org.hzs.server.负载均衡.Property.i业务服务_b) {
            return;
        }
        d业务 = cd业务;
        i内部端口_i = i中心端口_i - ci端口偏移_i - 1;
        i外部端口_i = i中心端口_i + ci端口偏移_i + 1;
        org.hzs.server.负载均衡.Property.d线程池.execute(new 监聽对内业务(i内部端口_i));
        if (i公网IP_s != null) {
            org.hzs.server.负载均衡.Property.d线程池.execute(new 监聽对外业务(i外部端口_i));
        }
    }

    private class 监聽对外业务 implements Runnable {

        private java.nio.channels.Selector d对外_selector = null;
        private final java.util.TreeMap<String, java.nio.channels.SelectionKey> i客户与业务对应 = new java.util.TreeMap<String, java.nio.channels.SelectionKey>();
        private final java.util.TreeMap<String, java.nio.ByteBuffer> i发送缓冲区集 = new java.util.TreeMap<String, java.nio.ByteBuffer>();
        private final java.util.TreeMap<String, java.nio.channels.Selector> d对内_selector = new java.util.TreeMap<String, java.nio.channels.Selector>();
        private final java.util.TreeMap<String, org.hzs.server.负载均衡.Session> d内对应1_selector = new java.util.TreeMap<String, org.hzs.server.负载均衡.Session>();
        private final java.util.TreeMap<String, Integer> d内对应2_selector = new java.util.TreeMap<String, Integer>();//记录业务sessionID

        public 监聽对外业务(final int port) throws java.io.IOException {
            // 打开服务器套接字通道  
            java.nio.channels.ServerSocketChannel serverSocketChannel = java.nio.channels.ServerSocketChannel.open();
            // 服务器配置为非阻塞  
            serverSocketChannel.configureBlocking(false);
            // 检索与此通道关联的服务器套接字  
            java.net.ServerSocket serverSocket = serverSocketChannel.socket();
            // 进行服务的绑定  
            serverSocket.bind(new java.net.InetSocketAddress(port));
            // 通过open()方法找到Selector  
            d对外_selector = java.nio.channels.Selector.open();
            // 注册到selector，等待连接  
            serverSocketChannel.register(d对外_selector, java.nio.channels.SelectionKey.OP_ACCEPT);
        }

        @Override
        public void run() {// 监听
            class 自用 {

                java.nio.ByteBuffer i接收缓冲区 = java.nio.ByteBuffer.allocate(org.hzs.server.负载均衡.Property.i接收缓冲区容量_i), i发送缓冲区 = null;
                java.nio.channels.ServerSocketChannel server = null;
                java.nio.channels.SocketChannel client外 = null, client内 = null;
                java.util.Set<java.nio.channels.SelectionKey> selectionKeys外 = null, selectionKeys内 = null;
                java.util.Iterator<java.nio.channels.SelectionKey> iterator外 = null, iterator内 = null;
                java.nio.channels.SelectionKey selectionKey外 = null, selectionKey内 = null;
                byte[] i_byteArray = null;
                int cont;
            }
            自用 ji自用 = new 自用();
            while (true) {
                try {
                    // 选择一组键，并且相应的通道已经打开  
                    d对外_selector.select();
                    // 返回此选择器的已选择键集。  
                    ji自用.selectionKeys外 = d对外_selector.selectedKeys();
                    ji自用.iterator外 = ji自用.selectionKeys外.iterator();
                    while (ji自用.iterator外.hasNext()) {
                        ji自用.selectionKey外 = ji自用.iterator外.next();
                        // 接受请求  
                        // 测试此键的通道是否已准备好接受新的套接字连接。  
                        if (ji自用.selectionKey外.isAcceptable()) {
                            // 返回为之创建此键的通道。  
                            ji自用.server = (java.nio.channels.ServerSocketChannel) ji自用.selectionKey外.channel();
                            // 接受到此通道套接字的连接。  
                            // 此方法返回的套接字通道（如果有）将处于阻塞模式。  
                            ji自用.client外 = ji自用.server.accept();
                            // 配置为非阻塞 
                            ji自用.client外.configureBlocking(false);
                            // 注册到selector，等待连接  
                            ji自用.client外.register(d对外_selector, java.nio.channels.SelectionKey.OP_READ);
                            ji自用.iterator外.remove();
                        } else if (ji自用.selectionKey外.isReadable()) {
                            // 返回为之创建此键的通道。  
                            ji自用.client外 = (java.nio.channels.SocketChannel) ji自用.selectionKey外.channel();
                            //将缓冲区清空以备下次读取  
                            ji自用.i接收缓冲区.clear();
                            //读取服务器发送来的数据到缓冲区中  
                            try {
                                ji自用.cont = ji自用.client外.read(ji自用.i接收缓冲区);
                            } catch (java.io.IOException ex) {//处理客户端意外非正常关闭情况
                                Logger.getLogger(均衡.class.getName()).log(Level.SEVERE, null, ex);
                                ji自用.client外.close();
                                continue;
                            }
                            if (ji自用.cont < 4) {
                                ji自用.i发送缓冲区 = java.nio.ByteBuffer.allocate(org.hzs.server.负载均衡.Property.i发送缓冲区容量_i);
                                ji自用.i发送缓冲区.put("{success:true,_:''}".getBytes("UTF-8"));
                                ji自用.i发送缓冲区.flip();
                                i发送缓冲区集.put(ji自用.client外.toString(), ji自用.i发送缓冲区);
                                ji自用.client外.register(d对外_selector, java.nio.channels.SelectionKey.OP_WRITE);
                            } else {
                                //启动线程，处理客户端返回来的数据。启动线程的目的是防止由于运算造成的阻塞
                                ji自用.i_byteArray = ji自用.i接收缓冲区.array();
                                ji自用.i_byteArray = java.util.Arrays.copyOf(ji自用.i_byteArray, ji自用.cont);
                                ji自用.i_byteArray = ji第一步_由客户數据to需处理數据(ji自用.client外, ji自用.i_byteArray);//取得客户数据
                                if (ji自用.i_byteArray != null) {
                                    jg第二步_向服务发送數据(ji自用.selectionKey外, ji自用.client外, ji自用.i_byteArray);//将整理后的数据交给业务服务器
//                                    client.register(d对外_selector, SelectionKey.OP_WRITE);
                                }
                            }
                            ji自用.iterator外.remove();
                        } else if (ji自用.selectionKey外.isWritable()) {
                            ji自用.client外 = (java.nio.channels.SocketChannel) ji自用.selectionKey外.channel();
                            ji自用.i发送缓冲区 = i发送缓冲区集.remove(ji自用.client外.toString());
                            if (ji自用.i发送缓冲区 != null) {
                                ji自用.client外.write(ji自用.i发送缓冲区);
                                ji自用.client外.register(d对外_selector, java.nio.channels.SelectionKey.OP_READ);
                            } else {
                                ji自用.selectionKey内 = (java.nio.channels.SelectionKey) i客户与业务对应.get(ji自用.selectionKey外.toString());
                                ji自用.selectionKey内.selector().select(1);
                                if (!ji自用.selectionKey内.isReadable()) {
                                    continue;
                                }
                                ji自用.client内 = (java.nio.channels.SocketChannel) ji自用.selectionKey内.channel();
                                ji自用.i发送缓冲区 = java.nio.ByteBuffer.allocate(org.hzs.server.负载均衡.Property.i发送缓冲区容量_i);
                                ji自用.cont = ji自用.client内.read(ji自用.i发送缓冲区);
                                ji自用.i_byteArray = java.util.Arrays.copyOf(ji自用.i_byteArray, ji自用.cont);
                                ji自用.i_byteArray = jg业务处理(ji自用.client外, ji自用.i发送缓冲区.array());
                                ji自用.i发送缓冲区.clear();
                                ji自用.i发送缓冲区.put(ji自用.i_byteArray);
                                ji自用.i发送缓冲区.flip();
                                ji自用.client外.write(ji自用.i发送缓冲区);

                                ji自用.client内.register(ji自用.selectionKey内.selector(), java.nio.channels.SelectionKey.OP_WRITE);
                                ji自用.client外.register(d对外_selector, java.nio.channels.SelectionKey.OP_READ);

                                i客户与业务对应.remove(ji自用.selectionKey外.toString());
                            }
                            ji自用.iterator外.remove();
                        }
                    }
                } catch (java.io.IOException ex) {
                    Logger.getLogger(均衡.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        private byte[] ji第一步_由客户數据to需处理數据(final java.nio.channels.SocketChannel client, final byte[] ci_byteArray) {
            // <editor-fold defaultstate="collapsed" desc="自用">
            class 自用 implements Cloneable, org.hzs.Close {

                byte[] session_byteArray = null, i_byteArray = null, i客户端传入_byteArray = null;
                int sessionid_i;
                org.hzs.server.负载均衡.Session session = null;
                org.hzs.json.JSONObject i_JSON = null;

                自用 d副本() throws CloneNotSupportedException {
                    自用 jd = (自用) super.clone();
                    return jd;
                }
            }// </editor-fold>
            自用 ji自用 = null;
            org.hzs.logging.error ji_error = null;
            boolean ji未捕获错误_b = true;
            try {
                // <editor-fold defaultstate="collapsed" desc="利用对象复制初始化过程内部变量">
                ji自用 = (自用) org.hzs.常用.d对象池.get(自用.class.getName());
                if (ji自用 == null) {
                    ji自用 = new 自用();
                    org.hzs.常用.d对象池.put(自用.class.getName(), ji自用);
                }
                ji自用 = ji自用.d副本();
                ji_error = org.hzs.logging.error.d副本();
                // </editor-fold>
                ji自用.i客户端传入_byteArray = ci_byteArray;
                //解析出session并传递给业务处理模块
                //***取会晤号
                ji自用.i_byteArray = new byte[4];
                System.arraycopy(ji自用.i客户端传入_byteArray, 0, ji自用.i_byteArray, 0, 4);
                ji自用.sessionid_i = org.hzs.lang.转换.byteArray_2_int(ji自用.i_byteArray);
                System.arraycopy(ji自用.i客户端传入_byteArray, 4, ji自用.i客户端传入_byteArray, 0, ji自用.i客户端传入_byteArray.length - 4);
                ji自用.i客户端传入_byteArray = java.util.Arrays.copyOf(ji自用.i客户端传入_byteArray, ji自用.i客户端传入_byteArray.length - 4);
                //处理内部session
                ji自用.session = session_集合.get(ji自用.sessionid_i);
                if (ji自用.session.i正在服务_b) {
                    java.nio.ByteBuffer ji发送缓冲区 = java.nio.ByteBuffer.allocate(org.hzs.server.负载均衡.Property.i发送缓冲区容量_i);
                    byte[] ji返回给客户端_byteArray = "{success:true,_:'正在为您服务...'}".getBytes("UTF-8");
                    ji返回给客户端_byteArray = org.hzs.压缩解压.Gzip.i压缩_byteArray(ji返回给客户端_byteArray);
                    ji返回给客户端_byteArray = org.hzs.安全.AES.i加密_byteArray(ji返回给客户端_byteArray, ji自用.session.AES_Key);
                    //向缓冲区中输入数据  
                    ji发送缓冲区.put(ji返回给客户端_byteArray);
                    //将缓冲区各标志复位,因为向里面put了数据标志被改变要想从中读取数据发向服务器,就要复位  
                    ji发送缓冲区.flip();

                    i发送缓冲区集.put(client.toString(), ji发送缓冲区);
                    //
                    client.register(d对外_selector, java.nio.channels.SelectionKey.OP_WRITE);//出现并发访问，则重新开始读取服务
                    ji未捕获错误_b = false;
                    return null;
                }
                ji自用.session.i正在服务_b = true;

                ji自用.session.i最近使用时间_l = java.util.Calendar.getInstance().getTimeInMillis();

                ji自用.i客户端传入_byteArray = org.hzs.安全.AES.i解密_byteArray(ji自用.i客户端传入_byteArray, ji自用.session.AES_Key);
                if (java.util.Arrays.equals(ji自用.i客户端传入_byteArray, org.hzs.lang.转换.int_2_byteArray(ji自用.sessionid_i))) {//传送过来的内容为加密session_id时，为退出操作
                    d业务.removeSession(ji自用.sessionid_i);
                    session_集合.remove(ji自用.sessionid_i);
                    ji自用.session.close();
                    client.close();
                    ji未捕获错误_b = false;
                    return null;
                }
                ji自用.i_byteArray = org.hzs.压缩解压.Gzip.i解压_byteArray(ji自用.i客户端传入_byteArray);
                if (ji自用.i_byteArray == null) {
                    ji自用.i_byteArray = ji自用.i客户端传入_byteArray;
                }
                ji自用.i_JSON = org.hzs.json.JSONObject.d副本().set(new String(ji自用.i_byteArray, "UTF-8"), ji_error);
                if (ji自用.i_JSON.getJSONObject("$", ji_error).size() == 0) {//维持连接
                    java.nio.ByteBuffer ji发送缓冲区 = java.nio.ByteBuffer.allocate(org.hzs.server.负载均衡.Property.i发送缓冲区容量_i);
                    byte[] ji返回给客户端_byteArray = "{success:true,_:''}".getBytes("UTF-8");
                    ji返回给客户端_byteArray = org.hzs.安全.AES.i加密_byteArray(ji返回给客户端_byteArray, ji自用.session.AES_Key);
                    //向缓冲区中输入数据  
                    ji发送缓冲区.put(ji返回给客户端_byteArray);
                    //将缓冲区各标志复位,因为向里面put了数据标志被改变要想从中读取数据发向服务器,就要复位  
                    ji发送缓冲区.flip();

                    i发送缓冲区集.put(client.toString(), ji发送缓冲区);
                    //
                    client.register(d对外_selector, java.nio.channels.SelectionKey.OP_WRITE);//出现并发访问，则重新开始读取服务

                    ji自用.session.i正在服务_b = false;

                    ji未捕获错误_b = false;
                    return null;
                }
                //取得session，
                ji自用.session_byteArray = d业务.getSession(ji自用.sessionid_i).toString(ji_error).getBytes("UTF-8");//取得业务的session
                ji自用.session_byteArray = org.hzs.压缩解压.Gzip.i压缩_byteArray(ji自用.session_byteArray);
                ji自用.i_byteArray = org.hzs.lang.转换.short_2_byteArray((short) ji自用.session_byteArray.length);
                {//拼接session与客户数据
                    d内对应1_selector.put(client.toString(), ji自用.session);
                    d内对应2_selector.put(client.toString(), ji自用.sessionid_i);

                    byte[] ji交给业务处理的_byteArray = new byte[2 + ji自用.session_byteArray.length + ji自用.i客户端传入_byteArray.length];
                    System.arraycopy(ji自用.i_byteArray, 0, ji交给业务处理的_byteArray, 0, 2);
                    System.arraycopy(ji自用.session_byteArray, 0, ji交给业务处理的_byteArray, 2, ji自用.session_byteArray.length);
                    System.arraycopy(ji自用.i客户端传入_byteArray, 0, ji交给业务处理的_byteArray, 2 + ji自用.session_byteArray.length, ji自用.i客户端传入_byteArray.length);
                    ji未捕获错误_b = false;
                    return ji交给业务处理的_byteArray;
                }
            } catch (java.io.IOException | CloneNotSupportedException | org.hzs.logging.error ex) {
                Logger.getLogger(均衡.class.getName()).log(Level.SEVERE, null, ex);
                ji未捕获错误_b = false;
                return null;
            } finally {
                // <editor-fold defaultstate="collapsed" desc="释放资源">
                if (ji自用 != null) {
                    ji自用.close();
                    ji自用 = null;
                }
                // </editor-fold>
                if (ji未捕获错误_b) {
                }
            }
        }

        private void jg第二步_向服务发送數据_1(final java.nio.channels.SelectionKey selectionKey, final java.net.InetSocketAddress cd通信链, final byte[] ci待发送數据_byteArray) throws java.io.IOException {
            class 自用 {

                java.util.Set<java.nio.channels.SelectionKey> selectionKeys = null;
                java.util.Iterator<java.nio.channels.SelectionKey> iterator = null;
                java.nio.channels.SelectionKey selectionKey = null;
                java.nio.channels.SocketChannel client = null;
                java.nio.channels.Selector selector = null;
                java.nio.ByteBuffer sendbuffer = java.nio.ByteBuffer.allocate(org.hzs.server.负载均衡.Property.i发送缓冲区容量_i);
                boolean i_b = false;
            }
            自用 ji自用 = new 自用();
            //
            ji自用.selector = d对内_selector.get(cd通信链.toString());
            if (ji自用.selector == null) {
                ji自用.selector = java.nio.channels.Selector.open();// 打开socket通道  
                ji自用.client = java.nio.channels.SocketChannel.open();// 打开选择器  
                ji自用.client.configureBlocking(false);// 设置为非阻塞方式  
                ji自用.client.register(ji自用.selector, java.nio.channels.SelectionKey.OP_CONNECT);// 注册连接服务端socket动作  
                ji自用.client.connect(cd通信链);// 连接  
                d对内_selector.put(cd通信链.toString(), ji自用.selector);
            }
            for (;;) {
                //选择一组键，其相应的通道已为 I/O 操作准备就绪。  
                //此方法执行处于阻塞模式的选择操作。  
                ji自用.selector.select();
                //返回此选择器的已选择键集。  
                ji自用.selectionKeys = ji自用.selector.selectedKeys();
                ji自用.iterator = ji自用.selectionKeys.iterator();
                while (ji自用.iterator.hasNext()) {
                    ji自用.selectionKey = ji自用.iterator.next();
                    if (ji自用.selectionKey.isConnectable()) {
                        ji自用.client = (java.nio.channels.SocketChannel) ji自用.selectionKey.channel();
                        // 判断此通道上是否正在进行连接操作。  
                        // 完成套接字通道的连接过程。  
                        if (ji自用.client.isConnectionPending()) {
                            ji自用.client.finishConnect();
                            ji自用.sendbuffer.clear();
                            ji自用.sendbuffer.put(ci待发送數据_byteArray);
                            ji自用.sendbuffer.flip();
                            ji自用.client.write(ji自用.sendbuffer);
                            i客户与业务对应.put(selectionKey.toString(), ji自用.selectionKey);//添加转发
                        }
                        ji自用.client.register(ji自用.selector, java.nio.channels.SelectionKey.OP_READ);
                        ji自用.i_b = true;
                    } else if (ji自用.selectionKey.isWritable()) {
                        ji自用.sendbuffer.clear();
                        ji自用.client = (java.nio.channels.SocketChannel) ji自用.selectionKey.channel();
                        ji自用.sendbuffer.put(ci待发送數据_byteArray);
                        //将缓冲区各标志复位,因为向里面put了数据标志被改变要想从中读取数据发向服务器,就要复位  
                        ji自用.sendbuffer.flip();
                        ji自用.client.write(ji自用.sendbuffer);
                        ji自用.client.register(ji自用.selector, java.nio.channels.SelectionKey.OP_READ);
                        i客户与业务对应.put(selectionKey.toString(), ji自用.selectionKey);//添加转发
                        ji自用.i_b = true;
                    }
                }
                ji自用.selectionKeys.clear();
                if (ji自用.i_b) {
                    return;
                }
            }
        }

        private void jg第二步_向服务发送數据(final java.nio.channels.SelectionKey selectionKey, final java.nio.channels.SocketChannel client, final byte[] ci待发送數据_byteArray) {
            // <editor-fold defaultstate="collapsed" desc="自用">
            class 自用 implements Cloneable, org.hzs.Close {

                byte[] session_byteArray = null;
                org.hzs.server.负载均衡.Session session = null;

                自用 d副本() throws CloneNotSupportedException {
                    自用 jd = (自用) super.clone();
                    return jd;
                }
            }// </editor-fold>
            // <editor-fold defaultstate="collapsed" desc="会晤">
            class 会晤 implements Cloneable, org.hzs.Close {

                java.net.InetSocketAddress d通信链 = null;
                java.net.InetAddress address = null;

                会晤 d副本() throws CloneNotSupportedException {
                    return (会晤) super.clone();
                }
            }// </editor-fold>
            自用 ji自用 = null;
            会晤 ji会晤 = null;
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
                // </editor-fold>
                ji自用.session = d内对应1_selector.get(client.toString());
                //随机选择业务服务器，不仅能降低网路压力，而且确保每个业务节点的压力相仿。
                for (;;) {
                    String ji键_s = null;
                    int ji权重_i = d不确定數.nextInt(org.hzs.server.负载均衡.__.i内总权重_i);
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
                    ji会晤.d通信链 = (java.net.InetSocketAddress) org.hzs.常用.d对象池.get(ji键_s + ":" + i内部端口_i);
                    if (ji会晤.d通信链 == null) {
                        ji会晤.d通信链 = new java.net.InetSocketAddress(ji键_s, i内部端口_i);
                        org.hzs.常用.d对象池.put(ji键_s + ":" + i内部端口_i, ji会晤.d通信链);
                    }
                    try {
                        jg第二步_向服务发送數据_1(selectionKey, ji会晤.d通信链, ci待发送數据_byteArray);
                        ////如果出现转交，则访问频率增加，否则不增加，以防非黑客捣乱带来影响。
                        ji自用.session.i访问频率_i++;
                        break;
                    } catch (java.io.IOException ex) {
                        //将服务专向寻得的务器；如果不能连通，则移除此服务器，并继续寻工作量最小的服务器
                        org.hzs.server.负载均衡.__.i内总权重_i -= i内服务器列表.get(ji键_s);
                        i内服务器列表.remove(ji键_s);
                    }
                }
                client.register(d对外_selector, java.nio.channels.SelectionKey.OP_WRITE);//取得返回值，开始监听写操作
                ji未捕获错误_b = false;
            } catch (java.io.IOException | CloneNotSupportedException ex) {
                Logger.getLogger(均衡.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                // <editor-fold defaultstate="collapsed" desc="释放资源">
                if (ji自用 != null) {
                    ji自用.close();
                    ji自用 = null;
                }
                if (ji会晤 != null) {
                    ji会晤.close();
                    ji会晤 = null;
                }
                // </editor-fold>
                if (ji未捕获错误_b) {
                }
            }
        }

        private byte[] jg业务处理(final java.nio.channels.SocketChannel client, final byte[] i_byteArray) {
            // <editor-fold defaultstate="collapsed" desc="自用">
            class 自用 implements Cloneable, org.hzs.Close {

                byte[] i_byteArray = null;
                org.hzs.json.JSONObject i_JSON = null;
                org.hzs.server.负载均衡.Session session = null;

                自用 d副本() throws CloneNotSupportedException {
                    自用 jd = (自用) super.clone();
                    return jd;
                }
            }// </editor-fold>
            // <editor-fold defaultstate="collapsed" desc="会晤">
            class 会晤 implements Cloneable, org.hzs.Close {

                byte[] i_byteArray = null;
                java.io.InputStream d读入 = null;
                java.io.OutputStream d写出 = null;
                java.net.InetSocketAddress d通信链 = null;
                java.net.InetAddress address = null;

                会晤 d副本() throws CloneNotSupportedException {
                    return (会晤) super.clone();
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
                ji会晤.i_byteArray = i_byteArray;
                //处理返回结果
                try {
                    ji自用.i_byteArray = org.hzs.压缩解压.Gzip.i解压_byteArray(ji会晤.i_byteArray);
                    if (ji自用.i_byteArray != null) {
                        ji会晤.i_byteArray = ji自用.i_byteArray;
                    }

                    ji自用.i_JSON = org.hzs.json.JSONObject.d副本();
                    ////回写session
                    ji自用.i_JSON.set(new String(ji会晤.i_byteArray, "UTF-8"), ji_error);
                    d业务.getSession(d内对应2_selector.remove(client.toString())).set(ji自用.i_JSON.getString("$", ji_error), ji_error);
                    //将服务结果交给客户
                    ji会晤.i_byteArray = ji自用.i_JSON.getString("_", ji_error).getBytes("UTF-8");
                    ji自用.i_byteArray = org.hzs.压缩解压.Gzip.i压缩_byteArray(ji会晤.i_byteArray);
                    if (ji自用.i_byteArray.length < ji会晤.i_byteArray.length) {
                        ji会晤.i_byteArray = ji自用.i_byteArray;
                    }
                } catch (org.hzs.logging.error | java.io.UnsupportedEncodingException ex) {
                    Logger.getLogger(均衡.class.getName()).log(Level.SEVERE, null, ex);
                }
                ji自用.session = d内对应1_selector.remove(client.toString());
                ji会晤.i_byteArray = org.hzs.安全.AES.i加密_byteArray(ji会晤.i_byteArray, ji自用.session.AES_Key);
                ji自用.session.i正在服务_b = false;
                ji自用.session = null;
                ji未捕获错误_b = false;
                return ji会晤.i_byteArray;
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(均衡.class.getName()).log(Level.SEVERE, null, ex);
                ji未捕获错误_b = false;
                return null;
            } finally {
                // <editor-fold defaultstate="collapsed" desc="释放资源">
                if (ji自用 != null) {
                    ji自用.close();
                    ji自用 = null;
                }
                if (ji会晤 != null) {
                    ji会晤.close();
                    ji会晤 = null;
                }
                // </editor-fold>
                if (ji未捕获错误_b) {
                }
            }
        }
    }

    private class 监聽对内业务 implements Runnable {

        private java.nio.channels.Selector selector = null;
        private java.util.TreeMap<String, java.nio.ByteBuffer> i发送缓冲区集 = new java.util.TreeMap<String, java.nio.ByteBuffer>();
        private java.nio.ByteBuffer i接收缓冲区 = null;
        private Runnable_ d通信 = new Runnable_();

        public 监聽对内业务(final int port) throws java.io.IOException {
            i接收缓冲区 = java.nio.ByteBuffer.allocate(org.hzs.server.负载均衡.Property.i接收缓冲区容量_i);
            // 打开服务器套接字通道  
            java.nio.channels.ServerSocketChannel serverSocketChannel = java.nio.channels.ServerSocketChannel.open();
            // 服务器配置为非阻塞  
            serverSocketChannel.configureBlocking(false);
            // 检索与此通道关联的服务器套接字  
            java.net.ServerSocket serverSocket = serverSocketChannel.socket();
            // 进行服务的绑定  
            serverSocket.bind(new java.net.InetSocketAddress(port));
            // 通过open()方法找到Selector  
            selector = java.nio.channels.Selector.open();
            // 注册到selector，等待连接  
            serverSocketChannel.register(selector, java.nio.channels.SelectionKey.OP_ACCEPT);
        }

        @Override
        public void run() {// 监听
            class 自用 {

                java.nio.ByteBuffer i发送缓冲区 = null;
                java.nio.channels.ServerSocketChannel server = null;
                java.nio.channels.SocketChannel client = null;
                java.util.Set<java.nio.channels.SelectionKey> selectionKeys = null;
                java.util.Iterator<java.nio.channels.SelectionKey> iterator = null;
                java.nio.channels.SelectionKey selectionKey = null;
                byte[] i_byteArray = null;
                int cont;
            }
            自用 ji自用 = new 自用();
            while (true) {
                try {
                    // 选择一组键，并且相应的通道已经打开  
                    ji自用.cont = selector.select(1);
                    if (ji自用.cont == 0) {
                        continue;
                    }
                    // 返回此选择器的已选择键集。  
                    ji自用.selectionKeys = selector.selectedKeys();
                    ji自用.iterator = ji自用.selectionKeys.iterator();
                    while (ji自用.iterator.hasNext()) {
                        ji自用.selectionKey = ji自用.iterator.next();
                        ji自用.iterator.remove();
                        if (ji自用.selectionKey.isAcceptable()) {
                            // 返回为之创建此键的通道。  
                            ji自用.server = (java.nio.channels.ServerSocketChannel) ji自用.selectionKey.channel();
                            // 接受到此通道套接字的连接。  
                            // 此方法返回的套接字通道（如果有）将处于阻塞模式。  
                            ji自用.client = ji自用.server.accept();
                            // 配置为非阻塞 
                            ji自用.client.configureBlocking(false);
                            // 注册到selector，等待连接  
                            ji自用.client.register(selector, java.nio.channels.SelectionKey.OP_READ);
                        } else if (ji自用.selectionKey.isReadable()) {
                            // 返回为之创建此键的通道。  
                            ji自用.client = (java.nio.channels.SocketChannel) ji自用.selectionKey.channel();
                            //将缓冲区清空以备下次读取  
                            i接收缓冲区.clear();
                            //读取服务器发送来的数据到缓冲区中  
                            ji自用.cont = ji自用.client.read(i接收缓冲区);
//                            i发送缓冲区集.put(ji自用.client.toString(), i接收缓冲区);
//                            ji自用.client.register(selector, SelectionKey.OP_WRITE);
                            ji自用.i_byteArray = i接收缓冲区.array();
                            ji自用.i_byteArray = java.util.Arrays.copyOf(ji自用.i_byteArray, ji自用.cont);
                            //启动线程，处理客户端返回来的数据。启动线程的目的是防止由于运算造成的阻塞
                            Runnable_ jd通信 = d通信.d副本(ji自用.client, ji自用.i_byteArray);
                            org.hzs.server.负载均衡.Property.d线程池.execute(jd通信);
                        } else if (ji自用.selectionKey.isWritable()) {
                            // 返回为之创建此键的通道。  
                            ji自用.client = (java.nio.channels.SocketChannel) ji自用.selectionKey.channel();
                            //输出到通道  
//                            ji自用.client.write(i接收缓冲区);
                            ji自用.i发送缓冲区 = i发送缓冲区集.remove(ji自用.client.toString());
                            if (ji自用.i发送缓冲区 != null) {
                                ji自用.client.write(ji自用.i发送缓冲区);
                                ji自用.client.register(selector, java.nio.channels.SelectionKey.OP_READ);
                            }
                        }
                    }
                } catch (java.io.IOException | CloneNotSupportedException ex) {
                    Logger.getLogger(均衡.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        private class Runnable_ implements Runnable, Cloneable {

            private java.nio.channels.SocketChannel client = null;
            private byte[] i_byteArray = null;

            @Override
            public void run() {
                // <editor-fold defaultstate="collapsed" desc="自用">
                class 自用 implements Cloneable, org.hzs.Close {

                    byte[] i缓冲_byteArray = new byte[1024], session = null;
                    org.hzs.json.JSONObject i_JSON = null;

                    自用 d副本() throws CloneNotSupportedException {
                        自用 jd = (自用) super.clone();
                        jd.i缓冲_byteArray = jd.i缓冲_byteArray.clone();
                        return jd;
                    }
                }// </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="会晤">
                class 会晤 implements Cloneable, org.hzs.Close {

                    byte[] i_byteArray = null;
                    java.nio.ByteBuffer i发送缓冲区 = null;

                    会晤 d副本() throws CloneNotSupportedException {
                        return (会晤) super.clone();
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
                    if (i集群内服务器列表.indexOf(client.socket().getInetAddress().getHostAddress()) < 0) {
                        ji会晤.i发送缓冲区 = java.nio.ByteBuffer.allocate(org.hzs.server.负载均衡.Property.i发送缓冲区容量_i);
                        //向缓冲区中输入数据  
                        ji会晤.i发送缓冲区.put("{success:true,_:''}".getBytes("UTF-8"));
                        //将缓冲区各标志复位,因为向里面put了数据标志被改变要想从中读取数据发向服务器,就要复位  
                        ji会晤.i发送缓冲区.flip();
                        //
                        i发送缓冲区集.put(client.toString(), ji会晤.i发送缓冲区);
                        ji会晤.i发送缓冲区 = null;

                        return;
                    }
                    //
                    ji会晤.i_byteArray = i_byteArray;
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

                        ji会晤.i发送缓冲区 = java.nio.ByteBuffer.allocate(org.hzs.server.负载均衡.Property.i发送缓冲区容量_i);
                        //向缓冲区中输入数据  
                        ji会晤.i发送缓冲区.put(ji会晤.i_byteArray);
                        //将缓冲区各标志复位,因为向里面put了数据标志被改变要想从中读取数据发向服务器,就要复位  
                        ji会晤.i发送缓冲区.flip();
                    } else {
                        ji会晤.i发送缓冲区 = java.nio.ByteBuffer.allocate(org.hzs.server.负载均衡.Property.i发送缓冲区容量_i);
                        //向缓冲区中输入数据  
                        ji会晤.i发送缓冲区.put("{success:true,_:''}".getBytes("UTF-8"));
                    }
                    i发送缓冲区集.put(client.toString(), ji会晤.i发送缓冲区);
                    ji会晤.i发送缓冲区 = null;
                } catch (java.io.IOException | CloneNotSupportedException ex) {
                    Logger.getLogger(均衡.class.getName()).log(Level.SEVERE, null, ex);
                    ji会晤.i发送缓冲区 = java.nio.ByteBuffer.allocate(org.hzs.server.负载均衡.Property.i发送缓冲区容量_i);
                    //向缓冲区中输入数据  
                    ji会晤.i发送缓冲区.put("{success:true,_:''}".getBytes());
                    //将缓冲区各标志复位,因为向里面put了数据标志被改变要想从中读取数据发向服务器,就要复位  
                    ji会晤.i发送缓冲区.flip();
                    //
                    i发送缓冲区集.put(client.toString(), ji会晤.i发送缓冲区);
                    ji会晤.i发送缓冲区 = null;
                } finally {
                    try {
                        client.register(selector, java.nio.channels.SelectionKey.OP_WRITE);//取得返回值，开始监听写操作
                    } catch (java.nio.channels.ClosedChannelException ex) {
                        Logger.getLogger(均衡.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    // <editor-fold defaultstate="collapsed" desc="释放资源">
                    if (ji自用 != null) {
                        ji自用.close();
                        ji自用 = null;
                    }
                    if (ji会晤 != null) {
                        ji会晤.close();
                        ji会晤 = null;
                    }
                    // </editor-fold>
                }
            }

            Runnable_ d副本(final java.nio.channels.SocketChannel client, final byte[] ci_byteArray) throws CloneNotSupportedException, java.net.SocketException {
                Runnable_ jd = (Runnable_) super.clone();
                jd.client = client;
//                jd.client.socket().setSoTimeout(org.hzs.server.负载均衡.Property.i连接时限_i);
                jd.i_byteArray = ci_byteArray;
                return jd;
            }
        }
    }

    private static class 请求开始业务 implements Runnable {

        private java.nio.ByteBuffer i接收缓冲区 = null;
        private java.util.TreeMap<String, java.nio.ByteBuffer> i发送缓冲区集 = new java.util.TreeMap<String, java.nio.ByteBuffer>();
        private java.util.TreeMap<String, java.nio.channels.SocketChannel> i移除集 = new java.util.TreeMap<String, java.nio.channels.SocketChannel>();
        private java.nio.channels.Selector selector;

        public 请求开始业务(int port) throws java.io.IOException {
            i接收缓冲区 = java.nio.ByteBuffer.allocate(org.hzs.server.负载均衡.Property.i接收缓冲区容量_i);
            // 打开服务器套接字通道  
            java.nio.channels.ServerSocketChannel serverSocketChannel = java.nio.channels.ServerSocketChannel.open();
            // 服务器配置为非阻塞  
            serverSocketChannel.configureBlocking(false);
            // 检索与此通道关联的服务器套接字  
            java.net.ServerSocket serverSocket = serverSocketChannel.socket();
            // 进行服务的绑定  
            serverSocket.bind(new java.net.InetSocketAddress(port));
            // 通过open()方法找到Selector  
            selector = java.nio.channels.Selector.open();
            // 注册到selector，等待连接  
            serverSocketChannel.register(selector, java.nio.channels.SelectionKey.OP_ACCEPT);
        }

        @Override
        public void run() {// 监听
            java.nio.channels.ServerSocketChannel server = null;
            java.nio.channels.SocketChannel client = null;
            java.util.Set<java.nio.channels.SelectionKey> selectionKeys = null;
            java.util.Iterator<java.nio.channels.SelectionKey> iterator = null;
            java.nio.channels.SelectionKey selectionKey = null;
            int count = 0;
            byte[] ji_byteArray = null;
            while (true) {
                try {
                    // 选择一组键，并且相应的通道已经打开  
                    selector.select();
                    // 返回此选择器的已选择键集。  
                    selectionKeys = selector.selectedKeys();
                    iterator = selectionKeys.iterator();
                    while (iterator.hasNext()) {
                        selectionKey = iterator.next();
                        iterator.remove();
                        // 测试此键的通道是否已准备好接受新的套接字连接。  
                        if (selectionKey.isAcceptable()) {
                            // 返回为之创建此键的通道。  
                            server = (java.nio.channels.ServerSocketChannel) selectionKey.channel();
                            // 接受到此通道套接字的连接。  
                            // 此方法返回的套接字通道（如果有）将处于阻塞模式。  
                            client = server.accept();
                            // 配置为非阻塞 
                            client.configureBlocking(false);
                            // 注册到selector，等待连接  
//                            client.write(i接收缓冲区);
                            client.register(selector, java.nio.channels.SelectionKey.OP_READ);
                        } else if (selectionKey.isReadable()) {
                            // 返回为之创建此键的通道。  
                            client = (java.nio.channels.SocketChannel) selectionKey.channel();
                            //将缓冲区清空以备下次读取  
                            i接收缓冲区.clear();
                            //读取服务器发送来的数据到缓冲区中
                            count = client.read(i接收缓冲区);
                            ji_byteArray = i接收缓冲区.array();
                            ji_byteArray = java.util.Arrays.copyOf(ji_byteArray, count);
                            run1(client, ji_byteArray);
                            client.register(selector, java.nio.channels.SelectionKey.OP_WRITE);//取得返回值，开始监听写操作
                        } else if (selectionKey.isWritable()) {
                            // 返回为之创建此键的通道。  
                            client = (java.nio.channels.SocketChannel) selectionKey.channel();
                            //输出到通道  
                            client.write(i发送缓冲区集.remove(client.toString()));
                            java.nio.channels.SocketChannel jd = i移除集.remove(client.toString());
                            if (jd != null) {
                                jd.close();
                            } else {
                                client.register(selector, java.nio.channels.SelectionKey.OP_READ);
                            }
                        }
                    }
                } catch (java.io.IOException ex) {
                    Logger.getLogger(均衡.class.getName()).log(Level.SEVERE, null, ex);
                    if (client != null) {
                        try {
                            client.close();
                        } catch (java.io.IOException ex1) {
                            Logger.getLogger(均衡.class.getName()).log(Level.SEVERE, null, ex1);
                        }
                    }
                }
            }
        }

        public void run1(final java.nio.channels.SocketChannel client, final byte[] i_byteArray) {
            // <editor-fold defaultstate="collapsed" desc="自用">
            class 自用 implements Cloneable, org.hzs.Close {

                byte[] i缓冲_byteArray = new byte[1024];
                String[] i键_sArray = null;
                long i最小客户量_l, i客户量_l;

                自用 d副本() throws CloneNotSupportedException {
                    自用 jd = (自用) super.clone();
                    jd.i缓冲_byteArray = jd.i缓冲_byteArray.clone();
                    return jd;
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
                        } catch (java.io.IOException ex) {
                        }
                        d读入 = null;
                    }
                    if (d写出 != null) {
                        try {
                            d写出.close();
                        } catch (java.io.IOException ex) {
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
                //接收客户端密钥或递出服务器公钥
                {
                    ji会晤.i_byteArray = i_byteArray;
                    if (ji会晤.i_byteArray == null || ji会晤.i_byteArray.length <= 1) {
                        //客户端未传送数据，说明是取得公钥
                        java.nio.ByteBuffer ji发送缓冲区 = java.nio.ByteBuffer.allocate(org.hzs.server.负载均衡.Property.i发送缓冲区容量_i);
                        //向缓冲区中输入数据  
                        ji发送缓冲区.put(org.hzs.server.负载均衡.Property.i公钥_byteArray);
                        //将缓冲区各标志复位,因为向里面put了数据标志被改变要想从中读取数据发向服务器,就要复位  
                        ji发送缓冲区.flip();
                        //
                        i发送缓冲区集.put(client.toString(), ji发送缓冲区);

                        return;
                    }
                }
                //随机选择负载服务器，不仅能降低网路压力，而且确保每个业务节点的压力相仿。基于管理软的客户总是需要退出，因此未考虑每台负载节点的所负责的客户端数量。
                for (;;) {
                    String ji键_s = null;
                    int ji权重_i = d不确定數.nextInt(org.hzs.server.负载均衡.__.i外总权重_i);
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
                    //将服务专向寻得的最小服务器。因处理速度很快，所以未采用nio，维持bio不变。
                    try {
                        ji会晤.d通信链 = new java.net.Socket(ji键_s, i业务处理端口_i);
                    } catch (java.io.IOException ex) {
                        org.hzs.server.负载均衡.__.i外总权重_i -= org.hzs.server.负载均衡.__.i外服务器列表.get(ji键_s);
                        org.hzs.server.负载均衡.__.i外服务器列表.remove(ji键_s);
                        continue;
                    }
                    {
                        ji会晤.i_byteArray = org.hzs.server.负载均衡.Property.rsa.i用私钥解密_byteArray(ji会晤.i_byteArray);
                        ji会晤.d写出 = ji会晤.d通信链.getOutputStream();
                        ji会晤.d写出.write(ji会晤.i_byteArray);//递交客户端密钥
                        ji会晤.d通信链.shutdownOutput();
                        ji会晤.d读入 = ji会晤.d通信链.getInputStream();//取得会晤号，对应的公网IP、端口
                        ji会晤.i_byteArray = new byte[0];
                        int ji_i;
                        if ((ji_i = ji会晤.d读入.read(ji自用.i缓冲_byteArray)) > 0) {
                            ji会晤.i_byteArray = java.util.Arrays.copyOf(ji会晤.i_byteArray, ji会晤.i_byteArray.length + ji_i);
                            System.arraycopy(ji自用.i缓冲_byteArray, 0, ji会晤.i_byteArray, ji会晤.i_byteArray.length - ji_i, ji_i);
                        }
                    }
                    ji会晤.d通信链.close();
                    break;
                }
                //将服务结果交给客户
                {
                    java.nio.ByteBuffer ji发送缓冲区 = java.nio.ByteBuffer.allocate(org.hzs.server.负载均衡.Property.i发送缓冲区容量_i);
                    //向缓冲区中输入数据  
                    ji发送缓冲区.put(ji会晤.i_byteArray);
                    //将缓冲区各标志复位,因为向里面put了数据标志被改变要想从中读取数据发向服务器,就要复位  
                    ji发送缓冲区.flip();
                    //
                    i发送缓冲区集.put(client.toString(), ji发送缓冲区);
                    i移除集.put(client.toString(), client);
//                    client.register(selector, SelectionKey.OP_WRITE);//取得返回值，开始监听写操作
                }
            } catch (java.io.IOException | CloneNotSupportedException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
                Logger.getLogger(均衡.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                // <editor-fold defaultstate="collapsed" desc="释放资源">
                if (ji自用 != null) {
                    ji自用.close();
                    ji自用 = null;
                }
                if (ji会晤 != null) {
                    ji会晤.close();
                    ji会晤 = null;
                }
                // </editor-fold>
            }
        }
    }

    private static class 监聽请求开始业务 implements Runnable {

        @Override
        public void run() {
            java.net.ServerSocket d通信服务 = null;
            // <editor-fold defaultstate="collapsed" desc="自用">
            class 自用 implements Cloneable, org.hzs.Close {

                byte[] i缓冲_byteArray = new byte[1024];
                long i最小工作量_l, i工作量_l;
                org.hzs.json.JSONObject i_JSON = null;
                org.hzs.server.负载均衡.Session session = null;
                int i会晤号_i;

                自用 d副本() throws CloneNotSupportedException {
                    自用 jd = (自用) super.clone();
                    jd.i缓冲_byteArray = jd.i缓冲_byteArray.clone();
                    return jd;
                }
            }// </editor-fold>
            // <editor-fold defaultstate="collapsed" desc="会晤">
            class 会晤 implements Cloneable, org.hzs.Close {

                byte[] i_byteArray = null;
                java.net.Socket d通信链 = null;
                java.io.InputStream d读入 = null;
                java.io.OutputStream d写出 = null;

                会晤 d副本() throws CloneNotSupportedException {
                    return (会晤) super.clone();
                }
            }// </editor-fold>
            自用 ji自用 = new 自用();
            会晤 ji会晤 = new 会晤();
            for (;;) {
                try {
                    d通信服务 = new java.net.ServerSocket(i业务处理端口_i);//注意监听端口
                    for (;;) {
                        ji会晤.d通信链 = d通信服务.accept();

                        ji会晤.d读入 = ji会晤.d通信链.getInputStream();
                        //获取外连接数据，客户端密钥
                        ji会晤.i_byteArray = new byte[0];
                        int ji_i;
                        while ((ji_i = ji会晤.d读入.read(ji自用.i缓冲_byteArray)) > 0) {
                            ji会晤.i_byteArray = java.util.Arrays.copyOf(ji会晤.i_byteArray, ji会晤.i_byteArray.length + ji_i);
                            System.arraycopy(ji自用.i缓冲_byteArray, 0, ji会晤.i_byteArray, ji会晤.i_byteArray.length - ji_i, ji_i);
                        }
                        //建立系统内部会晤
                        ji自用.session = new org.hzs.server.负载均衡.Session();
                        ji自用.session.AES_Key = new javax.crypto.spec.SecretKeySpec(ji会晤.i_byteArray, "AES");
                        ////生成会晤号
                        do {
                            ji自用.i会晤号_i = d不确定數.nextInt();
                        } while (session_集合.get(ji自用.i会晤号_i) != null);
                        session_集合.put(ji自用.i会晤号_i, ji自用.session);
                        //
                        ji自用.i_JSON = org.hzs.json.JSONObject.d副本();
                        ji自用.i_JSON.put("IP_s", i公网IP_s);
//                        ji自用.i_JSON.put("端口_i", i外部端口_i);
                        ji自用.i_JSON.put("会晤号_i", ji自用.i会晤号_i);
                        ji会晤.i_byteArray = ji自用.i_JSON.toString(null).getBytes("UTF-8");
                        ji会晤.i_byteArray = org.hzs.压缩解压.Gzip.i压缩_byteArray(ji会晤.i_byteArray);
                        ji会晤.i_byteArray = org.hzs.安全.AES.i加密_byteArray(ji会晤.i_byteArray, ji自用.session.AES_Key);
                        //将服务结果交给客户
                        ji会晤.d写出 = ji会晤.d通信链.getOutputStream();
                        ji会晤.d写出.write(ji会晤.i_byteArray);
                    }
                } catch (java.io.IOException | org.hzs.logging.error ex) {
                    Logger.getLogger(均衡.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    if (d通信服务 != null) {
                        if (ji会晤 != null) {
                            ji会晤.close();
                        }
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
}
