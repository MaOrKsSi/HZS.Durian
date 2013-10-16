package org.hzs.压缩解压;

import java.io.IOException;

public class Zip {

   /**
    * *
    * 压缩Zip
    *
    * @param ci待压缩_byteArray
    * @return
    */
   public static byte[] i压缩_byteArray(byte[] ci待压缩_byteArray) {
      java.io.ByteArrayOutputStream bos = null;
      java.util.zip.ZipOutputStream zip = null;
      java.util.zip.ZipEntry entry = null;
      try {
         bos = new java.io.ByteArrayOutputStream();
         zip = new java.util.zip.ZipOutputStream(bos);
         entry = new java.util.zip.ZipEntry("zip");
         entry.setSize(ci待压缩_byteArray.length);
         zip.putNextEntry(entry);
         zip.write(ci待压缩_byteArray);
         zip.closeEntry();
         return bos.toByteArray();
      } catch (Exception ex) {
         return null;
      } finally {
         entry = null;
         if (bos != null) {
            try {
               bos.close();
            } catch (IOException ex) {
            }
            bos = null;
         }
         if (zip != null) {
            try {
               zip.close();
            } catch (IOException ex) {
            }
            zip = null;
         }
      }
   }

   /**
    * *
    * 解压Zip
    *
    * @param ci待解压_byteArray
    * @return
    */
   public static byte[] i解压_byteArray(byte[] ci待解压_byteArray) {
      byte[] b = null;
      java.io.ByteArrayInputStream bis = null;
      java.util.zip.ZipInputStream zip = null;
      java.io.ByteArrayOutputStream baos = null;
      byte[] buf = null;
      int num = -1;
      try {
         bis = new java.io.ByteArrayInputStream(ci待解压_byteArray);
         zip = new java.util.zip.ZipInputStream(bis);
         while (zip.getNextEntry() != null) {
            buf = new byte[1024];
            baos = new java.io.ByteArrayOutputStream();
            while ((num = zip.read(buf, 0, buf.length)) != -1) {
               baos.write(buf, 0, num);
            }
            b = baos.toByteArray();
            baos.flush();
            baos.close();
         }
         zip.close();
         bis.close();
         return b;
      } catch (Exception ex) {
         return null;
      } finally {
         b = null;
         buf = null;
         if (baos != null) {
            try {
               baos.close();
            } catch (IOException ex) {
            }
            baos = null;
         }
         if (zip != null) {
            try {
               zip.close();
            } catch (IOException ex) {
            }
            zip = null;
         }
         if (bis != null) {
            try {
               bis.close();
            } catch (IOException ex) {
            }
            bis = null;
         }
      }
   }
}
