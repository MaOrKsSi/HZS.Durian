package com.hzs;

public abstract class Session extends org.hzs.json.JSONObject {

    public org.hzs.json.JSONArray i指令团_ArrayJSON = org.hzs.json.JSONArray.d副本();
    public org.hzs.json.JSONObject i指令_JSON = null;//初始化时为登入前指令集合、登入後改成登入後指令集合
    public org.hzs.lang.ID i操作员_ID = null;
    public String i账套模_s = null;
    public String i手机号_s = null;
    public int i登入进度_i = 0;//0等待生成口令  1等待校验口令  2等待登入  3已经登入
}
