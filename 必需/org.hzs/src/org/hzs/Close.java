package org.hzs;

public abstract class Close {

    public void close() {
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
                        default:
                            if (type_s.endsWith("[]")) {
                                for (Object o1 : ((Object[]) o)) {
                                    try {
                                        method = o.getClass().getMethod("g关闭");
                                        if (method != null) {
                                            method.invoke(o.getClass().newInstance());
                                            break;
                                        }

                                        method = o.getClass().getMethod("close");
                                        if (method == null) {
                                            method.invoke(o.getClass().newInstance());
                                            break;
                                        }

                                        method = o.getClass().getMethod("clear");
                                        if (method == null) {
                                            method.invoke(o.getClass().newInstance());
                                            break;
                                        }
                                    } catch (NoSuchMethodException | SecurityException | InstantiationException | java.lang.reflect.InvocationTargetException ex) {
//                                        Logger.getLogger(Close.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            } else {
                                try {
                                    method = o.getClass().getMethod("g关闭");
                                    if (method != null) {
                                        method.invoke(o.getClass().newInstance());
                                        break;
                                    }

                                    method = o.getClass().getMethod("close");
                                    if (method == null) {
                                        method.invoke(o.getClass().newInstance());
                                        break;
                                    }

                                    method = o.getClass().getMethod("clear");
                                    if (method == null) {
                                        method.invoke(o.getClass().newInstance());
                                        break;
                                    }
                                } catch (NoSuchMethodException | SecurityException | InstantiationException | java.lang.reflect.InvocationTargetException ex) {
//                                Logger.getLogger(Close.class.getName()).log(Level.SEVERE, null, ex);
                                }
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
