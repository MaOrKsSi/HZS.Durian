package com.hzs;

import java.util.logging.Level;
import java.util.logging.Logger;

public class 医学_Session extends com.hzs.Session implements Cloneable, org.hzs.Close {

    private static 医学_Session session = null;

    public static 医学_Session d副本() {
        if (session == null) {
            session = new 医学_Session();
        }
        return (医学_Session) session.clone();
    }

    public org.hzs.json.JSONObject toJSON(final org.hzs.logging.error ci_error) throws org.hzs.logging.error {
        if (i操作员_ID != null) {
            this.put("操作员_ID", i操作员_ID.toString(), ci_error);
        }
        this.put("账套模_s", i账套模_s, ci_error);
        this.put("手机号_s", i手机号_s, ci_error);
        this.put("指令团_ArrayJSON", i指令团_ArrayJSON, ci_error);
        this.put("指令_JSON", i指令_JSON, ci_error);
        this.put("登入进度_i", i登入进度_i, ci_error);
        return this;
    }

    @Override
    public 医学_Session set(final String ci_s, final org.hzs.logging.error ci_error) throws org.hzs.logging.error {
        try {
            if (ci_s == null) {
                return null;
            }
            super.set(ci_s, ci_error);
            this.i操作员_ID = org.hzs.lang.ID.d副本().set(this.getString("操作员_ID", ci_error), ci_error);
            this.i账套模_s = this.getString("账套模_s", ci_error);
            this.i手机号_s = this.getString("手机号_s", ci_error);
            this.i指令团_ArrayJSON = this.getJSONArray("指令团_ArrayJSON", ci_error);
            this.i指令_JSON = this.getJSONObject("指令_JSON", ci_error);
            this.i登入进度_i = this.getInt("登入进度_i", ci_error);
            return this;
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(医学_Session.class.getName()).log(Level.SEVERE, null, ex);
            throw ci_error;
        } finally {
        }
    }
}
