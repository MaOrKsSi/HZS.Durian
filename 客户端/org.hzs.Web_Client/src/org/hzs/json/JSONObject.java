/**
 *
 *
 */
package org.hzs.json;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.hzs.logging.error;

/**
 *
 * @author 韩占山
 */
public class JSONObject extends java.util.TreeMap<String, Object> implements Cloneable, java.io.Serializable {

    private static final JSONObject i_JSON = new JSONObject();

    protected void set(final JSONTokener x, final org.hzs.logging.error ci_error) throws org.hzs.logging.error {
        this.clear();
        char c;
        String key;
        Object value;

        if (x.nextClean(ci_error) != '{') {
            ci_error.g添加错误信息("JSON文本必需以'{'开始！");
            throw ci_error;
        }
        for (;;) {
            c = x.nextClean(ci_error);
            switch (c) {
                case 0:
                    ci_error.g添加错误信息("JSON文本必需以'}'结束！");
                    throw ci_error;
                case '}':
                    return;
                default:
                    x.back();
                    key = x.nextValue(null, ci_error).toString();
            }

            /*
             * The key is followed by ':'. We will also tolerate '=' or '=>'.
             */
            c = x.nextClean(ci_error);
            if (c == '=') {
                if (x.next() != '>') {
                    x.back();
                }
            } else if (c != ':') {
                ci_error.g添加错误信息("Expected a ':' after a key");
                throw ci_error;
            }
            value = x.nextValue(this, ci_error);
            if (value != null) {
                super.put(key, value);
            }

            /*
             * Pairs are separated by ','. We will also tolerate ';'.
             */
            switch (x.nextClean(ci_error)) {
                case ';':
                case ',':
                    if (x.nextClean(ci_error) == '}') {
                        return;
                    }
                    x.back();
                    break;
                case '}':
                    return;
                default:
                    ci_error.g添加错误信息("Expected a ',' or '}'");
                    throw ci_error;
            }
        }
    }

