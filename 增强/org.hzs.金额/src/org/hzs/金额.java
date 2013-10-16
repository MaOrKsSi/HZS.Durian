package org.hzs;

/**
 * @author 韩占山
 * @version 1.0
 */
public final class 金额 {

    public static final int 大写 = 0, 小写 = 1;
    private static String i金额_s = null;
    private static String[][] i數字_s = {{"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"}, {"〇", "一", "二", "三", "四", "五", "六", "七", "八", "九"}};
    private static String[][] i第一层_s = {{"", "拾", "佰", "仟"}, {"", "十", "百", "千"}};
    private static String[] i第二层_s = {"元", "万", "亿", "兆", "京", "垓", "秭", "穣", "沟", "涧", "正", "载", "极"};

    public 金额(final Object ci金额, final org.hzs.logging.error ci_error) throws org.hzs.logging.error {
        // <editor-fold defaultstate="collapsed" desc="记录过程">
        ci_error.g添加过程信息("{类:\"org.金额\","
                + "过程:\"金额(final Object ci金额, final org.logging.error ci_error)\"}");
        // </editor-fold>
        try {
            set(ci金额, ci_error);
        } catch (org.hzs.logging.error ex) {
            // <editor-fold defaultstate="collapsed" desc="抛出错误">
            ex.g添加错误信息("{类:\"org.金额\","
                    + "过程:\"金额(final Object ci金额, final org.logging.error ci_error)\"}"
                    + "位置:\"24\""
                    + "错误类型:\"捕获\","
                    + "错误:\"\"}");
            throw ex;
            // </editor-fold>
        }
    }

    public void set(final Object ci金额, final org.hzs.logging.error ci_error) throws org.hzs.logging.error {
        // <editor-fold defaultstate="collapsed" desc="记录过程">
        ci_error.g添加过程信息("{类:\"org.金额\","
                + "过程:\"set(final Object ci金额, final org.logging.error ci_error)\"}");
        // </editor-fold>
        java.util.regex.Pattern p = null;
        java.util.regex.Matcher m = null;
        StringBuilder ji_S = null;
        Boolean ji错误被捕获 = null;
        try {
            if (ci金额 == null || ci金额.toString().length() == 0) {
                i金额_s = "0";
                return;
            }
            ji_S = new StringBuilder(ci金额.toString());
            g整理(ji_S);
            //利用正则表达式斠验
            p = java.util.regex.Pattern.compile("-?\\d*(\\.\\d*)?");
            m = p.matcher(ji_S);
            if (!m.matches()) {
                // <editor-fold defaultstate="collapsed" desc="抛出错误">
                ci_error.g添加错误信息("{类:\"org.金额\","
                        + "过程:\"set(final Object ci金额, final org.logging.error ci_error)\"}"
                        + "位置:\"55\""
                        + "错误类型:\"自发\","
                        + "错误:\"\"}");
                throw ci_error;
                // </editor-fold>
            }
            i金额_s = ji_S.toString();
            ji错误被捕获 = false;
        } finally {
            p = null;
            m = null;
            ji_S = null;
            if (ji错误被捕获 == null) {
                // <editor-fold defaultstate="collapsed" desc="抛出错误">
                ci_error.g添加错误信息("{类:\"org.金额\","
                        + "过程:\"set(final Object ci金额, final org.logging.error ci_error)\","
                        + "位置:\"71\""
                        + "错误类型:\"未捕获\","
                        + "错误:\"\"}");
                throw ci_error;
                // </editor-fold>
            }
        }
    }

