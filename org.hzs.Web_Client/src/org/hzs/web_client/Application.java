package org.hzs.web_client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Application extends javax.swing.JApplet {

    private static org.hzs.web_client.servlet.业务 d业务_HttpHandler = null;
    public static org.hzs.web_client.servlet.打印 d打印_HttpHandler = null;

    private final static class i {

        static java.awt.SystemTray tray = null;
        static String i标题_s = null;
        static javafx.embed.swing.JFXPanel fxContainer = null;
        static javax.swing.JApplet JApplet = null;
        static org.hzs.json.JSONArray i服务器列表_ArrayJSON = null;//负责第一层集群分配节点的服务器列表
        static java.awt.TrayIcon trayIcon = null;
        static java.net.URL 图标用图片URL = null;
    }

    public static void g开始(final org.hzs.json.JSONObject ci_JSON) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                org.hzs.logging.error ji_error = null;
                try {
                    ji_error = org.hzs.logging.error.d副本();
                    //初始化配置
                    org.hzs.web_client.Property.init();
                    //
                    org.hzs.web_client.Property.i端口偏移_i = ci_JSON.getInt("端口偏移_i", ji_error);
                    i.i标题_s = ci_JSON.getString("标题_s", ji_error);
                    i.i服务器列表_ArrayJSON = ci_JSON.getJSONArray("服务器列表_ArrayJSON", ji_error);
                    org.hzs.web_client.Property.i本地服务端口_i = ci_JSON.getInt("本地服务端口_i", ji_error);
                    i.图标用图片URL = (java.net.URL) ci_JSON.get("图标用图片URL");
                    //
                    if (!"ubuntu".equals(System.getenv().get("GDMSESSION"))) {
                        try {
                            javax.swing.UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");
//                        javax.swing.UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
//                        javax.swing.UIManager.setLookAndFeel("com.jtattoo.plaf.mint.MintLookAndFeel");
//                        javax.swing.UIManager.setLookAndFeel("com.jtattoo.plaf.fast.FastLookAndFeel");
                        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
                            java.util.logging.Logger.getLogger(Application.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                        }
                    }

                    org.hzs.web_client.Property.frame = new javax.swing.JFrame("正在登入，请耐心等待......");
                    org.hzs.web_client.Property.frame.setIconImage(javax.imageio.ImageIO.read(i.图标用图片URL));
                    org.hzs.web_client.Property.frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);

                    i.JApplet = new Application();
                    i.JApplet.init();

                    //
                    i.JApplet.start();
                } catch (CloneNotSupportedException | org.hzs.logging.error | IOException ex) {
                    Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    ji_error = null;
                }
            }
        });
    }

    @Override
    public void init() {
        i.fxContainer = new javafx.embed.swing.JFXPanel();
        i.fxContainer.setPreferredSize(new java.awt.Dimension(800, 600));
        add(i.fxContainer, java.awt.BorderLayout.CENTER);
        // create JavaFX scene
        javafx.application.Platform.runLater(new Runnable() {

            @Override
            public void run() {
                createScene();
            }
        });
    }

    public void createScene() {
        //显示窗口
        //斠验资源。若发生改变，则登入服务器；若未改变，则登入本地
        // <editor-fold defaultstate="collapsed" desc="自用">
        class 自用 {

            public javafx.scene.layout.StackPane root = null;
            public javafx.scene.web.WebView view = null;
            public javafx.scene.Scene scene = null;
            public Runnable_ d初始化线程 = null;
            public com.sun.net.httpserver.HttpServer server = null;

            public void close() {
                d初始化线程 = null;
                root = null;
                view = null;
                scene = null;
                server = null;
            }
        }// </editor-fold>
        自用 ji自用 = null;
        try {
            ji自用 = new 自用();
            //
            ji自用.root = new javafx.scene.layout.StackPane();
            ji自用.view = new javafx.scene.web.WebView();
            ji自用.view.setEventDispatcher(new MyEventDispatcher(ji自用.view.getEventDispatcher()));//杜绝系统的默认右键菜单
            org.hzs.web_client.Property.webEngine = ji自用.view.getEngine();
            //允许执行JS
            org.hzs.web_client.Property.webEngine.setJavaScriptEnabled(true);
            //注册插件
            org.hzs.web_client.Property.webEngine.getLoadWorker().stateProperty().addListener(new javafx.beans.value.ChangeListener<javafx.concurrent.Worker.State>() {
                @Override
                public void changed(javafx.beans.value.ObservableValue ov, javafx.concurrent.Worker.State oldState, javafx.concurrent.Worker.State newState) {
                    if (newState == javafx.concurrent.Worker.State.SUCCEEDED) {
                        org.hzs.web_client.Property.frame.setTitle(org.hzs.web_client.Property.webEngine.getTitle());
                        netscape.javascript.JSObject window = (netscape.javascript.JSObject) org.hzs.web_client.Property.webEngine.executeScript("window");
                        window.setMember("applet", org.hzs.web_client.Property.applet);
                    }
                }
            });
            //开启业务监听
            d业务_HttpHandler = new org.hzs.web_client.servlet.业务(org.hzs.web_client.Property.i本地服务端口_i + org.hzs.web_client.Property.i端口偏移_i, null);
            ji自用.server = com.sun.net.httpserver.HttpServer.create(new java.net.InetSocketAddress(org.hzs.web_client.Property.i本地服务端口_i + org.hzs.web_client.Property.i端口偏移_i), 0);
            ji自用.server.createContext("/", d业务_HttpHandler);
            ji自用.server.start();
            org.hzs.web_client.Property.webEngine.load(d业务_HttpHandler.i本地服务网址_s);
            //
            ji自用.root.getChildren().add(ji自用.view);
            ji自用.scene = new javafx.scene.Scene(ji自用.root);
            i.fxContainer.setScene(ji自用.scene);
            org.hzs.web_client.Property.frame.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent evt) {
                    if (org.hzs.web_client.Property.applet != null) {
                        org.hzs.web_client.Property.applet.g退出();
                    } else {
                        g退出();
                    }
                }
            });
            //
            org.hzs.web_client.Property.frame.setContentPane(i.JApplet.getContentPane());

            org.hzs.web_client.Property.frame.pack();
            org.hzs.web_client.Property.frame.setLocationRelativeTo(null);
            org.hzs.web_client.Property.frame.setVisible(true);
            //
            javafx.application.Platform.runLater(new Runnable_());
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
            g退出();
        } catch (Exception ex) {
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
            g退出();
        } finally {
            if (ji自用 != null) {
                ji自用.close();
                ji自用 = null;
            }
        }
    }

    public static void g退出() {
        if (i.tray != null) {
            i.tray.remove(i.trayIcon);
        }
        System.exit(0);
    }

    private class Runnable_ implements Runnable {

        @Override
        public void run() {
            托盘init();
            // <editor-fold defaultstate="collapsed" desc="自用">
            class 自用 extends org.hzs.Close {

                public java.util.Random d不确定数 = null;
                public org.hzs.json.JSONObject i_JSON = null;
                public byte[] i_byteArray = null;
                public javafx.geometry.Rectangle2D bounds = null;
                public com.sun.net.httpserver.HttpServer server = null;
                public boolean i出错_b;
                public java.net.InetAddress address = null;
            }
            // </editor-fold>
            自用 ji自用 = null;
            org.hzs.logging.error ji_error = null;
            boolean ji未捕获错误_b = true;
            try {
                ji_error = org.hzs.logging.error.d副本();
                ji自用 = new 自用();
                //与服务握手，顺便检测是否连通，取得服务的服务器，采用随机抽取，达到减轻单一服务器压力的目的
                ji自用.d不确定数 = new java.util.Random();
                int ji1_i = i.i服务器列表_ArrayJSON.size();
                while (ji1_i > 0) {
                    int ji2_i = ji自用.d不确定数.nextInt(ji1_i);
                    ji自用.i_JSON = i.i服务器列表_ArrayJSON.getJSONObject(ji2_i, ji_error);
                    ji自用.i出错_b = true;
                    try {
                        ji自用.address = java.net.InetAddress.getByName(ji自用.i_JSON.getString("服务器IP_s", ji_error));//ping this IP 
                        if (!(ji自用.address instanceof java.net.Inet4Address) && !(ji自用.address instanceof java.net.Inet6Address)) {//非IP4或IP6，提示错误
                            i.i服务器列表_ArrayJSON.remove(ji自用.i_JSON);
                            ji1_i = i.i服务器列表_ArrayJSON.size();
                            if (ji1_i == 0) {
                                d业务_HttpHandler.g失败();
                                org.hzs.web_client.Property.webEngine.load(d业务_HttpHandler.i本地服务网址_s);
                                ji未捕获错误_b = false;
                                return;
                            } else {
                                continue;
                            }
                        }
                        org.hzs.web_client.Property.applet.g握手(ji自用.i_JSON.getString("服务器IP_s", ji_error), ji自用.i_JSON.getInt("服务器端口_i", ji_error), 1, ji_error);
//               ji自用.d通信链.sendUrgentData(0xFF);
                    } catch (IOException ex) {
                        ji1_i--;
                        i.i服务器列表_ArrayJSON.remove(ji自用.i_JSON);
                        if (ji1_i == 0) {
                            throw ex;
                        }
                    } catch (org.hzs.logging.error ex) {
                        ji1_i--;
                        i.i服务器列表_ArrayJSON.remove(ji自用.i_JSON);
                        if (ji1_i == 0) {
                            throw ex;
                        }
                    }
                    ji自用.i出错_b = false;
                    //初始化配置
                    break;
                }
                //
                //开启打印监听
                org.hzs.web_client.Property.i打印端口_i = org.hzs.web_client.Property.i本地服务端口_i - org.hzs.web_client.Property.i端口偏移_i;
                d打印_HttpHandler = new org.hzs.web_client.servlet.打印(org.hzs.web_client.Property.i打印端口_i, null);
                ji自用.server = com.sun.net.httpserver.HttpServer.create(new java.net.InetSocketAddress(org.hzs.web_client.Property.i打印端口_i), 0);
                ji自用.server.createContext("/", d打印_HttpHandler);
                ji自用.server.start();
                //开启业务模块
                org.hzs.web_client.Property.frame.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
                d业务_HttpHandler.g置业务模块();
                org.hzs.web_client.Property.webEngine.load(d业务_HttpHandler.i本地服务网址_s);
                org.hzs.web_client.Property.web = d业务_HttpHandler.i本地服务网址_s;
                (new 维持连接()).start();
                ji未捕获错误_b = false;
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
            } catch (org.hzs.logging.error ex) {
                Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
                d业务_HttpHandler.g失败();
                org.hzs.web_client.Property.webEngine.load(d业务_HttpHandler.i本地服务网址_s);
                ji未捕获错误_b = false;
            } catch (CloneNotSupportedException | IOException | InvalidKeySpecException ex) {
                Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
                if (ji自用.i出错_b) {
                    d业务_HttpHandler.g失败();
                    org.hzs.web_client.Property.webEngine.load(d业务_HttpHandler.i本地服务网址_s);
                    ji未捕获错误_b = false;
                }
            } finally {
                if (ji自用 != null) {
                    ji自用.close();
                    ji自用 = null;
                }
                ji_error = null;
                if (ji未捕获错误_b) {
                    g退出();
                }
            }
        }

        private void 托盘init() {
            if (!java.awt.SystemTray.isSupported()) {//检查当前系统是否支持系统托盘。若不支持，直接返回
                return;
            }
            java.awt.Image image = null;
            java.awt.PopupMenu popupMenu = null;
            java.awt.MenuItem MenuItem = null;
            i.tray = java.awt.SystemTray.getSystemTray();
            image = java.awt.Toolkit.getDefaultToolkit().getImage(i.图标用图片URL);
            popupMenu = new java.awt.PopupMenu();
            MenuItem = new java.awt.MenuItem("退出");
            MenuItem.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    if (org.hzs.web_client.Property.applet != null) {
                        org.hzs.web_client.Property.applet.g退出();
                    } else {
                        g退出();
                    }
                }
            });
            popupMenu.add(MenuItem);
            i.trayIcon = new java.awt.TrayIcon(image, i.i标题_s, popupMenu);
            i.trayIcon.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    if (e.getClickCount() == 2) {

                        //注意下面的API调用，这个可以给用户提示信息
                        i.trayIcon.displayMessage("提示", "双击显示窗口！", java.awt.TrayIcon.MessageType.INFO);
                        org.hzs.web_client.Property.frame.setVisible(true);
                    }
                }
            });
            i.trayIcon.setImageAutoSize(true);
            try {
                i.tray.add(i.trayIcon);  // 将 TrayIcon 添加到 SystemTray。
            } catch (java.awt.AWTException e) {
            }
        }
    }

    private static final class MyEventDispatcher implements javafx.event.EventDispatcher {

        private final javafx.event.EventDispatcher originalDispatcher;

        public MyEventDispatcher(javafx.event.EventDispatcher originalDispatcher) {
            this.originalDispatcher = originalDispatcher;
        }

        @Override
        public javafx.event.Event dispatchEvent(javafx.event.Event event, javafx.event.EventDispatchChain tail) {
            if (event instanceof javafx.scene.input.MouseEvent) {
                javafx.scene.input.MouseEvent mouseEvent = (javafx.scene.input.MouseEvent) event;
                if (javafx.scene.input.MouseButton.SECONDARY == mouseEvent.getButton()) {
                    mouseEvent.consume();
                }
            }
            return originalDispatcher.dispatchEvent(event, tail);
        }
    }

    public static class 维持连接 extends Thread {

        @Override
        public void run() {
            for (;;) {
                try {
                    Thread.sleep(5_000);//5″循环一次，当会晤次数小于等于零，则会晤服务器，确保服务器不失效
                } catch (InterruptedException ex) {
                }
                org.hzs.web_client.Property.i会晤次数_i--;
                if (org.hzs.web_client.Property.i会晤次数_i <= 0) {
                    org.hzs.web_client.Property.applet.g会晤服务器("{}", "{}");
                }
            }
        }
    }
}
