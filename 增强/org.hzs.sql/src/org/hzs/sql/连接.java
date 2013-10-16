package org.hzs.sql;

import java.io.UnsupportedEncodingException;

public interface 连接 {

    public org.hzs.lang.ID i_ID(final String ci子數據表_s, String ci母數據表_s) throws java.sql.SQLException, CloneNotSupportedException;

    public void g关闭() throws java.sql.SQLException, CloneNotSupportedException, java.net.SocketException, UnsupportedEncodingException;

    public void g递交事务() throws java.sql.SQLException;

    public 操作 d操作(final String ciSQL语句_s, final org.hzs.logging.error ci_error) throws java.sql.SQLException, org.hzs.logging.error;

    public int i终端_i();
}