    /**
     * 文本串可包含“0123456789￥，、,。.”等，“￥”必需在开头，“。.”必需在最后 <BR>主调方法,传入金额字符串返回大写金额 参数:String num 字符型金额，如 ‘￥100,200.01’ <BR>注意：1、可以加‘￥’和‘,’分割金额,其他符号不识别。 <BR>
     * 2、金额需在万亿以下,即整数部分只识别13位有效数字。 3、小数只保留2位即‘角’和‘分’，非四舍五入，小于分的单位舍掉。 例子： 传入:￥100,200.01 返回:￥壹拾万零贰佰元壹分
     *
     * @param ci大小写状态_i 大小写状态
     * @return 中文对数的表述
     */
    public String i中文_s(final int ci大小写状态_i) {
        int ji_i, ji第一层_i, ji第二层_i;
        boolean ji负数_B = false;
        StringBuilder ji_S = null, ji1_S = null;
        try {
            if (i金额_s.equals("0") || i金额_s.equals("")) {
                switch (ci大小写状态_i) {
                    case 大写:
                        return "零元整";
                    case 小写:
                        return "〇元整";
                }
            }
            ji_S = new StringBuilder(i金额_s);
            if (ji_S.substring(0, 1).equals("-")) {
                ji负数_B = true;
                ji_S.delete(0, 1);
            }
            g去掉首部〇(ji_S);
            //生成只有小数点以及数字的文本
            i金额_s = ji_S.toString();
            //生成整数部份
            if (ji_S.indexOf(".") > -1) {
                ji_S.delete(ji_S.indexOf("."), ji_S.length());
            }
            ji_S.reverse();
            ji第二层_i = 0;
            ji第一层_i = 0;
            ji1_S = new StringBuilder();
            while (ji_S.length() > 0) {
                if (ji第一层_i == 0) {
                    ji1_S.append(i第二层_s[ji第二层_i]);
                }
                ji1_S.append(i第一层_s[ci大小写状态_i][ji第一层_i]);
                ji1_S.append(i數字_s[ci大小写状态_i][Integer.valueOf(ji_S.substring(0, 1))]);
                ji_S.delete(0, 1);
                if (ji第一层_i == 3) {
                    ji第一层_i = 0;
                    ji第二层_i++;
                } else {
                    ji第一层_i++;
                }
            }
            if (ji负数_B) {
                ji1_S.append("负");
            }
            ji1_S.reverse();
            if (i金额_s.contains(".")) {
                ji_S.append(i金额_s.substring(i金额_s.indexOf(".") + 1, i金额_s.length()));
                ji1_S.append(i數字_s[ci大小写状态_i][Integer.valueOf(ji_S.substring(0, 1))]);
                ji1_S.append('角');
                if (ji_S.length() > 1) {
                    ji1_S.append(i數字_s[ci大小写状态_i][Integer.valueOf(ji_S.substring(1, 2))]);
                    ji1_S.append('分');
                }
            }
            do {
                ji_i = ji1_S.length();
                g清理(ji1_S, ci大小写状态_i);
            } while (ji_i > ji1_S.length());
            if (ji1_S.substring(ji_i - 1, ji_i).equals(i第二层_s[0])) {
                ji1_S.append("整");
            }
            return ji1_S.toString();
        } finally {
            i金额_s = null;
            ji_S = null;
            ji1_S = null;
        }
    }

    public String i英文_s(final Object ci金额, final int ci大小写状态_i, final org.hzs.logging.error ci_error) throws org.hzs.logging.error {
        return null;
    }

    private void g整理(final StringBuilder ci_S) {
        int ji_i;
        //将“。”换成“.”
        ji_i = ci_S.indexOf("。");
        while (ji_i > -1) {
            ci_S.replace(ji_i, ji_i + 1, ".");
            ji_i = ci_S.indexOf("。");
        }
        //去掉尾部“.”
        ji_i = ci_S.length();
        if (ci_S.substring(ji_i - 1, ji_i).equals(".")) {
            ci_S.delete(ji_i - 1, ji_i);
        }
        //去掉“，”
        ji_i = ci_S.indexOf("、");
        while (ji_i > -1) {
            ci_S.delete(ji_i, ji_i + 1);
            ji_i = ci_S.indexOf("，");
        }
        //去掉“￥”
        if (ci_S.indexOf("￥") == 0) {
            ci_S.delete(0, 1);
        }
        //去掉“,”
        ji_i = ci_S.indexOf(",");
        while (ji_i > -1) {
            ci_S.delete(ji_i, ji_i + 1);
            ji_i = ci_S.indexOf(",");
        }
        //去掉“ ”空格
        ji_i = ci_S.indexOf(" ");
        while (ji_i > -1) {
            ci_S.delete(ji_i, ji_i + 1);
            ji_i = ci_S.indexOf(" ");
        }
        g去掉首部〇(ci_S);
    }

    private void g去掉首部〇(final StringBuilder ci_S) {
        while (ci_S.indexOf("0") == 0) {
            ci_S.delete(0, 1);
        }
    }

