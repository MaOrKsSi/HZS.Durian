
import java.io.IOException;
import org.hzs.logging.error;

public class 混淆入口 {

    public static void main(String[] args) throws IOException, error {
//        //java8base64与自身base64测速，结论是能用java8base64最好
//        StringBuilder ji_S = new StringBuilder();
//        ji_S.append("我是一只小小你看，没有老百姓就没有共产党。现只小小你看，没有老百姓就没有共产党在好像反过只小小你看，没有老百姓就没有共产党来了，只小小你看，没有老百姓就没只小小你看，没有老百姓就没有共产党有只小小你看，没有老百姓就没有共产党共产党好像共产党创造了老百姓，难道堪比女娲，创造人类？");
//        ji_S.append("我是一只小小你看，没有老百姓就没有共产党。现只小小你看，没有老百姓就没有共产党在好像反过只小小你看，没有老百姓就没有共产党来了，只小小你看，没有老百姓就没只小小你看，没有老百姓就没有共产党有只小小你看，没有老百姓就没有共产党共产党好像共产党创造了老百姓，难道堪比女娲，创造人类？");
//        ji_S.append("我是一只小小你看，没有老百姓就没有共产党。现只小小你看，没有老百姓就没有共产党在好像反过只小小你看，没有老百姓就没有共产党来了，只小小你看，没有老百姓就没只小小你看，没有老百姓就没有共产党有只小小你看，没有老百姓就没有共产党共产党好像共产党创造了老百姓，难道堪比女娲，创造人类？");
//        ji_S.append("我是一只小小你看，没有老百姓就没有共产党。现只小小你看，没有老百姓就没有共产党在好像反过只小小你看，没有老百姓就没有共产党来了，只小小你看，没有老百姓就没只小小你看，没有老百姓就没有共产党有只小小你看，没有老百姓就没有共产党共产党好像共产党创造了老百姓，难道堪比女娲，创造人类？");
//        ji_S.append("我是一只小小你看，没有老百姓就没有共产党。现只小小你看，没有老百姓就没有共产党在好像反过只小小你看，没有老百姓就没有共产党来了，只小小你看，没有老百姓就没只小小你看，没有老百姓就没有共产党有只小小你看，没有老百姓就没有共产党共产党好像共产党创造了老百姓，难道堪比女娲，创造人类？");
//        ji_S.append("我是一只小小你看，没有老百姓就没有共产党。现只小小你看，没有老百姓就没有共产党在好像反过只小小你看，没有老百姓就没有共产党来了，只小小你看，没有老百姓就没只小小你看，没有老百姓就没有共产党有只小小你看，没有老百姓就没有共产党共产党好像共产党创造了老百姓，难道堪比女娲，创造人类？");
//        ji_S.append("我是一只小小你看，没有老百姓就没有共产党。现只小小你看，没有老百姓就没有共产党在好像反过只小小你看，没有老百姓就没有共产党来了，只小小你看，没有老百姓就没只小小你看，没有老百姓就没有共产党有只小小你看，没有老百姓就没有共产党共产党好像共产党创造了老百姓，难道堪比女娲，创造人类？");
//        ji_S.append("我是一只小小你看，没有老百姓就没有共产党。现只小小你看，没有老百姓就没有共产党在好像反过只小小你看，没有老百姓就没有共产党来了，只小小你看，没有老百姓就没只小小你看，没有老百姓就没有共产党有只小小你看，没有老百姓就没有共产党共产党好像共产党创造了老百姓，难道堪比女娲，创造人类？");
//        ji_S.append("我是一只小小你看，没有老百姓就没有共产党。现只小小你看，没有老百姓就没有共产党在好像反过只小小你看，没有老百姓就没有共产党来了，只小小你看，没有老百姓就没只小小你看，没有老百姓就没有共产党有只小小你看，没有老百姓就没有共产党共产党好像共产党创造了老百姓，难道堪比女娲，创造人类？");
//        ji_S.append("我是一只小小你看，没有老百姓就没有共产党。现只小小你看，没有老百姓就没有共产党在好像反过只小小你看，没有老百姓就没有共产党来了，只小小你看，没有老百姓就没只小小你看，没有老百姓就没有共产党有只小小你看，没有老百姓就没有共产党共产党好像共产党创造了老百姓，难道堪比女娲，创造人类？");
//        ji_S.append("我是一只小小你看，没有老百姓就没有共产党。现只小小你看，没有老百姓就没有共产党在好像反过只小小你看，没有老百姓就没有共产党来了，只小小你看，没有老百姓就没只小小你看，没有老百姓就没有共产党有只小小你看，没有老百姓就没有共产党共产党好像共产党创造了老百姓，难道堪比女娲，创造人类？");
//        ji_S.append("我是一只小小你看，没有老百姓就没有共产党。现只小小你看，没有老百姓就没有共产党在好像反过只小小你看，没有老百姓就没有共产党来了，只小小你看，没有老百姓就没只小小你看，没有老百姓就没有共产党有只小小你看，没有老百姓就没有共产党共产党好像共产党创造了老百姓，难道堪比女娲，创造人类？");
//        ji_S.append("我是一只小小你看，没有老百姓就没有共产党。现只小小你看，没有老百姓就没有共产党在好像反过只小小你看，没有老百姓就没有共产党来了，只小小你看，没有老百姓就没只小小你看，没有老百姓就没有共产党有只小小你看，没有老百姓就没有共产党共产党好像共产党创造了老百姓，难道堪比女娲，创造人类？");
//        ji_S.append("我是一只小小你看，没有老百姓就没有共产党。现只小小你看，没有老百姓就没有共产党在好像反过只小小你看，没有老百姓就没有共产党来了，只小小你看，没有老百姓就没只小小你看，没有老百姓就没有共产党有只小小你看，没有老百姓就没有共产党共产党好像共产党创造了老百姓，难道堪比女娲，创造人类？");
//        ji_S.append("我是一只小小你看，没有老百姓就没有共产党。现只小小你看，没有老百姓就没有共产党在好像反过只小小你看，没有老百姓就没有共产党来了，只小小你看，没有老百姓就没只小小你看，没有老百姓就没有共产党有只小小你看，没有老百姓就没有共产党共产党好像共产党创造了老百姓，难道堪比女娲，创造人类？");
//        byte[] ji_byteArray = ji_S.toString().getBytes("UTF-8");
//        System.out.println(java.util.Calendar.getInstance().getTimeInMillis());
//        java.util.Base64.getEncoder().encode(ji_byteArray);
//        System.out.println(java.util.Calendar.getInstance().getTimeInMillis());
//        org.hzs.转码.Base64.i编码_byteArray(ji_byteArray);
//        System.out.println(java.util.Calendar.getInstance().getTimeInMillis());
//        java.util.Base64.getEncoder().encode(ji_byteArray);
//        System.out.println(java.util.Calendar.getInstance().getTimeInMillis());
//        org.hzs.转码.Base64.i编码_byteArray(ji_byteArray);
//        System.out.println(java.util.Calendar.getInstance().getTimeInMillis());
//      try {
        char a = org.hzs.编码.二维码.H_30;
        org.hzs.编码.二维码.i编码_byteArray(null, 0, null, null, a, null);
        a = org.hzs.编码.二维码.L_7;
        org.hzs.编码.二维码.i编码_byteArray(null, 0, null, null, a, null);
        a = org.hzs.编码.二维码.M_15;
        org.hzs.编码.二维码.i编码_byteArray(null, 0, null, null, a, null);
        a = org.hzs.编码.二维码.Q_25;
        org.hzs.编码.二维码.i编码_byteArray(null, 0, null, null, a, null);
        org.hzs.编码.二维码.i编码_byteArray(null, 0, null, null, org.hzs.编码.二维码.H_30, null);
        org.hzs.编码.二维码.i编码_byteArray(null, 0, null, null, org.hzs.编码.二维码.L_7, null);
        org.hzs.编码.二维码.i编码_byteArray(null, 0, null, null, org.hzs.编码.二维码.M_15, null);
        org.hzs.编码.二维码.i编码_byteArray(null, 0, null, null, org.hzs.编码.二维码.Q_25, null);
        org.hzs.编码.二维码.i解码_byteArray(null);
        org.hzs.编码.Base64.i编码_byteArray(null);
        org.hzs.编码.Base64.i解码_byteArray(null);
        org.hzs.编码.Base79.i编码_byteArray(null);
        org.hzs.编码.Base79.i解码_BASE64byteArray(null);
//      org.hzs.编码.Base94.i编码_byteArray(null);
//      org.hzs.编码.Base94.i解码_BASE64byteArray(null);
//      //
//      org.tool.转码.Escape.g编码_s("");
//      org.tool.转码.Escape.g解码_s("");
//      } catch (IOException ex) {
//         Logger.getLogger(混淆入口.class.getName()).log(Level.SEVERE, null, ex);
//      }
    }
}
