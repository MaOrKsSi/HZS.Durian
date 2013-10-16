package org.hzs.web_client.servlet;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hzs.logging.error;

public class 业务 extends __ implements com.sun.net.httpserver.HttpHandler {

    public 业务(final int ci端口_i, final org.hzs.logging.error ci_error) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeySpecException {
        super(ci端口_i, ci_error);
        d资源缓冲 = new java.util.TreeMap<String, byte[]>();
        d资源缓冲.put("/index.html", ("<!DOCTYPE HTML><HTML><HEAD><TITLE>正在登入，请耐心等待......</TITLE><META http-equiv=Content-Type content='text/html; charset=UTF-8'></HEAD><BODY><DIV style='text-align:center;'><BR><BR>正在登入，请耐心等待。。。。。。<BR><BR><IMG src='data:image/gif;base64,R0lGODlhvgAOAOYAAP///8nJyQEaS5OTk4qOknNycyB05zWU6CqC6DiW5g1RwgQtgQg9ihho3hxu0ESe+GOo5Fqx+HDK+G7D+HDK9TOM7ojN74+vzgszbGrF9DOO6AtIqWe99zSO6FOs9xtZkS+I2tTU1BlqzoXL7kGa9hRIc2ar5KWkpLy7vFel66bD1QUoYRdo3il53MTExI/N+2K67Clxs5GbpDuJtgc7ikaZ6onV+JuirRlgqzCJ7YmKiz6X9mfD9i6G65vX9gg7lhJdyKje90RRYpOSkrbj+MrJyZubm4qOkZWUlAEZRoqPkgEUPMnIyAIeVI2RlWFhYQEaSkN9oZKVl5STk8zLy83NzQEWRFVgbQIbTbKysmbC7wMeSbCytD2T6jOB6juO6n3N+E6s4DON7jeV53Cw53eUv0qP5lWZ7a2trkuZ3h92xRNb0TFlmHDC6z+Z5l+l4mWs7YKjy4nA7QQxhwg5fnu9+Vy26UKf0n7A+mqx8AAAAAAAAAAAAAAAAAAAAAAAACH/C05FVFNDQVBFMi4wAwEAAAAh+QQEAAAAACwAAAAAvgAOAAAH/4AAACFGQ0MDiImKi4yNjo+QkZKTlJWWl5iNSEZoVYIAOk8FOkcEpqeoqaqrrK2ur7CxsrO0tbarSk5SUygAJ08XQT4WxMXGFiYQysvMECYjx8fJzc3P0dHT1MvW18bZ2s7Q3cXf2tzjyODb4ujl1NYjbWFcIQUyREFg+vv7GRkJAAMKDCihoMGDEgYqBEihocOHDhcqhEixocSBFSleFJgR4saAHR9+ZOhQy50oKAqoCGIDTMZ/I3l0hPlRZkiaG23OHJlA50uePivivBiU4lCJPrXM+JBypQ2EBjMcmEq1KlUeUKNa3ToVa1atXK16/SpBatiqY7+aPduVbMG1bP/TZoV7Vi6MGSWa+gAzoa/fCRw6dNBAeHDhwR04/F0cGDHhx4cVL2ZsWLBgDZcJS57sNzDkw5gHb+YM2PBn0xpGc/aMOvRl1ZM9Y4aMGvbdvCpfcIjAu7cHBMCDCw/uobdx3r+HK0dQ/Pjx5MuFN3fuO/rw6dQjQLfOPHt17sCxU99uXTzvFDFwq8Dj4YF79yQMyJ9Pf/6O9/jh198v/37+/PHxV59//70XoID2FYjfgQgaQKCCDCL4YIERCjjhe13EgEFKF+RRgxgVhJhDAyQ2wEKJKDbQQwUgghiiiCmeGGMPYrjIYog1VjDiiTKmSCKNNr5444glsiBjjyri+GLujizu6COKLAB545JNPomkijm62CKMVqK44pZCtvDBCijoEEcNLTigpggKtOnmm26KoOacdLIJ550KyEnnng7YieebevI5p59/thmooH0WCiiigyoaJ6NrOmoooyLgQIcVLmRxAxAbMMAADQuEKuqootLg6amogkrqqguYiuqrn7K6qquwnqqqrKXWmiquo9KqKwO85vqrp8GG6iusdGBghRAhAIDCDRg0gYUA1FZr7bXYZqvtttx26+234IYr7rjkZgtFEktc4cInIbjgAhMBxCvvvPTWa++9+Oar77789uvvvwAHbG8RRVDRLACBAAAh+QQEAAAAACwBAAIAvAAKAAAH/4BPF3JkJhAQJiMWi4yNi4aHkZKIio6NkJOSiZaWmJmHm5yXn5qVoo+kkaGnFp6fq6eukW9pIGgycim6uhS9vr++CcLDxMPAx8XJwsfMysnMyM7E0MDS09TB1svYvdpjalEXeSluwjzcGdrm1Onq59jt2u/s6gkZ6PXz0PHW+sz8ysZomPFBXI0xB3hIWMiwoYQMByJKnChRoUOGEClqTHjRYcaNEy12XPgRZESRI0uaRNlRJUiWF11utDOjxAU4NTpwmMCzp88JHDpo6EBUg9GjQnf+7BnUqFCiT6EqXcq0qFOhQ7NOpQoU69WrSblW9XrUqdGtVINCzYr0rFieTf+9WpUKo+aFFF88RNjLt28EDwgCCx4sWK9fvoAJK0Zg+DDixYobO/4LmbBkx4krB758OLNmzn49V9abIkaJOGdSPFjNuvUDEgZiy54te4fr1rBp6zZg+zbu3bp7+16dG3jt4ayLG+eNnPjy482VG+/dJQaGODjFiKlQQTt37TkasGhAXnx58ix6bOfe/Xv48/DRq2/vvfv29+fHw+/Bnn5//PqVFyB/69n3n4AInsdfewba916A+ZFHIIP9ifFghAqy18IHK8RRQwsOhCiiiCIoYOKJKJ4owogslpjiiwqsyGKLML4o44wk1pjijTg64KKOJvKI449ACjkjkTreKAJCDnRscQMQGzAg5ZRS0rDAlVhmiSUNVHZppZZgLsBll2SGCeaYZFJpppZopsnAl2te2WaacMY5J5l1rtkmHRhsIUQgACH5BAQAAAAALAEAAgC8AAoAAAf/gE8qcmQmEIeIiCYjFo2Oj42GiZMQi5CQkpSKjJeYmomWnY+Zn6GikZ+bp46kmqanhm9pIGgycikpCbq7vBS+v8C/vMO9wcHEyMbKyMTKxszDzsfQu9LA1NXWwrpjamwXeSlu0DzaGdi65c7n6OrS7Nju6+gJ8srw1PbG+OTmvGMacJQAV2PMgYMID/KQwLChQwkZEkpMuPBhw4gTJ1a0eDGjRo4OMXpEuBGkyJEHSnI8OVKlRZYHQODAcAFOjQ4dNGjImZPDhJ9Ag07ggBPnzpw7dXbwKRQo0aRGdR5d2lToU6RTjVKt6hSrV6RbuQ4FK7WoUaZiryY9ChUtV6I8/4/KpJniC4K7eBF4iMC3r98IHvIKzrv3b9/AgwcXNnw4sWLGfhE7xrsYsuTJeiE3xny3MuPLeVvMjHPGi4HTqHc8WM269QMSqGPLNqDaNWvYs2fXtn07t27erXH7Tg2893DixV8fR15c+OwGG1bEMdOigXXrPcRU0F6h+/btOa6LH9+gh3ftYtJXCD+eRQP37st3T89dvRj24lnEfy+/vvr57O1HXnncnefdevxdF99+5m2HnoH4kQdff/VB2J517gHxQxNltLCGAiAqIIIDJJZoogMihKjiiiKeaGKKLLI4oosvxigjjTXaqOKMOKKo4449kgjjjy0GOaSN0W1xwz0GP8yxwAI0MCDllFQyQMOTWGaZZZRVTnmllmBC2WWVX4a55ZhVmqkll2haqeaZbbr55pNsolmmmitsIUQgACH5BAQAAAAALAEAAgC8AAoAAAf/gE8qQT4WhhYmEIqLjBAmI4eRkomNjY+Sk5WWkJiZmouXnZGUn46cooakn6GoiKWKrJIjbWFcN0QUubkJvL2+vbrBwhS/xbzDwsbFyMPKv8zJzsDQutLT1MTWCdhad2yDNmAUGdoJPNjk2ufM6dbr0O3S7+zl88jxzvbD+Mr6wjA4SoCTkOGAwYMID/KQwLChQ4IJIxpc+LBhQYkJKVa0iDHjRocXO078yFHkSJIQTR7Q+DEDCBwYVPgAw6GDhps2b+K0yWGCz59AJ9TMSdSm0Z5Bfw7FqVNDh6cdkCZV+tSp0ao5pU4VmtNqUZ5bqRJ1uvOm1qlDr1bVGTWsz5ov/2O+4ICgrt27dj1E2Mu3bwQPeAPX1euXL2DBeAkXNow48eK+hxsPfsxY8mTKfy1fpny4BUwVdbwYGE269OgdD1KrXv2AhOnXp1mvdg3bNGrZs2vbxp1bN+nbvFv7/h08Ne3hwHkfb7BhxQUzLRpIny6dhfQeFcRUyK69e4Uc1BtYp24d+3bu28WAtz4+fHnt6c+nBx9++ngW2MXozy7/e/X61OXXnX7wqQdgfT3Ax9+C/rnX3nX87VeggdMB8UMTZbSwhgIcduihCA6EKOKIDojg4YkfkjiiiSiiCKKKK7boIowxypgijSGyaCOHL+Ko44490vijAs1ZccMGP8yxwDeSTC5JAwNQRiklAzQ0aWWTT04ZZZVXXpmlllt26SWYU4pp5Zdkcmmmk2SGuSabbVJ55QpWCBEIADs='/><BR></DIV></BODY></HTML>").getBytes("UTF-8"));
    }

