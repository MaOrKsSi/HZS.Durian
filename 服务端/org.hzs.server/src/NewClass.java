
import java.io.UnsupportedEncodingException;
import java.net.SocketException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NewClass {

    public static void main(String[] args) {
        try {
            org.hzs.server.Property.init(1);
            org.hzs.server.Property.i數據库口令_s = "zz22388820";
            org.hzs.server.Property.i數據库用户_s = "postgres";
            org.hzs.server.Property.i數據库连接_s = "jdbc:postgresql://127.0.0.1:5432/fa1yd4";
            org.hzs.server.Property.i數據密钥_byteArray = org.hzs.编码.Base64.i解码_byteArray("ibPTSjHJ0+3AjR98W9UfEQ==".getBytes("UTF-8"));
            org.hzs.server.Property.i中心端口_i = 8080;
            org.hzs.server.Property.i集群号_byte = 0;
            //启动负载均衡
            org.hzs.json.JSONArray dd = org.hzs.json.JSONArray.d副本();
            dd.put("127.0.0.1");
            org.hzs.server.负载均衡.__.init(dd, true, true);
            new org.hzs.server.负载均衡.均衡(1, null);
            System.out.println("启动完毕");
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException | InvalidKeySpecException | SocketException ex) {
            Logger.getLogger(NewClass.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
        }
    }
}
