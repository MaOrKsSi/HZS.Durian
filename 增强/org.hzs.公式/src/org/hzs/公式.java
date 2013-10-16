/**
 * 用于对表达式的转换以及计算
 * <BR>支持“幂、乘、除、加、减、馀”六种运算
 */
package org.hzs;

/**
 *
 * @author 韩占山
 */
public final class 公式 {

    /**
     * 将文本型中序表达式转换成文本型数组型後序表达式
     *
     * @param ci中序表达式_s 中序表达式
     * @param ci_error 错误容器，用于记录发生的错误；程序发生错误时，将抛出此容器
     * @return 文本型数组型後序表达式，可替换其中的变量，进行运算
     */
    public static String[] g中序表达式to後序表达式(final String ci中序表达式_s, final org.hzs.logging.error ci_error) throws org.hzs.logging.error {
        //<editor-fold defaultstate="collapsed" desc="记录过程">
        ci_error.g添加过程信息("{类:\"org.公式\","
                + "过程:\"g中序表达式to後序表达式(final String ci中序表达式_s, final org.logging.error ci_error)\"}");
        // </editor-fold>
        String[] ji中序表达式_sArray = null, ji栈_sArray = new String[0], ji後序表达式_sArray = new String[0];
        String ji符号_s = null;
        int ji中序表达式下标_i = -1, ji栈下标_i = -1, ji後序表达式下标_i = -1;
        boolean ji未捕获错误_b = true;
        try {
            ji中序表达式_sArray = ci中序表达式_s.split(" ");
            switch (ji中序表达式_sArray[0]) {
                case "+":
                case "-":
                case "*":
                case "/":
                case "^":
                case ")":
                    ji未捕获错误_b = false;
                    // <editor-fold defaultstate="collapsed" desc="抛出错误">
                    ci_error.g添加错误信息("{类:\"org.公式\","
                            + "过程:\"g中序表达式to後序表达式(final String ci中序表达式_s, final org.logging.error ci_error)\","
                            + "序号:\"1\","
                            + "错误类型:\"\","
                            + "错误:\"起始符号错误\"}");
                    throw ci_error;
                // </editor-fold>
            }
            switch (ji中序表达式_sArray[ji中序表达式_sArray.length - 1]) {
                case "+":
                case "-":
                case "*":
                case "/":
                case "^":
                case "(":
                    ji未捕获错误_b = false;
                    // <editor-fold defaultstate="collapsed" desc="抛出错误">
                    ci_error.g添加错误信息("{类:\"org.公式\","
                            + "过程:\"g中序表达式to後序表达式(final String ci中序表达式_s, final org.logging.error ci_error)\","
                            + "序号:\"1\","
                            + "错误类型:\"\","
                            + "错误:\"末尾符号错误\"}");
                    throw ci_error;
                // </editor-fold>
            }
            for (ji中序表达式下标_i = 0; ji中序表达式下标_i < ji中序表达式_sArray.length; ji中序表达式下标_i++) {
                switch (ji中序表达式_sArray[ji中序表达式下标_i]) {
                    case "(":
                        if (ji符号_s != null && ji符号_s.equals(")")) {
                            ji未捕获错误_b = false;
                            // <editor-fold defaultstate="collapsed" desc="抛出错误">
                            ci_error.g添加错误信息("{类:\"org.公式\","
                                    + "过程:\"g中序表达式to後序表达式(final String ci中序表达式_s, final org.logging.error ci_error)\","
                                    + "序号:\"1\","
                                    + "错误类型:\"\","
                                    + "错误:\"开括号前不可为闭括号\"}");
                            throw ci_error;
                            // </editor-fold>
                        }
                        ji栈下标_i++;
                        if (ji栈_sArray.length == ji栈下标_i) {
                            ji栈_sArray = java.util.Arrays.copyOf(ji栈_sArray, ji栈下标_i + 1);
                        }
                        ji栈_sArray[ji栈下标_i] = ji中序表达式_sArray[ji中序表达式下标_i];
                        ji符号_s = "(";
                        break;
                    case ")":
                        if (ji符号_s != null) {
                            switch (ji符号_s) {
                                case "+":
                                case "-":
                                case "*":
                                case "/":
                                case "^":
                                case "(":
                                    ji未捕获错误_b = false;
                                    // <editor-fold defaultstate="collapsed" desc="抛出错误">
                                    ci_error.g添加错误信息("{类:\"org.公式\","
                                            + "过程:\"g中序表达式to後序表达式(final String ci中序表达式_s, final org.logging.error ci_error)\","
                                            + "序号:\"1\","
                                            + "错误类型:\"\","
                                            + "错误:\"符号相邻错误\"}");
                                    throw ci_error;
                                // </editor-fold>
                            }
                        }
                        while (ji栈下标_i >= 0 && !ji栈_sArray[ji栈下标_i].equals("(")) {
                            ji後序表达式下标_i++;
                            if (ji後序表达式_sArray.length == ji後序表达式下标_i) {
                                ji後序表达式_sArray = java.util.Arrays.copyOf(ji後序表达式_sArray, ji後序表达式下标_i + 1);
                            }
                            ji後序表达式_sArray[ji後序表达式下标_i] = ji栈_sArray[ji栈下标_i];
                            ji栈下标_i--;
                        }
                        if (ji栈下标_i >= 0) {
                            ji栈下标_i--;
                        } else {
                            //- + * /     (
                            ji未捕获错误_b = false;
                            // <editor-fold defaultstate="collapsed" desc="抛出错误">
                            ci_error.g添加错误信息("{类:\"org.公式\","
                                    + "过程:\"g中序表达式to後序表达式(final String ci中序表达式_s, final org.logging.error ci_error)\","
                                    + "序号:\"1\","
                                    + "错误类型:\"\","
                                    + "错误:\"闭括号不匹配\"}");
                            throw ci_error;
                            // </editor-fold>
                        }
                        ji符号_s = ")";
                        break;
                    case "^":
                        if (ji符号_s != null) {
                            switch (ji符号_s) {
                                case "+":
                                case "-":
                                case "*":
                                case "/":
                                case "^":
                                case "(":
                                    ji未捕获错误_b = false;
                                    // <editor-fold defaultstate="collapsed" desc="抛出错误">
                                    ci_error.g添加错误信息("{类:\"org.公式\","
                                            + "过程:\"g中序表达式to後序表达式(final String ci中序表达式_s, final org.logging.error ci_error)\","
                                            + "序号:\"1\","
                                            + "错误类型:\"\","
                                            + "错误:\"符号相邻错误\"}");
                                    throw ci_error;
                                // </editor-fold>
                            }
                        }
                        while (ji栈下标_i >= 0
                                && !ji栈_sArray[ji栈下标_i].equals("*")
                                && !ji栈_sArray[ji栈下标_i].equals("/")
                                && !ji栈_sArray[ji栈下标_i].equals("+")
                                && !ji栈_sArray[ji栈下标_i].equals("-")
                                && !ji栈_sArray[ji栈下标_i].equals("(")) {
                            ji後序表达式下标_i++;
                            if (ji後序表达式_sArray.length == ji後序表达式下标_i) {
                                ji後序表达式_sArray = java.util.Arrays.copyOf(ji後序表达式_sArray, ji後序表达式下标_i + 1);
                            }
                            ji後序表达式_sArray[ji後序表达式下标_i] = ji栈_sArray[ji栈下标_i];
                            ji栈下标_i--;
                        }
                        ji栈下标_i++;
                        if (ji栈_sArray.length == ji栈下标_i) {
                            ji栈_sArray = java.util.Arrays.copyOf(ji栈_sArray, ji栈下标_i + 1);
                        }
                        ji栈_sArray[ji栈下标_i] = ji中序表达式_sArray[ji中序表达式下标_i];
                        ji符号_s = "^";
                        break;
                    case "*":
                    case "/":
                        if (ji符号_s != null) {
                            switch (ji符号_s) {
                                case "+":
                                case "-":
                                case "*":
                                case "/":
                                case "^":
                                case "(":
                                    ji未捕获错误_b = false;
                                    // <editor-fold defaultstate="collapsed" desc="抛出错误">
                                    ci_error.g添加错误信息("{类:\"org.公式\","
                                            + "过程:\"g中序表达式to後序表达式(final String ci中序表达式_s, final org.logging.error ci_error)\","
                                            + "序号:\"1\","
                                            + "错误类型:\"\","
                                            + "错误:\"符号相邻错误\"}");
                                    throw ci_error;
                                // </editor-fold>
                            }
                        }
                        while (ji栈下标_i >= 0
                                && !ji栈_sArray[ji栈下标_i].equals("+")
                                && !ji栈_sArray[ji栈下标_i].equals("-")
                                && !ji栈_sArray[ji栈下标_i].equals("(")) {
                            ji後序表达式下标_i++;
                            if (ji後序表达式_sArray.length == ji後序表达式下标_i) {
                                ji後序表达式_sArray = java.util.Arrays.copyOf(ji後序表达式_sArray, ji後序表达式下标_i + 1);
                            }
                            ji後序表达式_sArray[ji後序表达式下标_i] = ji栈_sArray[ji栈下标_i];
                            ji栈下标_i--;
                        }
                        ji栈下标_i++;
                        if (ji栈_sArray.length == ji栈下标_i) {
                            ji栈_sArray = java.util.Arrays.copyOf(ji栈_sArray, ji栈下标_i + 1);
                        }
                        ji栈_sArray[ji栈下标_i] = ji中序表达式_sArray[ji中序表达式下标_i];
                        ji符号_s = ji中序表达式_sArray[ji中序表达式下标_i];
                        break;
                    case "+":
                    case "-":
                        if (ji符号_s != null) {
                            switch (ji符号_s) {
                                case "+":
                                case "-":
                                case "*":
                                case "/":
                                case "^":
                                case "(":
                                    ji未捕获错误_b = false;
                                    // <editor-fold defaultstate="collapsed" desc="抛出错误">
                                    ci_error.g添加错误信息("{类:\"org.公式\","
                                            + "过程:\"g中序表达式to後序表达式(final String ci中序表达式_s, final org.logging.error ci_error)\","
                                            + "序号:\"1\","
                                            + "错误类型:\"\","
                                            + "错误:\"符号相邻错误\"}");
                                    throw ci_error;
                                // </editor-fold>
                            }
                        }
                        while (ji栈下标_i >= 0 && !ji栈_sArray[ji栈下标_i].equals("(")) {
                            ji後序表达式下标_i++;
                            if (ji後序表达式_sArray.length == ji後序表达式下标_i) {
                                ji後序表达式_sArray = java.util.Arrays.copyOf(ji後序表达式_sArray, ji後序表达式下标_i + 1);
                            }
                            ji後序表达式_sArray[ji後序表达式下标_i] = ji栈_sArray[ji栈下标_i];
                            ji栈下标_i--;
                        }
                        ji栈下标_i++;
                        if (ji栈_sArray.length == ji栈下标_i) {
                            ji栈_sArray = java.util.Arrays.copyOf(ji栈_sArray, ji栈下标_i + 1);
                        }
                        ji栈_sArray[ji栈下标_i] = ji中序表达式_sArray[ji中序表达式下标_i];
                        ji符号_s = ji中序表达式_sArray[ji中序表达式下标_i];
                        break;
                    default:
                        if (ji符号_s != null) {
                            switch (ji符号_s) {
                                case ")":
                                    ji未捕获错误_b = false;
                                    // <editor-fold defaultstate="collapsed" desc="抛出错误">
                                    ci_error.g添加错误信息("{类:\"org.公式\","
                                            + "过程:\"g中序表达式to後序表达式(final String ci中序表达式_s, final org.logging.error ci_error)\","
                                            + "序号:\"1\","
                                            + "错误类型:\"\","
                                            + "错误:\"操作数前不可为闭括号\"}");
                                    throw ci_error;
                                // </editor-fold>
                            }
                        }
                        if (ji栈下标_i == -1) {
                            ji後序表达式下标_i++;
                            if (ji後序表达式_sArray.length == ji後序表达式下标_i) {
                                ji後序表达式_sArray = java.util.Arrays.copyOf(ji後序表达式_sArray, ji後序表达式下标_i + 1);
                            }
                            ji後序表达式_sArray[ji後序表达式下标_i] = ji中序表达式_sArray[ji中序表达式下标_i];
                        } else {
                            ji栈下标_i++;
                            if (ji栈_sArray.length == ji栈下标_i) {
                                ji栈_sArray = java.util.Arrays.copyOf(ji栈_sArray, ji栈下标_i + 1);
                            }
                            ji栈_sArray[ji栈下标_i] = ji中序表达式_sArray[ji中序表达式下标_i];
                        }
                        ji符号_s = null;
                }
            }
            while (ji栈下标_i >= 0) {
                if (ji栈_sArray[ji栈下标_i].equals("(")) {
                    ji未捕获错误_b = false;
                    // <editor-fold defaultstate="collapsed" desc="抛出错误">
                    ci_error.g添加错误信息("{类:\"org.公式\","
                            + "过程:\"g中序表达式to後序表达式(final String ci中序表达式_s, final org.logging.error ci_error)\","
                            + "序号:\"2\","
                            + "错误类型:\"\","
                            + "错误:\"开括号不匹配\"}");
                    throw ci_error;
                    // </editor-fold>
                }
                ji後序表达式下标_i++;
                if (ji後序表达式_sArray.length == ji後序表达式下标_i) {
                    ji後序表达式_sArray = java.util.Arrays.copyOf(ji後序表达式_sArray, ji後序表达式下标_i + 1);
                }
                ji後序表达式_sArray[ji後序表达式下标_i] = ji栈_sArray[ji栈下标_i];
                ji栈下标_i--;
            }
            ji未捕获错误_b = false;
            return ji後序表达式_sArray;
        } finally {
            // <editor-fold defaultstate="collapsed" desc="释放资源">
            if (ji中序表达式_sArray != null) {
                for (int ji_i = 0; ji_i < ji中序表达式_sArray.length; ji_i++) {
                    ji中序表达式_sArray[ji_i] = null;
                }
                ji中序表达式_sArray = null;
            }
            for (int ji_i = 0; ji_i < ji栈_sArray.length; ji_i++) {
                ji栈_sArray[ji_i] = null;
            }
            ji栈_sArray = null;
            ji符号_s = null;
            // </editor-fold>
            if (ji未捕获错误_b) {
                // <editor-fold defaultstate="collapsed" desc="抛出错误">
                ci_error.g添加错误信息("{类:\"org.公式\","
                        + "过程:\"g中序表达式to後序表达式(final String ci中序表达式_s, final org.logging.error ci_error)\","
                        + "序号:\"\","
                        + "错误类型:\"未捕获\","
                        + "错误:\"\"}");
                throw ci_error;
                // </editor-fold>
            }
        }
    }

