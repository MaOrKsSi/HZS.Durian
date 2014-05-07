
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import org.hzs.logging.error;

public class Main {

    public static void main(String[] args) {
        org.hzs.json.JSONObject ji_JSON = null;
        org.hzs.安全.RSA rsa = null;
        byte[] ji_byteArray = null;
        java.io.FileOutputStream out = null;
        try {
            ji_JSON = org.hzs.json.JSONObject.d出列();
            ji_JSON.put("软件到期时间_L", null);
            ji_JSON.put("服务器号_i", Short.MIN_VALUE);
            ji_JSON.put("數據库口令_s", "zz22388820");
            ji_JSON.put("數據库用户_s", "postgres");
            ji_JSON.put("數據库连接_s", "jdbc:postgresql://127.0.0.1:5432/fa1yd4");
            ji_JSON.put("數據密钥_BASE64s", "sdfsdf");
            ji_JSON.put("rsa私钥_BASE64s", "MIIF2wIBADANBgkqhkiG9w0BAQEFAASCBcUwggXBAgEAAoIBQQCOzydI5LP8J3fyn1KSfgc515uirPcL1DuBEb+BcvADidfcZB4b2SQBuZdzTcvLjvFFKcDeyoaltEyCr4R0AfQptTJ9FQwmFK3h0wI9RIX5a+8KXtyqv6Uz6DCcLpnpTqe08SBrUMyPg8t+IbkYXxhh11xkcnsrAAg3xg4fyDlkhYdrYtNf+G420sOztaPEifLu+B04M+YvOnwH+Jk8QHC3tE/YKNlQ6OroAvVs+AgruZI59AftYH5xM8U/GhESmibqVUGMUaiom0RCdzMGz73qV+q/pQltS/4ghYSpv1n6oUBHHQfaK3RiI0UWhSVxRxrOWCm+hb7+/FwmsM+c5yQh0AyGmfBP2cWjbH/FNfOrO9JjYrIdXsruDjIRLxMfEWdpjAWPWqgqrSLpehc0KxFDm0Tuq/LU40RUPDg1k930awIDAQABAoIBQGpELahpPqEwwWnSMoB9tWTOUqqfaInt2IV3rnhmDcGkTfNasKjqUIfUEaWkSzTi3hgoNLPUIpOlcZWBg99LIOP0SGiZQ9wGYUl8Wds7ZTIvpcWixzNrvkO80Ic3iiH4/ISBNS2GB2H3oXHvKCvBf4Q23DKGma5qrLLL9vChbkulrRo0hLOSSnkTJrtQO1fhittV13Bd9jhIncjUrToPfY+n/2csrokKl0MR0t5WfE7Nb489y21e9F6A1Fx/AhiCTgmwghwNz/o+A8P+4SMW/tJmNRTHSMCNBxydN+QqldCiLuUV5PPcHhtrJ47kqaElhN0J4I5QKjGzHBcp74tx8/MUdJSIXy+4mRavdqljbJ9hw4hfDAFnFPSSeOx5+9ozVKlH+karbxx8LnmXBeTQ7oMWh2dtTxrYi2o3+OHclOnxAoGhAM56gVHnNzAmOuJvL/2OJLfzc7diRNNo4n7VBIysdbOZemlJVCb07J1GsY2xb9u5LOrrums/GX/+To4w6iXr+ZtZusXJDfiGxmG7IwNGE/i5l6shaQRsdzXBB8IpnFlOweow+iw8GsO1dh1K1osTPW0e820EVuV18bA/0PSkyOUGLYw9fjhx9DFXNtDaNiynrct8FIm9awyBn5hMEEGKKskCgaEAsQ9wci3DDtIonVylXKYFgZ71tLF8C1RivzD371GElpm4j5CHfzZ8xeZqej5Ovw1QwxBL4v49ciQV1EzWkCzN/j59qMDapnqwmv9djOIUVog2zaI/NlN4gkEB3wGxPMpXD5kIZlsucl7cCiy2YeYUD47v7CoyB42xM/11b+p63F5P/a8ePjf/gSktE1/Wv/PnJCVbr7RvAD7gnOn1J8rLkwKBoECrIcGzNxe/gYXV6SPy1xE+EKoI/yFvojCzKv+cLCkaErrTGqYw48LGl2mXfz9EGgth1TstwiDSV2w1acCCzeSYD+VOeS+zloKZh/fIhQzLo/MXPbsC9btCP9nSCoP2JMnimRgtovxU4y99dwCMABsjLH6ic0zeydm3www8aCdC7hUUVWn8Vsz1UdfL4FfQFKC8tfoF93ctSfz7JcRBL/ECgaBUUG3TcUc5f4onpT55Z/WnS/LlIL9ZBc4gDUmDhOYGcR0aAFEFmwS/mIGLxK/q+oRC3zfD2B2ahOSAPDbQYQWW1FsySOPV8wa2SHRb6iyVKDgwo9pGHGl+XUZu03d64z+iBdWiKDlt7Q4LKk+PPui+n+pQI6e/QRHpGAEcUiQ/XAnrW8CjEDdzYhA88Z5pudOPutp2SQ2yqD59ektrr35RAoGfFO2MuS5rrZuCGSwlNZ8Fb51MllSiRVVHO7CZAglRrJOjkHmxeK0+TuisfnQm/F9zFDUvR16+LLchB+1gofPWlhyuxnH5Lt6WkoLqD3wnHPlTab3/OHqzAeFD6zdHLV4EubXmyRErz7bsvxNBENfXWRVeuuVcal9CISxtfvg+mJ7IazaUP5NHBn5PFXT7NyHDOVUxzmNOOqL2wp8skvP4");
            ji_JSON.put("中心端口_i", 8080);
            rsa = new org.hzs.安全.RSA(5);
//            rsa.g置公钥(org.hzs.转码.Base64.i解码_byteArray("MIIBYjANBgkqhkiG9w0BAQEFAAOCAU8AMIIBSgKCAUEAjs8nSOSz/Cd38p9Skn4HOdeboqz3C9Q7gRG/gXLwA4nX3GQeG9kkAbmXc03Ly47xRSnA3sqGpbRMgq+EdAH0KbUyfRUMJhSt4dMCPUSF+WvvCl7cqr+lM+gwnC6Z6U6ntPEga1DMj4PLfiG5GF8YYddcZHJ7KwAIN8YOH8g5ZIWHa2LTX/huNtLDs7WjxIny7vgdODPmLzp8B/iZPEBwt7RP2CjZUOjq6AL1bPgIK7mSOfQH7WB+cTPFPxoREpom6lVBjFGoqJtEQnczBs+96lfqv6UJbUv+IIWEqb9Z+qFARx0H2it0YiNFFoUlcUcazlgpvoW+/vxcJrDPnOckIdAMhpnwT9nFo2x/xTXzqzvSY2KyHV7K7g4yES8THxFnaYwFj1qoKq0i6XoXNCsRQ5tE7qvy1ONEVDw4NZPd9GsCAwEAAQ==".getBytes("UTF-8")));
            rsa.g置私钥(org.hzs.转码.Base64.i解码_byteArray("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".getBytes("UTF-8")));
            ji_byteArray = ji_JSON.toString(null).getBytes("UTF-8");
            ji_byteArray = org.hzs.压缩解压.Gzip.i压缩_byteArray(ji_byteArray);
            ji_byteArray = rsa.i用私钥加密_byteArray(ji_byteArray);
            out = new java.io.FileOutputStream("/home/hzs/参數.ini");
            out.write(ji_byteArray);
            out.flush();
            out.close();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (error ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
        }
    }
}
