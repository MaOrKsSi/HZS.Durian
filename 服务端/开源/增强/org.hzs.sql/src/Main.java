
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.SocketException;
import java.sql.SQLException;
import org.hzs.logging.error;

public class Main {

    public static void main(String[] args) throws SQLException, error, CloneNotSupportedException, SocketException, UnsupportedEncodingException {
        org.hzs.sql.连接池 d7 = null;
        d7 = new org.hzs.sql.连接池("".getBytes()[0], "", "", "", null);
        d7.d视图(null, null);
        org.hzs.sql.连接 d8 = d7.d连接();
        org.hzs.sql.写操作 d9 = d8.d操作("", null);
        d8.g关闭();
        d8.g回滚事务();
        d8.g废弃新记录();
        d8.g递交事务();
        d8.i_ID("", "");
        d8.i终端_i();
        d9.g关闭();
        d9.g执行缓冲();
        d9.g清空缓冲();
        d9.g立即执行();
        d9.g缓冲();
        d9.g置参數(args, null);
        d9.g置参數(Integer.SIZE, new Object());
        d9.g置参數(Integer.SIZE, new Object(), Integer.SIZE);
        d9.g置参數_B(Integer.SIZE, Boolean.TRUE);
        d9.g置参數_BD(Integer.SIZE, BigDecimal.ZERO);
        d9.g置参數_I(Integer.SIZE, Integer.SIZE);
        d9.g置参數_ID(Integer.SIZE, null);
        d9.g置参數_L(Integer.SIZE, Long.MIN_VALUE);
        d9.g置参數_byteArray(Integer.SIZE, new byte[0]);
        d9.g置参數_s(Integer.SIZE, null);
        d9.g置空参數(Integer.SIZE, Integer.SIZE);
        d9.g置空参數(Integer.SIZE, Integer.SIZE, null);
        d9.p关闭();
        org.hzs.sql.视图 d10 = null, d11 = null;
        d10.g关闭();
        d10.g置参數(args, null);
        d10.g置参數(Integer.SIZE, new Object(), null);
        d10.g置参數(Integer.SIZE, new Object(), Integer.SIZE);
        d10.g置参數_B(Integer.SIZE, Boolean.TRUE);
        d10.g置参數_BD(Integer.SIZE, BigDecimal.ZERO);
        d10.g置参數_I(Integer.SIZE, Integer.SIZE);
        d10.g置参數_ID(Integer.SIZE, null);
        d10.g置参數_L(Integer.SIZE, Long.MIN_VALUE);
        d10.g置参數_byteArray(Integer.SIZE, new byte[0]);
        d10.g置参數_s(Integer.SIZE, null);
        d10.g置空参數(Integer.SIZE, Integer.SIZE);
        d10.g置空参數(Integer.SIZE, Integer.SIZE, null);
        d10.p关闭();
        d11.g前筆();
        d11.g尾部();
        d11.g後筆();
        d11.g末筆();
        d11.g首筆();
        d11.g首部();
        d11.i值_byteArray(null);
        d11.i值(null);
        d11.i值_B(null);
        d11.i值_BD(null);
        d11.i值_I(null);
        d11.i值_ID(null, null);
        d11.i值_L(null);
        d11.i值_s(null);
    }
}
