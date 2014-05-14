package org.hzs.server.业务;

public abstract class __ {

    public static org.hzs.json.JSONObject i指令_JSON = null;

    public abstract byte[] g处理请求(final String session_s, final byte[] ci客户数据_byteArray);

    public abstract org.hzs.json.JSONObject getSession(final int sessionid_i);

    public abstract void removeSession(final int sessionid_i);
}
