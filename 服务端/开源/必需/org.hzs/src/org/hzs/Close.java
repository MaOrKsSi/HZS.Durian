package org.hzs;

public interface Close {

    default void close() {
        Object o = null;
        java.lang.reflect.Field[] fieldArray = null;
        java.lang.reflect.Method method = null;
        String type_s = null;
        try {
            fieldArray = this.getClass().getDeclaredFields();
            for (java.lang.reflect.Field field : fieldArray) {
                try {
                    field.setAccessible(true);
                    o = field.get(this);
                    if (o == null) {
                        continue;
                    }
                    type_s = field.getType().toString();
                    switch (type_s) {
                        case "int":
                        case "double":
                        case "byte":
                        case "char":
                        case "float":
                        case "short":
                            continue;
                        case "interface org.hzs.sql.视图":
                            try {
                                ((org.hzs.sql.视图) o).g关闭();
                            } catch (IllegalArgumentException | java.sql.SQLException ex) {
//                                Logger.getLogger(Close.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            break;
                        case "interface org.hzs.sql.视图[]":
                            for (org.hzs.sql.视图 jd视图 : ((org.hzs.sql.视图[]) o)) {
                                try {
                                    jd视图.g关闭();
                                } catch (IllegalArgumentException | java.sql.SQLException ex) {
//                                    Logger.getLogger(Close.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                            break;
                        case "interface org.hzs.sql.操作":
                            try {
                                ((org.hzs.sql.操作) o).g关闭();
                            } catch (IllegalArgumentException | java.sql.SQLException ex) {
//                                Logger.getLogger(Close.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            break;
                        case "interface org.hzs.sql.操作[]":
                            for (org.hzs.sql.操作 jd操作 : ((org.hzs.sql.操作[]) o)) {
                                try {
                                    jd操作.g关闭();
                                } catch (IllegalArgumentException | java.sql.SQLException ex) {
//                                    Logger.getLogger(Close.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                            break;
                        case "interface org.hzs.sql.连接":
                            try {
                                ((org.hzs.sql.连接) o).g废弃新记录();
                                ((org.hzs.sql.连接) o).g回滚事务();
                                ((org.hzs.sql.连接) o).g关闭();
                            } catch (IllegalArgumentException | java.sql.SQLException | CloneNotSupportedException | java.net.SocketException | java.io.UnsupportedEncodingException ex) {
//                                Logger.getLogger(Close.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            break;
                        case "interface org.hzs.sql.连接[]":
                            for (org.hzs.sql.连接 jd连接 : ((org.hzs.sql.连接[]) o)) {
                                try {
                                    jd连接.g废弃新记录();
                                    jd连接.g回滚事务();
                                    jd连接.g关闭();
                                } catch (IllegalArgumentException | java.sql.SQLException | CloneNotSupportedException | java.net.SocketException | java.io.UnsupportedEncodingException ex) {
//                                    Logger.getLogger(Close.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                            break;
                        case "class org.hzs.json.JSONArray":
                            ((org.hzs.json.JSONArray) o).clear();
                            break;
                        case "class org.hzs.json.JSONArray[]":
                            for (org.hzs.json.JSONArray JSONArray : ((org.hzs.json.JSONArray[]) o)) {
                                JSONArray.clear();
                            }
                            break;
                        case "class org.hzs.json.JSONObject":
                            ((org.hzs.json.JSONObject) o).clear();
                            break;
                        case "class org.hzs.json.JSONObject[]":
                            for (org.hzs.json.JSONObject JSONObject : ((org.hzs.json.JSONObject[]) o)) {
                                JSONObject.clear();
                            }
                            break;
                        default:
                            try {
                                method = o.getClass().getMethod("close");
                                if (method == null) {
                                    break;
                                }
                                method.invoke(o.getClass().newInstance());
                            } catch (NoSuchMethodException | SecurityException | InstantiationException | java.lang.reflect.InvocationTargetException ex) {
//                                Logger.getLogger(Close.class.getName()).log(Level.SEVERE, null, ex);
                            }
                    }
                    field.set(this, null);
                    field.setAccessible(false);
                } catch (IllegalArgumentException | IllegalAccessException ex) {
//                    Logger.getLogger(Close.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } finally {
            o = null;
            fieldArray = null;
            method = null;
            type_s = null;
        }
    }
}
