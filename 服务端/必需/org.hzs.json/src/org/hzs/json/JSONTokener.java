package org.hzs.json;

public class JSONTokener {

    private int myIndex;

    private String mySource;

    public JSONTokener(String s, final org.hzs.logging.error ci_error) throws org.hzs.logging.error {
        if (s == null) {
            ci_error.g添加错误信息("待解析文本不可为空");
            throw ci_error;
        }
        this.myIndex = 0;
        this.mySource = s;
    }

    public void back() {
        if (this.myIndex > 0) {
            this.myIndex -= 1;
        }
    }

//    private static int dehexchar(char c) {
//        if (c >= '0' && c <= '9') {
//            return c - '0';
//        }
//        if (c >= 'A' && c <= 'F') {
//            return c - ('A' - 10);
//        }
//        if (c >= 'a' && c <= 'f') {
//            return c - ('a' - 10);
//        }
//        return -1;
//    }

    private boolean more() {
        return this.myIndex < this.mySource.length();
    }

    public char next() {
        if (more()) {
            char c = this.mySource.charAt(this.myIndex);
            this.myIndex += 1;
            return c;
        }
        return 0;
    }

//    private char next(char c, final org.hzs.logging.error ci_error) throws org.hzs.logging.error {
//        char n = next();
//        if (n != c) {
//            ci_error.g添加错误信息("Expected '" + c + "' and instead saw '" + n + "'.");
//            throw ci_error;
//        }
//        return n;
//    }

    private String next(int n, final org.hzs.logging.error ci_error) throws org.hzs.logging.error {
        int i = this.myIndex;
        int j = i + n;
        if (j >= this.mySource.length()) {
            ci_error.g添加错误信息("Substring bounds error");
            throw ci_error;
        }
        this.myIndex += n;
        return this.mySource.substring(i, j);
    }

    public char nextClean(final org.hzs.logging.error ci_error) throws org.hzs.logging.error {
        for (;;) {
            char c = next();
            if (c == '/') {
                switch (next()) {
                    case '/':
                        do {
                            c = next();
                        } while (c != '\n' && c != '\r' && c != 0);
                        break;
                    case '*':
                        for (;;) {
                            c = next();
                            if (c == 0) {
                                ci_error.g添加错误信息("Unclosed comment.");
                                throw ci_error;
                            }
                            if (c == '*') {
                                if (next() == '/') {
                                    break;
                                }
                                back();
                            }
                        }
                        break;
                    default:
                        back();
                        return '/';
                }
            } else if (c == '#') {
                do {
                    c = next();
                } while (c != '\n' && c != '\r' && c != 0);
            } else if (c == 0 || c > ' ') {
                return c;
            }
        }
    }

    private String nextString(char quote, final org.hzs.logging.error ci_error) throws org.hzs.logging.error {
        char c;
        StringBuilder sb = new StringBuilder();
        for (;;) {
            c = next();
            switch (c) {
                case 0:
                case '\n':
                case '\r':
                    ci_error.g添加错误信息("Unterminated string");
                    throw ci_error;
                case '\\':
                    c = next();
                    switch (c) {
                        case 'b':
                            sb.append('\b');
                            break;
                        case 't':
                            sb.append('\t');
                            break;
                        case 'n':
                            sb.append('\n');
                            break;
                        case 'f':
                            sb.append('\f');
                            break;
                        case 'r':
                            sb.append('\r');
                            break;
                        case 'u':
                            sb.append((char) Integer.parseInt(next(4, ci_error), 16));
                            break;
                        case 'x':
                            sb.append((char) Integer.parseInt(next(2, ci_error), 16));
                            break;
                        default:
                            sb.append(c);
                    }
                    break;
                default:
                    if (c == quote) {
                        return sb.toString();
                    }
                    sb.append(c);
            }
        }
    }
//
//    private String nextTo(char d) {
//        StringBuilder sb = new StringBuilder();
//        for (;;) {
//            char c = next();
//            if (c == d || c == 0 || c == '\n' || c == '\r') {
//                if (c != 0) {
//                    back();
//                }
//                return sb.toString().trim();
//            }
//            sb.append(c);
//        }
//    }
//
//    private String nextTo(String delimiters) {
//        char c;
//        StringBuilder sb = new StringBuilder();
//        for (;;) {
//            c = next();
//            if (delimiters.indexOf(c) >= 0 || c == 0
//                    || c == '\n' || c == '\r') {
//                if (c != 0) {
//                    back();
//                }
//                return sb.toString().trim();
//            }
//            sb.append(c);
//        }
//    }

