package org.hzs;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.color.ColorSpace;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.imageio.ImageIO;

/**
 * 图片处理工具类：<br> 功能：缩放图像、裁剪图像、图像类型转换、彩色转黑白、文字水印、图片水印等
 *
 * @author Administrator
 */
public final class 图像 {

   /**
    * 几种常见的图片格式
    */
   public static String IMAGE_TYPE_GIF = "GIF";// 图形交换格式
   public static String IMAGE_TYPE_JPG = "JPG";// 联合照片专家组
   public static String IMAGE_TYPE_JPEG = "JPEG";// 联合照片专家组
   public static String IMAGE_TYPE_BMP = "BMP";// 英文Bitmap（位图）的简写，它是Windows操作系统中的标准图像文件格式
   public static String IMAGE_TYPE_PNG = "PNG";// 可移植网络图形
   public static String IMAGE_TYPE_PSD = "PSD";// Photoshop的专用格式Photoshop

   /**
    * 缩放图像（按比例缩放）
    *
    * @param inputStream 输入流
    * @param outputStream 输出流
    * @param ci缩放比例_d 缩放比例
    */
   public static void g缩放(InputStream inputStream, OutputStream outputStream, double ci缩放比例_d) throws IOException {
      int width, height;
      BufferedImage src = null;
      Graphics g = null;
      BufferedImage tag = null;
      Image image = null;
      try {
         src = ImageIO.read(inputStream); // 读入流
         width = src.getWidth(); // 得到源图宽
         height = src.getHeight(); // 得到源图长
         width = (int) (width * ci缩放比例_d);
         height = (int) (height * ci缩放比例_d);
         image = src.getScaledInstance(width, height, Image.SCALE_DEFAULT);
         tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
         g = tag.getGraphics();
         g.drawImage(image, 0, 0, null); // 绘制缩小后的图
         g.dispose();
         ImageIO.write(tag, "PNG", outputStream);// 输出到流
      } finally {
         src = null;
         g = null;
         tag = null;
         image = null;
      }
   }

   /**
    * 缩放图像（按高度和宽度缩放）
    *
    * @param inputStream 源图像文件地址
    * @param outputStream 缩放后的图像地址
    * @param height 缩放后的高度
    * @param width 缩放后的宽度
    * @param ci补白_b 比例不对时是否需要补白：true为补白; false为不补白;
    */
   public static void g缩放(InputStream inputStream, OutputStream outputStream, int height, int width, boolean ci补白_b) throws IOException {
      double ratio = 0.0; // 缩放比例
      BufferedImage bi = null, image = null;
      Image itemp = null;
      AffineTransformOp op = null;
      try {
         bi = ImageIO.read(inputStream);
         itemp = bi.getScaledInstance(width, height, bi.SCALE_SMOOTH);
         // 计算比例
         if ((bi.getHeight() > height) || (bi.getWidth() > width)) {
            if (bi.getHeight() > bi.getWidth()) {
               ratio = (new Integer(height)).doubleValue() / bi.getHeight();
            } else {
               ratio = (new Integer(width)).doubleValue() / bi.getWidth();
            }
            op = new AffineTransformOp(AffineTransform.getScaleInstance(ratio, ratio), null);
            itemp = op.filter(bi, null);
         }
         if (ci补白_b) {//补白
            image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = image.createGraphics();
            g.setColor(Color.white);
            g.fillRect(0, 0, width, height);
            if (width == itemp.getWidth(null)) {
               g.drawImage(itemp, 0, (height - itemp.getHeight(null)) / 2, itemp.getWidth(null), itemp.getHeight(null), Color.white, null);
            } else {
               g.drawImage(itemp, (width - itemp.getWidth(null)) / 2, 0, itemp.getWidth(null), itemp.getHeight(null), Color.white, null);
            }
            g.dispose();
            itemp = image;
         }
         ImageIO.write((BufferedImage) itemp, "PNG", outputStream);
      } finally {
         bi = null;
         image = null;
         itemp = null;
         op = null;
      }
   }

