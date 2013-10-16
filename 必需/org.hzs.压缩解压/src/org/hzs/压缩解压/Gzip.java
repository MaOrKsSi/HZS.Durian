package org.hzs.压缩解压;

import java.io.IOException;

public final class Gzip {

   /**
    * *
    * 压缩GZip
    *
    * @param ci待压缩_byteArray
    * @return
    */
   public static byte[] i压缩_byteArray(byte[] ci待压缩_byteArray) {
      java.io.ByteArrayOutputStream jd输出流 = null;
      java.util.zip.GZIPOutputStream gzip = null;
      try {
         jd输出流 = new java.io.ByteArrayOutputStream();
         gzip = new java.util.zip.GZIPOutputStream(jd输出流);
         gzip.write(ci待压缩_byteArray);
         gzip.finish();
         return jd输出流.toByteArray();
      } catch (Exception ex) {
         return null;
      } finally {
         if (jd输出流 != null) {
            try {
               jd输出流.close();
            } catch (IOException ex) {
            }
            jd输出流 = null;
         }
         if (gzip != null) {
            try {
               gzip.close();
            } catch (IOException ex) {
            }
            gzip = null;
         }
      }
   }

   /**
    * *
    * 压缩GZip
    *
    * @param cd输入流
    * @return
    */
   public static byte[] i压缩_byteArray(java.io.InputStream cd输入流) {
      java.io.ByteArrayOutputStream jd输出流 = null;
      java.util.zip.GZIPOutputStream gzip = null;
      int ji长度_i;
      byte[] ji缓冲 = null;
      try {
         ji缓冲 = new byte[1024];
         jd输出流 = new java.io.ByteArrayOutputStream();
         gzip = new java.util.zip.GZIPOutputStream(jd输出流);
         while ((ji长度_i = cd输入流.read(ji缓冲)) > 0) {
            gzip.write(ji缓冲, 0, ji长度_i);
         }
         gzip.finish();
         return jd输出流.toByteArray();
      } catch (Exception ex) {
         return null;
      } finally {
         ji缓冲 = null;
         if (jd输出流 != null) {
            try {
               jd输出流.close();
            } catch (IOException ex) {
            }
            jd输出流 = null;
         }
         if (gzip != null) {
            try {
               gzip.close();
            } catch (IOException ex) {
            }
            gzip = null;
         }
      }
   }

   /**
    * *
    * 解压GZip
    *
    * @param ci待解压_byteArray
    * @return
    */
   public static byte[] i解压_byteArray(byte[] ci待解压_byteArray) {
      java.io.ByteArrayInputStream jd输入流 = null;
      java.util.zip.GZIPInputStream gzip = null;
      byte[] ji缓冲_b = null;
      int num = -1;
      java.io.ByteArrayOutputStream jd输出流 = null;
      try {
         ji缓冲_b = new byte[1024];
         jd输入流 = new java.io.ByteArrayInputStream(ci待解压_byteArray);
         gzip = new java.util.zip.GZIPInputStream(jd输入流);
         jd输出流 = new java.io.ByteArrayOutputStream();
         while ((num = gzip.read(ji缓冲_b, 0, ji缓冲_b.length)) != -1) {
            jd输出流.write(ji缓冲_b, 0, num);
         }
         jd输出流.flush();
         return jd输出流.toByteArray();
      } catch (Exception ex) {
         return null;
      } finally {
         if (jd输出流 != null) {
            try {
               jd输出流.close();
            } catch (IOException ex) {
            }
            jd输出流 = null;
         }
         if (gzip != null) {
            try {
               gzip.close();
            } catch (IOException ex) {
            }
            gzip = null;
         }
         if (jd输入流 != null) {
            try {
               jd输入流.close();
            } catch (IOException ex) {
            }
            jd输入流 = null;
         }
         ji缓冲_b = null;
      }
   }

   /**
    * *
    * 解压GZip
    *
    * @param cd输入流
    * @return
    */
   public static byte[] i解压_byteArray(java.io.InputStream cd输入流) {
      java.util.zip.GZIPInputStream gzip = null;
      byte[] ji缓冲_b = null;
      int num = -1;
      java.io.ByteArrayOutputStream jd输出流 = null;
      try {
         ji缓冲_b = new byte[1024];
         gzip = new java.util.zip.GZIPInputStream(cd输入流);
         jd输出流 = new java.io.ByteArrayOutputStream();
         while ((num = gzip.read(ji缓冲_b, 0, ji缓冲_b.length)) != -1) {
            jd输出流.write(ji缓冲_b, 0, num);
         }
         jd输出流.flush();
         return jd输出流.toByteArray();
      } catch (Exception ex) {
         return null;
      } finally {
         if (jd输出流 != null) {
            try {
               jd输出流.close();
            } catch (IOException ex) {
            }
            jd输出流 = null;
         }
         if (gzip != null) {
            try {
               gzip.close();
            } catch (IOException ex) {
            }
            gzip = null;
         }
         ji缓冲_b = null;
      }
   }

   /**
    * *
    * 解压GZip
    *
    * @param cd输入流
    * @return
    */
   public static String i解压_s(java.io.InputStream cd输入流) {
      java.util.zip.GZIPInputStream gzip = null;
      byte[] ji缓冲_b = null;
      int num = -1;
      java.io.ByteArrayOutputStream jd输出流 = null;
      try {
         ji缓冲_b = new byte[1024];
         gzip = new java.util.zip.GZIPInputStream(cd输入流);
         jd输出流 = new java.io.ByteArrayOutputStream();
         while ((num = gzip.read(ji缓冲_b, 0, ji缓冲_b.length)) != -1) {
            jd输出流.write(ji缓冲_b, 0, num);
         }
         jd输出流.flush();
         return jd输出流.toString();
//         return jd输出流.toByteArray();
      } catch (Exception ex) {
         return null;
      } finally {
         if (jd输出流 != null) {
            try {
               jd输出流.close();
            } catch (IOException ex) {
            }
            jd输出流 = null;
         }
         if (gzip != null) {
            try {
               gzip.close();
            } catch (IOException ex) {
            }
            gzip = null;
         }
         ji缓冲_b = null;
      }
   }
}
