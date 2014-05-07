package org.hzs.lang;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class 转换 {

    public static byte[] s_2_byteArray(final String ci_s) throws UnsupportedEncodingException {
        if (ci_s == null || "".equals(ci_s)) {
            return null;
        }
        return ci_s.getBytes("UTF-8");
    }

    public static String byteArray_2_s(final byte[] ci_byteArray) throws UnsupportedEncodingException {
        if (ci_byteArray == null || ci_byteArray.length == 0) {
            return null;
        }
        return new String(ci_byteArray, "UTF-8");
    }

    public static java.math.BigDecimal byteArray_2_BD(final byte[] ci_byteArray) throws UnsupportedEncodingException {
        if (ci_byteArray == null || ci_byteArray.length == 0) {
            return null;
        }
        String ji_s = new String(ci_byteArray, "UTF-8");
        return new java.math.BigDecimal(ji_s);
    }

    public static byte[] DB_2_byteArray(final java.math.BigDecimal ci_BD) throws UnsupportedEncodingException {
        if (ci_BD == null) {
            return null;
        }
        return ci_BD.toPlainString().getBytes("UTF-8");
    }

    public static byte[] short_2_byteArray(Short ci_I) {
        if (ci_I == null) {
            return null;
        }
        int ji_i = ci_I;
        byte[] targets = new byte[2];

        targets[0] = (byte) (ji_i & 0xff);// 最低位 
        targets[1] = (byte) (ji_i >>> 8);// 最高位,无符号右移。 

        return targets;
    }

    public static Short byteArray_2_short(byte[] ci_byteArray) {
        if (ci_byteArray == null) {
            return null;
        }
// 一个byte数据左移24位变成0x??000000，再右移8位变成0x00??0000 
        short ji_i = 0;
        switch (ci_byteArray.length) {
            case 2:
                ji_i |= (ci_byteArray[1] << 8);// | 表示安位或 
            case 1:
                ji_i |= ci_byteArray[0] & 0xff;// | 表示安位或 
        }
        return ji_i;
    }

    public static byte[] int_2_byteArray(Integer ci_I) {
        if (ci_I == null) {
            return null;
        }
        int ji_i = ci_I;
        byte[] targets = new byte[4];

        targets[0] = (byte) (ji_i & 0xff);// 最低位 
        targets[1] = (byte) ((ji_i >> 8) & 0xff);// 次低位 
        targets[2] = (byte) ((ji_i >> 16) & 0xff);// 次高位 
        targets[3] = (byte) (ji_i >>> 24);// 最高位,无符号右移。 

        return targets;
    }

    public static Integer byteArray_2_int(byte[] ci_byteArray) {
        if (ci_byteArray == null) {
            return null;
        }
// 一个byte数据左移24位变成0x??000000，再右移8位变成0x00??0000 
        int ji_i = 0;
        switch (ci_byteArray.length) {
            case 4:
                ji_i |= ci_byteArray[3] << 24;// | 表示安位或 
            case 3:
                ji_i |= (ci_byteArray[2] << 24) >>> 8;// | 表示安位或 
            case 2:
                ji_i |= (ci_byteArray[1] << 8) & 0xff00;// | 表示安位或 
            case 1:
                ji_i |= ci_byteArray[0] & 0xff;// | 表示安位或 
        }
        return ji_i;
    }

    public static byte[] long_2_byteArray(Long ci_L) {
        if (ci_L == null) {
            return null;
        }
        long ji_l = ci_L;
        byte[] targets = new byte[8];

        targets[0] = (byte) (ji_l & 0xff);// 最低位 
        targets[1] = (byte) ((ji_l >> 8) & 0xff);// 次低位 
        targets[2] = (byte) ((ji_l >> 16) & 0xff);// 次高位 
        targets[3] = (byte) ((ji_l >> 24) & 0xff);
        targets[4] = (byte) ((ji_l >> 32) & 0xff);
        targets[5] = (byte) ((ji_l >> 40) & 0xff);
        targets[6] = (byte) ((ji_l >> 48) & 0xff);
        targets[7] = (byte) (ji_l >>> 56);// 最高位,无符号右移。 

        return targets;
    }

    public static Long byteArray_2_long(byte[] ci_byteArray) {
        if (ci_byteArray == null) {
            return null;
        }
// 一个byte数据左移24位变成0x??000000，再右移8位变成0x00??0000 
        long ji_i = 0;
        switch (ci_byteArray.length) {
            case 8:
                ji_i |= ci_byteArray[3] << 56;// | 表示安位或 
            case 7:
                ji_i |= (ci_byteArray[2] << 56) >>> 8;// | 表示安位或 
            case 6:
                ji_i |= (ci_byteArray[1] << 40) & 0xff00;// | 表示安位或 
            case 5:
                ji_i |= (ci_byteArray[1] << 32) & 0xff00;// | 表示安位或 
            case 4:
                ji_i |= (ci_byteArray[1] << 24) & 0xff00;// | 表示安位或 
            case 3:
                ji_i |= (ci_byteArray[1] << 16) & 0xff00;// | 表示安位或 
            case 2:
                ji_i |= (ci_byteArray[1] << 8) & 0xff00;// | 表示安位或 
            case 1:
                ji_i |= ci_byteArray[0] & 0xff;// | 表示安位或 
        }
        return ji_i;
    }

    public static org.postgresql.util.PGobject ID_2_PGobject(final org.hzs.lang.ID ci_ID) {
        org.postgresql.util.PGobject ji_PGobject = null;
        try {
            if (ci_ID == null || ci_ID.server == null) {
                return null;
            }
            ji_PGobject = new org.postgresql.util.PGobject();
            ji_PGobject.setType("id");
            ji_PGobject.setValue("(" + ci_ID.server.toString() + "," + ci_ID.vd.toString() + "," + ci_ID.nm.toString() + ")");
            return ji_PGobject;
        } catch (SQLException ex) {
            Logger.getLogger(转换.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            ji_PGobject = null;
        }
    }

    public static org.hzs.lang.ID PGobject_2_ID(final org.postgresql.util.PGobject ci_PGobject) throws SQLException {
        String ji_s = null;
        String[] ji_sArray = null;
        org.hzs.lang.ID ji_ID = null;
        try {
            if (!"id".equals(ci_PGobject.getType()) || null == ci_PGobject) {
                return null;
            }
            ji_s = ci_PGobject.getValue();
            ji_s = ji_s.substring(1, ji_s.length() - 1);
            ji_sArray = ji_s.split(",");
            ji_ID = org.hzs.lang.ID.d副本();
            ji_ID.server = Short.parseShort(ji_sArray[0]);
            ji_ID.vd = Integer.parseInt(ji_sArray[1]);
            ji_ID.nm = Long.parseLong(ji_sArray[2]);
            return ji_ID;
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(转换.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            ji_s = null;
            ji_sArray = null;
            ji_ID = null;
        }
    }
}
