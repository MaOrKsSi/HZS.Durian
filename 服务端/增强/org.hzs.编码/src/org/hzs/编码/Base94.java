package org.hzs.编码;

public class Base94 {

   private static final byte[] i码表_byteArray = {
      (byte) '!', (byte) '"', (byte) '#', (byte) '$', (byte) '%', (byte) '&', (byte) '\'', (byte) '(', (byte) ')', (byte) '*', (byte) '+', (byte) ',', (byte) '-',
      (byte) '.', (byte) '/', (byte) '0', (byte) '1', (byte) '2', (byte) '3', (byte) '4', (byte) '5', (byte) '6', (byte) '7', (byte) '8', (byte) '9', (byte) ':',
      (byte) ';', (byte) '<', (byte) '=', (byte) '>', (byte) '?', (byte) '@', (byte) 'A', (byte) 'B', (byte) 'C', (byte) 'D', (byte) 'E', (byte) 'F', (byte) 'G',
      (byte) 'H', (byte) 'I', (byte) 'J', (byte) 'K', (byte) 'L', (byte) 'M', (byte) 'N', (byte) 'O', (byte) 'P', (byte) 'Q', (byte) 'R', (byte) 'S', (byte) 'T',
      (byte) 'U', (byte) 'V', (byte) 'W', (byte) 'X', (byte) 'Y', (byte) 'Z', (byte) '[', (byte) '\\', (byte) ']', (byte) '^', (byte) '_', (byte) '`', (byte) 'a',
      (byte) 'b', (byte) 'c', (byte) 'd', (byte) 'e', (byte) 'f', (byte) 'g', (byte) 'h', (byte) 'i', (byte) 'j', (byte) 'k', (byte) 'l', (byte) 'm', (byte) 'n',
      (byte) 'o', (byte) 'p', (byte) 'q', (byte) 'r', (byte) 's', (byte) 't', (byte) 'u', (byte) 'v', (byte) 'w', (byte) 'x', (byte) 'y', (byte) 'z', (byte) '{',
      (byte) '|', (byte) '}', (byte) '~'
   };

   public static byte[] i编码_byteArray(final byte[] ci待编码_BASE64byteArray) {
      byte[] ji待编码_BASE64byteArray = null;
      java.math.BigInteger ji待编码_BD = null, ji待编码进制_BD = null, ji码表进制_BD = null, ji临时_BD = null;
      byte[] ji_byteArray = null;
      try {
         ji待编码_BASE64byteArray = ci待编码_BASE64byteArray.clone();
         ji待编码_BD = java.math.BigInteger.ONE;
         ji待编码进制_BD = new java.math.BigInteger("64");
         for (int ji_i = 0; ji_i < ji待编码_BASE64byteArray.length; ji_i++) {
            ji待编码_BASE64byteArray[ji_i] = (byte) (ji待编码_BASE64byteArray[ji_i] - '0');
            ji待编码_BD = ji待编码_BD.multiply(ji待编码进制_BD).add(new java.math.BigInteger(Byte.toString(ji待编码_BASE64byteArray[ji_i])));
         }
         ji码表进制_BD = new java.math.BigInteger(Integer.toString(i码表_byteArray.length));
         ji_byteArray = new byte[0];
         while (ji待编码_BD.compareTo(java.math.BigInteger.ZERO) > 0) {
            ji临时_BD = ji待编码_BD.remainder(ji码表进制_BD);
            ji待编码_BD = ji待编码_BD.divide(ji码表进制_BD);
            ji_byteArray = java.util.Arrays.copyOf(ji_byteArray, ji_byteArray.length + 1);
            ji_byteArray[ji_byteArray.length - 1] = i码表_byteArray[ji临时_BD.intValue()];
         }
         return ji_byteArray;
      } finally {
         ji待编码_BD = null;
         ji待编码进制_BD = null;
         ji码表进制_BD = null;
         ji临时_BD = null;
         ji_byteArray = null;
      }
   }

   public static byte[] i解码_BASE64byteArray(final byte[] ci待解码_BASE79byteArray) {
      byte[] ji待解码_BASE79byteArray = null;
      java.math.BigInteger ji待解码_BD = null, ji目标码进制_BD = null, ji码表进制_BD = null, ji临时_BD = null;
      byte[] ji_byteArray = null;
      try {
         ji待解码_BASE79byteArray = ci待解码_BASE79byteArray.clone();
         ji待解码_BD = java.math.BigInteger.ONE;
         ji码表进制_BD = new java.math.BigInteger(Integer.toString(i码表_byteArray.length));
         for (int ji2_i, ji1_i = 0; ji1_i < ji待解码_BASE79byteArray.length; ji1_i++) {
            for (ji2_i = 0; ji2_i < i码表_byteArray.length; ji2_i++) {
               if (i码表_byteArray[ji2_i] == ji待解码_BASE79byteArray[ji1_i]) {
                  break;
               }
            }
            ji待解码_BD = ji待解码_BD.multiply(ji码表进制_BD).add(new java.math.BigInteger(Integer.toString(ji2_i)));
         }
         ji目标码进制_BD = new java.math.BigInteger("64");
         ji_byteArray = new byte[0];
         while (ji待解码_BD.compareTo(java.math.BigInteger.ZERO) > 0) {
            ji临时_BD = ji待解码_BD.remainder(ji目标码进制_BD);
            ji待解码_BD = ji待解码_BD.divide(ji目标码进制_BD);
            ji_byteArray = java.util.Arrays.copyOf(ji_byteArray, ji_byteArray.length + 1);
            ji_byteArray[ji_byteArray.length - 1] = i码表_byteArray[ji临时_BD.intValue()];
         }
         return ji_byteArray;
      } finally {
         ji待解码_BD = null;
         ji目标码进制_BD = null;
         ji码表进制_BD = null;
         ji临时_BD = null;
         ji_byteArray = null;
      }
   }
}