    public Object nextValue(final JSONObject ci_, final org.hzs.logging.error ci_error) throws org.hzs.logging.error {
        char c = nextClean(ci_error);
        String s;

        switch (c) {
            case '"':
            case '\'':
                return nextString(c, ci_error);
            case '{':
                back();
                JSONObject d1 = JSONObject.d副本();
                d1.set(this, ci_error);
                return d1;
            case '[':
                back();
                JSONArray d2 = JSONArray.d副本();
                d2.set(this, ci_error);
                return d2;
        }

        /*
         * Handle unquoted text. This could be the values true, false, or null, or it can be a number. An implementation (such as this one) is allowed
         * to also accept non-standard forms.
         *
         * Accumulate characters until we reach the end of the text or a formatting character.
         */
        StringBuilder sb = new StringBuilder();
        char b = c;
        while (c >= ' ' && ",:]}\\\"[{;=#".indexOf(c) < 0) {//去掉/
            sb.append(c);
            c = next();
        }
        back();

        /*
         * If it is true, false, or null, return the proper value.
         */
        s = sb.toString().trim();
        if (s.equals("")) {
            ci_error.g添加错误信息("Missing value.");
            throw ci_error;
        }
        if (s.equalsIgnoreCase("true")) {
            return Boolean.TRUE;
        }
        if (s.equalsIgnoreCase("false")) {
            return Boolean.FALSE;
        }
        if (s.equalsIgnoreCase("null")) {
            return null;
        }

        /*
         * If it might be a number, try converting it. We support the 0- and 0x- conventions. If a number cannot be produced, then the value will just
         * be a string. Note that the 0-, 0x-, plus, and implied string conventions are non-standard. A JSON parser is free to accept non-JSON forms as
         * long as it accepts all correct JSON forms.
         */
        if ((b >= '0' && b <= '9') || b == '.' || b == '-' || b == '+') {
            if (b == '0') {
                if (s.length() > 2 && (s.charAt(1) == 'x' || s.charAt(1) == 'X')) {//16进制
                    try {
                        return new Integer(Integer.parseInt(s.substring(2), 16));
                    } catch (Exception e) {
                        /*
                         * Ignore the error
                         */
                    }
                } else {
                    try {
                        return new Integer(Integer.parseInt(s, 8));
                    } catch (Exception e) {
                        /*
                         * Ignore the error
                         */
                    }
                }
            }
            try {
                return new Integer(s);
            } catch (Exception e) {
                try {
                    return new Long(s);
                } catch (Exception f) {
                    try {
                        return new Double(s);
                    } catch (Exception g) {
                        /*
                         * Ignore the error
                         */
                    }
                }
            }
        }
        if (ci_ != null) {
            //仅支持同级路径
            if (s.startsWith("./")) {
                s = s.substring(2, s.length());
            }
            Object o = ci_.get(s);
            if (o == null) {
                return s;
            } else {
                return o;
            }
        } else {
            return s;
        }
    }

    public char skipTo(char to) {
        char c;
        int index = this.myIndex;
        do {
            c = next();
            if (c == 0) {
                this.myIndex = index;
                return c;
            }
        } while (c != to);
        back();
        return c;
    }

    public void skipPast(String to) {
        this.myIndex = this.mySource.indexOf(to, this.myIndex);
        if (this.myIndex < 0) {
            this.myIndex = this.mySource.length();
        } else {
            this.myIndex += to.length();
        }
    }

    @Override
    public String toString() {
        return " at character " + this.myIndex + " of " + this.mySource;
    }
}
