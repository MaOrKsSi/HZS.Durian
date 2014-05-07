
import java.io.IOException;
import org.hzs.图像;

public class 混淆入口 {

   /**
    * 程序入口：用于测试
    *
    * @param args
    */
   public static void main(String[] args) throws IOException {
      // 1-缩放图像：
      // 方法一：按比例缩放
      图像.g缩放(null, null, 5.2);//测试OK
      // 方法二：按高度和宽度缩放
      图像.g缩放(null, null, 500, 300, false);//测试OK

      // 2-切割图像：
      // 方法一：按指定起点坐标和宽高切割
      图像.g裁剪(null, null, 0, 0, 400, 400);

      // 3-图像类型转换：
      图像.g格式转换(null, null, null);

      // 4-彩色转黑白：
      图像.g黑白化(null, null);

      // 5-给图片添加文字水印：
      图像.g加水印(null, null, null, 0, 0, 0);

      // 6-给图片添加图片水印：
      图像.g加水印(null, null, null, null, null, 0, 0, 0);
   }
}
