package org.hzs.web_client;

public class Property {

    public static final java.util.TreeMap<String, Object> d对象池 = new java.util.TreeMap();
    public static String i工作路径_s = null;
    public static org.hzs.安全.RSA RSA = null;
    public static byte[] iAES密钥_byteArray = null;
    public static java.security.Key AES_Key = null;
    public static java.security.PublicKey i服务器公钥 = null;
    public static byte[] i会晤号_byteArray = new byte[4];//4字节支持40亿用户，够用
    public static int i会晤次数_i = 0;
    public static final applet applet = new applet();
    public static javax.swing.JFrame frame = null;
    public static String web = null;
    public static javafx.scene.web.WebEngine webEngine = null;
    public static int i服务器端口_i, i端口偏移_i, i打印端口_i, i本地服务端口_i;
    public static String i服务器IP_s = null;

    public static void init() throws CloneNotSupportedException, java.security.NoSuchAlgorithmException {
        RSA = new org.hzs.安全.RSA(5);
        iAES密钥_byteArray = new byte[16];
        (new java.util.Random()).nextBytes(iAES密钥_byteArray);
        AES_Key = new javax.crypto.spec.SecretKeySpec(iAES密钥_byteArray, "AES");
        //         Class.forName(smallsql.database.SSDriver.class.getName());//本地桌面數據库，用于缓存
        //取得配置文档路径
        i工作路径_s = System.getProperty("user.dir").trim();
        i工作路径_s = i工作路径_s.replaceAll("\\\\", "/");
        if (i工作路径_s.endsWith("/")) {
            i工作路径_s += "lib/";
        } else {
            i工作路径_s += "/lib/";
        }
        //
    }
}
