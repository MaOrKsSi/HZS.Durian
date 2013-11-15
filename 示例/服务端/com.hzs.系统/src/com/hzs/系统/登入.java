package com.hzs.系统;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class 登入 extends com.hzs.__ {

    private static final String i类名_s = "com.hzs.系统.登入.";
    private static int i_i = 1;

    public static String g登入(final org.hzs.json.JSONObject ci参數_JSON, final com.hzs.Session session, final org.hzs.logging.error ci_error) throws org.hzs.logging.error {
        final String ji过程名_s = "g登入";
        // <editor-fold defaultstate="collapsed" desc="记录过程">
        ci_error.g添加过程信息("{类:'" + i类名_s + "',"
                + "过程:'" + ji过程名_s + "'}");
        // </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="参數">
        class 参數 implements Cloneable, org.hzs.Close {

            String i验证码_s = null, i口令_s = null, i手机号_s = null, i系统_s = null;

            参數 d副本(org.hzs.json.JSONObject ci参數_JSON, final org.hzs.logging.error ci_error) throws CloneNotSupportedException, org.hzs.logging.error {
                参數 jd = (参數) super.clone();
                jd.i验证码_s = ci参數_JSON.getString("验证码_s", ci_error);
                jd.i口令_s = ci参數_JSON.getString("口令_s", ci_error);
                jd.i手机号_s = ci参數_JSON.getString("手机号_s", ci_error);
                return jd;
            }
        }// </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="自用">
        class 自用 implements Cloneable, org.hzs.Close {

            org.hzs.sql.视图 d视图 = null;

            自用 d副本() throws CloneNotSupportedException, java.sql.SQLException {
                自用 jd = (自用) super.clone();
                return jd;
            }
        }// </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="递出">
        class 递出 implements Cloneable, org.hzs.Close {

            org.hzs.json.JSONObject i_JSON = null;
            String i_s = null;

            递出 d副本() throws CloneNotSupportedException {
                return (递出) super.clone();
            }
        }// </editor-fold>
        参數 ji参數 = null;
        自用 ji自用 = null;
        递出 ji递出 = null;
        boolean ji未捕获错误_b = true;
        try {
            // <editor-fold defaultstate="collapsed" desc="利用对象复制初始化过程内部变量">
            ji自用 = (自用) org.hzs.常用.d对象池.get(自用.class.getName());
            if (ji自用 == null) {
                ji自用 = new 自用();
                org.hzs.常用.d对象池.put(自用.class.getName(), ji自用);
                ji参數 = new 参數();
                org.hzs.常用.d对象池.put(参數.class.getName(), ji参數);
                ji递出 = new 递出();
                org.hzs.常用.d对象池.put(递出.class.getName(), ji递出);
            } else {
                ji参數 = (参數) org.hzs.常用.d对象池.get(参數.class.getName());
                ji递出 = (递出) org.hzs.常用.d对象池.get(递出.class.getName());
            }
            ji参數 = ji参數.d副本(ci参數_JSON, ci_error);
            ji自用 = ji自用.d副本();
            ji递出 = ji递出.d副本();
            // </editor-fold>
            ji递出.i_JSON = org.hzs.json.JSONObject.d副本();
            ji递出.i_JSON.put("success", true, ci_error);
            ji递出.i_JSON.put("_", i_i, ci_error);
            ji递出.i_s = ji递出.i_JSON.toString(ci_error);
            i_i++;
            ji未捕获错误_b = false;
            return ji递出.i_s;
        } catch (CloneNotSupportedException | java.sql.SQLException ex) {
            Logger.getLogger(登入.class.getName()).log(Level.SEVERE, null, ex);
            ji未捕获错误_b = false;
            // <editor-fold defaultstate="collapsed" desc="抛出错误">
            ci_error.g添加错误信息("{类:'" + i类名_s + "',"
                    + "过程:'" + ji过程名_s + "',"
                    + "序号:'',"
                    + "错误类型:'捕获',"
                    + "错误:'" + ex.getMessage() + "'}");
            throw ci_error;
            // </editor-fold>
        } finally {
            // <editor-fold defaultstate="collapsed" desc="释放资源">
            if (ji参數 != null) {
                ji参數.close();
                ji参數 = null;
            }
            if (ji自用 != null) {
                ji自用.close();
                ji自用 = null;
            }
            if (ji递出 != null) {
                ji递出.close();
                ji递出 = null;
            }
            // </editor-fold>
            if (ji未捕获错误_b) {
                // <editor-fold defaultstate="collapsed" desc="抛出错误">
                ci_error.g添加错误信息("{类:'" + i类名_s + "',"
                        + "过程:'" + ji过程名_s + "',"
                        + "序号:'',"
                        + "错误类型:'未捕获',"
                        + "错误:''}");
                throw ci_error;
                // </editor-fold>
            }
        }
    }
}
