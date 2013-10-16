
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class 混淆入口 {

    public static void main(String[] args) {
        try {
            org.hzs.图形验证码 vCode = new org.hzs.图形验证码();
            vCode = new org.hzs.图形验证码(250, 60);
            vCode = new org.hzs.图形验证码(250, 60, 5, 100);
            vCode.getCode();
            vCode.i_byteArray();
            vCode.createCode();
            vCode.createCode(null);
//            vCode.write(new java.io.FileOutputStream(""));
        } catch (IOException ex) {
            Logger.getLogger(混淆入口.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
