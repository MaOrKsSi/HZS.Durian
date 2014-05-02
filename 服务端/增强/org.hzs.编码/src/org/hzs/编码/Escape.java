package org.hzs.编码;

public final class Escape {

   public static String g编码_s(String ci明文_s) {
      int i;
      char j;
      StringBuilder ji密文_S = null;
      try {
         ji密文_S = new StringBuilder();
         ji密文_S.ensureCapacity(ci明文_s.length() * 6);
         for (i = 0; i < ci明文_s.length(); i++) {
            j = ci明文_s.charAt(i);
            if (Character.isDigit(j) || Character.isLowerCase(j) || Character.isUpperCase(j)) {
               ji密文_S.append(j);
            } else if (j < 256) {
               ji密文_S.append("%");
               if (j < 16) {
                  ji密文_S.append("0");
               }
               ji密文_S.append(Integer.toString(j, 16));
            } else {
               ji密文_S.append("%u");
               ji密文_S.append(Integer.toString(j, 16));
            }
         }
         return ji密文_S.toString();
      } finally {
         ji密文_S = null;
      }
   }

   public static String g解码_s(String ci密文_s) {
      StringBuilder ji明文_S = null;
      int lastPos = 0, pos = 0;
      char ch;
      try {
         ji明文_S = new StringBuilder();
         ji明文_S.ensureCapacity(ci密文_s.length());
         while (lastPos < ci密文_s.length()) {
            pos = ci密文_s.indexOf("%", lastPos);
            if (pos == lastPos) {
               if (ci密文_s.charAt(pos + 1) == 'u') {
                  ch = (char) Integer.parseInt(ci密文_s.substring(pos + 2, pos + 6), 16);
                  ji明文_S.append(ch);
                  lastPos = pos + 6;
               } else {
                  ch = (char) Integer.parseInt(ci密文_s.substring(pos + 1, pos + 3), 16);
                  ji明文_S.append(ch);
                  lastPos = pos + 3;
               }
            } else {
               if (pos == -1) {
                  ji明文_S.append(ci密文_s.substring(lastPos));
                  lastPos = ci密文_s.length();
               } else {
                  ji明文_S.append(ci密文_s.substring(lastPos, pos));
                  lastPos = pos;
               }
            }
         }
         return ji明文_S.toString();
      } finally {
         ji明文_S = null;
      }
   }
}