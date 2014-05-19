package org.hzs.sql;

import java.io.UnsupportedEncodingException;
import java.net.SocketException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 提供连接<BR>共用连接
 */
public final class 连接池 {

    private final static String i类_s = "org.hzs.sql.连接池.";

    private class i {

        java.util.TreeMap<Integer, 连接class> d连接池 = null;
        int i终端号_i = Integer.MIN_VALUE;
        String i连接文本_s = null, i用户_s = null, i口令_s = null;
        byte i集群号_byte;
        short i服务器号_i;//由数据库竞争得到
        连接 d保留连接 = null;//用于查询
        byte[] i密钥_byteArray = null;//密钥为空，则采用默认密钥为 "".getBytes()
    }
    private i i = new i();

    public 连接池(final byte ci集群号_byte, final String ci连接文本_s, final String ci用户_s, final String ci口令_s, byte[] ci密钥_byteArray) throws java.sql.SQLException, CloneNotSupportedException, java.net.SocketException, UnsupportedEncodingException {
        i.i密钥_byteArray = ci密钥_byteArray;
        i.i集群号_byte = ci集群号_byte;
        i.i连接文本_s = ci连接文本_s;
        i.i用户_s = ci用户_s;
        i.i口令_s = ci口令_s;
        i.d连接池 = new java.util.TreeMap<Integer, 连接class>();
        i.d保留连接 = jd连接(0, null);
        ((连接class) i.d保留连接).d连接.setAutoCommit(true);
        //清理终端号
        (new java.lang.Thread() {

            @Override
            public void run() {
                try {
                    try {
                        java.lang.Thread.sleep(50_000);
                    } catch (InterruptedException ex) {
                    }
                    jd连接(2, null);
                } catch (CloneNotSupportedException | SQLException | SocketException | UnsupportedEncodingException ex) {
                    Logger.getLogger(连接池.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                }
            }
        }).start();
    }

    private short i服务器号() throws java.sql.SQLException, CloneNotSupportedException, java.net.SocketException, UnsupportedEncodingException {
        // <editor-fold defaultstate="collapsed" desc="自用">
        class 自用 implements Cloneable {

            int i服务号_i;
            java.util.Random d未确定数 = null;
            java.sql.Connection d连接 = null;
            java.sql.PreparedStatement d操作 = null;
            java.sql.ResultSet d视图 = null;
            java.util.Enumeration<java.net.NetworkInterface> mEnumeration = null;
            String i内网IP_s = null;

            自用 d副本() throws java.sql.SQLException, CloneNotSupportedException {
                自用 jd = (自用) super.clone();
                jd.d连接 = java.sql.DriverManager.getConnection(i.i连接文本_s, i.i用户_s, i.i口令_s);
                return jd;
            }

            void close() {
                d未确定数 = null;
                if (d操作 != null) {
                    try {
                        d操作.close();
                    } catch (java.sql.SQLException ex) {
                    }
                    d操作 = null;
                }
                if (d连接 != null) {
                    try {
                        d连接.close();
                    } catch (java.sql.SQLException ex) {
                    }
                    d连接 = null;
                }
            }
        }
        自用 ji自用 = null;
        try {
            ji自用 = (自用) org.hzs.常用.d对象池.get(自用.class.getName());
            if (ji自用 == null) {
                ji自用 = new 自用();
                org.hzs.常用.d对象池.put(自用.class.getName(), ji自用);
            }
            ji自用 = ji自用.d副本();
            //
            ji自用.mEnumeration = java.net.NetworkInterface.getNetworkInterfaces();
            while (ji自用.mEnumeration.hasMoreElements()) {
                java.net.NetworkInterface intf = ji自用.mEnumeration.nextElement();
                for (java.util.Enumeration<java.net.InetAddress> enumIPAddr = intf.getInetAddresses(); enumIPAddr.hasMoreElements();) {
                    java.net.InetAddress inetAddress = enumIPAddr.nextElement();
                    // 如果不是回环地址  
                    if (!inetAddress.isLoopbackAddress()) {
                        // 直接返回本地IP地址  
                        ji自用.i内网IP_s = inetAddress.getHostAddress();
                        break;
                    }
                }
            }
            if (ji自用.i内网IP_s == null) {
                ji自用.i内网IP_s = "127.0.0.1";
            }
            //
            ji自用.d操作 = ji自用.d连接.prepareStatement(""
                    + "SELECT"
                    + "   server "
                    + "FROM public._server "
                    + "WHERE"
                    + "   encrypt(?, ?, 'bf')=ip;", java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE, java.sql.ResultSet.CONCUR_READ_ONLY, java.sql.ResultSet.HOLD_CURSORS_OVER_COMMIT);
            ji自用.d操作.setBytes(1, ji自用.i内网IP_s.getBytes("UTF-8"));
            ji自用.d操作.setBytes(2, i.i密钥_byteArray);
            ji自用.d视图 = ji自用.d操作.executeQuery();
            if (ji自用.d视图.first()) {
                ji自用.i服务号_i = ji自用.d视图.getShort("server");
            } else {
                ji自用.d操作 = ji自用.d连接.prepareStatement("INSERT INTO public._server (server, ip) VALUES (?, encrypt(?, ?, 'bf'));", java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE, java.sql.ResultSet.CONCUR_READ_ONLY, java.sql.ResultSet.HOLD_CURSORS_OVER_COMMIT);
                do {
                    ji自用.d未确定数 = new java.util.Random();
                    ji自用.i服务号_i = i.i集群号_byte;
                    ji自用.i服务号_i = (ji自用.i服务号_i << 12) | ji自用.d未确定数.nextInt(0xFFF);
                    //
                    ji自用.d操作.setShort(1, (short) ji自用.i服务号_i);
                    ji自用.d操作.setBytes(2, ji自用.i内网IP_s.getBytes("UTF-8"));
                    ji自用.d操作.setBytes(3, i.i密钥_byteArray);
                    try {
                        ji自用.d操作.execute();
                        break;
                    } catch (java.sql.SQLException ex) {
                        continue;
                    }
                } while (true);
            }
            //清理服务器号对应的记录，将曾经新建的记录改为删除状态，以备再次使用
            ji自用.d操作 = ji自用.d连接.prepareStatement("UPDATE public._ SET xt.bj=1 WHERE (xt).bj=2 AND (id).server=?;", java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE, java.sql.ResultSet.CONCUR_READ_ONLY, java.sql.ResultSet.HOLD_CURSORS_OVER_COMMIT);
            ji自用.d操作.setShort(1, (short) ji自用.i服务号_i);
            ji自用.d操作.execute();
            //
            return (short) ji自用.i服务号_i;
        } finally {
            if (ji自用 != null) {
                ji自用.close();
                ji自用 = null;
            }
        }
    }

    public 连接 d连接() throws java.sql.SQLException, CloneNotSupportedException, java.net.SocketException, UnsupportedEncodingException {
        return jd连接(0, null);
    }

    public 视图 d视图(final String ciSQL_s, final org.hzs.logging.error ci_error) throws java.sql.SQLException, org.hzs.logging.error {
        return i.d保留连接.d操作(ciSQL_s, ci_error);
    }

    private synchronized 连接 jd连接(final int ci操作_i, final 连接池.连接class cd连接) throws java.sql.SQLException, CloneNotSupportedException, java.net.SocketException, UnsupportedEncodingException {
        switch (ci操作_i) {
            case 0://取连接池
                if (i.d连接池.size() == 0) {
                    if (i.i终端号_i == Integer.MAX_VALUE) {
                        return null;
                    }
                    i.i终端号_i++;
                    return new 连接池.连接class(i.i终端号_i);
                } else {
                    java.util.Map.Entry<Integer, 连接class> entry = i.d连接池.entrySet().iterator().next();
                    连接class jd连接 = i.d连接池.remove(entry.getKey());
                    try {
                        jd连接.d连接.prepareStatement("SELECT 1;", java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE, java.sql.ResultSet.CONCUR_READ_ONLY, java.sql.ResultSet.HOLD_CURSORS_OVER_COMMIT);
                        return entry.getValue();
                    } catch (java.sql.SQLException ex) {
                        i.d连接池.clear();
                        throw ex;
                    }
                }
            case 1://置入连接池
                try {
                    cd连接.d连接.prepareStatement("SELECT 1;", java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE, java.sql.ResultSet.CONCUR_READ_ONLY, java.sql.ResultSet.HOLD_CURSORS_OVER_COMMIT);
                    i.d连接池.put(cd连接.i终端_i, cd连接);
                } catch (java.sql.SQLException ex) {
                    i.d连接池.clear();
                } finally {
                    return null;
                }
            case 2://清理连接池
                boolean ji继续清理_b = true;
                while (ji继续清理_b) {
                    ji继续清理_b = false;
                    if (i.i终端号_i > Integer.MIN_VALUE && i.d连接池.get(i.i终端号_i) != null) {
                        i.d连接池.remove(i.i终端号_i).close();
                        i.i终端号_i--;
                        if (i.i终端号_i > Integer.MIN_VALUE) {
                            ji继续清理_b = true;
                        }
                    }
                }
            default:
                return null;
        }
    }

    /////////////////////////////////////////////////
    private final class 连接class implements 连接 {

        private final static String i类1_s = "连接class.";
        public 连接class d後筆 = null;
        protected int i终端_i;
        private java.sql.Connection d连接 = null;

        public 连接class(final int ci终端_i) throws java.sql.SQLException, CloneNotSupportedException, java.net.SocketException, UnsupportedEncodingException {
            if (i.d连接池.size() == 0) {//连接池内不存在连接时，说明未取得服务或服务出现问题而需重新获取
                i.i服务器号_i = i服务器号();
            }
            i终端_i = ci终端_i;
            d连接 = java.sql.DriverManager.getConnection(i.i连接文本_s, i.i用户_s, i.i口令_s);
            d连接.setAutoCommit(false);
        }

        @Override
        public org.hzs.lang.ID i_ID(final String ci子數據表_s, String ci母數據表_s) throws java.sql.SQLException, CloneNotSupportedException {
//            final String ji过程_s = "i_ID(";
            // <editor-fold defaultstate="collapsed" desc="自用">
            class 自用 implements Cloneable {

                java.sql.Connection d连接 = null;
                java.sql.PreparedStatement d读操作 = null, d写操作 = null;
                java.sql.ResultSet d视图 = null;
                StringBuilder i_S = null;

                public 自用 d副本() throws java.sql.SQLException, CloneNotSupportedException {
                    自用 jd = (自用) super.clone();
                    jd.d连接 = java.sql.DriverManager.getConnection(i.i连接文本_s, i.i用户_s, i.i口令_s);
                    jd.d连接.setAutoCommit(false);
                    return jd;
                }

                public void close() {
                    if (i_S != null) {
                        i_S.delete(0, i_S.length());
                        i_S = null;
                    }
                    if (d视图 != null) {
                        try {
                            d视图.close();
                        } catch (java.sql.SQLException ex) {
                        }
                        d视图 = null;
                    }
                    if (d读操作 != null) {
                        try {
                            d读操作.close();
                        } catch (java.sql.SQLException ex) {
                        }
                        d读操作 = null;
                    }
                    if (d写操作 != null) {
                        try {
                            d写操作.close();
                        } catch (java.sql.SQLException ex) {
                        }
                        d写操作 = null;
                    }
                    if (d连接 != null) {
                        try {
                            d连接.rollback();
                            d连接.close();
                        } catch (java.sql.SQLException ex) {
                        }
                        d连接 = null;
                    }
                }
            }// </editor-fold>
            自用 ji自用 = null;
            org.hzs.lang.ID ji_ID = null;
            try {
                ji自用 = (自用) org.hzs.常用.d对象池.get(自用.class.getName());
                if (ji自用 == null) {
                    ji自用 = new 自用();
                    org.hzs.常用.d对象池.put(自用.class.getName(), ji自用);
                }
                ji自用 = ji自用.d副本();
                //
                ji_ID = org.hzs.lang.ID.d副本();
                ji自用.i_S = new StringBuilder(256);
                if (ci母數據表_s == null) {
                    ci母數據表_s = ci子數據表_s;
                }
                ji_ID.server = i.i服务器号_i;
                ji_ID.vd = i终端_i;
                //寻找被删除的记录。被用次數为0的记录可被回收
                ji自用.i_S.delete(0, ji自用.i_S.length());
                ji自用.i_S.append("SELECT (id).nm As nm FROM ");
                ji自用.i_S.append(ci子數據表_s);
                ji自用.i_S.append(" WHERE (xt).bj = 1 And (xt).byci <= 0 And (id).server = ? And (id).vd = ?;");
                ji自用.d读操作 = ji自用.d连接.prepareStatement(ji自用.i_S.toString(), java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE, java.sql.ResultSet.CONCUR_READ_ONLY, java.sql.ResultSet.HOLD_CURSORS_OVER_COMMIT);
                ji自用.d读操作.setShort(1, i.i服务器号_i);
                ji自用.d读操作.setInt(2, i终端_i);
                ji自用.d视图 = ji自用.d读操作.executeQuery();
                if (ji自用.d视图.first()) {//如果找到可回收记录，则清空该记录，并返回id
                    ji_ID.nm = ji自用.d视图.getLong("nm");
                    ji自用.i_S.delete(0, ji自用.i_S.length());
                    ji自用.i_S.append("UPDATE ");
                    ji自用.i_S.append(ci子數據表_s);
                    ji自用.i_S.append(" SET xt.bj=2 WHERE (id).server = ? And (id).vd = ? And (id).nm = ?;");
                    ji自用.d写操作 = ji自用.d连接.prepareStatement(ji自用.i_S.toString(), java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE, java.sql.ResultSet.CONCUR_READ_ONLY, java.sql.ResultSet.HOLD_CURSORS_OVER_COMMIT);
                    ji自用.d写操作.setShort(1, ji_ID.server);
                    ji自用.d写操作.setInt(2, ji_ID.vd);
                    ji自用.d写操作.setLong(3, ji_ID.nm);
                    ji自用.d写操作.executeUpdate();
                    ji自用.d连接.commit();
                    return ji_ID;
                }
                //插入新记录
                ji自用.i_S.delete(0, ji自用.i_S.length());
                ji自用.i_S.append("SELECT Count(id) As gi FROM ");
                ji自用.i_S.append(ci母數據表_s);
                ji自用.i_S.append(" WHERE (id).server = ? And (id).vd = ?");
                ji自用.d读操作 = ji自用.d连接.prepareStatement(ji自用.i_S.toString(), java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE, java.sql.ResultSet.CONCUR_READ_ONLY, java.sql.ResultSet.HOLD_CURSORS_OVER_COMMIT);
                ji自用.d读操作.setShort(1, i.i服务器号_i);
                ji自用.d读操作.setInt(2, i终端_i);
                ji自用.d视图 = ji自用.d读操作.executeQuery();
                ji自用.d视图.first();
                if (ji自用.d视图.getInt("gi") > 0) {
                    ji自用.i_S.delete(0, ji自用.i_S.length());
                    ji自用.i_S.append("SELECT Max((id).nm) As nm FROM ");
                    ji自用.i_S.append(ci母數據表_s);
                    ji自用.i_S.append(" WHERE (id).server = ? And (id).vd = ?");
                    ji自用.d读操作 = ji自用.d连接.prepareStatement(ji自用.i_S.toString(), java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE, java.sql.ResultSet.CONCUR_READ_ONLY, java.sql.ResultSet.HOLD_CURSORS_OVER_COMMIT);
                    ji自用.d读操作.setShort(1, i.i服务器号_i);
                    ji自用.d读操作.setInt(2, i终端_i);
                    ji自用.d视图 = ji自用.d读操作.executeQuery();
                    ji自用.d视图.first();
                    ji_ID.nm = ji自用.d视图.getLong("nm") + 1;
                } else {
                    ji_ID.nm = Long.MIN_VALUE;
                }
                ji自用.i_S.delete(0, ji自用.i_S.length());
                ji自用.i_S.append("INSERT INTO ");
                ji自用.i_S.append(ci子數據表_s);
                ji自用.i_S.append(" (xt.ujij, xt.byci, xt.bj, id.server, id.vd, id.nm) VALUES (current_timestamp, 0, 2, ?, ?, ?)");
                ji自用.d写操作 = ji自用.d连接.prepareStatement(ji自用.i_S.toString(), java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE, java.sql.ResultSet.CONCUR_READ_ONLY, java.sql.ResultSet.HOLD_CURSORS_OVER_COMMIT);
                ji自用.d写操作.setInt(1, ji_ID.server);
                ji自用.d写操作.setInt(2, ji_ID.vd);
                ji自用.d写操作.setLong(3, ji_ID.nm);
                ji自用.d写操作.executeUpdate();
                ji自用.d连接.commit();
                return ji_ID;
            } finally {
                if (ji自用 != null) {
                    ji自用.close();
                    ji自用 = null;
                }
            }
        }

        private void jg废弃新记录() throws java.sql.SQLException {
            java.sql.PreparedStatement jd写操作 = null;
            try {
                //删除新增记录
                jd写操作 = d连接.prepareStatement("UPDATE public._ SET xt.bj = 1 WHERE (xt).bj = 2 And (id).server = ? And (id).vd = ?", java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE, java.sql.ResultSet.CONCUR_READ_ONLY, java.sql.ResultSet.HOLD_CURSORS_OVER_COMMIT);
                jd写操作.setShort(1, i.i服务器号_i);
                jd写操作.setInt(2, i终端_i);
                jd写操作.executeUpdate();
            } catch (java.sql.SQLException ex) {
            } finally {
                if (jd写操作 != null) {
                    try {
                        jd写操作.close();
                    } catch (java.sql.SQLException ex) {
                    }
                    jd写操作 = null;
                }
            }
        }

        @Override
        public void g关闭() throws java.sql.SQLException, CloneNotSupportedException, java.net.SocketException, UnsupportedEncodingException {
            this.jg废弃新记录();
            this.jg回滚事务();
            jd连接(1, this);
        }

        @Override
        public void g递交事务() throws java.sql.SQLException {
            d连接.commit();
        }

        private void jg回滚事务() throws java.sql.SQLException {
            d连接.rollback();
        }

        @Override
        public 连接池.连接class.操作class d操作(final String ciSQL语句_s, final org.hzs.logging.error ci_error) throws java.sql.SQLException, org.hzs.logging.error {
            连接池.连接class.操作class jd操作 = null;
            String jiSQL语句_s = null;
            try {
                jiSQL语句_s = ciSQL语句_s.toUpperCase();
                if (!jiSQL语句_s.startsWith("SELECT ") && !jiSQL语句_s.startsWith("UPDATE ") && !jiSQL语句_s.startsWith("INSERT INTO ") && !jiSQL语句_s.startsWith("DELETE FROM ")) {
                    // <editor-fold defaultstate="collapsed" desc="抛出错误">
                    ci_error.g添加错误信息("{类:\"org.sql.连接池(内部类：连接class)\","
                            + "过程:\"d操作(final String ciSQL语句_s, org.hzs.logging.error ci_error)\","
                            + "时间:" + (new java.util.Date()).getTime() + ","
                            + "位置:\"\","
                            + "错误类型:\"生成\","
                            + "错误:\"用了非许可操作\"}");
                    throw ci_error;
                    // </editor-fold>
                }
                jd操作 = new 连接池.连接class.操作class();
                jd操作.d操作 = d连接.prepareStatement(ciSQL语句_s, java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE, java.sql.ResultSet.CONCUR_READ_ONLY, java.sql.ResultSet.HOLD_CURSORS_OVER_COMMIT);
                jd操作.i参數个數_i = jd操作.d操作.getParameterMetaData().getParameterCount() + 1;
                return jd操作;
            } finally {
                jd操作 = null;
                jiSQL语句_s = null;
            }
        }

        @Override
        public int i终端_i() {
            return i终端_i;
        }

        protected void close() throws java.sql.SQLException {
            d连接.rollback();
            d连接.close();
            d连接 = null;
        }

        private final class 操作class implements 操作 {

            private java.sql.PreparedStatement d操作 = null;
            private int i参數个數_i = 0;

            /**
             * ************************************************************************************************************************
             * 参數 ***********************************************************************************************************************
             */
            @Override
            public void g置密钥_byteArray(final int... ci索引位置_iArray) throws java.sql.SQLException {
                jg关闭视图();
                for (int ji索引位置_i : ci索引位置_iArray) {
                    if (ji索引位置_i < 0) {
                        d操作.setBytes(i参數个數_i + ji索引位置_i, i.i密钥_byteArray);
                    } else {
                        d操作.setBytes(ji索引位置_i, i.i密钥_byteArray);
                    }
                }
            }

            @Override
            public void g置参數_ID(final int ci索引位置_i, final org.hzs.lang.ID ci参數_ID) throws java.sql.SQLException {
                jg关闭视图();
                int ji索引位置_i;
                org.postgresql.util.PGobject jd = null;
                if (ci索引位置_i < 0) {
                    ji索引位置_i = i参數个數_i + ci索引位置_i;
                } else {
                    ji索引位置_i = ci索引位置_i;
                }
                d操作.setObject(ji索引位置_i, org.hzs.lang.转换.ID_2_PGobject(ci参數_ID));
            }

            @Override
            public void g置参數(final int ci索引位置_i, final Object ci参數_o) throws java.sql.SQLException {
                jg关闭视图();
                if (ci索引位置_i < 0) {
                    if (ci参數_o == null) {
                        d操作.setNull(i参數个數_i + ci索引位置_i, java.sql.Types.OTHER);
                    } else {
                        d操作.setObject(i参數个數_i + ci索引位置_i, ci参數_o);
                    }
                } else {
                    if (ci参數_o == null) {
                        d操作.setNull(ci索引位置_i, java.sql.Types.OTHER);
                    } else {
                        d操作.setObject(ci索引位置_i, ci参數_o);
                    }
                }
            }

            @Override
            public void g置参數(final Object[] ci参數_oArray, final org.hzs.logging.error ci_error) throws java.sql.SQLException, org.hzs.logging.error {
                jg关闭视图();
                if (ci参數_oArray == null) {
                    ci_error.g添加错误信息("{类:\"org.sql.连接池(内部类：连接class、操作class)\","
                            + "过程:\"g置参數(final Object[] ci参數_oArray, org.hzs.logging.error ci_error)\","
                            + "时间:" + (new java.util.Date()).getTime() + ","
                            + "错误类型:\"生成\","
                            + "错误:\"索引位置为空or索引位置为0\"}");
                    throw ci_error;
                }
                int ji_i = 0;
                for (ji_i = 0; ji_i < ci参數_oArray.length; ji_i++) {
                    if (ci参數_oArray[ji_i] == null) {
                        d操作.setNull(ji_i + 1, java.sql.Types.OTHER);
                    } else {
                        d操作.setObject(ji_i + 1, ci参數_oArray[ji_i]);
                    }
                }
            }

            @Override
            public void g置参數(final int ci索引位置_i, final Object ci参數_o, final Integer ci类型_i) throws java.sql.SQLException {
                jg关闭视图();
                if (ci索引位置_i < 0) {
                    if (ci参數_o == null) {
                        if (ci类型_i != null) {
                            d操作.setNull(i参數个數_i + ci索引位置_i, ci类型_i);
                        } else {
                            d操作.setNull(i参數个數_i + ci索引位置_i, java.sql.Types.OTHER);
                        }
                    } else {
                        if (ci类型_i == null) {
                            d操作.setObject(i参數个數_i + ci索引位置_i, ci参數_o);
                        } else {
                            d操作.setObject(i参數个數_i + ci索引位置_i, ci参數_o, ci类型_i);
                        }
                    }
                } else {
                    if (ci参數_o == null) {
                        if (ci类型_i != null) {
                            d操作.setNull(ci索引位置_i, ci类型_i);
                        } else {
                            d操作.setNull(ci索引位置_i, java.sql.Types.OTHER);
                        }
                    } else {
                        if (ci类型_i == null) {
                            d操作.setObject(ci索引位置_i, ci参數_o);
                        } else {
                            d操作.setObject(ci索引位置_i, ci参數_o, ci类型_i);
                        }
                    }
                }
            }

            @Override
            public void g置参數_byteArray(final int ci索引位置_i, final byte[] ci参數_byteArray) throws java.sql.SQLException {
                jg关闭视图();
                if (ci索引位置_i < 0) {
                    if (ci参數_byteArray == null) {
                        d操作.setNull(i参數个數_i + ci索引位置_i, java.sql.Types.OTHER);
                    } else {
                        d操作.setBytes(i参數个數_i + ci索引位置_i, ci参數_byteArray);
                    }
                } else {
                    if (ci参數_byteArray == null) {
                        d操作.setNull(ci索引位置_i, java.sql.Types.OTHER);
                    } else {
                        d操作.setBytes(ci索引位置_i, ci参數_byteArray);
                    }
                }
            }

            @Override
            public void g置参數_s(final int ci索引位置_i, final String ci参數_s) throws java.sql.SQLException {
                jg关闭视图();
                if (ci索引位置_i < 0) {
                    if (ci参數_s == null) {
                        d操作.setNull(i参數个數_i + ci索引位置_i, java.sql.Types.LONGVARCHAR);
                    } else {
                        d操作.setString(i参數个數_i + ci索引位置_i, ci参數_s);
                    }
                } else {
                    if (ci参數_s == null) {
                        d操作.setNull(ci索引位置_i, java.sql.Types.LONGVARCHAR);
                    } else {
                        d操作.setString(ci索引位置_i, ci参數_s);
                    }
                }
            }

            @Override
            public void g置参數_L(final int ci索引位置_i, final Long ci参數_l) throws java.sql.SQLException {
                jg关闭视图();
                if (ci索引位置_i < 0) {
                    if (ci参數_l == null) {
                        d操作.setNull(i参數个數_i + ci索引位置_i, java.sql.Types.BIGINT);
                    } else {
                        d操作.setLong(i参數个數_i + ci索引位置_i, ci参數_l);
                    }
                } else {
                    if (ci参數_l == null) {
                        d操作.setNull(ci索引位置_i, java.sql.Types.BIGINT);
                    } else {
                        d操作.setLong(ci索引位置_i, ci参數_l);
                    }
                }
            }

            @Override
            public void g置参數_I(final int ci索引位置_i, final Integer ci参數_I) throws java.sql.SQLException {
                jg关闭视图();
                if (ci索引位置_i < 0) {
                    if (ci参數_I == null) {
                        d操作.setNull(i参數个數_i + ci索引位置_i, java.sql.Types.INTEGER);
                    } else {
                        d操作.setInt(i参數个數_i + ci索引位置_i, ci参數_I);
                    }
                } else {
                    if (ci参數_I == null) {
                        d操作.setNull(ci索引位置_i, java.sql.Types.INTEGER);
                    } else {
                        d操作.setInt(ci索引位置_i, ci参數_I);
                    }
                }
            }

            @Override
            public void g置参數_BD(final int ci索引位置_i, final java.math.BigDecimal ci参數_BD) throws java.sql.SQLException {
                jg关闭视图();
                if (ci索引位置_i < 0) {
                    if (ci参數_BD == null) {
                        d操作.setNull(i参數个數_i + ci索引位置_i, java.sql.Types.OTHER);
                    } else {
                        d操作.setBigDecimal(i参數个數_i + ci索引位置_i, ci参數_BD);
                    }
                } else {
                    if (ci参數_BD == null) {
                        d操作.setNull(ci索引位置_i, java.sql.Types.OTHER);
                    } else {
                        d操作.setBigDecimal(ci索引位置_i, ci参數_BD);
                    }
                }
            }

            @Override
            public void g置参數_B(final int ci索引位置_i, final Boolean ci参數_B) throws java.sql.SQLException {
                jg关闭视图();
                if (ci索引位置_i < 0) {
                    if (ci参數_B == null) {
                        d操作.setNull(i参數个數_i + ci索引位置_i, java.sql.Types.BOOLEAN);
                    } else {
                        d操作.setBoolean(i参數个數_i + ci索引位置_i, ci参數_B);
                    }
                } else {
                    if (ci参數_B == null) {
                        d操作.setNull(ci索引位置_i, java.sql.Types.BOOLEAN);
                    } else {
                        d操作.setBoolean(ci索引位置_i, ci参數_B);
                    }
                }
            }

            @Override
            public void g置空参數(final int ci索引位置_i, final int ci类型_i) throws java.sql.SQLException {
                jg关闭视图();
                if (ci索引位置_i < 0) {
                    d操作.setNull(i参數个數_i + ci索引位置_i, ci类型_i);
                } else {
                    d操作.setNull(ci索引位置_i, ci类型_i);
                }
            }

            @Override
            public void g置空参數(final int ci索引位置_i, final int ci类型_i, final String ci字段_s) throws java.sql.SQLException {
                jg关闭视图();
                if (ci索引位置_i < 0) {
                    if (ci字段_s == null) {
                        d操作.setNull(i参數个數_i + ci索引位置_i, ci类型_i);
                    } else {
                        d操作.setNull(i参數个數_i + ci索引位置_i, ci类型_i, ci字段_s);
                    }
                } else {
                    if (ci字段_s == null) {
                        d操作.setNull(ci索引位置_i, ci类型_i);
                    } else {
                        d操作.setNull(ci索引位置_i, ci类型_i, ci字段_s);
                    }
                }
            }

            ///////////////////////////////
            @Override
            public void g立即执行() throws java.sql.SQLException {
                d操作.executeUpdate();
            }

            @Override
            public void g缓冲() throws java.sql.SQLException {
                d操作.addBatch();
            }

            @Override
            public void g清空缓冲() throws java.sql.SQLException {
                d操作.clearBatch();
            }

            @Override
            public int[] g执行缓冲() throws java.sql.SQLException {
                return d操作.executeBatch();
            }

            @Override
            public void g关闭() throws java.sql.SQLException {
                if (d视图 != null) {
                    d视图.close();
                    d视图 = null;
                }
                if (d操作 != null) {
                    d操作.close();
                    d操作 = null;
                }
            }

            @Override
            public boolean p关闭() throws java.sql.SQLException {
                if (d操作 != null) {
                    return d操作.isClosed();
                } else {
                    return true;
                }
            }
            private java.sql.ResultSet d视图 = null;

            private void jg关闭视图() {
                if (d视图 != null) {
                    try {
                        d视图.close();
                    } catch (java.sql.SQLException ex) {
                    }
                    d视图 = null;
                }
            }

            public void jg开启视图() throws java.sql.SQLException {
                if (d视图 == null) {
                    d视图 = d操作.executeQuery();
                }
            }

            @Override
            public org.hzs.lang.ID i值_ID(final String ci字段_s, final org.hzs.logging.error ci_error) throws java.sql.SQLException, org.hzs.logging.error {
                final String ji过程_s = "i值_ID(.";
                Object ji1 = null;
                org.postgresql.util.PGobject ji2 = null;
                try {
                    ji1 = d视图.getObject(ci字段_s);
                    if (ji1 == null) {
                        return null;
                    }
                    if (!(ji1 instanceof org.postgresql.util.PGobject)) {
                        ci_error.g添加错误信息("{类:'" + i类_s + i类1_s + "',"
                                + "过程:'" + ji过程_s + "',"
                                + "序号:'1',"
                                + "错误类型:'',"
                                + "错误:'类型不符！'}");
                        throw ci_error;
                    }
                    ji2 = (org.postgresql.util.PGobject) ji1;
                    if (!"id".equals(ji2.getType())) {
                        ci_error.g添加错误信息("{类:'" + i类_s + i类1_s + "',"
                                + "过程:'" + ji过程_s + "',"
                                + "序号:'2',"
                                + "错误类型:'',"
                                + "错误:'类型不符！'}");
                        throw ci_error;
                    }
                    return org.hzs.lang.转换.PGobject_2_ID(ji2);
                } finally {
                    ji1 = null;
                    ji2 = null;
                }
            }

            @Override
            public Object i值(final String ci字段_s) throws java.sql.SQLException {
                return d视图.getObject(ci字段_s);
            }

            @Override
            public String i值_s(final String ci字段_s) throws java.sql.SQLException {
                return d视图.getString(ci字段_s);
            }

            @Override
            public byte[] i值_byteArray(final String ci字段_s) throws java.sql.SQLException {
                return d视图.getBytes(ci字段_s);
            }

            @Override
            public Integer i值_I(final String ci字段_s) throws java.sql.SQLException {
                Object ji_o = null;
                try {
                    ji_o = d视图.getObject(ci字段_s);
                    if (ji_o == null) {
                        return null;
                    }
                    if (ji_o instanceof Integer) {
                        return (Integer) ji_o;
                    }
                    return d视图.getInt(ci字段_s);
                } finally {
                    ji_o = null;
                }
            }

            @Override
            public java.math.BigDecimal i值_BD(final String ci字段_s) throws java.sql.SQLException {
                return d视图.getBigDecimal(ci字段_s);
            }

            @Override
            public Boolean i值_B(final String ci字段_s) throws java.sql.SQLException {
                Object ji_o = null;
                try {
                    ji_o = d视图.getObject(ci字段_s);
                    if (ji_o == null) {
                        return null;
                    }
                    if (ji_o instanceof Boolean) {
                        return (Boolean) ji_o;
                    }
                    return d视图.getBoolean(ci字段_s);
                } finally {
                    ji_o = null;
                }
            }

            @Override
            public Long i值_L(final String ci字段_s) throws java.sql.SQLException {
                Object ji_o = null;
                try {
                    ji_o = d视图.getObject(ci字段_s);
                    if (ji_o == null) {
                        return null;
                    }
                    if (ji_o instanceof Long) {
                        return (Long) ji_o;
                    }
                    return d视图.getLong(ci字段_s);
                } finally {
                    ji_o = null;
                }
            }

            @Override
            public void g首部() throws java.sql.SQLException {
                jg开启视图();
                d视图.beforeFirst();
            }

            @Override
            public boolean g首筆() throws java.sql.SQLException {
                jg开启视图();
                return d视图.first();
            }

            @Override
            public boolean g前筆() throws java.sql.SQLException {
                jg开启视图();
                return d视图.previous();
            }

            @Override
            public boolean g後筆() throws java.sql.SQLException {
                jg开启视图();
                return d视图.next();
            }

            @Override
            public boolean g末筆() throws java.sql.SQLException {
                jg开启视图();
                return d视图.last();
            }

            @Override
            public void g尾部() throws java.sql.SQLException {
                jg开启视图();
                d视图.afterLast();
            }
        }
    }
}
