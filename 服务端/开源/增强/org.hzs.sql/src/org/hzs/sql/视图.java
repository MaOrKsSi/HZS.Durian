package org.hzs.sql;

import java.sql.SQLException;

public interface 视图 extends 操作_ {

    public org.hzs.lang.ID i值_ID(final String ci字段_s, final org.hzs.logging.error ci_error) throws java.sql.SQLException, org.hzs.logging.error;

    public Object i值(final String ci字段_s) throws java.sql.SQLException;

    public String i值_s(final String ci字段_s) throws SQLException;

    public byte[] i值_byteArray(final String ci字段_s) throws java.sql.SQLException;

    public Integer i值_I(final String ci字段_s) throws java.sql.SQLException;

    public java.math.BigDecimal i值_BD(final String ci字段_s) throws java.sql.SQLException;

    public Boolean i值_B(final String ci字段_s) throws java.sql.SQLException;

    public Long i值_L(final String ci字段_s) throws java.sql.SQLException;

    public void g首部() throws java.sql.SQLException;

    public boolean g首筆() throws java.sql.SQLException;

    public boolean g前筆() throws java.sql.SQLException;

    public boolean g後筆() throws java.sql.SQLException;

    public boolean g末筆() throws java.sql.SQLException;

    public void g尾部() throws java.sql.SQLException;
}
