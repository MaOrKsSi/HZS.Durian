package org.hzs.extjs;

public class 树节点 {

    public org.hzs.lang.ID i_ID = null;
    public Integer i多选_I = null;
    public boolean i展开_b = false;
    public String[] i键_s = null;
    public Object[] i值_o = null;
    public 树节点[] d下级 = null;
    public 树节点 d上级 = null;
    public int i下级个數_i = 0;//可以不读入下级节点而设置下级节点个数，为动态载入做准备

    public org.hzs.json.JSONObject toJSON(final org.hzs.logging.error ci_error) throws org.hzs.logging.error {
        org.hzs.json.JSONObject ji_JSON = null;
        org.hzs.json.JSONArray ji_ArrayJSON = null;
        int ji_i;
        try {
            ji_JSON = org.hzs.json.JSONObject.d副本();
            if (i下级个數_i < d下级.length) {
                i下级个數_i = d下级.length;
            }
            //id
            if (i_ID != null) {
                ji_JSON.put("id", i_ID.toString());
            }
            //值
            for (ji_i = 0; ji_i < i键_s.length; ji_i++) {
                ji_JSON.put(i键_s[ji_i], i值_o[ji_i], ci_error);
            }
            //是否多选
            if (i多选_I != null) {
                if (i多选_I.compareTo(0) <= 0) {
                    ji_JSON.put("checked", false);
                } else if (i多选_I.compareTo(i下级个數_i) >= 0) {
                    ji_JSON.put("checked", true);
                } else {//为半選做好准备，一旦extjs开始支持，可以很方便的增加半选功能
                    ji_JSON.put("checked", true);
                }
            }
            //子节点
            if (d下级 != null) {
                //是否展开
                if (i展开_b) {
                    ji_JSON.put("expanded", true);
                }
                ji_JSON.put("leaf", false);
                ji_ArrayJSON = org.hzs.json.JSONArray.d副本();
                ji_i = 0;
                for (树节点 jd下级 : d下级) {
                    if (jd下级 == null) {
                        continue;
                    }
                    ji_ArrayJSON.put(ji_i, jd下级.toJSON(ci_error), ci_error);
                    ji_i++;
                }
                ji_JSON.put("children", ji_ArrayJSON);
            } else {
                if (i下级个數_i > 0) {
                    ji_JSON.put("leaf", false);
                } else {
                    ji_JSON.put("leaf", true);
                }
            }
            return ji_JSON;
        } finally {
            ji_JSON = null;
            ji_ArrayJSON = null;
        }
    }

    public void close() {
        i_ID = null;
        i多选_I = null;
        if (i键_s != null) {
            for (int ji_i = 0; ji_i < i键_s.length; ji_i++) {
                i键_s[ji_i] = null;
            }
            i键_s = null;
        }
        if (i值_o != null) {
            for (int ji_i = 0; ji_i < i值_o.length; ji_i++) {
                i值_o[ji_i] = null;
            }
            i值_o = null;
        }
        if (d下级 != null) {
            for (int ji_i = 0; ji_i < d下级.length; ji_i++) {
                if (d下级[ji_i] != null) {
                    d下级[ji_i].close();
                    d下级[ji_i] = null;
                }
            }
            d下级 = null;
        }
        d上级 = null;
    }
}
