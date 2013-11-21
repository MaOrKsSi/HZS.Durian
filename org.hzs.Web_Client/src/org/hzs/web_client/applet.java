package org.hzs.web_client;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.hzs.logging.error;

public final class applet extends org.hzs.Client {

    /**
     * @param ciJS打印代码_s
     */
    public void g打印(final String ciJS打印代码_s) {
        try {
            org.hzs.web_client.Application.d打印_HttpHandler.i打印代码_S.delete(0, org.hzs.web_client.Application.d打印_HttpHandler.i打印代码_S.length());
            org.hzs.web_client.Application.d打印_HttpHandler.i打印代码_S.append("<!DOCTYPE html><html><head><META HTTP-EQUIV='Content-Type' CONTENT='text/html; charset=UTF-8'></head><body><object id='LODOP_OB' classid='clsid:2105C259-1E0C-4534-8141-A753534CB4CA' width=0 height=0><embed id='LODOP_EM' type='application/x-print-lodop' width=0 height=0 /></object><script>var b = function(LODOP) {");
            org.hzs.web_client.Application.d打印_HttpHandler.i打印代码_S.append(ciJS打印代码_s);
            org.hzs.web_client.Application.d打印_HttpHandler.i打印代码_S.append("};var a = function() {var LODOP = null;try {LODOP = document.getElementById('LODOP_OB');if (LODOP === null || typeof (LODOP.VERSION) === \"undefined\") {LODOP = document.getElementById('LODOP_EM');if (LODOP === null || typeof (LODOP.VERSION) === \"undefined\") {document.write(\"<a href='http://115.28.52.17/'>进入主页下载LODOP插件并安装</a>\");return;}}b(LODOP);window.opener = null;window.open('', '_parent', '');window.close();} catch (err) {}};a();</script></body></html>");
            java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
            if (java.awt.Desktop.isDesktopSupported() && desktop.isSupported(java.awt.Desktop.Action.BROWSE)) {
                java.net.URI uri = new java.net.URI(org.hzs.web_client.Application.d打印_HttpHandler.i本地服务网址_s);
                desktop.browse(uri);
            }
//         Runtime.getRuntime().exec("\"" + Property.i程序文件袷_s + "\\Internet Explorer\\IEXPLORE.EXE\" \"http://localhost:" + (8080 - Property.i端口偏移_i) + "/\"");
        } catch (java.io.IOException | java.net.URISyntaxException ex) {
            Logger.getLogger(applet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @param ci指令_s
     * @param ci参數_s
     * @return
     */
    public String g会晤服务器(final String ci指令_s, final String ci参數_s) {
        try {
            return super.g会晤服务器(ci指令_s, ci参數_s, org.hzs.logging.error.d副本());
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(applet.class.getName()).log(Level.SEVERE, null, ex);
            return "{success:false,_:'本地错误！'}";
        } catch (error ex) {
            Logger.getLogger(applet.class.getName()).log(Level.SEVERE, null, ex);
            try {
                org.hzs.json.JSONObject ji_JSON = org.hzs.json.JSONObject.d副本().set(ex.getMessage(), ex);
                org.hzs.json.JSONArray ji_ArrayJSON = ji_JSON.getJSONArray("错误", ex);
                ji_JSON = ji_ArrayJSON.getJSONObject(ji_ArrayJSON.size() - 1, ex);
                return "{success:false,_:'" + ji_JSON.getString("错误1", ex) + "'}";
            } catch (error ex1) {
                Logger.getLogger(applet.class.getName()).log(Level.SEVERE, null, ex1);
                return "{success:false,_:'莫名其妙的错误！'}";
            }
        }
    }

    @Override
    public void g退出() {
        super.g退出();
        Application.g退出();
    }
//==============================================================================================================================    

    /**
     * @param ciJSON_s
     * @param ci前缀_s
     * @param ci後缀_s
     * @return 返回整理後的文本，如果出错则原样返回
     */
    public String i添加前缀与後缀(final String ciJSON_s, final String ci前缀_s, final String ci後缀_s) {
        // <editor-fold defaultstate="collapsed" desc="自用">
        class 自用 implements Cloneable {

            java.util.Iterator keys = null;
            StringBuilder sb = null;
            String o = null;
            org.hzs.json.JSONObject i_JSON = null;

            public 自用 d副本() throws CloneNotSupportedException {
                return (自用) super.clone();
            }

            public void close() {
                i_JSON = null;
                keys = null;
                sb = null;
                o = null;
            }
        }// </editor-fold>
        自用 ji自用 = null;
        org.hzs.logging.error ji_error = null;
        try {
            ji_error = org.hzs.logging.error.d副本();
            ji自用 = (自用) org.hzs.常用.d对象池.get(自用.class.getName());
            if (ji自用 == null) {
                ji自用 = new 自用();
                org.hzs.常用.d对象池.put(自用.class.getName(), ji自用);
            }
            ji自用 = ji自用.d副本();
            //
            ji自用.i_JSON = org.hzs.json.JSONObject.d副本();
            ji自用.i_JSON.set(ciJSON_s, ji_error);
            ji自用.keys = ji自用.i_JSON.keys();
            ji自用.sb = new StringBuilder("{");

            while (ji自用.keys.hasNext()) {
                if (ji自用.sb.length() > 1) {
                    ji自用.sb.append(',');
                }
                ji自用.o = (String) ji自用.keys.next();
                ji自用.sb.append(ci前缀_s + ji自用.o + ci後缀_s);
                ji自用.sb.append(':');
                ji自用.sb.append(ji自用.i_JSON.get(ji自用.o));
            }
            ji自用.sb.append('}');
            return ji自用.sb.toString();
        } catch (org.hzs.logging.error | CloneNotSupportedException ex) {
            Logger.getLogger(applet.class.getName()).log(Level.SEVERE, null, ex);
            return ciJSON_s;
        } finally {
            if (ji自用 != null) {
                ji自用.close();
                ji自用 = null;
            }
        }
    }

    /**
     * 注意：不能有声卡驱动；若已安装声卡驱动，必需卸载
     */
    public void g蜂鸣() {
        org.hzs.Client.d蜂鸣.beep();
    }

    /**
     * @param ci数字金额_s
     * @return
     */
    public String i金额$中文大写_s(final String ci数字金额_s) {
        org.hzs.金额 jd金额 = null;
        try {
            jd金额 = new org.hzs.金额(ci数字金额_s, org.hzs.logging.error.d副本());
            return jd金额.i中文_s(org.hzs.金额.大写);
        } catch (org.hzs.logging.error | CloneNotSupportedException ex) {
            Logger.getLogger(applet.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
    }

    /**
     * 将中文用拼音首字母表示
     *
     * @param ci_s
     * @return
     */
    public String i助记文本_s(final String ci_s) {
        return org.hzs.助记文本.i助记文本_s(ci_s);
    }

    //公式整理及计算，适合高精度数值计算==============================================================================================================================
    public String g中序表达式to後序表达式(final String ci公式_s) {
        org.hzs.logging.error ji_error = null;
        org.hzs.json.JSONObject ji_JSON = null;
        String[] ji_sArray = null;
        StringBuilder ji_S = null;
        try {
            ji_error = org.hzs.logging.error.d副本();
            ji_JSON = org.hzs.json.JSONObject.d副本();
            ji_S = new StringBuilder(128);
            ji_sArray = org.hzs.公式.g中序表达式to後序表达式(ci公式_s, ji_error);
            for (String ji_s : ji_sArray) {
                ji_S.append(ji_s);
                ji_S.append(" ");
            }
            ji_JSON.put("_", ji_S.toString().trim());
            ji_JSON.put("success", true);
            return ji_JSON.toString(ji_error);
        } catch (org.hzs.logging.error ex) {
            return "{success:false,_:'" + ex.i错误_S.toString() + "'}";
        } catch (CloneNotSupportedException ex) {
            return "{success:false,_:'未知错误！'}";
        } finally {
            ji_error = null;
            ji_JSON = null;
            ji_sArray = null;
            ji_S = null;
        }
    }

    public String g计算後序表达式(final String ci後序表达式_s) {
        org.hzs.logging.error ji_error = null;
        String[] ji_sArray = null;
        java.math.BigDecimal ji_BD = null;
        try {
            ji_error = org.hzs.logging.error.d副本();
            ji_sArray = ci後序表达式_s.split(" ");
            ji_BD = org.hzs.公式.g计算後序表达式(ji_sArray, ji_error);
            return ji_BD.toPlainString();
        } catch (CloneNotSupportedException | org.hzs.logging.error ex) {
            return "";
        } finally {
            ji_error = null;
            ji_sArray = null;
            ji_BD = null;
        }
    }
    //用于操作计算表==============================================================================================================================
    private java.util.TreeMap<Integer, org.apache.poi.hssf.usermodel.HSSFWorkbook> d工作簿 = new java.util.TreeMap<>();

    public int EXCEL_新建文档() {
        for (int ji_i = Integer.MIN_VALUE + 1; ji_i < Integer.MAX_VALUE; ji_i++) {
            if (d工作簿.get(ji_i) == null) {
                d工作簿.put(ji_i, new org.apache.poi.hssf.usermodel.HSSFWorkbook());
                return ji_i;
            }
        }
        return Integer.MIN_VALUE;//返回最小整数，表示无法再建立工作簿
    }

    public void EXCEL_保存(final int ci句柄_i, final String ci文档_s) {
        org.apache.poi.hssf.usermodel.HSSFWorkbook jd工作簿 = null;
        try {
            jd工作簿 = d工作簿.get(ci句柄_i);
            jd工作簿.write(new java.io.FileOutputStream(ci文档_s));
        } catch (java.io.IOException ex) {
            jd工作簿 = null;
        }
    }

    public void EXCEL_关闭(final int ci句柄_i) {
        d工作簿.remove(ci句柄_i);
    }

    public void EXCEL_合并表元(final int ci句柄_i, final int ci工作表_i, final int ci起始行_i, final int ci截至行_i, final int ci起始列_i, final int ci截至列_i) {
        org.apache.poi.hssf.usermodel.HSSFWorkbook jd工作簿 = d工作簿.get(ci句柄_i);
        org.apache.poi.hssf.usermodel.HSSFSheet sheet = jd工作簿.getSheetAt(ci工作表_i);
        sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(ci起始行_i, ci截至行_i, ci起始列_i, ci截至列_i));
    }

    public void EXCEL_置值(final int ci句柄_i, final int ci工作表_i, final int ci行_i, final int ci列_i, final String ci_s) {
        org.apache.poi.hssf.usermodel.HSSFWorkbook jd工作簿 = d工作簿.get(ci句柄_i);
        org.apache.poi.hssf.usermodel.HSSFSheet sheet = jd工作簿.getSheetAt(ci工作表_i);
        org.apache.poi.hssf.usermodel.HSSFRow row = sheet.createRow(ci行_i); //建立新行
        org.apache.poi.hssf.usermodel.HSSFCell cell = row.createCell(ci列_i); //建立新cell 
        cell.setCellValue(ci_s);
    }

    public void EXCEL_置值(final int ci句柄_i, final int ci工作表_i, final int ci行_i, final int ci列_i, final String ci_s, final int ci类型_i) {
        org.apache.poi.hssf.usermodel.HSSFWorkbook jd工作簿 = d工作簿.get(ci句柄_i);
        org.apache.poi.hssf.usermodel.HSSFSheet sheet = jd工作簿.getSheetAt(ci工作表_i);
        org.apache.poi.hssf.usermodel.HSSFRow row = sheet.createRow(ci行_i); //建立新行
        org.apache.poi.hssf.usermodel.HSSFCell cell = row.createCell(ci列_i); //建立新cell 
        cell.setCellValue(ci_s);
        cell.setCellType(ci类型_i);
    }

    public void EXCEL_置值(final int ci句柄_i, final int ci工作表_i, final int ci行_i, final int ci列_i, final double ci_i) {
        org.apache.poi.hssf.usermodel.HSSFWorkbook jd工作簿 = d工作簿.get(ci句柄_i);
        org.apache.poi.hssf.usermodel.HSSFSheet sheet = jd工作簿.getSheetAt(ci工作表_i);
        org.apache.poi.hssf.usermodel.HSSFRow row = sheet.createRow(ci行_i); //建立新行
        org.apache.poi.hssf.usermodel.HSSFCell cell = row.createCell(ci列_i); //建立新cell 
        cell.setCellValue(ci_i);
    }

    public void EXCEL_置值(final int ci句柄_i, final int ci工作表_i, final int ci行_i, final int ci列_i, final double ci_i, final int ci类型_i) {
        org.apache.poi.hssf.usermodel.HSSFWorkbook jd工作簿 = d工作簿.get(ci句柄_i);
        org.apache.poi.hssf.usermodel.HSSFSheet sheet = jd工作簿.getSheetAt(ci工作表_i);
        org.apache.poi.hssf.usermodel.HSSFRow row = sheet.createRow(ci行_i); //建立新行
        org.apache.poi.hssf.usermodel.HSSFCell cell = row.createCell(ci列_i); //建立新cell 
        cell.setCellValue(ci_i);
        cell.setCellType(ci类型_i);
    }

    public void EXCEL_置值(final int ci句柄_i, final int ci工作表_i, final int ci行_i, final int ci列_i, final long ci_i) {
        org.apache.poi.hssf.usermodel.HSSFWorkbook jd工作簿 = d工作簿.get(ci句柄_i);
        org.apache.poi.hssf.usermodel.HSSFSheet sheet = jd工作簿.getSheetAt(ci工作表_i);
        org.apache.poi.hssf.usermodel.HSSFRow row = sheet.createRow(ci行_i); //建立新行
        org.apache.poi.hssf.usermodel.HSSFCell cell = row.createCell(ci列_i); //建立新cell 
        cell.setCellValue(ci_i);
    }

    public void EXCEL_置值(final int ci句柄_i, final int ci工作表_i, final int ci行_i, final int ci列_i, final long ci_i, final int ci类型_i) {
        org.apache.poi.hssf.usermodel.HSSFWorkbook jd工作簿 = d工作簿.get(ci句柄_i);
        org.apache.poi.hssf.usermodel.HSSFSheet sheet = jd工作簿.getSheetAt(ci工作表_i);
        org.apache.poi.hssf.usermodel.HSSFRow row = sheet.createRow(ci行_i); //建立新行
        org.apache.poi.hssf.usermodel.HSSFCell cell = row.createCell(ci列_i); //建立新cell 
        cell.setCellValue(ci_i);
        cell.setCellType(ci类型_i);
    }

    public void EXCEL_置列宽(final int ci句柄_i, final int ci工作表_i, final int ci列_i, final int ci列宽_i) {
        org.apache.poi.hssf.usermodel.HSSFWorkbook jd工作簿 = d工作簿.get(ci句柄_i);
        org.apache.poi.hssf.usermodel.HSSFSheet sheet = jd工作簿.getSheetAt(ci工作表_i);
        sheet.setColumnWidth(ci列_i, ci列宽_i);
    }

    public void EXCEL_置列宽(final int ci句柄_i, final int ci工作表_i, final int ci列_i) {
        org.apache.poi.hssf.usermodel.HSSFWorkbook jd工作簿 = d工作簿.get(ci句柄_i);
        org.apache.poi.hssf.usermodel.HSSFSheet sheet = jd工作簿.getSheetAt(ci工作表_i);
        sheet.autoSizeColumn(ci列_i);
    }

    public void EXCEL_置行高(final int ci句柄_i, final int ci工作表_i, final int ci行_i, final short ci行高_i) {
        org.apache.poi.hssf.usermodel.HSSFWorkbook jd工作簿 = d工作簿.get(ci句柄_i);
        org.apache.poi.hssf.usermodel.HSSFSheet sheet = jd工作簿.getSheetAt(ci工作表_i);
        org.apache.poi.hssf.usermodel.HSSFRow row = sheet.createRow(ci行_i); //建立新行
        row.setHeight(ci行高_i);
    }

    public void EXCEL_冻结窗口(final int ci句柄_i, final int ci工作表_i, final int ci行_i, final int ci列_i) {
        org.apache.poi.hssf.usermodel.HSSFWorkbook jd工作簿 = d工作簿.get(ci句柄_i);
        org.apache.poi.hssf.usermodel.HSSFSheet sheet = jd工作簿.getSheetAt(ci工作表_i);
        sheet.createFreezePane(ci行_i, ci列_i, ci行_i, ci列_i);
    }

    //文档操作==============================================================================================================================
    /**
     * @param ci过滤扩展名_ArrayJSONs
     * @param ci提示_s
     * @return
     */
    public String i获取文档名_ArrayJSONs(final String ci过滤扩展名_ArrayJSONs, final String ci提示_s) {
        // <editor-fold defaultstate="collapsed" desc="自用">
        class 自用 implements Cloneable {

            public javafx.stage.FileChooser d文档選择窗 = null;
            public java.util.List<String> i扩展档列表 = null;
            public java.util.List<java.io.File> i文档 = null;
            public javafx.stage.FileChooser.ExtensionFilter d扩展档过滤 = null;
            public org.hzs.json.JSONArray i_JSONArray = null;

            public 自用 d副本() throws CloneNotSupportedException {
                return (自用) super.clone();
            }

            public void close() {
                d文档選择窗 = null;
                if (i扩展档列表 != null) {
                    i扩展档列表.clear();
                    i扩展档列表 = null;
                }
                i文档 = null;
                d扩展档过滤 = null;
                i_JSONArray = null;
            }
        }// </editor-fold>
        org.hzs.logging.error ji_error = null;
        自用 ji自用 = null;
        try {
            ji_error = org.hzs.logging.error.d副本();
            ji自用 = (自用) org.hzs.常用.d对象池.get(自用.class.getName());
            if (ji自用 == null) {
                ji自用 = new 自用();
                org.hzs.常用.d对象池.put(自用.class.getName(), ji自用);
            }
            ji自用 = ji自用.d副本();
            //
            ji自用.d文档選择窗 = new javafx.stage.FileChooser();
            //建立过滤
            ji自用.i_JSONArray = org.hzs.json.JSONArray.d副本();
            ji自用.i_JSONArray.set(ci过滤扩展名_ArrayJSONs, ji_error);
            ji自用.i扩展档列表 = new java.util.ArrayList<String>();
            int ji1_i = ji自用.i_JSONArray.size();
            if (ji1_i > 0) {
                for (int ji2_i = 0; ji2_i < ji1_i; ji2_i++) {
                    ji自用.i扩展档列表.add("*." + ji自用.i_JSONArray.getString(ji2_i));
                }
                ji自用.d扩展档过滤 = new javafx.stage.FileChooser.ExtensionFilter(ci提示_s, ji自用.i扩展档列表);
                ji自用.d文档選择窗.getExtensionFilters().add(ji自用.d扩展档过滤);
            }
            ji自用.i文档 = ji自用.d文档選择窗.showOpenMultipleDialog(null);//以完全模式显示文档选择窗
            //labelFile.setText(file.getPath()); 
            if (ji自用.i文档 == null) {
                return "[]";
            }
            ji自用.i_JSONArray = new org.hzs.json.JSONArray();
            Object[] ddd = ji自用.i文档.toArray();
            for (Object ddd1 : ddd) {
                ji自用.i_JSONArray.put(((java.io.File) ddd1).getPath().replaceAll("\\\\", "/"));
            }
            return ji自用.i_JSONArray.toString();
        } catch (org.hzs.logging.error | CloneNotSupportedException ex) {
            Logger.getLogger(applet.class.getName()).log(Level.SEVERE, null, ex);
            return "[]";
        } finally {
            if (ji自用 != null) {
                ji自用.close();
                ji自用 = null;
            }
            ji_error = null;
        }
    }

    public String i获取文档内容_BASE64s(final String ci文档_s) {
        // <editor-fold defaultstate="collapsed" desc="自用">
        class 自用 implements Cloneable {

            public java.io.File file = null;
            public java.io.FileInputStream in = null;
            public byte[] i_byteArray = null;

            public 自用 d副本() throws CloneNotSupportedException {
                return (自用) super.clone();
            }

            public void close() {
                file = null;
                in = null;
                i_byteArray = null;
            }
        }// </editor-fold>
        自用 ji自用 = null;
        try {
            ji自用 = (自用) org.hzs.常用.d对象池.get(自用.class.getName());
            if (ji自用 == null) {
                ji自用 = new 自用();
                org.hzs.常用.d对象池.put(自用.class.getName(), ji自用);
            }
            ji自用 = ji自用.d副本();
            //
            ji自用.file = new java.io.File(ci文档_s);
            ji自用.in = new java.io.FileInputStream(ji自用.file);
            ji自用.i_byteArray = new byte[(int) ji自用.file.length()];
            ji自用.in.read(ji自用.i_byteArray);
            ji自用.i_byteArray = org.hzs.编码.Base64.i编码_byteArray(ji自用.i_byteArray);
            return new String(ji自用.i_byteArray, "UTF-8");
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(applet.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        } catch (java.io.FileNotFoundException ex) {
            Logger.getLogger(applet.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        } catch (java.io.IOException ex) {
            Logger.getLogger(applet.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        } finally {
            if (ji自用 != null) {
                ji自用.close();
                ji自用 = null;
            }
        }
    }

    /**
     * @param ci过滤扩展名_ArrayJSONs
     * @param ci提示_s
     * @return
     */
    public String i获取文档名_s(final String ci过滤扩展名_ArrayJSONs, final String ci提示_s) {
        // <editor-fold defaultstate="collapsed" desc="自用">
        class 自用 implements Cloneable {

            public javafx.stage.FileChooser d文档選择窗 = null;
            public java.util.List<String> i扩展档列表 = null;
            public java.io.File i文档 = null;
            public javafx.stage.FileChooser.ExtensionFilter d扩展档过滤 = null;
            public org.hzs.json.JSONArray i_JSONArray = null;

            public 自用 d副本() throws CloneNotSupportedException {
                return (自用) super.clone();
            }

            public void close() {
                d文档選择窗 = null;
                if (i扩展档列表 != null) {
                    i扩展档列表.clear();
                    i扩展档列表 = null;
                }
                i文档 = null;
                d扩展档过滤 = null;
                i_JSONArray = null;
            }
        }// </editor-fold>
        org.hzs.logging.error ji_error = null;
        自用 ji自用 = null;
        try {
            ji_error = org.hzs.logging.error.d副本();
            ji自用 = (自用) org.hzs.常用.d对象池.get(自用.class.getName());
            if (ji自用 == null) {
                ji自用 = new 自用();
                org.hzs.常用.d对象池.put(自用.class.getName(), ji自用);
            }
            ji自用 = ji自用.d副本();
            //
            ji自用.d文档選择窗 = new javafx.stage.FileChooser();
            //建立过滤
            ji自用.i_JSONArray = org.hzs.json.JSONArray.d副本();
            ji自用.i_JSONArray.set(ci过滤扩展名_ArrayJSONs, ji_error);
            ji自用.i扩展档列表 = new java.util.ArrayList<String>();
            int ji1_i = ji自用.i_JSONArray.size();
            if (ji1_i > 0) {
                for (int ji2_i = 0; ji2_i < ji1_i; ji2_i++) {
                    ji自用.i扩展档列表.add("*." + ji自用.i_JSONArray.getString(ji2_i));
                }
                ji自用.d扩展档过滤 = new javafx.stage.FileChooser.ExtensionFilter(ci提示_s, ji自用.i扩展档列表);
                ji自用.d文档選择窗.getExtensionFilters().add(ji自用.d扩展档过滤);
            }
            ji自用.i文档 = ji自用.d文档選择窗.showSaveDialog(null); //以完全模式显示文档选择窗
            //labelFile.setText(file.getPath()); 
            if (ji自用.i文档 == null) {
                return "";
            }
            return ji自用.i文档.getPath().replaceAll("\\\\", "/");
        } catch (org.hzs.logging.error | CloneNotSupportedException ex) {
            Logger.getLogger(applet.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        } finally {
            if (ji自用 != null) {
                ji自用.close();
                ji自用 = null;
            }
            ji_error = null;
        }
    }
}
