package org.hzs.web_client;

import java.io.IOException;

public class Property {

    public static String i工作路径_s = null;
    public static byte[] i会晤号_byteArray = new byte[4];//4字节支持40亿用户，够用
    public static int i会晤次数_i = 0;
    public static applet applet = null;
    public static javax.swing.JFrame frame = null;
    public static String web = null;
    public static javafx.scene.web.WebEngine webEngine = null;
    public static int i服务器端口_i, i端口偏移_i, i打印端口_i, i本地服务端口_i;
    public static String i服务器IP_s = null;

    public static void init() throws IOException {
        applet = new applet();
//         Class.forName(smallsql.database.SSDriver.class.getName());//本地桌面數據库，用于缓存
        //取得配置文档路径
        i工作路径_s = System.getProperty("user.dir").trim();
        i工作路径_s = i工作路径_s.replaceAll("\\\\", "/");

        //删除错误日记
        java.io.File dir = new java.io.File(i工作路径_s);
        java.io.File[] files = dir.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    continue;
                }
                String FileName = files[i].getName();
                if (FileName.startsWith("hs_err_pid") && FileName.endsWith(".log")) {
                    files[i].delete();
                }
            }
        }

        if (i工作路径_s.endsWith("/")) {
            i工作路径_s += "lib/";
        } else {
            i工作路径_s += "/lib/";
        }
        //
    }
}
