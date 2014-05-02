package org.hzs.json;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class JSONArray extends java.util.ArrayList implements Cloneable, java.io.Serializable {

    private static final JSONArray i_ArrayJSON = new JSONArray();

    protected void set(final JSONTokener x, final org.hzs.logging.error ci_error) throws org.hzs.logging.error {
        this.clear();
        if (x.nextClean(ci_error) != '[') {
            ci_error.g添加错误信息("JSONArray text 必需以'['开始！");
            throw ci_error;
        }
        if (x.nextClean(ci_error) == ']') {
            return;
        }
        x.back();
        for (;;) {
            if (x.nextClean(ci_error) == ',') {
                x.back();
                super.add(null);
            } else {
                x.back();
                super.add(x.nextValue(null, ci_error));
            }
            switch (x.nextClean(ci_error)) {
                case ';':
                case ',':
                    if (x.nextClean(ci_error) == ']') {
                        return;
                    }
                    x.back();
                    break;
                case ']':
                    return;
                default:
                    ci_error.g添加错误信息("Expected a ',' or ']'");
                    throw ci_error;
            }
        }
    }

    public JSONArray set(final String ci_s, final org.hzs.logging.error ci_error) throws org.hzs.logging.error {
        if (ci_s == null) {
            return null;
        }
        set(new JSONTokener(ci_s, ci_error), ci_error);
        return this;
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public JSONArray put(final Object value) {
        if (value == null) {
            super.add(null);
        } else {
            if (value instanceof java.math.BigDecimal) {
                super.add(((java.math.BigDecimal) value).toPlainString());
            } else if (value instanceof java.math.BigInteger) {
                super.add(value.toString());
            } else {
                super.add(value);
            }
        }
        return this;
    }

    public JSONArray put(final boolean value) {
        put(value ? Boolean.TRUE : Boolean.FALSE);
        return this;
    }

    public JSONArray put(final double value, final org.hzs.logging.error ci_error) throws org.hzs.logging.error {
        JSONObject.testValidity(value, ci_error);
        put(value);
        return this;
    }

    public JSONArray put(final int value) {
        put(new Integer(value));
        return this;
    }

    public JSONArray put(final long value) {
        put(new Long(value));
        return this;
    }

//    public JSONArray put(final java.util.Date value) {
//        if (value != null) {
//            super.add(value.getTime());
//        } else {
//            super.add(null);
//        }
//        return this;
//    }
//    public JSONArray put(final java.math.BigDecimal value) {
//        if (value != null) {
//            super.add(value.toPlainString());
//        } else {
//            super.add(null);
//        }
//        return this;
//    }
//
//    public JSONArray put(final java.math.BigInteger value) {
//        if (value != null) {
//            super.add(value.toString());
//        } else {
//            super.add(null);
//        }
//        return this;
//    }
//
//    public JSONArray put(final org.hzs.json.JSONObject value) {
//        if (value != null) {
//            super.add(value);
//        } else {
//            super.add(null);
//        }
//        return this;
//    }
//
    public JSONArray put(final int index, final Object value, final org.hzs.logging.error ci_error) throws org.hzs.logging.error {
        JSONObject.testValidity(value, ci_error);
        if (index < 0) {
            ci_error.g添加错误信息("JSONArray[" + index + "] not found.");
            throw ci_error;
        }
        if (index < size()) {
            super.set(index, value);
        } else {
            while (index != size()) {
                super.add(null);
            }
            put(value);
        }
        return this;
    }
//
//    public JSONArray put(final int index, final java.util.Date value, final org.hzs.logging.error ci_error) throws org.hzs.logging.error {
//        if (index < 0) {
//            ci_error.g添加错误信息("JSONArray[" + index + "] not found.");
//            throw ci_error;
//        }
//        if (index < size()) {
//            if (value != null) {
//                super.set(index, value.getTime());
//            } else {
//                super.set(index, null);
//            }
//        } else {
//            while (index != size()) {
//                super.add(null);
//            }
//            put(value);
//        }
//        return this;
//    }

    public JSONArray put(final int index, final java.math.BigDecimal value, final org.hzs.logging.error ci_error) throws org.hzs.logging.error {
        if (index < 0) {
            ci_error.g添加错误信息("JSONArray[" + index + "] not found.");
            throw ci_error;
        }
        if (index < size()) {
            if (value != null) {
                super.set(index, value.toPlainString());
            } else {
                super.set(index, null);
            }
        } else {
            while (index != size()) {
                super.add(null);
            }
            put(value);
        }
        return this;
    }

    public JSONArray put(final int index, final java.math.BigInteger value, final org.hzs.logging.error ci_error) throws org.hzs.logging.error {
        if (index < 0) {
            ci_error.g添加错误信息("JSONArray[" + index + "] not found.");
            throw ci_error;
        }
        if (index < size()) {
            if (value != null) {
                super.set(index, value.toString());
            } else {
                super.set(index, null);
            }
        } else {
            while (index != size()) {
                super.add(null);
            }
            put(value);
        }
        return this;
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Object get(int index, final org.hzs.logging.error ci_error) throws org.hzs.logging.error {
        if (index < 0) {
            ci_error.g添加错误信息("JSONArray[" + index + "]下标只能大于等于0！");
            throw ci_error;
        } else if (index >= size()) {
            ci_error.g添加错误信息("JSONArray[" + index + "]下标只能小于" + size() + "！");
            throw ci_error;
        } else {
            return super.get(index);
        }
    }

    public Boolean getBoolean(int index, final org.hzs.logging.error ci_error) throws org.hzs.logging.error {
        Object ji_o = null;
        try {
            ji_o = get(index);
            if (ji_o == null) {
                return null;
            }
            if (ji_o.equals(Boolean.FALSE) || (ji_o instanceof String && ((String) ji_o).equalsIgnoreCase("false"))) {
                return false;
            } else if (ji_o.equals(Boolean.TRUE) || (ji_o instanceof String && ((String) ji_o).equalsIgnoreCase("true"))) {
                return true;
            }
            ci_error.g添加错误信息("JSONArray[" + index + "] is not a Boolean.");
            throw ci_error;
        } finally {
            ji_o = null;
        }
    }

    public Double getDouble(int index, final org.hzs.logging.error ci_error) throws org.hzs.logging.error {
        Object o = get(index);
        try {
            if (o == null) {
                return null;
            }
            return o instanceof Number ? ((Number) o).doubleValue() : Double.parseDouble((String) o);
        } catch (NumberFormatException e) {
            ci_error.g添加错误信息("JSONArray[" + index + "] is not a number.");
            throw ci_error;
        }
    }
//
//    public java.util.Date getDate(int index, final org.hzs.logging.error ci_error) throws org.hzs.logging.error {
//        Object o = get(index);
//        try {
//            if (o == null) {
//                return null;
//            }
//            if (o instanceof java.util.Date) {
//                return (java.util.Date) o;
//            } else if (o instanceof Number) {
//                return new java.util.Date((long) o);
//            } else if (o instanceof String) {
//                return new java.util.Date((String) o);
//            } else {
//                return null;
//            }
//        } catch (Exception e) {
//            ci_error.g添加错误信息("JSONArray[" + index + "] is not a number.");
//            throw ci_error;
//        }
//    }

    public java.math.BigDecimal getBigDecimal(int index, final org.hzs.logging.error ci_error) throws org.hzs.logging.error {
        Object o = get(index);
        try {
            if (o == null) {
                return null;
            }
            if (o instanceof java.math.BigDecimal) {
                return (java.math.BigDecimal) o;
            } else if (o instanceof Number) {
                return new java.math.BigDecimal(o.toString());
            } else if (o instanceof String) {
                return new java.math.BigDecimal((String) o);
            } else {
                return null;
            }
        } catch (Exception e) {
            ci_error.g添加错误信息("JSONArray[" + index + "] is not a number.");
            throw ci_error;
        }
    }

    public java.math.BigInteger getBigInteger(int index, final org.hzs.logging.error ci_error) throws org.hzs.logging.error {
        Object o = get(index);
        try {
            if (o == null) {
                return null;
            }
            if (o instanceof java.math.BigInteger) {
                return (java.math.BigInteger) o;
            } else if (o instanceof Number) {
                return new java.math.BigInteger(o.toString());
            } else if (o instanceof String) {
                return new java.math.BigInteger((String) o);
            } else {
                return null;
            }
        } catch (Exception e) {
            ci_error.g添加错误信息("JSONArray[" + index + "] is not a number.");
            throw ci_error;
        }
    }

    public Integer getInt(int index) {
        Object o = get(index);
        if (o == null) {
            return null;
        }
        return o instanceof Number ? ((Number) o).intValue() : Integer.valueOf(o.toString());
    }

    public JSONArray getJSONArray(int index, final org.hzs.logging.error ci_error) throws org.hzs.logging.error {
        Object o = get(index);
        if (o == null) {
            return null;
        }
        if (o instanceof JSONArray) {
            return (JSONArray) o;
        }
        ci_error.g添加错误信息("JSONArray[" + index + "] is not a JSONArray.");
        throw ci_error;
    }

    public JSONObject getJSONObject(int index, final org.hzs.logging.error ci_error) throws org.hzs.logging.error {
        Object o = get(index);
        if (o == null) {
            return null;
        }
        if (o instanceof JSONObject) {
            return (JSONObject) o;
        }
        ci_error.g添加错误信息("JSONArray[" + index + "] is not a JSONObject.");
        throw ci_error;
    }

    public Long getLong(int index) {
        Object o = get(index);
        if (o == null) {
            return null;
        }
        return o instanceof Number ? ((Number) o).longValue() : Long.valueOf(o.toString());
    }

    public String getString(int index) {
        Object o = get(index);
        if (o == null) {
            return null;
        }
        return o.toString();
    }

    public Byte getByte(final int index, final org.hzs.logging.error ci_error) throws org.hzs.logging.error {
        Object o = get(index);
        if (o == null) {
            return null;
        }
        if (o instanceof Byte) {
            return (byte) o;
        } else {
            ci_error.g添加错误信息("JSONArray[" + index + "] 不是字节型.");
            throw ci_error;
        }
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    protected static boolean p存在循环引用(final java.util.LinkedList<Object> cd, final java.util.Collection ci) {
        Object value = null;
        java.util.Iterator m = null;
        try {
            m = ci.iterator();
            while (m.hasNext()) {
                value = m.next();
                if (value instanceof java.util.Collection) {
                    if (cd.indexOf(value) > -1) {
                        return true;
                    }
                    cd.add(value);
                    if (p存在循环引用(cd, (java.util.Collection) value)) {
                        return true;
                    }
                }
                if (value instanceof java.util.Map) {
                    if (cd.indexOf(value) > -1) {
                        return true;
                    }
                    cd.add(value);
                    if (p存在循环引用(cd, ((java.util.Map) value).values())) {
                        return true;
                    }
                }
                cd.remove(value);
            }
            return false;
        } finally {
            value = null;
            m = null;
        }
    }

    protected boolean p存在循环引用() {
        java.util.LinkedList<Object> d = null;
        try {
            d = new java.util.LinkedList<>();
            d.add(this);
            return p存在循环引用(d, this);
        } finally {
            if (d != null) {
                d.clear();
                d = null;
            }
        }
    }

    @Override
    public String toString() {
        org.hzs.logging.error ji_error = null;
        try {
            ji_error = org.hzs.logging.error.d副本();
            return toString(ji_error);
        } catch (CloneNotSupportedException | org.hzs.logging.error ex) {
            Logger.getLogger(JSONArray.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            ji_error = null;
        }
    }

    public String toString(final org.hzs.logging.error ci_error) throws org.hzs.logging.error {
        if (p存在循环引用()) {
            ci_error.g添加错误信息("存在循环引用！");
            throw ci_error;
        }
        int len = size();
        StringBuilder sb = new StringBuilder();
        Object value = null;

        for (int i = 0; i < len; i += 1) {
            if (i > 0) {
                sb.append(",");
            }
            value = this.get(i);
            if (value instanceof java.math.BigInteger || value instanceof java.math.BigDecimal || value instanceof Boolean) {
                sb.append(value.toString());
            } else if (value instanceof JSONObject) {
                sb.append(((JSONObject) value).toString(ci_error));
            } else if (value instanceof JSONArray) {
                sb.append(((JSONArray) value).toString(ci_error));
            } else {
                sb.append(JSONObject.valueToString(this.get(i), ci_error));
            }
        }
        return "[" + sb.toString() + "]";
    }

    public JSONArray append(JSONArray ci_ArrayJSON) {
        int ji_i = ci_ArrayJSON.size();
        for (int ji1_i = 0; ji1_i < ji_i; ji1_i++) {
            super.add(ci_ArrayJSON.get(ji1_i));
        }
        return this;
    }

    /**
     *
     * 移除自身条目及仔项内的条目，彻底清理佔用资源。用于释放
     */
    @Override
    public void clear() {
        int ji长度_i, ji_i;
        Object jd = null;
        try {
            ji长度_i = size();
            for (ji_i = 0; ji_i < ji长度_i; ji_i++) {
                jd = get(ji_i);
                if (jd instanceof JSONArray) {
                    ((JSONArray) jd).clear();
                } else if (jd instanceof JSONObject) {
                    ((JSONObject) jd).clear();
                }
            }
            super.clear();
        } catch (Exception e) {
        } finally {
            jd = null;
        }
    }

    /**
     *
     * 移除自身的条目，但不移除仔项内的项目，用于清理缓冲
     */
    public void removeall() {
        super.clear();
    }
//    public int length() {
//        return super.size();
//    }

    /**
     *
     * @return 新的JSONArray对象，建议用此方式，加快程序运行速度
     */
    public static org.hzs.json.JSONArray d副本() {
        return (org.hzs.json.JSONArray) i_ArrayJSON.clone();
    }
}
