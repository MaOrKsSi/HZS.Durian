
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

public class 混淆入口 {

    public static void main(final String[] args) throws org.hzs.logging.error, SQLException, UnsupportedEncodingException {
        int i1 = org.hzs.金额.大写;
        i1 = org.hzs.金额.小写;
        org.hzs.金额 d2 = null;
        d2.i中文_s(0);
//        d2.i英文_s(null, 0, null);
        d2.set(null, null);
    }
}