    /**
     * 下载所需资源，已下载但未改变的资源不再下载
     */
    public void g置业务模块() throws error, MalformedURLException, IOException {
        d资源缓冲.clear();
        //取得可下载资源
        String ji_s = org.hzs.web_client.Property.applet.g会晤服务器("{指令0:'资源'}", "{}");
        org.hzs.json.JSONObject ji2_JSON = org.hzs.json.JSONObject.d副本();
        ji2_JSON.set(ji_s, null);
        //搭建可能需下载资源
        java.io.File file = null;
        java.io.FileInputStream in = null;
        Object key = null;
        org.hzs.json.JSONObject ji1_JSON = org.hzs.json.JSONObject.d副本();
        Iterator keys = ji2_JSON.keys();
        while (keys.hasNext()) {
            key = keys.next();
            file = new java.io.File(org.hzs.web_client.Property.i工作路径_s + key);
            if (!file.exists()) {
                continue;
            }
            byte[] byt = new byte[(int) file.length()];
            try {
                in = new java.io.FileInputStream(file);
                in.read(byt);
                byt = org.hzs.安全.DIGEST.i摘要_byteArray(byt, org.hzs.安全.DIGEST.SHA_512);
                ji1_JSON.put((String) key, new String(org.hzs.编码.Base64.i编码_byteArray(byt), "UTF-8"));
            } catch (FileNotFoundException ex) {
            } catch (IOException | NoSuchAlgorithmException ex) {
            }
        }
        //再次取得可下载资源
        ji_s = org.hzs.web_client.Property.applet.g会晤服务器("{指令0:'资源'}", ji1_JSON.toString(null));
        ji1_JSON.set(ji_s, null);
        //开始下载资源
        keys = ji1_JSON.keys();
        while (keys.hasNext()) {
            key = keys.next();
            ji_s = ji1_JSON.getString((String) key, null);
            URL url = new URL(ji_s);
            URLConnection conn = url.openConnection();
            InputStream inStream = conn.getInputStream();
            FileOutputStream fs = new FileOutputStream(org.hzs.web_client.Property.i工作路径_s + key);
            byte[] buffer = new byte[1204];
            int byteread;
            while ((byteread = inStream.read(buffer)) != -1) {
                fs.write(buffer, 0, byteread);
            }
            fs.flush();
            fs.close();
            fs = null;
            inStream.close();
        }
        //开始缓冲资源
        keys = ji2_JSON.keys();
        while (keys.hasNext()) {
            g缓冲资源((String) keys.next());
        }
    }

