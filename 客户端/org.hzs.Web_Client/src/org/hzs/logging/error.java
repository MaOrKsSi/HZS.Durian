/*
 * 记录系统错误日记
 */
package org.hzs.logging;

/**
 *
 * @author 韩占山
 */
public final class error extends Exception implements Cloneable {

    private String i参數_s = "";
    public StringBuilder i错误_S = null;
    private StringBuilder i过程_S = null;
    private static final error i_error = new error();

    public static error d副本() throws CloneNotSupportedException {
        error jd = null;
        jd = (error) i_error.clone();
        jd.i错误_S = new StringBuilder();
        jd.i过程_S = new StringBuilder();
        return jd;
    }

    public void g置参數(final String ci参數_s) {
        i参數_s = ci参數_s;
    }

    public void g添加错误信息(final String ci错误_s) {
        i错误_S.append(",");
        i错误_S.append(ci错误_s);
    }

    public void g添加过程信息(final String ci过程_s) {
        i过程_S.append(",");
        i过程_S.append(ci过程_s);
    }

//    private void g清空() {
//        i错误_S.delete(0, i错误_S.length());
//        i过程_S.delete(0, i过程_S.length());
//    }
    //////////////////////////////////////////
//    private static String i日记路径_s = null;
    private static java.io.FileWriter d日记文件_FileWriter = null;
    private static String i日记文件_s = null;

    public void g记录() {
        g记录("{参數:\"" + i参數_s + "\",过程:[" + i过程_S.toString() + "],错误:[" + i错误_S.toString() + "]}");
    }

    private synchronized static void g记录(final String ci错误日记_s) {
        java.util.Calendar jd时间_Calendar = null;
        String ji日记文件_s = null, ji日记路径_s = null;
        try {
            jd时间_Calendar = java.util.Calendar.getInstance();
            jd时间_Calendar.setTimeZone(java.util.TimeZone.getTimeZone("GMT-8:00"));
            ji日记文件_s = Integer.toHexString(jd时间_Calendar.get(java.util.Calendar.YEAR)) + "_" + Integer.toHexString(jd时间_Calendar.get(java.util.Calendar.WEEK_OF_YEAR)) + ".log";
            if (i日记文件_s == null || !i日记文件_s.equals(ji日记文件_s)) {
                i日记文件_s = ji日记文件_s;
                if (d日记文件_FileWriter != null) {
                    d日记文件_FileWriter.close();
                }
                if (System.getProperty("os.name").startsWith("Windows")) {
                    ji日记路径_s = "C:/program files/韩占山/错误日记/";
                    (new java.io.File(ji日记路径_s)).mkdirs();
                } else {
                    ji日记路径_s = "/opt/韩占山/错误日记/";
                    (new java.io.File(ji日记路径_s)).mkdirs();
                }
                d日记文件_FileWriter = new java.io.FileWriter(new java.io.File(ji日记路径_s + ji日记文件_s));
            }
            d日记文件_FileWriter.write("[" + ci错误日记_s + "]");
            d日记文件_FileWriter.flush();
        } catch (java.io.IOException ex) {
        } finally {
            jd时间_Calendar = null;
            ji日记文件_s = null;
            ji日记路径_s = null;
        }
    }

    @Override
    public String getMessage() {
        if (i错误_S != null) {
            return "{参數:\"" + i参數_s + "\",过程:[" + i过程_S.toString() + "],错误:[" + i错误_S.toString() + "]}";
        } else {
            return "{参數:\"" + i参數_s + "\",过程:[],错误:[]}";
        }
    }
}
