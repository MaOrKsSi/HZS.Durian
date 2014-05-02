
import java.util.Base64;


public class 混淆入口 {

    public static void main(final String[] args) {
        org.hzs.常用.d对象池.put(null, null);
        org.hzs.常用.d对象池.get(null);
        Base64.Encoder d1 = org.hzs.常用.base64编码;
        Base64.Decoder d2 = org.hzs.常用.base64解码;
    }
}
