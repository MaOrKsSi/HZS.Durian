package com.hzs;

import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class __ {

    public static org.hzs.sql.连接池 d连接池 = null;//不同模块采用同一个连接池，意味着不同模块用同一个数据库

    private static final String i类名_s = "com.zzkj.da4lg3cs2vt4.yu4sc4.__";
    private static final String i锁定记录_s = "public._lock_id", i锁定表_s = "public._lock_bk3";

    public static void g锁定记录(final com.hzs.Session session, final org.hzs.lang.ID ci_ID, final org.hzs.logging.error ci_error) throws org.hzs.logging.error {
        final String ji过程名_s = "g锁定记录";
        // <editor-fold defaultstate="collapsed" desc="自用">
        class 自用 implements Cloneable, org.hzs.Close {

            public org.hzs.sql.连接 d连接 = null;
            public org.hzs.sql.视图 d视图 = null;
            public org.hzs.sql.写操作 d写操作 = null;

            public 自用 d副本() throws CloneNotSupportedException, java.sql.SQLException, java.net.SocketException, UnsupportedEncodingException {
                自用 jd = (自用) super.clone();
                jd.d连接 = d连接池.d连接();
                return jd;
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
            ji自用.d写操作 = ji自用.d连接.d操作("DELETE FROM " + i锁定记录_s + " WHERE (extract('epoch' from CURRENT_TIMESTAMP)::bigint) - time > 500 AND id=?;", ci_error);
            ji自用.d写操作.g置参數_ID(1, ci_ID);
            ji自用.d写操作.g立即执行();
            ji自用.d连接.g递交事务();
            ji自用.d写操作 = ji自用.d连接.d操作("INSERT INTO " + i锁定记录_s + "(id, \"[cd1zo4yc2]id\", \"time\") VALUES (?, ?, extract('epoch' from CURRENT_TIMESTAMP)::bigint);", ci_error);
            ji自用.d写操作.g置参數_ID(1, ci_ID);
            ji自用.d写操作.g置参數_ID(2, session.i操作员_ID);
            ji自用.d写操作.g立即执行();
            ji自用.d连接.g递交事务();
        } catch (CloneNotSupportedException | java.sql.SQLException | java.net.SocketException | UnsupportedEncodingException ex) {
            Logger.getLogger(__.class.getName()).log(Level.SEVERE, null, ex);
            // <editor-fold defaultstate="collapsed" desc="抛出错误">
            ci_error.g添加错误信息("{类:'" + i类名_s + "',"
                    + "过程:'" + ji过程名_s + "',"
                    + "序号:'1'"
                    + "错误类型:'捕获',"
                    + "错误:'" + ex.getMessage() + "'}");
            throw ci_error;
            // </editor-fold>
        } catch (org.hzs.logging.error ex) {
            Logger.getLogger(__.class.getName()).log(Level.SEVERE, null, ex);
            // <editor-fold defaultstate="collapsed" desc="抛出错误">
            ex.g添加错误信息("{类:'" + i类名_s + "',"
                    + "过程:'" + ji过程名_s + "',"
                    + "序号:''"
                    + "错误类型:'捕获',"
                    + "错误:''}");
            throw ex;
            // </editor-fold>
        } finally {
            if (ji自用 != null) {
                ji自用.close();
                ji自用 = null;
            }
        }
    }

    public static void g锁定表(final com.hzs.Session session, final String ci表_s, final org.hzs.logging.error ci_error) throws org.hzs.logging.error {
        final String ji过程名_s = "g锁定记录";
        // <editor-fold defaultstate="collapsed" desc="自用">
        class 自用 implements Cloneable, org.hzs.Close {

            public org.hzs.sql.连接 d连接 = null;
            public org.hzs.sql.视图 d视图 = null;
            public org.hzs.sql.写操作 d写操作 = null;

            public 自用 d副本() throws CloneNotSupportedException, java.sql.SQLException, java.net.SocketException, UnsupportedEncodingException {
                自用 jd = (自用) super.clone();
                jd.d连接 = d连接池.d连接();
                return jd;
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
            ji自用.d写操作 = ji自用.d连接.d操作("DELETE FROM " + i锁定表_s + " WHERE (extract('epoch' from CURRENT_TIMESTAMP)::bigint) - time > 500 AND text = ?;", ci_error);
            ji自用.d写操作.g置参數_s(1, ci表_s);
            ji自用.d写操作.g立即执行();
            ji自用.d连接.g递交事务();
            ji自用.d写操作 = ji自用.d连接.d操作("INSERT INTO " + i锁定表_s + "(text, \"[cd1zo4yc2]id\", \"time\") VALUES (?, ?, extract('epoch' from CURRENT_TIMESTAMP)::bigint);", ci_error);
            ji自用.d写操作.g置参數_s(1, ci表_s);
            ji自用.d写操作.g置参數_ID(2, session.i操作员_ID);
            ji自用.d写操作.g立即执行();
            ji自用.d连接.g递交事务();
        } catch (CloneNotSupportedException | java.sql.SQLException | java.net.SocketException | UnsupportedEncodingException ex) {
            Logger.getLogger(__.class.getName()).log(Level.SEVERE, null, ex);
            // <editor-fold defaultstate="collapsed" desc="抛出错误">
            ci_error.g添加错误信息("{类:'" + i类名_s + "',"
                    + "过程:'" + ji过程名_s + "',"
                    + "序号:'1'"
                    + "错误类型:'捕获',"
                    + "错误:'" + ex.getMessage() + "'}");
            throw ci_error;
            // </editor-fold>
        } catch (org.hzs.logging.error ex) {
            Logger.getLogger(__.class.getName()).log(Level.SEVERE, null, ex);
            // <editor-fold defaultstate="collapsed" desc="抛出错误">
            ex.g添加错误信息("{类:'" + i类名_s + "',"
                    + "过程:'" + ji过程名_s + "',"
                    + "序号:''"
                    + "错误类型:'捕获',"
                    + "错误:''}");
            throw ex;
            // </editor-fold>
        } finally {
            if (ji自用 != null) {
                ji自用.close();
                ji自用 = null;
            }
        }
    }

    public static void g解锁记录(final com.hzs.Session session, final org.hzs.lang.ID ci_ID, final org.hzs.logging.error ci_error) throws org.hzs.logging.error {
        final String ji过程名_s = "g解锁记录";
        // <editor-fold defaultstate="collapsed" desc="自用">
        class 自用 implements Cloneable, org.hzs.Close {

            public org.hzs.sql.连接 d连接 = null;
            public org.hzs.sql.视图 d视图 = null;
            public org.hzs.sql.写操作 d写操作 = null;

            public 自用 d副本() throws CloneNotSupportedException, java.sql.SQLException, java.net.SocketException, UnsupportedEncodingException {
                自用 jd = (自用) super.clone();
                jd.d连接 = d连接池.d连接();
                return jd;
            }
        }// </editor-fold>
        自用 ji自用 = null;
        try {
            if (ci_ID == null) {
                g解锁(session, ci_error);
                return;
            }
            // <editor-fold defaultstate="collapsed" desc="利用对象复制初始化过程内部变量">
            ji自用 = (自用) org.hzs.常用.d对象池.get(自用.class.getName());
            if (ji自用 == null) {
                ji自用 = new 自用();
                org.hzs.常用.d对象池.put(自用.class.getName(), ji自用);
            }
            ji自用 = ji自用.d副本();
            // </editor-fold>
            ji自用.d写操作 = ji自用.d连接.d操作("DELETE FROM " + i锁定记录_s + " WHERE \"[cd1zo4yc2]id\"=? AND id=?;", ci_error);
            ji自用.d写操作.g置参數_ID(-1, ci_ID);
            ji自用.d写操作.g置参數_ID(-2, session.i操作员_ID);
            ji自用.d写操作.g立即执行();
            ji自用.d连接.g递交事务();
        } catch (CloneNotSupportedException | java.sql.SQLException | java.net.SocketException | UnsupportedEncodingException ex) {
            Logger.getLogger(__.class.getName()).log(Level.SEVERE, null, ex);
            // <editor-fold defaultstate="collapsed" desc="抛出错误">
            ci_error.g添加错误信息("{类:'" + i类名_s + "',"
                    + "过程:'" + ji过程名_s + "',"
                    + "序号:''"
                    + "错误类型:'捕获',"
                    + "错误:'" + ex.getMessage() + "'}");
            throw ci_error;
            // </editor-fold>
        } catch (org.hzs.logging.error ex) {
            Logger.getLogger(__.class.getName()).log(Level.SEVERE, null, ex);
            // <editor-fold defaultstate="collapsed" desc="抛出错误">
            ex.g添加错误信息("{类:'" + i类名_s + "',"
                    + "过程:'" + ji过程名_s + "',"
                    + "序号:''"
                    + "错误类型:'捕获',"
                    + "错误:''}");
            throw ex;
            // </editor-fold>
        } finally {
            if (ji自用 != null) {
                ji自用.close();
                ji自用 = null;
            }
        }
    }

    public static void g解锁表(final com.hzs.Session session, final String ci表_s, final org.hzs.logging.error ci_error) throws org.hzs.logging.error {
        final String ji过程名_s = "g解锁记录";
        // <editor-fold defaultstate="collapsed" desc="自用">
        class 自用 implements Cloneable, org.hzs.Close {

            public org.hzs.sql.连接 d连接 = null;
            public org.hzs.sql.视图 d视图 = null;
            public org.hzs.sql.写操作 d写操作 = null;

            public 自用 d副本() throws CloneNotSupportedException, java.sql.SQLException, java.net.SocketException, UnsupportedEncodingException {
                自用 jd = (自用) super.clone();
                jd.d连接 = d连接池.d连接();
                return jd;
            }
        }// </editor-fold>
        自用 ji自用 = null;
        try {
            if (ci表_s == null) {
                g解锁(session, ci_error);
                return;
            }
            // <editor-fold defaultstate="collapsed" desc="利用对象复制初始化过程内部变量">
            ji自用 = (自用) org.hzs.常用.d对象池.get(自用.class.getName());
            if (ji自用 == null) {
                ji自用 = new 自用();
                org.hzs.常用.d对象池.put(自用.class.getName(), ji自用);
            }
            ji自用 = ji自用.d副本();
            // </editor-fold>
            ji自用.d写操作 = ji自用.d连接.d操作("DELETE FROM " + i锁定表_s + " WHERE \"[cd1zo4yc2]id\"=? AND text=?;", ci_error);
            ji自用.d写操作.g置参數_s(-1, ci表_s);
            ji自用.d写操作.g置参數_ID(-2, session.i操作员_ID);
            ji自用.d写操作.g立即执行();
            ji自用.d连接.g递交事务();
        } catch (CloneNotSupportedException | java.sql.SQLException | java.net.SocketException | UnsupportedEncodingException ex) {
            Logger.getLogger(__.class.getName()).log(Level.SEVERE, null, ex);
            // <editor-fold defaultstate="collapsed" desc="抛出错误">
            ci_error.g添加错误信息("{类:'" + i类名_s + "',"
                    + "过程:'" + ji过程名_s + "',"
                    + "序号:''"
                    + "错误类型:'捕获',"
                    + "错误:'" + ex.getMessage() + "'}");
            throw ci_error;
            // </editor-fold>
        } catch (org.hzs.logging.error ex) {
            Logger.getLogger(__.class.getName()).log(Level.SEVERE, null, ex);
            // <editor-fold defaultstate="collapsed" desc="抛出错误">
            ex.g添加错误信息("{类:'" + i类名_s + "',"
                    + "过程:'" + ji过程名_s + "',"
                    + "序号:''"
                    + "错误类型:'捕获',"
                    + "错误:''}");
            throw ex;
            // </editor-fold>
        } finally {
            if (ji自用 != null) {
                ji自用.close();
                ji自用 = null;
            }
        }
    }

    public static void g解锁(final com.hzs.Session session, final org.hzs.logging.error ci_error) throws org.hzs.logging.error {
        final String ji过程名_s = "g解锁记录(";
        // <editor-fold defaultstate="collapsed" desc="自用">
        class 自用 implements Cloneable, org.hzs.Close {

            public org.hzs.sql.连接 d连接 = null;
            public org.hzs.sql.视图 d视图 = null;
            public org.hzs.sql.写操作 d写操作 = null;

            public 自用 d副本() throws CloneNotSupportedException, java.sql.SQLException, java.net.SocketException, UnsupportedEncodingException {
                自用 jd = (自用) super.clone();
                jd.d连接 = d连接池.d连接();
                return jd;
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
            ji自用.d写操作 = ji自用.d连接.d操作("DELETE FROM " + i锁定记录_s + " WHERE \"[cd1zo4yc2]id\"=?;", ci_error);
            ji自用.d写操作.g置参數_ID(-1, session.i操作员_ID);
            ji自用.d写操作.g立即执行();

            ji自用.d写操作 = ji自用.d连接.d操作("DELETE FROM " + i锁定表_s + " WHERE \"[cd1zo4yc2]id\"=?;", ci_error);
            ji自用.d写操作.g置参數_ID(-1, session.i操作员_ID);
            ji自用.d写操作.g立即执行();

            ji自用.d连接.g递交事务();
        } catch (CloneNotSupportedException | java.sql.SQLException | java.net.SocketException | UnsupportedEncodingException ex) {
            Logger.getLogger(__.class.getName()).log(Level.SEVERE, null, ex);
            // <editor-fold defaultstate="collapsed" desc="抛出错误">
            ci_error.g添加错误信息("{类:'" + i类名_s + "',"
                    + "过程:'" + ji过程名_s + "',"
                    + "序号:''"
                    + "错误类型:'捕获',"
                    + "错误:'" + ex.getMessage() + "'}");
            throw ci_error;
            // </editor-fold>
        } catch (org.hzs.logging.error ex) {
            Logger.getLogger(__.class.getName()).log(Level.SEVERE, null, ex);
            // <editor-fold defaultstate="collapsed" desc="抛出错误">
            ex.g添加错误信息("{类:'" + i类名_s + "',"
                    + "过程:'" + ji过程名_s + "',"
                    + "序号:''"
                    + "错误类型:'捕获',"
                    + "错误:''}");
            throw ex;
            // </editor-fold>
        } finally {
            if (ji自用 != null) {
                ji自用.close();
                ji自用 = null;
            }
        }
    }
}