    /**
     * @param ci_s 待解析文本
     * @param ci_error org.hzs.logging.error类型的错误对象，用于记录错误信息
     * @return 解析生成的org.hzs.JSONObject
     * @throws org.hzs.logging.error 解析失败抛出错误，此错误记录了所经历的类与过程
     */
    public JSONObject set(final String ci_s, final org.hzs.logging.error ci_error) throws org.hzs.logging.error {
        if (ci_s == null) {
            return null;
        }
        set(new JSONTokener(ci_s, ci_error), ci_error);
        return this;
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    protected static void testValidity(final Object o, final org.hzs.logging.error ci_error) throws org.hzs.logging.error {
        if (o == null) {
            return;
        }
        if (o instanceof Double) {
            if (((Double) o).isInfinite() || ((Double) o).isNaN()) {
                ci_error.g添加错误信息("JSON不允许非有限数");
                throw ci_error;
            }
        } else if (o instanceof Float) {
            if (((Float) o).isInfinite() || ((Float) o).isNaN()) {
                ci_error.g添加错误信息("JSON不允许非有限数");
                throw ci_error;
            }
        }
    }

    /**
     * 向JSON加入一个键值对，此处并未对日期型做特殊处理
     *
     * @param key 待加入的键，为空时不加入
     * @param value 待加入的值，为空时不加入
     * @return 加入的值
     */
    @Override
    public Object put(final String key, final Object value) {
        if (key == null) {
            return null;
        }
        if (value == null) {
            super.remove(key);
        } else {
            if (value instanceof java.math.BigDecimal) {
                super.put(key, ((java.math.BigDecimal) value).toPlainString());
            } else if (value instanceof java.math.BigInteger) {
                super.put(key, value.toString());
            } else {
                super.put(key, value);
            }
        }
        return value;
    }

    /**
     * 向JSON加入一个键值对
     *
     * @param key 为空时抛出错误
     * @param value 为空时不加入
     * @param ci_error org.hzs.logging.error类型的错误对象，用于记录错误信息
     * @return 返回JSON对象自身
     * @throws org.hzs.logging.error 解析失败抛出错误，此错误记录了所经历的类与过程
     */
    public JSONObject put(final String key, final Object value, final org.hzs.logging.error ci_error) throws org.hzs.logging.error {
        if (key == null) {
            ci_error.g添加错误信息("键不可为空");
            throw ci_error;
        }
        if (value == null) {
            super.remove(key);
        } else {
            testValidity(value, ci_error);
            put(key, value);
        }
        return this;
    }

    /**
     * 向JSON加入一个键值对
     *
     * @param key 为空时抛出错误
     * @param value byte类型，为空时不加入
     * @param ci_error org.hzs.logging.error类型的错误对象，用于记录错误信息
     * @return 返回JSON对象自身
     * @throws org.hzs.logging.error 解析失败抛出错误，此错误记录了所经历的类与过程
     */
    public JSONObject put(final String key, final byte value, final org.hzs.logging.error ci_error) throws org.hzs.logging.error {
        put(key, new Byte(value), ci_error);
        return this;
    }

    /**
     * 向JSON加入一个键值对
     *
     * @param key 为空时抛出错误
     * @param value boolean类型，为空时不加入
     * @param ci_error org.hzs.logging.error类型的错误对象，用于记录错误信息
     * @return 返回JSON对象自身
     * @throws org.hzs.logging.error 解析失败抛出错误，此错误记录了所经历的类与过程
     */
    public JSONObject put(final String key, final boolean value, final org.hzs.logging.error ci_error) throws org.hzs.logging.error {
        put(key, value ? Boolean.TRUE : Boolean.FALSE, ci_error);
        return this;
    }

    /**
     * 向JSON加入一个键值对
     *
     * @param key 为空时抛出错误
     * @param value double类型，为空时不加入
     * @param ci_error org.hzs.logging.error类型的错误对象，用于记录错误信息
     * @return 返回JSON对象自身
     * @throws org.hzs.logging.error 解析失败抛出错误，此错误记录了所经历的类与过程
     */
    public JSONObject put(final String key, final double value, final org.hzs.logging.error ci_error) throws org.hzs.logging.error {
        put(key, new Double(value), ci_error);
        return this;
    }

    /**
     * 向JSON加入一个键值对
     *
     * @param key 为空时抛出错误
     * @param value int类型，为空时不加入
     * @param ci_error org.hzs.logging.error类型的错误对象，用于记录错误信息
     * @return 返回JSON对象自身
     * @throws org.hzs.logging.error 解析失败抛出错误，此错误记录了所经历的类与过程
     */
    public JSONObject put(final String key, final int value, final org.hzs.logging.error ci_error) throws org.hzs.logging.error {
        put(key, new Integer(value), ci_error);
        return this;
    }

    /**
     * 向JSON加入一个键值对
     *
     * @param key 为空时抛出错误
     * @param value long类型，为空时不加入
     * @param ci_error org.hzs.logging.error类型的错误对象，用于记录错误信息
     * @return 返回JSON对象自身
     * @throws org.hzs.logging.error 解析失败抛出错误，此错误记录了所经历的类与过程
     */
    public JSONObject put(final String key, final long value, final org.hzs.logging.error ci_error) throws org.hzs.logging.error {
        put(key, new Long(value), ci_error);
        return this;
    }

//    public JSONObject put(final String key, final java.util.Date value, final org.hzs.logging.error ci_error) throws org.hzs.logging.error {
//        if (key == null) {
//            ci_error.g添加错误信息("键不可为空");
//            throw ci_error;
//        }
//        if (value != null) {
//            put(key, value.getTime(), ci_error);
//        } else {
//            super.remove(key);
//        }
//        return this;
//    }
//    /**
//     * 向JSON加入一个键值对
//     *
//     * @param key 为空时抛出错误
//     * @param value java.math.BigDecimal类型，为空时不加入
//     * @param ci_error org.hzs.logging.error类型的错误对象，用于记录错误信息
//     * @return 返回JSON对象自身
//     * @throws org.hzs.logging.error 解析失败抛出错误，此错误记录了所经历的类与过程
//     */
//    public JSONObject put(final String key, final java.math.BigDecimal value, final org.hzs.logging.error ci_error) throws org.hzs.logging.error {
//        if (key == null) {
//            ci_error.g添加错误信息("键不可为空");
//            throw ci_error;
//        }
//        if (value != null) {
//            put(key, value.toPlainString(), ci_error);
//        } else {
//            super.remove(key);
//        }
//        return this;
//    }
//
//    /**
//     * 向JSON加入一个键值对
//     *
//     * @param key 为空时抛出错误
//     * @param value java.math.BigInteger类型，为空时不加入
//     * @param ci_error org.hzs.logging.error类型的错误对象，用于记录错误信息
//     * @return 返回JSON对象自身
//     * @throws org.hzs.logging.error 解析失败抛出错误，此错误记录了所经历的类与过程
//     */
//    public JSONObject put(final String key, final java.math.BigInteger value, final org.hzs.logging.error ci_error) throws org.hzs.logging.error {
//        if (key == null) {
//            ci_error.g添加错误信息("键不可为空");
//            throw ci_error;
//        }
//        if (value != null) {
//            put(key, value.toString(), ci_error);
//        } else {
//            super.remove(key);
//        }
//        return this;
//    }
//
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static String quote(final String string) {
        if (string == null || string.length() == 0) {
            return "\"\"";
        }

        char b;
        char c = 0;
        int i;
        int len = string.length();
        StringBuilder sb = new StringBuilder(len + 4);
        String t;

        sb.append('"');
        for (i = 0; i < len; i += 1) {
            b = c;
            c = string.charAt(i);
            switch (c) {
                case '\\':
                case '"':
                    sb.append('\\');
                    sb.append(c);
                    break;
                case '/':
                    if (b == '<') {
                        sb.append('\\');
                    }
                    sb.append(c);
                    break;
                case '\b':
                    sb.append("\\b");
                    break;
                case '\t':
                    sb.append("\\t");
                    break;
                case '\n':
                    sb.append("\\n");
                    break;
                case '\f':
                    sb.append("\\f");
                    break;
                case '\r':
                    sb.append("\\r");
                    break;
                default:
                    if (c < ' ') {
                        t = "000" + Integer.toHexString(c);
                        sb.append("\\u").append(t.substring(t.length() - 4));
                    } else {
                        sb.append(c);
                    }
            }
        }
        sb.append('"');
        return sb.toString();
    }

    /**
     *
     * @param key 为空时抛出错误
     * @param ci_error org.hzs.logging.error类型的错误对象，用于记录错误信息
     * @return 返回取得的值；若key不存在，则返回空值。
     * @throws org.hzs.logging.error 解析失败抛出错误，此错误记录了所经历的类与过程
     */
    public Object get(final String key, final org.hzs.logging.error ci_error) throws org.hzs.logging.error {
        if (key == null) {
            ci_error.g添加错误信息("JSONObject[" + quote(key) + "]不可为空！");
            throw ci_error;
        }
        if (super.containsKey(key)) {
            return super.get(key);
        } else {
            return null;
        }
    }

    /**
     *
     * @param key 为空时抛出错误
     * @param ci_error org.hzs.logging.error类型的错误对象，用于记录错误信息
     * @return 返回取得的值；若key不存在，则返回空值。
     * @throws org.hzs.logging.error 解析失败抛出错误，此错误记录了所经历的类与过程
     */
    public Byte getByte(final String key, final org.hzs.logging.error ci_error) throws org.hzs.logging.error {
        Object o = get(key);
        if (o == null) {
            return null;
        }
        if (o instanceof Byte) {
            return (byte) o;
        } else {
            ci_error.g添加错误信息("JSONObject[" + quote(key) + "] 不是字节型.");
            throw ci_error;
        }
    }

    /**
     *
     * @param key 为空时抛出错误
     * @param ci_error org.hzs.logging.error类型的错误对象，用于记录错误信息
     * @return 返回取得的值；若key不存在，则返回空值。
     * @throws org.hzs.logging.error 解析失败抛出错误，此错误记录了所经历的类与过程
     */
    public Boolean getBoolean(final String key, final org.hzs.logging.error ci_error) throws org.hzs.logging.error {
        Object o = get(key);
        if (o == null) {
            return null;
        }
        if (o.equals(Boolean.FALSE)
                || (o instanceof String && ((String) o).equalsIgnoreCase("false"))
                || (o instanceof Number && !((Number) o).equals(0))) {
            return false;
        } else if (o.equals(Boolean.TRUE) || (o instanceof String && ((String) o).equalsIgnoreCase("true"))) {
            return true;
        }
        ci_error.g添加错误信息("JSONObject[" + quote(key) + "] 不是逻辑型.");
        throw ci_error;
    }

    /**
     *
     * @param key 为空时抛出错误
     * @param ci_error org.hzs.logging.error类型的错误对象，用于记录错误信息
     * @return 返回取得的值；若key不存在，则返回空值。
     * @throws org.hzs.logging.error 解析失败抛出错误，此错误记录了所经历的类与过程
     */
    public Double getDouble(final String key, final org.hzs.logging.error ci_error) throws org.hzs.logging.error {
        Object o = get(key);
        try {
            if (o == null) {
                return null;
            }
            return o instanceof Number ? ((Number) o).doubleValue() : Double.valueOf(o.toString());
        } catch (NumberFormatException e) {
            ci_error.g添加错误信息("JSONObject[" + quote(key) + "] 不是数值型.");
            throw ci_error;
        }
    }

//    public java.util.Date getDate(final String key, final org.hzs.logging.error ci_error) throws org.hzs.logging.error {
//        Object o = null;
//        try {
//            o = get(key);
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
//            ci_error.g添加错误信息("JSONObject[" + quote(key) + "] 不是数值or日期型.");
//            throw ci_error;
//        } finally {
//            o = null;
//        }
//    }
    /**
     *
     * @param key 为空时抛出错误
     * @param ci_error org.hzs.logging.error类型的错误对象，用于记录错误信息
     * @return 返回取得的值；若key不存在，则返回空值。
     * @throws org.hzs.logging.error 解析失败抛出错误，此错误记录了所经历的类与过程
     */
    public java.math.BigDecimal getBigDecimal(final String key, final org.hzs.logging.error ci_error) throws org.hzs.logging.error {
        Object o = null;
        try {
            o = get(key);
            if (o == null) {
                return null;
            }
            if (o instanceof java.math.BigDecimal) {
                return (java.math.BigDecimal) o;
            } else if (o instanceof Number) {
                return new java.math.BigDecimal(o.toString());
            } else if (o instanceof String) {
                if (o.equals("")) {
                    return java.math.BigDecimal.ZERO;
                } else {
                    return new java.math.BigDecimal((String) o);
                }
            } else {
                return null;
            }
        } catch (Exception e) {
            ci_error.g添加错误信息("JSONObject[" + quote(key) + "] 不是数值or文本型型.");
            throw ci_error;
        } finally {
            o = null;
        }
    }

    /**
     *
     * @param key 为空时抛出错误
     * @param ci_error org.hzs.logging.error类型的错误对象，用于记录错误信息
     * @return 返回取得的值；若key不存在，则返回空值。
     * @throws org.hzs.logging.error 解析失败抛出错误，此错误记录了所经历的类与过程
     */
    public java.math.BigInteger getBigInteger(final String key, final org.hzs.logging.error ci_error) throws org.hzs.logging.error {
        Object o = null;
        try {
            o = get(key);
            if (o == null) {
                return null;
            }
            if (o instanceof java.math.BigInteger) {
                return (java.math.BigInteger) o;
            } else if (o instanceof Number) {
                return new java.math.BigInteger(o.toString());
            } else if (o instanceof String) {
                if (o.equals("")) {
                    return java.math.BigInteger.ZERO;
                } else {
                    return new java.math.BigInteger((String) o);
                }
            } else {
                return null;
            }
        } catch (Exception e) {
            ci_error.g添加错误信息("JSONObject[" + quote(key) + "] 不是数值or文本型.");
            throw ci_error;
        } finally {
            o = null;
        }
    }

    /**
     *
     * @param key 为空时抛出错误
     * @param ci_error org.hzs.logging.error类型的错误对象，用于记录错误信息
     * @return 返回取得的值；若key不存在，则返回空值。
     * @throws org.hzs.logging.error 解析失败抛出错误，此错误记录了所经历的类与过程
     */
    public Integer getInt(final String key, final org.hzs.logging.error ci_error) throws org.hzs.logging.error {
        Object o = get(key);
        if (o == null) {
            return null;
        }
        return o instanceof Number ? ((Number) o).intValue() : Integer.valueOf(o.toString());
    }

    /**
     *
     * @param key 为空时抛出错误
     * @param ci_error org.hzs.logging.error类型的错误对象，用于记录错误信息
     * @return 返回取得的值；若key不存在，则返回空值。
     * @throws org.hzs.logging.error 解析失败抛出错误，此错误记录了所经历的类与过程
     */
    public JSONArray getJSONArray(final String key, final org.hzs.logging.error ci_error) throws org.hzs.logging.error {
        Object o = get(key);
        if (o == null) {
            return null;
        }
        if (o instanceof JSONArray) {
            return (JSONArray) o;
        }
        ci_error.g添加错误信息("JSONObject[" + quote(key) + "] 不是“JSONArray”型.");
        throw ci_error;
    }

    /**
     *
     * @param key 为空时抛出错误
     * @param ci_error org.hzs.logging.error类型的错误对象，用于记录错误信息
     * @return 返回取得的值；若key不存在，则返回空值。
     * @throws org.hzs.logging.error 解析失败抛出错误，此错误记录了所经历的类与过程
     */
    public JSONObject getJSONObject(final String key, final org.hzs.logging.error ci_error) throws org.hzs.logging.error {
        Object o = get(key);
        if (o == null) {
            return null;
        }
        if (o instanceof JSONObject) {
            return (JSONObject) o;
        }
        ci_error.g添加错误信息("JSONObject[" + quote(key) + "] 不是“JSONObject”型.");
        throw ci_error;
    }

    /**
     *
     * @param key 为空时抛出错误
     * @param ci_error org.hzs.logging.error类型的错误对象，用于记录错误信息
     * @return 返回取得的值；若key不存在，则返回空值。
     * @throws org.hzs.logging.error 解析失败抛出错误，此错误记录了所经历的类与过程
     */
    public Long getLong(final String key, final org.hzs.logging.error ci_error) throws org.hzs.logging.error {
        Object o = get(key);
        if (o == null) {
            return null;
        }
        return o instanceof Number ? ((Number) o).longValue() : Long.valueOf(o.toString());
    }

    /**
     *
     * @param key 为空时抛出错误
     * @param ci_error org.hzs.logging.error类型的错误对象，用于记录错误信息
     * @return 返回取得的值；若key不存在，则返回空值。
     * @throws org.hzs.logging.error 解析失败抛出错误，此错误记录了所经历的类与过程
     */
    public Short getShort(final String key, final org.hzs.logging.error ci_error) throws org.hzs.logging.error {
        Object o = get(key);
        if (o == null) {
            return null;
        }
        return o instanceof Number ? ((Number) o).shortValue() : Short.valueOf(o.toString());
    }

    /**
     *
     * @param key 为空时抛出错误
     * @param ci_error org.hzs.logging.error类型的错误对象，用于记录错误信息
     * @return 返回取得的值；若key不存在，则返回空值。
     * @throws org.hzs.logging.error 解析失败抛出错误，此错误记录了所经历的类与过程
     */
    public String getString(final String key, final org.hzs.logging.error ci_error) throws org.hzs.logging.error {
        Object o = get(key);
        if (o == null) {
            return null;
        }
        return o.toString();
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public boolean p存在循环引用() {
        java.util.LinkedList<Object> d = null;
        try {
            d = new java.util.LinkedList<>();
            d.add(this);
            return JSONArray.p存在循环引用(d, this.values());
        } finally {
            if (d != null) {
                d.clear();
                d = null;
            }
        }
    }

    public java.util.Iterator keys() {
        return super.keySet().iterator();
    }

//    public int length() {
//        return super.size();
//    }
    private static String numberToString(final Number n, final org.hzs.logging.error ci_error) throws org.hzs.logging.error {
        if (n == null) {
            ci_error.g添加错误信息("Null pointer");
            throw ci_error;
        }
        testValidity(n, ci_error);

// Shave off trailing zeros and decimal point, if possible.
        String s = n.toString();
        if (s.indexOf('.') > 0 && s.indexOf('e') < 0 && s.indexOf('E') < 0) {
            while (s.endsWith("0")) {
                s = s.substring(0, s.length() - 1);
            }
            if (s.endsWith(".")) {
                s = s.substring(0, s.length() - 1);
            }
        }
        return s;
    }

    public static String valueToString(final Object value, final org.hzs.logging.error ci_error) throws org.hzs.logging.error {
        if (value instanceof java.math.BigInteger || value instanceof java.math.BigDecimal || value instanceof Boolean) {
            return value.toString();
        }
        if (value instanceof JSONObject) {
            return ((JSONObject) value).toString(ci_error);
        }
        if (value instanceof JSONArray) {
            return ((JSONArray) value).toString(ci_error);
        }
        if (value instanceof Number) {
            return numberToString((Number) value, ci_error);
        }
        return quote(value.toString());
    }

    @Override
    public String toString() {
        org.hzs.logging.error ji_error = null;
        try {
            ji_error = org.hzs.logging.error.d副本();
            return toString(ji_error);
        } catch (CloneNotSupportedException | error ex) {
            Logger.getLogger(JSONObject.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
        }
    }

    /**
     *
     * @param ci_error org.hzs.logging.error类型的错误对象，用于记录错误信息
     * @return 序列化後的文本
     * @throws org.hzs.logging.error 解析失败抛出错误，此错误记录了所经历的类与过程
     */
    public String toString(final org.hzs.logging.error ci_error) throws org.hzs.logging.error {
        if (p存在循环引用()) {
            ci_error.g添加错误信息("存在循环引用！");
            throw ci_error;
        }
        java.util.Iterator keys = null;
        StringBuilder sb = null;
        try {
            keys = keys();
            sb = new StringBuilder("{");

            while (keys.hasNext()) {
                if (sb.length() > 1) {
                    sb.append(',');
                }
                Object o = keys.next();
//            quote(o.toString());
                sb.append(quote(o.toString()));
                sb.append(':');
                sb.append(valueToString(super.get(o), ci_error));
            }
            sb.append('}');
            return sb.toString();
        } finally {
            keys = null;
            sb = null;
        }
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     *
     * 移除自身条目及仔项内的条目，彻底清理佔用资源。用于释放
     */
    @Override
    public void clear() {
        java.util.Iterator keys = null;
        Object jd = null;
        try {
            keys = keys();
            while (keys.hasNext()) {
                Object o = keys.next();
                jd = super.get(o);
                if (jd instanceof JSONArray) {
                    ((JSONArray) jd).clear();
                } else if (jd instanceof JSONObject) {
                    ((JSONObject) jd).clear();
                }
            }
            super.clear();
        } catch (Exception e) {
        } finally {
            keys = null;
        }
    }

    /**
     *
     * 移除自身的条目，但不移除仔项内的项目，用于清理缓冲
     */
    public void removeall() {
        super.clear();
    }

    /**
     *
     * @return 新的JSON对象，建议用此方式，加快程序运行速度
     */
    public static org.hzs.json.JSONObject d副本() {
        return (org.hzs.json.JSONObject) i_JSON.clone();
    }
}
