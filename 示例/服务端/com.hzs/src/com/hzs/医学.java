package com.hzs;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.hzs.logging.error;

public class 医学 extends org.hzs.server.业务.__ {

    public java.util.TreeMap<Integer, com.hzs.医学_Session> session_集合 = new java.util.TreeMap<>();
    private final java.util.TreeMap<String, String> i资源指纹 = new java.util.TreeMap<String, String>();
    private final java.util.TreeMap<String, String> i资源路径 = new java.util.TreeMap<String, String>();

    public 医学() {
        try {
            //解析API图谱
            i指令_JSON = org.hzs.json.JSONObject.d副本();
            i指令_JSON.set("{'登入':{'登入':'系统.登入.g登入'},'主页':{'导航分类':'系统.主页.i导航分类','导航树':'系统.主页.i导航树'}}", null);
            i资源指纹.put("base.hz", "RzAdcX06BkykKyoLAXw1faRv5s5fiSQjkdlYZCWLJ2QOVffQQzcT4oxeBVXmHb6BSLI6iX6/BTzgM0i2qUYO+w==");
            i资源指纹.put("医学.hz", "sJs5f9IiMbfa0wyYuMbJx2dEEJCHr8Q/kHnYXr728W84ALELwxIJsn2vhB815rfJaorrypJ7+tjsLu6/8tKaEw==");
            i资源路径.put("base.hz", "http://192.168.0.113/base.hz");
            i资源路径.put("医学.hz", "http://192.168.0.113/医学.hz");
        } catch (error ex) {
            Logger.getLogger(医学.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
        }
    }
    //============================================================================================================

    @Override
    public org.hzs.json.JSONObject getSession(final int sessionid_i) {
        com.hzs.医学_Session session = null;
        try {
            session = session_集合.get(sessionid_i);
            if (session == null) {
                session = com.hzs.医学_Session.d副本();
                session.i指令_JSON = org.hzs.json.JSONObject.d副本().set("{'登入': {'登入':'系统.登入.g登入'}}", null);
                session_集合.put(sessionid_i, session);
            }
            return session.toJSON(null);
        } catch (org.hzs.logging.error ex) {
            Logger.getLogger(医学.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            session = null;
        }
    }

    @Override
    public void removeSession(int sessionid_i) {
        com.hzs.医学_Session session = session_集合.get(sessionid_i);
        if (session != null) {
            session_集合.remove(sessionid_i);
            session.close();
            session = null;
        }
    }

    @Override
    public byte[] g处理请求(final String session_s, final byte[] ci客户数据_byteArray) {
        // <editor-fold defaultstate="collapsed" desc="自用">
        class 自用 implements Cloneable, org.hzs.Close {

            com.hzs.医学_Session session = null;
            org.hzs.json.JSONObject i_JSON = null, i指令_JSON = null, i1_JSON = null;
            org.hzs.logging.error i_error = null;
            String i_s = null, i过程_s = null;
            java.util.Random d任意數 = null;

            自用 d副本() throws CloneNotSupportedException {
                return (自用) super.clone();
            }
        }
        // </editor-fold>
        byte[] ji递出_byteArray = null;
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
            ji自用.session = com.hzs.医学_Session.d副本();
            ji自用.session.set(session_s, ji自用.i_error);
            ji自用.i_JSON = org.hzs.json.JSONObject.d副本();
            ji自用.i_JSON.set(new String(ci客户数据_byteArray, "UTF-8"), ji自用.i_error);
            ji自用.i指令_JSON = ji自用.i_JSON.getJSONObject("$", ji自用.i_error);
            ji自用.i_JSON = ji自用.i_JSON.getJSONObject("_", ji自用.i_error);
            if (ji自用.i指令_JSON.getString("指令0", ji自用.i_error) == null) {
                ji自用.i指令_JSON.put("指令0", "", ji自用.i_error);
            }
            switch (ji自用.i指令_JSON.getString("指令0", ji自用.i_error)) {
                case "资源"://告诉客户端需要下载哪些资源、到哪下载
                    org.hzs.json.JSONObject ji_JSON = org.hzs.json.JSONObject.d副本();
                    java.util.Iterator keys = null;
                    Object key = null;
                    keys = i资源指纹.keySet().iterator();
                    String value = null;
                    while (keys.hasNext()) {
                        key = keys.next();
                        value = ji自用.i_JSON.getString((String) key, null);
                        if (value != null && i资源指纹.get(key).equals(value)) {
                            continue;
                        }
                        ji_JSON.put(key, i资源路径.get(key));
                    }
                    ji自用.i_s = ji_JSON.toString();
                    break;
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
                    ji自用.i1_JSON = ji自用.session.i指令_JSON;
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
                            ji自用.i_s = (String) g处理请求(ji自用.i过程_s, ji自用.i_JSON, ji自用.session, ji自用.i_error);
                            break;
                    }
            }
            ji自用.i_JSON.clear();
            ji自用.i_JSON.put("$", ji自用.session.toJSON(ji自用.i_error).toString(ji自用.i_error), ji自用.i_error);
            ji自用.i_JSON.put("_", ji自用.i_s, ji自用.i_error);
            ji递出_byteArray = ji自用.i_JSON.toString(ji自用.i_error).getBytes("UTF-8");
            ji未捕获错误_b = false;
            return ji递出_byteArray;
        } catch (org.hzs.logging.error ex) {
            Logger.getLogger(医学.class.getName()).log(Level.SEVERE, null, ex);
            ji未捕获错误_b = false;
            ex.g记录();
            return null;
        } catch (java.io.IOException | CloneNotSupportedException ex) {
            Logger.getLogger(医学.class.getName()).log(Level.SEVERE, null, ex);
            ji未捕获错误_b = false;
            return null;
        } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | java.lang.reflect.InvocationTargetException | InstantiationException ex) {
            Logger.getLogger(医学.class.getName()).log(Level.SEVERE, null, ex);
            ji未捕获错误_b = false;
            return null;
        } finally {
            if (ji自用 != null) {
                ji自用.i1_JSON = null;//提前置空，防止过度释放
                ji自用.close();
                ji自用 = null;
            }
            if (ji未捕获错误_b) {
                return null;
            }
        }
    }

    private Object g处理请求(String ci过程_s, org.hzs.json.JSONObject ci_JSON, com.hzs.Session session, org.hzs.logging.error ci_error) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, java.lang.reflect.InvocationTargetException, InstantiationException, CloneNotSupportedException {
        // <editor-fold defaultstate="collapsed" desc="自用">
        class 自用 implements Cloneable, org.hzs.Close {

            String i过程_s = null;
            Class<?> class1 = null;
            java.lang.reflect.Method method = null;
            String i_s = null;

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
            ji自用.i过程_s = "com.hzs." + ci过程_s;
            ji自用.class1 = Class.forName(ji自用.i过程_s.substring(0, ji自用.i过程_s.lastIndexOf(".")));
            ji自用.method = ji自用.class1.getMethod(ji自用.i过程_s.substring(ji自用.i过程_s.lastIndexOf(".") + 1, ji自用.i过程_s.length()), org.hzs.json.JSONObject.class, com.hzs.Session.class, org.hzs.logging.error.class);
            ji自用.i_s = (String) ji自用.method.invoke(ji自用.class1.newInstance(), ci_JSON, session, ci_error);
            ji未捕获错误_b = false;
            return ji自用.i_s;
        } finally {
            if (ji自用 != null) {
                ji自用.close();
                ji自用 = null;
            }
            if (ji未捕获错误_b) {
                return null;
            }
        }
    }
}
