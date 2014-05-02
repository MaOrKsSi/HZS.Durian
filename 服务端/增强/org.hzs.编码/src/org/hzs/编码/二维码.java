package org.hzs.编码;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import jp.sourceforge.qrcode.QRCodeDecoder;
import jp.sourceforge.qrcode.exception.DecodingFailedException;
import com.swetake.util.Qrcode;
import java.util.logging.Level;
import java.util.logging.Logger;
import jp.sourceforge.qrcode.data.QRCodeImage;
import org.hzs.logging.error;

public final class 二维码 {

    private static final String i类名_s = "org.hzs.二维码.";
    public static final char L_7 = 'L', M_15 = 'M', Q_25 = 'Q', H_30 = 'H';

    public final static byte[] i编码_byteArray(final byte[] ci_byteArray, final int ci尺寸_i, final Color ci背景色_Color, final Color ci前景色_Color, final char ci排错率_char, final org.hzs.logging.error ci_error) throws error {
        final String ji过程名_s = "i编码_byteArray(final byte[] ci_byteArray, final int ci尺寸_i, final Color ci背景色_Color, final Color ci前景色_Color, final char ci排错率_char).";
        ci_error.g添加过程信息(ji过程名_s);
        // <editor-fold defaultstate="collapsed" desc="自用">
        class 自用 implements Cloneable {

            public Qrcode qrcodeHandler = null;
            public BufferedImage bufImg = null;
            public Graphics2D gs = null;
            public java.io.ByteArrayOutputStream dd = null;
            public int imgSize;
            public int pixoff = 2;// 设置偏移量，不设置可能导致解析出错  
            public boolean[][] codeOut = null;

            public 自用 d副本() throws CloneNotSupportedException {
                return (自用) super.clone();
            }

            public void close() {
                codeOut = null;
                qrcodeHandler = null;
                bufImg = null;
                gs = null;
                if (dd != null) {
                    try {
                        dd.close();
                    } catch (IOException ex) {
                    }
                    dd = null;
                }
            }
        }
        // </editor-fold>
        自用 ji自用 = null;
        try {
            // <editor-fold defaultstate="collapsed" desc="斠验参數是否合法">
            if (ci_byteArray == null) {
                ci_error.g添加错误信息("待编码不可为空！");
                throw ci_error;
            }
            if (ci_byteArray.length <= 0) {
                ci_error.g添加错误信息("待编码太短！");
                throw ci_error;
            }
            if (ci_byteArray.length >= 800) {
                ci_error.g添加错误信息("待编码过长，！");
                throw ci_error;
            }
            if (ci背景色_Color == null) {
                ci_error.g添加错误信息("必需有背景色，否则可能造成无法识别！");
                throw ci_error;
            }
            if (ci前景色_Color == null) {
                ci_error.g添加错误信息("必需有前景色！");
                throw ci_error;
            }
            if (ci前景色_Color.equals(ci背景色_Color)) {
                ci_error.g添加错误信息("前景色不可与背景色相同！");
                throw ci_error;
            }
            // </editor-fold>
            // <editor-fold defaultstate="collapsed" desc="利用对象复制初始化过程内部变量">
            ji自用 = (自用) org.hzs.常用.d对象池.get(i类名_s + ji过程名_s + "自用");
            if (ji自用 == null) {
                ji自用 = new 自用();
                org.hzs.常用.d对象池.put(i类名_s + ji过程名_s + "自用", ji自用);
            }
            ji自用 = ji自用.d副本();
            // </editor-fold>
            ji自用.qrcodeHandler = new Qrcode();
            ji自用.qrcodeHandler.setQrcodeErrorCorrect(ci排错率_char);//设置二维码排错率，可选L(7%)、M(15%)、Q(25%)、H(30%)，排错率越高可存储的信息越少，但对二维码清晰度的要求越小
            ji自用.qrcodeHandler.setQrcodeEncodeMode('B');
            ji自用.qrcodeHandler.setQrcodeVersion(ci尺寸_i);// 设置设置二维码尺寸，取值范围1-40，值越大尺寸越大，可存储的信息越大  
            // 图片尺寸  
            ji自用.imgSize = 67 + 12 * (ci尺寸_i - 1);
            ji自用.bufImg = new BufferedImage(ji自用.imgSize, ji自用.imgSize, BufferedImage.TYPE_INT_RGB);
            ji自用.gs = ji自用.bufImg.createGraphics();
            // 设置背景颜色  
            if (ci背景色_Color != null) {
                ji自用.gs.setBackground(ci背景色_Color);
            }
            ji自用.gs.clearRect(0, 0, ji自用.imgSize, ji自用.imgSize);
            // 设定图像颜色
            ji自用.gs.setColor(ci前景色_Color);
            // 输出内容> 二维码  
//         if (ci_byteArray.length > 0 && ci_byteArray.length < 800) {
            ji自用.codeOut = ji自用.qrcodeHandler.calQrcode(ci_byteArray);
            for (int i = 0; i < ji自用.codeOut.length; i++) {
                for (int j = 0; j < ji自用.codeOut.length; j++) {
                    if (ji自用.codeOut[j][i]) {
                        ji自用.gs.fillRect(j * 3 + ji自用.pixoff, i * 3 + ji自用.pixoff, 3, 3);
                    }
                }
            }
            ji自用.gs.dispose();
            ji自用.bufImg.flush();
            java.io.ByteArrayOutputStream dd = new java.io.ByteArrayOutputStream();
            javax.imageio.ImageIO.write(ji自用.bufImg, "png", dd);
            return dd.toByteArray();
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(二维码.class.getName()).log(Level.SEVERE, null, ex);
            ci_error.g添加错误信息("复制出错！");
            throw ci_error;
        } catch (IOException ex) {
            Logger.getLogger(二维码.class.getName()).log(Level.SEVERE, null, ex);
            ci_error.g添加错误信息("前景色不可与背景色相同！");
            throw ci_error;
        } finally {
            // <editor-fold defaultstate="collapsed" desc="是放资源">
            if (ji自用 != null) {
                ji自用.close();
                ji自用 = null;
            }
            // </editor-fold>
        }
    }

    public final static byte[] i解码_byteArray(final byte[] ci_byteArray) throws DecodingFailedException, IOException {
        BufferedImage bufImg = null;
        QRCodeDecoder decoder = null;
        try {
            bufImg = ImageIO.read(new java.io.ByteArrayInputStream(ci_byteArray));
            decoder = new QRCodeDecoder();
            return decoder.decode(new TwoDimensionCodeImage(bufImg));
        } finally {
            bufImg = null;
            decoder = null;
        }
    }

    private static class TwoDimensionCodeImage implements QRCodeImage {

        private BufferedImage bufImg;

        public TwoDimensionCodeImage(BufferedImage bufImg) {
            this.bufImg = bufImg;
        }

        @Override
        public int getHeight() {
            return bufImg.getHeight();
        }

        @Override
        public int getPixel(int x, int y) {
            return bufImg.getRGB(x, y);
        }

        @Override
        public int getWidth() {
            return bufImg.getWidth();
        }
    }
}
