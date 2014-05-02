
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hzs.json.JSONObject;
import org.hzs.logging.error;
import org.hzs.server.业务.Session;

public class 混淆入口 extends org.hzs.server.业务.__ {

    public static void main(String[] args) {
        // <editor-fold defaultstate="collapsed" desc="通信">
        class ty1xl4 {

            java.net.Socket d通信链 = null;
            java.net.ServerSocket d通信服务 = null;
            java.io.InputStream d读入 = null;
            java.io.OutputStream d写出 = null;

            public void close() {
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
                if (d通信链 != null) {
                    try {
                        d通信链.close();
                    } catch (IOException ex) {
                    }
                    d通信链 = null;
                }
                if (d通信服务 != null) {
                    try {
                        d通信服务.close();
                    } catch (IOException ex) {
                    }
                    d通信服务 = null;
                }
            }
        }// </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="自用">
        class zi4yy4 {

            public org.hzs.json.JSONObject i_JSON = null;
            public byte[] i_byteArray = null;
            public byte[] i口令_byteArray = null;
            public int i_i;
            public java.io.FileInputStream d读 = null;
            public java.util.Calendar i当前时间 = null;
            public String i_s = null;
            public java.sql.Connection d连接 = null;
            public java.sql.PreparedStatement d操作 = null;
            public java.sql.ResultSet d视图 = null;
            public final String i配置文档_s = "/home/hzs/参數箱.ini";
//   public final String i配置文档_s = "c:/参數箱.ini";

            public void close() {
                if (this.i_JSON != null) {
                    this.i_JSON.clear();
                    this.i_JSON = null;
                }
                i_byteArray = null;
                i口令_byteArray = null;
                i_s = null;
                if (d视图 != null) {
                    try {
                        d视图.close();
                    } catch (SQLException ex) {
                    }
                }
                if (d操作 != null) {
                    try {
                        d操作.close();
                    } catch (SQLException ex) {
                    }
                }
                if (d连接 != null) {
                    try {
                        d连接.close();
                    } catch (SQLException ex) {
                    }
                }
            }
        }// </editor-fold>
        ty1xl4 jd通信 = null;
        zi4yy4 ji自用 = null;
        org.hzs.logging.error ji_error = null;
        try {
            hsession hs = null;
            org.hzs.server.业务.Session hs1 = null;
            ji_error = org.hzs.logging.error.d副本();
            jd通信 = new ty1xl4();
            ji自用 = new zi4yy4();
            //
            Class.forName(org.postgresql.Driver.class.getName());//载入数据库的jdbc
            org.hzs.server.Property.i中心端口_i = 8080;
            org.hzs.server.Property.init(5);//物流、会员、代收、管理、客户
            jd通信.d通信服务 = new java.net.ServerSocket(org.hzs.server.Property.i中心端口_i);
            ji自用.i_byteArray = new byte[0];
            System.out.println("等待启动授权");
            for (;;) {
                try {
                    //取得用户传输过来的口令
                    jd通信.d通信链 = jd通信.d通信服务.accept();
                    jd通信.d读入 = jd通信.d通信链.getInputStream();
                    ji自用.i_byteArray = java.util.Arrays.copyOf(ji自用.i_byteArray, 1024);
                    //接收客户端传过来的参数
                    ji自用.i_i = jd通信.d读入.read(ji自用.i_byteArray);
                    if (ji自用.i_i <= 0) {
                        continue;
                    }
                    ji自用.i_byteArray = java.util.Arrays.copyOf(ji自用.i_byteArray, ji自用.i_i);
                    ji自用.i_byteArray = org.hzs.压缩解压.Gzip.i解压_byteArray(ji自用.i_byteArray);
                    //解析参数
                    ji自用.i_s = new String(ji自用.i_byteArray, "UTF-8");
                    ji自用.i_JSON = org.hzs.json.JSONObject.d副本();
                    ji自用.i_JSON.set(ji自用.i_s, ji_error);
                    ji自用.i_s = ji自用.i_JSON.getString("$", ji_error);
                    if ("向取服务器索取公钥".equals(ji自用.i_s)) {
                        jd通信.d写出 = jd通信.d通信链.getOutputStream();
                        ji自用.i_byteArray = org.hzs.编码.Base64.i解码_byteArray("MIIBYjANBgkqhkiG9w0BAQEFAAOCAU8AMIIBSgKCAUEAjs8nSOSz/Cd38p9Skn4HOdeboqz3C9Q7gRG/gXLwA4nX3GQeG9kkAbmXc03Ly47xRSnA3sqGpbRMgq+EdAH0KbUyfRUMJhSt4dMCPUSF+WvvCl7cqr+lM+gwnC6Z6U6ntPEga1DMj4PLfiG5GF8YYddcZHJ7KwAIN8YOH8g5ZIWHa2LTX/huNtLDs7WjxIny7vgdODPmLzp8B/iZPEBwt7RP2CjZUOjq6AL1bPgIK7mSOfQH7WB+cTPFPxoREpom6lVBjFGoqJtEQnczBs+96lfqv6UJbUv+IIWEqb9Z+qFARx0H2it0YiNFFoUlcUcazlgpvoW+/vxcJrDPnOckIdAMhpnwT9nFo2x/xTXzqzvSY2KyHV7K7g4yES8THxFnaYwFj1qoKq0i6XoXNCsRQ5tE7qvy1ONEVDw4NZPd9GsCAwEAAQ==".getBytes("UTF-8"));
                        org.hzs.server.Property.rsa.g置公钥(ji自用.i_byteArray);
                        //无需压缩，压缩後反而变大
                        jd通信.d写出.write(ji自用.i_byteArray);
                        jd通信.d通信链.close();
                        continue;
                    } else if ("向服务器递交口令".equals(ji自用.i_s)) {
                        ji自用.i_s = ji自用.i_JSON.getString("_", ji_error);
                        ji自用.i_byteArray = org.hzs.编码.Base64.i解码_byteArray(ji自用.i_s.getBytes("UTF-8"));
                        ji自用.i口令_byteArray = org.hzs.server.Property.rsa.i用私钥解密_byteArray(ji自用.i_byteArray);
                        //取得配置文档内容
                        ji自用.d读 = new java.io.FileInputStream(ji自用.i配置文档_s);
                        ji自用.i_byteArray = java.util.Arrays.copyOf(ji自用.i_byteArray, 1024);
                        ji自用.i_i = ji自用.d读.read(ji自用.i_byteArray);
                        ji自用.i_byteArray = java.util.Arrays.copyOf(ji自用.i_byteArray, ji自用.i_i);
                        ji自用.i_byteArray = org.hzs.server.Property.rsa.i用公钥解密_byteArray(ji自用.i_byteArray, org.hzs.安全.RSA.i公钥(ji自用.i口令_byteArray));
                        //解析配置
                        ji自用.i_JSON = org.hzs.json.JSONObject.d副本();
                        ji自用.i_JSON.set(new String(ji自用.i_byteArray, "UTF-8"), ji_error);
                        ////判断软件使用是否到期，若到期时间为空则为永久使用
                        org.hzs.server.Property.i软件到期时间_L = ji自用.i_JSON.getLong("软件到期时间_L", ji_error);
                        ji自用.i当前时间 = java.util.Calendar.getInstance();
                        if (org.hzs.server.Property.i软件到期时间_L == null || org.hzs.server.Property.i软件到期时间_L.compareTo(ji自用.i当前时间.getTimeInMillis() - ji自用.i当前时间.getTimeZone().getRawOffset()) >= 0) {
                            org.hzs.server.Property.i數據库口令_s = ji自用.i_JSON.getString("數據库口令_s", ji_error);
                            org.hzs.server.Property.i數據库用户_s = ji自用.i_JSON.getString("數據库用户_s", ji_error);
                            org.hzs.server.Property.i數據库连接_s = ji自用.i_JSON.getString("數據库连接_s", ji_error);
                            org.hzs.server.Property.i數據密钥_byteArray = org.hzs.编码.Base64.i解码_byteArray(ji自用.i_JSON.getString("數據密钥_BASE64s", ji_error).getBytes("UTF-8"));
                            org.hzs.server.Property.i中心端口_i = ji自用.i_JSON.getShort("中心端口_i", ji_error);
                            org.hzs.server.Property.i集群号_byte = ji自用.i_JSON.getByte("集群号_byte", ji_error);
                            //启动负载均衡
                            org.hzs.server.负载均衡.__.init(ji自用.i_JSON.getJSONArray("集群服务器列表", ji_error), true, true);
                            new org.hzs.server.负载均衡.均衡(1, null);//物流
                            new org.hzs.server.负载均衡.均衡(2, null);//代收
                            new org.hzs.server.负载均衡.均衡(3, null);//会员
                            new org.hzs.server.负载均衡.均衡(4, null);//管理
                            new org.hzs.server.负载均衡.均衡(5, null);//客户
                        } else {
                            //启动负载均衡
                            org.hzs.server.负载均衡.__.init(null, true, true);
                        }
                        jd通信.d通信链.close();
                        break;
                    } else {
                        jd通信.d通信链.close();
                        continue;
                    }
                } catch (Exception ex) {
                    Logger.getLogger(混淆入口.class.getName()).log(Level.SEVERE, null, ex);
                    jd通信.d通信链.close();
                    continue;
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(混淆入口.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(0);
        } finally {
            // <editor-fold defaultstate="collapsed" desc="释放资源">
            if (ji自用 != null) {
                ji自用.close();
                ji自用 = null;
            }
            if (jd通信 != null) {
                jd通信.close();
                jd通信 = null;
            }
            // </editor-fold>
        }
    }

    @Override
    public JSONObject getSession(final int sessionid_i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeSession(int sessionid_i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object g处理请求(String ci过程_s, JSONObject ci_JSON, Session session, error ci_error) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private class hsession extends org.hzs.server.业务.Session {

        @Override
        public JSONObject toJSON(error ci_error) throws error {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public Session set(String ci_s, error ci_error) throws error {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }
}
