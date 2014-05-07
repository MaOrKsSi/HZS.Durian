package org.hzs.sql;

import java.sql.SQLException;

public interface 写操作 extends 操作_ {

    public void g立即执行() throws java.sql.SQLException;

    public void g缓冲() throws SQLException;

    public void g清空缓冲() throws SQLException;

    public int[] g执行缓冲() throws java.sql.SQLException;
}