    /**
     * 计算後序表达式
     *
     * @param ci_sArray 文本型数组型後序表达式
     * @param ci_error 错误容器，用于记录发生的错误；程序发生错误时，将抛出此容器
     * @return java.math.BigDecimal型计算结果
     */
    public static java.math.BigDecimal g计算後序表达式(final String[] ci_sArray, final org.hzs.logging.error ci_error) throws org.hzs.logging.error {
        //<editor-fold defaultstate="collapsed" desc="记录过程">
        ci_error.g添加过程信息("{类:\"org.公式\","
                + "过程:\"g计算後序表达式(final String[] ci_sArray, final org.logging.error ci_error)\"}");
        // </editor-fold>
        java.math.BigDecimal ji结果_BD = null;
        java.math.BigDecimal[] ji结果_BDArray = null;
        int ji结果下标_i = -1;
        boolean ji未捕获错误_b = true;
        try {
            ji结果_BDArray = new java.math.BigDecimal[0];
            for (int ji_i = 0; ji_i < ci_sArray.length; ji_i++) {
                switch (ci_sArray[ji_i].substring(0, 1)) {
                    case ".":
                    case "0":
                    case "1":
                    case "2":
                    case "3":
                    case "4":
                    case "5":
                    case "6":
                    case "7":
                    case "8":
                    case "9":
                        ji结果下标_i++;
                        if (ji结果下标_i == ji结果_BDArray.length) {
                            ji结果_BDArray = java.util.Arrays.copyOf(ji结果_BDArray, ji结果下标_i + 1);
                        }
                        ji结果_BDArray[ji结果下标_i] = new java.math.BigDecimal(ci_sArray[ji_i]);
                        break;
                    default:
                        ji结果下标_i--;
                        switch (ci_sArray[ji_i]) {
                            case "+":
                                ji结果_BDArray[ji结果下标_i] = ji结果_BDArray[ji结果下标_i].add(ji结果_BDArray[ji结果下标_i + 1]);
                                break;
                            case "-":
                                ji结果_BDArray[ji结果下标_i] = ji结果_BDArray[ji结果下标_i].subtract(ji结果_BDArray[ji结果下标_i + 1]);
                                break;
                            case "*":
                                ji结果_BDArray[ji结果下标_i] = ji结果_BDArray[ji结果下标_i].multiply(ji结果_BDArray[ji结果下标_i + 1]);
                                break;
                            case "/":
                                ji结果_BDArray[ji结果下标_i] = ji结果_BDArray[ji结果下标_i].divide(ji结果_BDArray[ji结果下标_i + 1]);
                                break;
                            case "^":
                                ji结果_BDArray[ji结果下标_i] = ji结果_BDArray[ji结果下标_i].pow(ji结果_BDArray[ji结果下标_i + 1].intValue());
                                break;
                            case "%":
                                ji结果_BDArray[ji结果下标_i] = ji结果_BDArray[ji结果下标_i].remainder(ji结果_BDArray[ji结果下标_i + 1]);
                                break;
                        }
                }
            }
            ji结果_BD = ji结果_BDArray[0];
            //递出结果
            ji未捕获错误_b = false;
            return ji结果_BD;
        } finally {
            //释放资源
            if (ji结果_BDArray != null) {
                for (int ji_i = 0; ji_i < ji结果_BDArray.length; ji_i++) {
                    ji结果_BDArray[ji_i] = null;
                }
                ji结果_BDArray = null;
            }
            if (ji未捕获错误_b) {
                // <editor-fold defaultstate="collapsed" desc="抛出错误">
                ci_error.g添加错误信息("{类:\"org.公式\","
                        + "过程:\"g计算後序表达式(final String[] ci_sArray, final org.logging.error ci_error)\","
                        + "序号:\"\","
                        + "错误类型:\"未捕获\","
                        + "错误:\"\"}");
                throw ci_error;
                // </editor-fold>
            }
        }
    }
}
