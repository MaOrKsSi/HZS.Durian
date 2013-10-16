
import java.io.IOException;
import org.hzs.logging.error;

public class 混淆入口 {

    public static void main(String[] args) throws error, IOException {
        org.hzs.Client d = new org.hzs.Client();
        d.g握手(null, Integer.MIN_VALUE, 1, null);
        d.g会晤服务器(null, null, null);
        d.g退出();
        org.hzs.Client.d蜂鸣.beep();
    }
}