    public void g失败() {
        try {
            org.hzs.web_client.Property.frame.setResizable(false);
            d资源缓冲.clear();
            d资源缓冲.put("/index.html", "<!DOCTYPE HTML><HTML><HEAD><TITLE>温馨提示</TITLE><META http-equiv=Content-Type content='text/html; charset=UTF-8'></HEAD><BODY><TABLE height='100%' cellSpacing=0 cellPadding=0 width='100%' border=0><TBODY><TR><TD><TABLE height=422 cellSpacing=0 cellPadding=0 width=382 align=center border=0><TBODY><TR><TD background='data:image/jpg;base64,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'><TABLE cellSpacing=0 cellPadding=0 width='100%' align=center border=0><TBODY><TR><TD style='PADDING-RIGHT: 20px; PADDING-LEFT: 180px; PADDING-BOTTOM: 30px; PADDING-TOP: 80px'>由于网站管理员的鸡鸡丢失，导致了本页面不能访问了，实在抱歉啊。</TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE></BODY><p align='center'>今天不聊天：身为站长，终日劳累，前途迷茫；三更睡，五更起<br>大米咸菜，小板木床；世人费解，屡失她爱，立足新疆，奋发图强</SPAN><br>Copyright &copy 2005-2010, <A href='http://960wcn.blog.163.com/'>960wcn.blog.163.com</A>. All rights reserved.<SCRIPT>applet.g禁止改变窗口大小();applet.g蜂鸣();</SCRIPT></BODY></HTML>".getBytes("UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(业务.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
        }
    }

    @Override
    public void handle(final com.sun.net.httpserver.HttpExchange he) throws IOException {
        // <editor-fold defaultstate="collapsed" desc="自用">
        class 自用 implements Cloneable {

            java.io.OutputStream out = null;
            String i路径_s = null, i入站网址_s = null, i浏览器_s = null;
            byte[] i缓冲 = null;
            org.hzs.json.JSONObject i_JSON = null;
            String i_s = null;
//            String $ = null, _ = null;

            自用 d副本() throws CloneNotSupportedException {
                return (自用) super.clone();
            }

            void close() {
                i_JSON = null;
                i_s = null;
                i缓冲 = null;
                i路径_s = null;
                i入站网址_s = null;
                i浏览器_s = null;
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
            ji自用 = (自用) org.hzs.常用.d对象池.get(自用.class.getName());
            if (ji自用 == null) {
                ji自用 = new 自用();
                org.hzs.常用.d对象池.put(自用.class.getName(), ji自用);
            }
            ji自用 = ji自用.d副本();
            // </editor-fold>
            he.sendResponseHeaders(200, 0);
            ji自用.i入站网址_s = he.getRemoteAddress().getHostName();
            if (ji自用.i入站网址_s != null && !ji自用.i入站网址_s.equals("localhost") && !ji自用.i入站网址_s.equals("127.0.0.1")) {
                return;
            }
            ji自用.i浏览器_s = he.getRequestHeaders().get("User-Agent").get(0); //得到浏览器等相关信息
            if (!ji自用.i浏览器_s.contains(" JavaFX/")) {//确保用的是自制浏览器     //"Mozilla/5.0 (Windows NT 5.1) AppleWebKit/535.14 (KHTML, like Gecko) JavaFX/2.2 Safari/535.14" 
                return;
            }
            //取得客户访问的资源
            //路径 ＝ 上下文路径 + 文档路径
            ji自用.i路径_s = java.net.URLDecoder.decode(he.getRequestURI().getRawPath(), "UTF-8");//解析出错抛出错误，并返回客户端，不影响程序运行
            if (ji自用.i路径_s.equals("/")) {
                ji自用.i路径_s = ji自用.i路径_s + "index.html";
            }
            ji自用.i路径_s = ji自用.i路径_s.replaceAll("//", "/");
            ji自用.i缓冲 = d资源缓冲.get(ji自用.i路径_s);
//            if (ji自用.i缓冲 == null) {//缓冲不存在数据，转向服务器索取
//                ji自用.$ = request.getParameter("$");
//                ji自用._ = request.getParameter("_");
//                if (ji自用.$ == null || ji自用._ == null) {
//                    return;
//                }
//                ji自用.i_s = org.hzs.web_client.Property.applet.g会晤服务器(ji自用.$, request.getParameter("_"));
//                ji自用.i_JSON = org.hzs.json.JSONObject.d副本();
//                ji自用.i_JSON.set(ji自用.i_s, null);
//                if (!Boolean.TRUE.equals(ji自用.i_JSON.getBoolean("success", null))) {
//                    return;
//                }
//                ji自用.i缓冲 = ji自用.i_JSON.getString("_", null).getBytes("UTF-8");
//            }
            he.getResponseHeaders().set("Content-Type", "text/html;charset=UTF-8");
            ji自用.out = he.getResponseBody();
            ji自用.out.write(ji自用.i缓冲);
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(业务.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (ji自用 != null) {
                ji自用.close();
                ji自用 = null;
            }
            he.close();
        }
    }
}
