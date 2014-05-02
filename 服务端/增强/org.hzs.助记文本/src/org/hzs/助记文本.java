package org.hzs;

public class 助记文本 {

    private static char[] i特殊字符_cArray = null;
    private static final java.util.LinkedHashMap<Character, Character> i汉字助记符_MAP = new java.util.LinkedHashMap<>();
    private static final String[][] i英文单词_sArray = new String[3111][2];
    private static final char[] alphatable = {'A', 'B', 'C', 'D', 'E', 'F',
        'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S',
        'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    /**
     *
     * 汉字拼音首字母编码表，可以如下方法得到：
     *
     * 字母Z使用了两个标签，这里有２７个值, i, u, v都不做声母, 跟随前面的字母(因为不可以出现，所以可以随便取)
     */
    private static final char[] chartable = {
        '啊', '芭', '擦', '搭', '蛾', '发', '噶', '哈', '哈',
        '击', '喀', '垃', '妈', '拿', '哦', '啪', '期', '然',
        '撒', '塌', '塌', '塌', '挖', '昔', '压', '匝', '座'
    };

    private static final int[] table = new int[27];

    public static void g初始化() {
        if (i汉字助记符_MAP.size() > 0) {
            return;
        }
        i特殊字符_cArray = org.hzs.vu4ji4fu2.特殊字符.i构建特殊字符_cArray();
        g构建助记符();
        g构建英文单词();
    }

    private static void g构建助记符() {
        i汉字助记符_MAP.clear();
        for (int i = 0; i < 27; ++i) {
            table[i] = gbValue(chartable[i]);
        }
        org.hzs.vu4ji4fu2.构建助记符0_00000_02031.g构建助记符(i汉字助记符_MAP);
        org.hzs.vu4ji4fu2.构建助记符1_02032_04063.g构建助记符(i汉字助记符_MAP);
        org.hzs.vu4ji4fu2.构建助记符2_04064_06095.g构建助记符(i汉字助记符_MAP);
        org.hzs.vu4ji4fu2.构建助记符3_06096_08127.g构建助记符(i汉字助记符_MAP);
        org.hzs.vu4ji4fu2.构建助记符4_08128_10159.g构建助记符(i汉字助记符_MAP);
        org.hzs.vu4ji4fu2.构建助记符5_10160_12191.g构建助记符(i汉字助记符_MAP);
        org.hzs.vu4ji4fu2.构建助记符6_12192_14223.g构建助记符(i汉字助记符_MAP);
        org.hzs.vu4ji4fu2.构建助记符7_14224_16255.g构建助记符(i汉字助记符_MAP);
        org.hzs.vu4ji4fu2.构建助记符8_16256_16540.g构建助记符(i汉字助记符_MAP);
    }

    private static void g构建英文单词() {
        org.hzs.vu4ji4fu2.构建英文单词0_00000_03110.g构建英文单词(i英文单词_sArray);
    }

    public static char i助记符_c(char ci字符_c) {
//      if (ci字符_c >= 0xff01) {
//         ci字符_c -= 0xff01 - 0x21;
//      }
        if (ci字符_c >= 'a' && ci字符_c <= 'z') {
            ci字符_c -= 'a' - 'A';
        }
        if (ci字符_c >= '0' && ci字符_c <= '9') {
//         return ci字符_c;
            return ' ';
        }
        if (ci字符_c >= 'A' && ci字符_c <= 'Z') {
            return ci字符_c;
        }
        Character ji_char = i汉字助记符_MAP.get(ci字符_c);
        if (ji_char != null) {
            return ji_char;
        }
        int gb = gbValue(ci字符_c);
        if (gb < table[0]) {
            return ' ';
        }
        for (int i = 0; i < 26; ++i) {
            if (match(i, gb)) {
                if (i >= 26) {
                    return 0;
                } else {
                    return alphatable[i];
                }
            }
        }
        return ' ';
    }

    private static boolean match(int i, int gb) {
        if (gb < table[i]) {
            return false;
        }
        int j = i + 1;
        // 字母Z使用了两个标签
        while (j < 26 && (table[j] == table[i])) {
            ++j;
        }
        if (j == 26) {
            return gb <= table[j];
        } else {
            return gb < table[j];
        }
    }

    private static int gbValue(char ch) {
        String str = new String();
        str += ch;
        try {
            byte[] bytes = str.getBytes("GB2312");
            if (bytes.length < 2) {
                return 0;
            }
            return (bytes[0] << 8 & 0xff00) + (bytes[1] & 0xff);
        } catch (Exception e) {
            return 0;
        }
    }

    public static String i助记文本_s(final String ci_s) {
        if (ci_s == null) {
            return null;
        }
        String ji_s = ci_s.toUpperCase();
        char[] ji_c = ji_s.toCharArray();
        int ji1_i, ji2_i;
        //删除特殊字符
        for (ji1_i = 0; ji1_i < ji_c.length; ji1_i++) {
            for (ji2_i = 0; ji2_i < i特殊字符_cArray.length; ji2_i++) {
                if (ji_c[ji1_i] == i特殊字符_cArray[ji2_i]) {
                    ji_c[ji1_i] = ' ';
                    break;
                }
            }
        }
        ji_s = " " + new String(ji_c) + " ";//在首尾添置空格
        //将英文单词转换成对应的首字母
        for (ji2_i = 0; ji2_i < i英文单词_sArray.length; ji2_i++) {
            ji_s = ji_s.replaceAll(i英文单词_sArray[ji2_i][0], i英文单词_sArray[ji2_i][1]);
        }
        ji_s = ji_s.replaceAll(" ", "");//删除空格
        char ji助记符_c = 0;
        ji_c = ji_s.toCharArray();
        StringBuilder ji助记文本_S = new StringBuilder(ji_c.length);
        //生成助记文本
        for (ji1_i = 0; ji1_i < ji_c.length; ji1_i++) {
            ji助记符_c = i助记符_c(ji_c[ji1_i]);
            if (ji助记符_c != 0) {
                ji助记文本_S.append(ji助记符_c);
            }
        }
        return ji助记文本_S.toString();
    }
}
