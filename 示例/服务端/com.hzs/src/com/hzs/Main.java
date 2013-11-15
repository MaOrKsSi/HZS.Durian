package com.hzs;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) {
        try {
            org.hzs.server.负载均衡.Property.init(5);//参数为启动功能的个数。这里有物流、会员、客户、管理、代收点
            org.hzs.server.负载均衡.Property.i中心端口_i = 8080;
            org.hzs.server.负载均衡.Property.i集群号_byte = 0;
            org.hzs.server.负载均衡.Property.i业务服务_b = true;
            org.hzs.server.负载均衡.Property.i发送缓冲区容量_i = 5 * 1024 * 1024;//5M
            org.hzs.server.负载均衡.Property.i接收缓冲区容量_i = 10 * 1024;//10K
            org.hzs.server.负载均衡.Property.i负载服务_b = true;
            org.hzs.server.负载均衡.Property.i频率上限_i = 36;
            org.hzs.server.负载均衡.Property.i集群内服务器列表_ArrayJSON = org.hzs.json.JSONArray.d副本();
            org.hzs.server.负载均衡.Property.i集群内服务器列表_ArrayJSON.put("192.168.0.113");
            //初始化数据库连接
            Class.forName(org.postgresql.Driver.class.getName());//载入数据库的jdbc
//            com.hzs.__.d连接池 = new org.hzs.sql.连接池(org.hzs.server.负载均衡.Property.i集群号_byte, "jdbc:postgresql://127.0.0.1:5432/da4lg3_cs2vt4_yu4sc4", "postgres", "1", org.hzs.编码.Base64.i解码_byteArray("ibPTSjHJ0+3AjR98W9UfEQ==".getBytes("UTF-8")));
//            tmp初始化_正式发佈时删除();
            //启动负载均衡
            org.hzs.server.负载均衡.__.init();
            new org.hzs.server.负载均衡.均衡(1, new 农学());
            new org.hzs.server.负载均衡.均衡(2, new 医学());
            System.out.println("启动完毕");
        } catch (java.io.UnsupportedEncodingException | java.security.NoSuchAlgorithmException | java.security.spec.InvalidKeySpecException | java.net.SocketException | ClassNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (java.sql.SQLException | CloneNotSupportedException ex) {
//            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
        }
    }
}
