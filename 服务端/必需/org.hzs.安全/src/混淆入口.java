
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import org.hzs.安全.RSA;

public class 混淆入口 {

    public static void main(String[] args) {
        try {
//         org.hzs.安全.EC ec = new org.hzs.安全.EC();
//         ec.g生成();
//         ec.i公钥_byteArray();
//         ec.i私钥_byteArray();
//         byte[] ddd = ec.i用公钥加密_byteArray("ci待加密數據_byteArray".getBytes("UTF-8"));
//         ec.i用公钥解密_byteArray(ddd);
            org.hzs.安全.RSA rsa = new org.hzs.安全.RSA(5);
//            rsa = new org.hzs.安全.RSA(3);
            rsa.g生成();
//      byte[] d1 = rsa.i私钥_byteArray();
//      String d2 = new String(org.tool.转码.Base64.i编码_byte(d1));
            byte[] d1 = rsa.i公钥_byteArray();
            rsa.i私钥_byteArray();
//      d2 = new String(org.tool.转码.Base64.i编码_byte(d1));
//      d1 = org.tool.转码.Base64.i解码_byte("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".getBytes());
            rsa.g置私钥(d1);
//         org.tool.加解密.RSA.g用公钥加密(null, null, null);
//         rsa.g用私钥解密(null, null);
            RSA.i公钥(null);
            rsa.i用公钥加密_byteArray(null);
            rsa.i用公钥加密_byteArray(null, null);
            rsa.i用公钥解密_byteArray(d1);
            rsa.i用公钥解密_byteArray(d1, null);
//         org.tool.加解密.RSA.i用公钥驗证簽名_b(new java.io.ByteArrayInputStream(new byte[0]), new byte[0], null);
            RSA.i用公钥驗证簽名_b(new byte[0], new byte[0], null);
//         rsa.i用私钥簽名_byteArray(new java.io.ByteArrayInputStream(new byte[0]));
            rsa.i用私钥簽名_byteArray(new byte[0]);
            rsa.i用私钥加密_byteArray(d1);
            rsa.i用私钥解密_byteArray(new byte[0]);
            rsa.g置公钥(new byte[0]);
            //
//      org.security.AES.g加密(null, null, null);
//      org.security.AES.g解密(null, null, null);
            org.hzs.安全.AES.i加密_byteArray(null, null);
            org.hzs.安全.AES.i解密_byteArray(null, null);
            //
            String s1 = org.hzs.安全.DIGEST.SHA_512;
//      dd = org.tool.加解密.DIGEST.MD5;
//      dd = org.tool.加解密.DIGEST.SHA_1;
//      dd = org.tool.加解密.DIGEST.SHA_256;
//      dd = org.tool.加解密.DIGEST.MD2;
            org.hzs.安全.DIGEST.i摘要_byteArray(null, s1);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IOException | IllegalBlockSizeException | BadPaddingException | InvalidKeySpecException | SignatureException ex) {
            Logger.getLogger(混淆入口.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
        }
    }
}
