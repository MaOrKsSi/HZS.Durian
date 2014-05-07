package org.hzs.server.业务;

import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class __ {

    public static org.hzs.json.JSONObject i指令_JSON = null;

    public byte[] g处理请求(final String session_s, final byte[] ci客户数据_byteArray) {
        // <editor-fold defaultstate="collapsed" desc="自用">
        class 自用 implements Cloneable {

            Session session = null;
            org.hzs.json.JSONObject i_JSON = null, i指令_JSON = null, i1_JSON = null;
            org.hzs.logging.error i_error = null;
            String i_s = null, i过程_s = null;
            byte[] i_byteArray = null;
            java.util.Random d任意數 = null;

            自用 d副本() throws CloneNotSupportedException {
                return (自用) super.clone();
            }

            void close() {
                session = null;
                i_error = null;
                i_s = null;
                i过程_s = null;
                i_byteArray = null;
                d任意數 = null;
                if (i_JSON != null) {
                    i_JSON.clear();
                    i_JSON = null;
                }
                if (i指令_JSON != null) {
                    i指令_JSON.clear();
                    i指令_JSON = null;
                }
                if (i1_JSON != null) {
                    i1_JSON.clear();
                    i1_JSON = null;
                }
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
                            if (ji自用.session.i指令_ArrayJSON.indexOf(ji自用.i过程_s) < 0) {
                                ji自用.i_s = "{success:false,_:'禁用的指令！'}";
                                break;
                            }
                            ji自用.i_s = (String) g处理请求(ji自用.i过程_s, ji自用.i_JSON, ji自用.session, ji自用.i_error);
//                            ji自用.i过程_s = "com.hzs.pm65." + ji自用.i过程_s;
//                            ji自用.class1 = Class.forName(ji自用.i过程_s.substring(0, ji自用.i过程_s.lastIndexOf(".")));
//                            ji自用.method = ji自用.class1.getMethod(ji自用.i过程_s.substring(ji自用.i过程_s.lastIndexOf(".") + 1, ji自用.i过程_s.length()), org.hzs.json.JSONObject.class, com.hzs.pm65.Session.class, org.hzs.logging.error.class);
//                            ji自用.i_s = (String) ji自用.method.invoke(ji自用.class1.newInstance(), ji自用.i_JSON, ji自用.session, ji自用.i_error);
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
            Logger.getLogger(__.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | java.lang.reflect.InvocationTargetException | InstantiationException ex) {
            Logger.getLogger(__.class.getName()).log(Level.SEVERE, null, ex);
            return null;
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

    protected abstract Object g处理请求(final String ci过程_s, final org.hzs.json.JSONObject ci_JSON, final Session session, final org.hzs.logging.error ci_error) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, java.lang.reflect.InvocationTargetException, InstantiationException, CloneNotSupportedException;

    public abstract org.hzs.json.JSONObject getSession(final int sessionid_i);

    public abstract void removeSession(final int sessionid_i);
}
