package org.hzs.server.业务;

public abstract class Session extends org.hzs.json.JSONObject implements Cloneable {

    protected org.hzs.json.JSONArray i指令团_ArrayJSON = org.hzs.json.JSONArray.d副本();
    protected org.hzs.json.JSONArray i指令_ArrayJSON = null;//初始化时为登入前指令集合、登入後改成登入後指令集合
    protected static Session session = null;

    public static Session d副本() {
        return (Session) session.clone();
    }

    public abstract org.hzs.json.JSONObject toJSON(final org.hzs.logging.error ci_error) throws org.hzs.logging.error;
}
