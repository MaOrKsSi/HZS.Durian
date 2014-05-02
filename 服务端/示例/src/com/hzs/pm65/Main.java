package com.hzs.pm65;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main implements org.hzs.server.业务.__ {

    private static org.hzs.json.JSONObject i指令_JSON = null;

    public static void main(String[] args) {
        try {
            org.hzs.server.Property.init(5);
            org.hzs.server.Property.i數據库口令_s = "1";
            org.hzs.server.Property.i數據库用户_s = "postgres";
            org.hzs.server.Property.i數據库连接_s = "jdbc:postgresql://127.0.0.1:5432/postgres";
            org.hzs.server.Property.i數據密钥_byteArray = java.util.Base64.getDecoder().decode("ibPTSjHJ0+3AjR98W9UfEQ==");
            org.hzs.server.Property.i中心端口_i = 8080;
            org.hzs.server.Property.i集群号_byte = 0;
            //初始化数据库连接
            org.hzs.助记文本.g初始化();
            Class.forName(org.postgresql.Driver.class.getName());//载入数据库的jdbc
            com.hzs.pm65.__.d连接池 = new org.hzs.sql.连接池(org.hzs.server.Property.i集群号_byte, org.hzs.server.Property.i數據库连接_s, org.hzs.server.Property.i數據库用户_s, org.hzs.server.Property.i數據库口令_s, org.hzs.server.Property.i數據密钥_byteArray);
            //解析API图谱
            i指令_JSON = org.hzs.json.JSONObject.d副本();
            i指令_JSON.set("{\"登入\":{\"登入\":\"系统.登入.g登入\"},\"主页\":{\"导航分类\":\"系统.主页.i导航分类\",\"导航树\":\"系统.主页.i导航树\"}}", null);
//            tmp初始化_正式发佈时删除();
            //启动负载均衡
            org.hzs.json.JSONArray dd = org.hzs.json.JSONArray.d副本();
            dd.put("10.144.51.50");
            org.hzs.server.负载均衡.__.init(dd, true, true);//集群内各节点IP、是否提供负载均衡服务、是否提供业务服务
            new org.hzs.server.负载均衡.第二层(1, new Main());
            System.out.println("启动完毕");
        } catch (java.io.UnsupportedEncodingException | java.security.NoSuchAlgorithmException | java.security.spec.InvalidKeySpecException | java.net.SocketException | ClassNotFoundException | java.sql.SQLException | CloneNotSupportedException | org.hzs.logging.error ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
        }
    }

    @Override
    public byte[] g处理请求(final String session_s, final byte[] ci客户数据_byteArray) {
        // <editor-fold defaultstate="collapsed" desc="自用">
        class 自用 implements Cloneable {

            Session session = null;
            org.hzs.json.JSONObject i_JSON = null, i指令_JSON = null, i1_JSON = null;
            org.hzs.logging.error i_error = null;
            String i_s = null, i过程_s = null;
            byte[] i_byteArray = null;
            java.util.Random d任意數 = null;
            Class<?> class1 = null;
            java.lang.reflect.Method method = null;

            public 自用 d副本() throws CloneNotSupportedException {
                return (自用) super.clone();
            }
        }
        // </editor-fold>
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
            ji自用.i_error = org.hzs.logging.error.d副本();
            ji自用.session = Session.d副本();
            ji自用.session.set(session_s, ji自用.i_error);
            ji自用.i_JSON = org.hzs.json.JSONObject.d副本();
            ji自用.i_JSON.set(new String(ci客户数据_byteArray, "UTF-8"), ji自用.i_error);
            ji自用.i指令_JSON = ji自用.i_JSON.getJSONObject("$", ji自用.i_error);
            ji自用.i_JSON = ji自用.i_JSON.getJSONObject("_", ji自用.i_error);
            if (ji自用.i指令_JSON.getString("指令0", ji自用.i_error) == null) {
                ji自用.i指令_JSON.put("指令0", "", ji自用.i_error);
            }
            switch (ji自用.i指令_JSON.getString("指令0", ji自用.i_error)) {
                case "当前时间":
                    java.util.Calendar jd = java.util.Calendar.getInstance();
                    ji自用.i_s = "{success:true,_:" + (jd.getTimeInMillis() - jd.getTimeZone().getRawOffset()) + "}";
                    break;
                case "入团":
                    if (ji自用.session.i指令团_ArrayJSON.size() > 0) {
                        ji自用.i_s = "{success:false,_:'您无权执行此指令！'}";
                        break;
                    }
                    ji自用.session.i指令团_ArrayJSON.put(ji自用.i_JSON.getString("_", ji自用.i_error));
                    ji自用.d任意數 = new java.util.Random();
                    ji自用.i_s = "{success:true,_:'" + ji自用.d任意數.nextInt() + "'}";
                    break;
                case "退团":
                    ji自用.session.i指令团_ArrayJSON.clear();
                    ji自用.i_s = "{success:true,_:''}";
                    break;
                default:
                    if (ji自用.session.i指令团_ArrayJSON.size() < 1) {
                        ji自用.i_s = "{success:false,_:'您无权执行此指令！'}";
                        break;
                    }
                    //进入指令团
                    ji自用.i1_JSON = i指令_JSON;
                    int ji1_i = ji自用.session.i指令团_ArrayJSON.size();
                    for (int ji2_i = 0; ji2_i < ji1_i; ji2_i++) {
                        ji自用.i1_JSON = ji自用.i1_JSON.getJSONObject(ji自用.session.i指令团_ArrayJSON.getString(ji2_i), ji自用.i_error);
                        if (ji自用.i1_JSON == null) {
                            ji自用.i_s = "{success:false,_:'非法指令！'}";
                            break;
                        }
                    }
                    //根据指令进行操作
                    switch (ji自用.i指令_JSON.getString("指令1", ji自用.i_error)) {
                        case "退团":
                            ji自用.session.i指令团_ArrayJSON.remove(ji自用.session.i指令团_ArrayJSON.size() - 1);
                            ji自用.i_s = "{success:true,_:''}";
                            break;
                        case "入团":
                            ji自用.session.i指令团_ArrayJSON.put(ji自用.i_JSON.getString("_", ji自用.i_error));
                            ji自用.d任意數 = new java.util.Random();
                            ji自用.i_s = "{success:true,_:'" + ji自用.d任意數.nextInt() + "'}";
                            break;
                        default:
                            ji自用.i过程_s = ji自用.i1_JSON.getString(ji自用.i指令_JSON.getString("指令1", ji自用.i_error), ji自用.i_error);
                            if (ji自用.i过程_s == null) {
                                ji自用.i_s = "{success:false,_:'无效指令！'}";
                                break;
                            }
                            ji自用.i过程_s = "com.hzs.pm65." + ji自用.i过程_s;
                            ji自用.class1 = Class.forName(ji自用.i过程_s.substring(0, ji自用.i过程_s.lastIndexOf(".")));
                            ji自用.method = ji自用.class1.getMethod(ji自用.i过程_s.substring(ji自用.i过程_s.lastIndexOf(".") + 1, ji自用.i过程_s.length()), org.hzs.json.JSONObject.class, com.hzs.pm65.Session.class, org.hzs.logging.error.class);
                            ji自用.i_s = (String) ji自用.method.invoke(ji自用.class1.newInstance(), ji自用.i_JSON, ji自用.session, ji自用.i_error);
                            break;
                    }
            }
            ji自用.i_JSON.clear();
            ji自用.i_JSON.put("$", ji自用.session.toJSON(ji自用.i_error).toString(ji自用.i_error), ji自用.i_error);
            ji自用.i_JSON.put("_", ji自用.i_s, ji自用.i_error);
            ji自用.i_byteArray = ji自用.i_JSON.toString(ji自用.i_error).getBytes("UTF-8");
            ji未捕获错误_b = false;
            return ji自用.i_byteArray;
        } catch (org.hzs.logging.error | java.io.IOException | CloneNotSupportedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | java.lang.reflect.InvocationTargetException | InstantiationException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            ji自用 = null;
            if (ji未捕获错误_b) {
                return null;
            }
        }
    }

    @Override
    public org.hzs.json.JSONObject getSession(final int sessionid_i) {
        Session session = null;
        try {
            session = com.hzs.pm65.__.session_集合.get(sessionid_i);
            if (session == null) {
                session = Session.d副本();
                com.hzs.pm65.__.session_集合.put(sessionid_i, session);
            }
            return session.toJSON(null);
        } catch (org.hzs.logging.error ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            session = null;
        }
    }

    @Override
    public void removeSession(final int sessionid_i) {
        com.hzs.pm65.__.session_集合.remove(sessionid_i);
    }

}