   /**
    * 图像裁剪(按指定起点坐标和宽高裁剪)
    *
    * @param inputStream 源图像
    * @param outputStream 裁剪後的图像
    * @param x 目标切片起点坐标X
    * @param y 目标切片起点坐标Y
    * @param width 目标切片宽度
    * @param height 目标切片高度
    */
   public static void g裁剪(InputStream inputStream, OutputStream outputStream, int x, int y, int width, int height) throws IOException {
      BufferedImage bi = null;
      ImageFilter cropFilter = null;
      Image img = null;
      BufferedImage tag = null;
      int srcWidth, srcHeight;
      try {
         // 读取源图像
         bi = ImageIO.read(inputStream);
         srcWidth = bi.getHeight(); // 源图宽度
         srcHeight = bi.getWidth(); // 源图高度
         if (srcWidth > 0 && srcHeight > 0) {
            Image image = bi.getScaledInstance(srcWidth, srcHeight, Image.SCALE_DEFAULT);
            // 四个参数分别为图像起点坐标和宽高
            // 即: CropImageFilter(int x,int y,int width,int height)
            cropFilter = new CropImageFilter(x, y, width, height);
            img = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(image.getSource(), cropFilter));
            tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            g.drawImage(img, 0, 0, width, height, null); // 绘制切割后的图
            g.dispose();
            // 输出为文件
            ImageIO.write(tag, "PNG", outputStream);
         }
      } finally {
         bi = null;
         cropFilter = null;
         img = null;
         tag = null;
      }
   }

   /**
    * 图像类型转换：GIF->JPG、GIF->PNG、PNG->JPG、PNG->GIF(X)、BMP->PNG
    *
    * @param inputStream 源图像
    * @param ci输出的图像格式_s 包含格式非正式名称的 String：如JPG、JPEG、GIF等
    * @param outputStream 目标图像
    */
   public static void g格式转换(InputStream inputStream, String ci输出的图像格式_s, OutputStream outputStream) throws IOException {
      BufferedImage src = null;
      try {
         src = ImageIO.read(inputStream);
         ImageIO.write(src, ci输出的图像格式_s, outputStream);
      } finally {
         src = null;
      }
   }

   /**
    * 彩色转为黑白
    *
    * @param inputStream 源图像
    * @param outputStream 目标图像
    */
   public static void g黑白化(InputStream inputStream, OutputStream outputStream) throws IOException {
      BufferedImage src = null;
      ColorSpace cs = null;
      ColorConvertOp op = null;
      try {
         src = ImageIO.read(inputStream);
         cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
         op = new ColorConvertOp(cs, null);
         src = op.filter(src, null);
         ImageIO.write(src, "PNG", outputStream);
      } finally {
         src = null;
         cs = null;
         op = null;
      }
   }

   /**
    * 给图片添加文字水印
    *
    * @param ci水印文字_s 水印文字
    * @param inputStream 源图像
    * @param outputStream 目标图像
    * @param ci字体 水印的字体
    * @param ci颜色 水印的字体颜色
    * @param x 修正值
    * @param y 修正值
    * @param ci透明度_f 透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
    */
   public static void g加水印(String ci水印文字_s, InputStream inputStream, OutputStream outputStream, java.awt.Font ci字体, Color ci颜色, int x, int y, float ci透明度_f) throws IOException {
      Image src = null;
      BufferedImage image = null;
      Graphics2D g = null;
      int width, height;
      try {
         src = ImageIO.read(inputStream);
         width = src.getWidth(null);
         height = src.getHeight(null);
         image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
         g = image.createGraphics();
         g.drawImage(src, 0, 0, width, height, null);
         g.setColor(ci颜色);
         g.setFont(ci字体);
         g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, ci透明度_f));
         // 在指定坐标绘制水印文字
         g.drawString(ci水印文字_s, (width - (getLength(ci水印文字_s) * ci字体.getSize())) / 2 + x, (height - ci字体.getSize()) / 2 + y);
         g.dispose();
         ImageIO.write((BufferedImage) image, "PNG", outputStream);// 输出到文件流
      } finally {
         src = null;
         image = null;
         g = null;
      }
   }

   /**
    * 给图片添加图片水印
    *
    * @param ci水印_inputStream 水印图像
    * @param inputStream 源图像
    * @param outputStream 目标图像
    * @param x 修正值。 默认在中间
    * @param y 修正值。 默认在中间
    * @param ci透明度_f 透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
    */
   public static void g加水印(InputStream ci水印_inputStream, InputStream inputStream, OutputStream outputStream, int x, int y, float ci透明度_f) throws IOException {
      Image src = null;
      BufferedImage image = null;
      Graphics2D g = null;
      Image src_biao = null;
      int wideth, height, wideth_biao, height_biao;
      try {
         src = ImageIO.read(inputStream);
         wideth = src.getWidth(null);
         height = src.getHeight(null);
         image = new BufferedImage(wideth, height, BufferedImage.TYPE_INT_RGB);
         g = image.createGraphics();
         g.drawImage(src, 0, 0, wideth, height, null);
         // 水印文件
         src_biao = ImageIO.read(ci水印_inputStream);
         wideth_biao = src_biao.getWidth(null);
         height_biao = src_biao.getHeight(null);
         g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, ci透明度_f));
         g.drawImage(src_biao, (wideth - wideth_biao) / 2, (height - height_biao) / 2, wideth_biao, height_biao, null);
         // 水印文件结束
         g.dispose();
         ImageIO.write((BufferedImage) image, "PNG", outputStream);
      } finally {
         src = null;
         image = null;
         g = null;
         src_biao = null;
      }
   }

   private static int getLength(String text) {
      int length = 0;
      for (int i = 0; i < text.length(); i++) {
         if (new String(text.charAt(i) + "").getBytes().length > 1) {
            length += 2;
         } else {
            length += 1;
         }
      }
      return length / 2;
   }
}