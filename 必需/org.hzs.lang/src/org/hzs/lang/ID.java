package org.hzs.lang;

/**
 *
 * @author 韩占山
 */
public final class ID implements Cloneable {

    private static final String i类_s = "org.hzs.lang.ID.";
    public Short server = null;
    public Integer vd = null;
    public Long nm = null;
    private static final ID i_ID = new ID();

    public ID set(final String ci_s, final org.hzs.logging.error ci_error) throws org.hzs.logging.error {
        final String ji过程_s = "set(final String ci_s.";
        if (ci_s == null) {
            return null;
        }
        String ji_s = null;
        String[] ji_sArray = null;
        short ji_server;
        int ji_vd;
        long ji_nm;
        boolean ji未捕获错误_b = true;
        try {
            if (ci_s == null) {
                ci_error.g添加错误信息("{类:'" + i类_s + "',"
                        + "过程:'" + ji过程_s + "',"
                        + "序号:'1',"
                        + "错误类型:'',"
                        + "错误:'待解析文本不可为空！'}");
                throw ci_error;
            }
            ji_s = ci_s.replaceAll("_", "-");
            ji_sArray = ji_s.split("H");
            if (ji_sArray.length < 4) {
                ci_error.g添加错误信息("{类:'" + i类_s + "',"
                        + "过程:'" + ji过程_s + "',"
                        + "序号:'2',"
                        + "错误类型:'',"
                        + "错误:'待解析文本格式不正确！'}");
                throw ci_error;
            }
            //不直接对属性赋值，是为了确保发生错误时，不影响原值
            ji_server = Short.parseShort(ji_sArray[1], 36);
            ji_vd = Integer.parseInt(ji_sArray[2], 36);
            ji_nm = Long.parseLong(ji_sArray[3], 36);
//            setValue("(" + Short.toString(ji_server) + "," + Integer.toString(ji_vd) + "," + Long.toString(ji_nm) + ")");
            //修改属性
            server = ji_server;
            vd = ji_vd;
            nm = ji_nm;
            ji未捕获错误_b = false;
            return this;
//        } catch (SQLException ex) {
//            Logger.getLogger(ID.class.getName()).log(Level.SEVERE, null, ex);
//            ji未捕获错误_b = false;
//            ci_error.g添加错误信息("{类:'" + i类_s + "',"
//                    + "过程:'" + ji过程_s + "',"
//                    + "序号:'',"
//                    + "错误类型:'捕获',"
//                    + "错误:'" + ex.getMessage() + "'}");
//            throw ci_error;
        } finally {
            ji_s = null;
            ji_sArray = null;
            if (ji未捕获错误_b) {
                ci_error.g添加错误信息("{类:'" + i类_s + "',"
                        + "过程:'" + ji过程_s + "',"
                        + "序号:'',"
                        + "错误类型:'未捕获',"
                        + "错误:''}");
                throw ci_error;
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder ji_S = null;
        try {
            if (server == null) {
                return null;
            }
            ji_S = new StringBuilder(64);
            ji_S.append("H");
            ji_S.append(Integer.toString(server, 36));
            ji_S.append("H");
            ji_S.append(Integer.toString(vd, 36));
            ji_S.append("H");
            ji_S.append(Long.toString(nm, 36));
            return ji_S.toString().replaceAll("-", "_");
        } finally {
            ji_S = null;
        }
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + java.util.Objects.hashCode(this.server);
        hash = 41 * hash + java.util.Objects.hashCode(this.vd);
        hash = 41 * hash + java.util.Objects.hashCode(this.nm);
        return hash;
    }

    @Override
    public boolean equals(final Object ci_) {
        if (server == null || ci_ == null || !(ci_ instanceof org.hzs.lang.ID)) {
            return false;
        }
        org.hzs.lang.ID ji_ID = (org.hzs.lang.ID) ci_;
        return server.equals(ji_ID.server) && vd.equals(ji_ID.vd) && nm.equals(ji_ID.nm);
    }

    public static ID d副本() throws CloneNotSupportedException {
        return (org.hzs.lang.ID) i_ID.clone();
    }
}
