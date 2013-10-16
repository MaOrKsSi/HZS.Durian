package org.hzs.sql;

public interface 操作_ {

    public void g关闭() throws java.sql.SQLException;

    public boolean p关闭() throws java.sql.SQLException;

    public void g置密钥_byteArray(final int... ci索引位置_iArray) throws java.sql.SQLException;

    public void g置参數_ID(final int ci索引位置_i, final org.hzs.lang.ID ci参數_ID) throws java.sql.SQLException;

    public void g置参數(final int ci索引位置_I, final Object ci参數_o) throws java.sql.SQLException;

    public void g置参數(final Object[] ci参數_o, org.hzs.logging.error ci_error) throws java.sql.SQLException, org.hzs.logging.error;

    public void g置参數(final int ci索引位置_I, final Object ci参數_o, final Integer ci类型_I) throws java.sql.SQLException;

    public void g置参數_byteArray(final int ci索引位置_I, final byte[] ci参數_byteArray) throws java.sql.SQLException;

    public void g置参數_s(final int ci索引位置_I, final String ci参數_s) throws java.sql.SQLException;

    public void g置参數_L(final int ci索引位置_I, final Long ci参數_L) throws java.sql.SQLException;

    public void g置参數_I(final int ci索引位置_I, final Integer ci参數_I) throws java.sql.SQLException;

    public void g置参數_BD(final int ci索引位置_I, final java.math.BigDecimal ci参數_BD) throws java.sql.SQLException;

    public void g置参數_B(final int ci索引位置_I, final Boolean ci参數_B) throws java.sql.SQLException;

    public void g置空参數(final int ci索引位置_I, final int ci类型_I) throws java.sql.SQLException;

    public void g置空参數(final int ci索引位置_I, final int ci类型_I, final String ci字段_s) throws java.sql.SQLException;
}