    private void g清理(final StringBuilder ci_S, final int ci大小写状态_i) {
        int ji2_i, ji_i, ji1_i, ji3_i;
        StringBuilder ji_S = null;
        String ji_s = null;
        try {
            ji_S = new StringBuilder("11");
            switch (ci大小写状态_i) {
                case 大写:
                    g清理_大写(ci_S);
                    break;
                case 小写:
                    g清理_小写(ci_S);
                    break;
            }
            do {//替换第二层交叉现象
                ji3_i = ci_S.length();
                for (ji1_i = i第二层_s.length - 1; ji1_i > 1; ji1_i--) {
                    ji_S.replace(0, 1, i第二层_s[ji1_i]);
                    for (ji2_i = ji1_i - 1; ji2_i > 0; ji2_i--) {
                        ji_S.replace(1, 2, i第二层_s[ji2_i]);
                        ji_s = ji_S.toString();
                        ji_i = ci_S.indexOf(ji_s);
                        if (ji_i > -1) {
                            ci_S.replace(ji2_i, ji2_i + 2, i第二层_s[ji1_i]);
                            break;
                        }
                        ji_s = null;
                    }
                }
            } while (ji3_i > ci_S.length());
        } finally {
            ji_S = null;
            ji_s = null;
        }
    }

    private void g清理_大写(final StringBuilder ci_S) {
        int ji_i, ji1_i;
        StringBuilder ji_S = null;
        String ji_s = null;
        try {
            ji_S = new StringBuilder(2);
            ji_S.append("零");
            //替换第一层
            for (ji1_i = 1; ji1_i < i第一层_s[0].length; ji1_i++) {
                ji_S.replace(1, 2, i第一层_s[0][ji1_i]);
                ji_s = ji_S.toString();
                ji_i = ci_S.indexOf(ji_s);
                while (ji_i > -1) {
                    ci_S.replace(ji_i, ji_i + 2, "零");
                    ji_i = ci_S.indexOf(ji_s);
                }
                ji_s = null;
            }
            do {//将“零零”换成“零”
                ji1_i = ci_S.length();
                ji_i = ci_S.indexOf("零零");
                while (ji_i > -1) {
                    ci_S.replace(ji_i, ji_i + 2, "零");
                    ji_i = ci_S.indexOf("零零");
                }
            } while (ji1_i > ci_S.length());
            //替换第二层
            for (ji1_i = 0; ji1_i < i第二层_s.length; ji1_i++) {
                ji_S.replace(1, 2, i第二层_s[ji1_i]);
                ji_s = ji_S.toString();
                ji_i = ci_S.indexOf(ji_s);
                if (ji_i > -1) {
                    ci_S.replace(ji_i, ji_i + 2, i第二层_s[ji1_i]);
                }
                ji_s = null;
            }
            //替换“角、分”
            ji_i = ci_S.indexOf("零角");
            if (ji_i > -1) {
                ci_S.replace(ji_i, ji_i + 2, "");
            }
            ji_i = ci_S.indexOf("零分");
            if (ji_i > -1) {
                ci_S.replace(ji_i, ji_i + 2, "");
            }
        } finally {
            ji_S = null;
            ji_s = null;
        }
    }

    private void g清理_小写(final StringBuilder ci_S) {
        int ji_i, ji1_i;
        StringBuilder ji_S = null;
        String ji_s = null;
        try {
            ji_S = new StringBuilder(2);
            ji_S.append("〇");
            //替换第一层
            for (ji1_i = 1; ji1_i < i第一层_s[1].length; ji1_i++) {
                ji_S.replace(1, 2, i第一层_s[1][ji1_i]);
                ji_s = ji_S.toString();
                ji_i = ci_S.indexOf(ji_s);
                while (ji_i > -1) {
                    ci_S.replace(ji_i, ji_i + 2, "〇");
                    ji_i = ci_S.indexOf(ji_s);
                }
                ji_s = null;
            }
            do {//将“〇〇”换成“〇”
                ji1_i = ci_S.length();
                ji_i = ci_S.indexOf("〇〇");
                while (ji_i > -1) {
                    ci_S.replace(ji_i, ji_i + 2, "〇");
                    ji_i = ci_S.indexOf("〇〇");
                }
            } while (ji1_i > ci_S.length());
            //替换第二层
            for (ji1_i = 0; ji1_i < i第二层_s.length; ji1_i++) {
                ji_S.replace(1, 2, i第二层_s[ji1_i]);
                ji_s = ji_S.toString();
                ji_i = ci_S.indexOf(ji_s);
                if (ji_i > -1) {
                    ci_S.replace(ji_i, ji_i + 2, i第二层_s[ji1_i]);
                }
                ji_s = null;
            }
            //替换“角、分”
            ji_i = ci_S.indexOf("〇角");
            if (ji_i > -1) {
                ci_S.replace(ji_i, ji_i + 2, "");
            }
            ji_i = ci_S.indexOf("〇分");
            if (ji_i > -1) {
                ci_S.replace(ji_i, ji_i + 2, "");
            }
        } finally {
            ji_S = null;
            ji_s = null;
        }
    }
}
