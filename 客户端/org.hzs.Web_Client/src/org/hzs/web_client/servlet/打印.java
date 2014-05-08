package org.hzs.web_client.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class 打印 extends __ implements com.sun.net.httpserver.HttpHandler {

    private final String i类名_s = "org.hzs.web_client.servlet.业务.";
    public final StringBuilder i打印代码_S = new StringBuilder(1024);

    public 打印(int ci端口_i, final org.hzs.logging.error ci_error) throws NoSuchAlgorithmException, InvalidKeySpecException, UnsupportedEncodingException {
        super(ci端口_i, ci_error);
    }

    @Override
    public void handle(final com.sun.net.httpserver.HttpExchange he) throws IOException {
        final String ji过程名_s = "jg执行(.";
        // <editor-fold defaultstate="collapsed" desc="自用">
        class 自用 implements Cloneable {

            java.io.OutputStream out = null;
            String i入站网址_s = null;
            boolean i触屏操作系统_b = false;
            java.io.File i文件 = null;
            String i类型_s = "text/html;charset=UTF-8";

            自用 d副本() throws CloneNotSupportedException {
                return (自用) super.clone();
            }

            void close() {
                i入站网址_s = null;
                if (out != null) {
                    try {
                        out.flush();
                        out.close();
                    } catch (IOException ex) {
                    }
                    out = null;
                }
            }
        }// </editor-fold>
        自用 ji自用 = null;
        try {
            // <editor-fold defaultstate="collapsed" desc="利用对象复制初始化过程内部变量">
            ji自用 = (自用) org.hzs.web_client.Property.d对象池.get(i类名_s + ji过程名_s + "自用");
            if (ji自用 == null) {
                ji自用 = new 自用();
                org.hzs.web_client.Property.d对象池.put(i类名_s + ji过程名_s + "自用", ji自用);
            }
            ji自用 = ji自用.d副本();
            // </editor-fold>
            he.sendResponseHeaders(200, 0);
            ji自用.i入站网址_s = he.getRemoteAddress().getHostName();
            if (ji自用.i入站网址_s != null && !ji自用.i入站网址_s.equals("localhost")) {//非指定域名，不提供任何信息。一定程度上防止盗链
                return;
            }
            ji自用.out = he.getResponseBody();
            if (i打印代码_S.length() == 0) {
                ji自用.out.write(null);
            } else {
                ji自用.out.write(i打印代码_S.toString().getBytes("UTF-8"));
            }
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(打印.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (ji自用 != null) {
                ji自用.close();
                ji自用 = null;
            }
            he.close();
        }
    }
}
