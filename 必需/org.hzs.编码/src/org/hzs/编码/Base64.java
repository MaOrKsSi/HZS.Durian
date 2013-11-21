package org.hzs.编码;

public final class Base64 {

    private static final byte[] encodingTable = {
        (byte) 'A', (byte) 'B', (byte) 'C', (byte) 'D', (byte) 'E',
        (byte) 'F', (byte) 'G', (byte) 'H', (byte) 'I', (byte) 'J',
        (byte) 'K', (byte) 'L', (byte) 'M', (byte) 'N', (byte) 'O',
        (byte) 'P', (byte) 'Q', (byte) 'R', (byte) 'S', (byte) 'T',
        (byte) 'U', (byte) 'V', (byte) 'W', (byte) 'X', (byte) 'Y',
        (byte) 'Z', (byte) 'a', (byte) 'b', (byte) 'c', (byte) 'd',
        (byte) 'e', (byte) 'f', (byte) 'g', (byte) 'h', (byte) 'i',
        (byte) 'j', (byte) 'k', (byte) 'l', (byte) 'm', (byte) 'n',
        (byte) 'o', (byte) 'p', (byte) 'q', (byte) 'r', (byte) 's',
        (byte) 't', (byte) 'u', (byte) 'v', (byte) 'w', (byte) 'x',
        (byte) 'y', (byte) 'z', (byte) '0', (byte) '1', (byte) '2',
        (byte) '3', (byte) '4', (byte) '5', (byte) '6', (byte) '7',
        (byte) '8', (byte) '9', (byte) '+', (byte) '/'
    };
    private static final byte[] decodingTable;

    static {
        decodingTable = new byte[128];
        for (int i = 0; i < 128; i++) {
            decodingTable[i] = (byte) -1;
        }
        for (int i = 'A'; i <= 'Z'; i++) {
            decodingTable[i] = (byte) (i - 'A');
        }
        for (int i = 'a'; i <= 'z'; i++) {
            decodingTable[i] = (byte) (i - 'a' + 26);
        }
        for (int i = '0'; i <= '9'; i++) {
            decodingTable[i] = (byte) (i - '0' + 52);
        }
        decodingTable['+'] = 62;
        decodingTable['/'] = 63;
    }

    public static byte[] i编码_byteArray(final byte[] ci待编码_byteArray) {
        //编码
        byte[] bytes = ji编码_byteArray(ci待编码_byteArray);

        //转换编码
        for (int ji_i = 0; ji_i < bytes.length; ji_i++) {
            bytes[ji_i] = encodingTable[bytes[ji_i]];
        }

        //补全编码
        int modulus = ci待编码_byteArray.length % 3;
        if (modulus != 0) {
            int ji_i = 4 * ((ci待编码_byteArray.length / 3) + 1);
            bytes = java.util.Arrays.copyOf(bytes, ji_i);
        }
        switch (modulus) {
            case 1:
                bytes[bytes.length - 2] = (byte) '=';
                bytes[bytes.length - 1] = (byte) '=';
                break;
            case 2:
                bytes[bytes.length - 1] = (byte) '=';
                break;
            default:
                break;
        }
        return bytes;
    }

    public static byte[] i解码_byteArray(final byte[] ci待解码_byteArray) {
        byte[] ji_byteArray, ji待解码_byteArray;

        //截除多于编码
        ji待解码_byteArray = discardNonBase64Bytes(ci待解码_byteArray);
        if (ji待解码_byteArray[ji待解码_byteArray.length - 2] == '=') {
            ji待解码_byteArray = java.util.Arrays.copyOf(ji待解码_byteArray, ji待解码_byteArray.length - 2);
        } else if (ji待解码_byteArray[ji待解码_byteArray.length - 1] == '=') {
            ji待解码_byteArray = java.util.Arrays.copyOf(ji待解码_byteArray, ji待解码_byteArray.length - 1);
        }

        //转换编码
        for (int ji_i = 0; ji_i < ji待解码_byteArray.length; ji_i++) {
            ji待解码_byteArray[ji_i] = encodingTable[ji待解码_byteArray[ji_i]];
        }

        //解码
        ji_byteArray = ji解码_byteArray(ji待解码_byteArray);

        return ji_byteArray;
    }

    private static byte[] discardNonBase64Bytes(byte[] data) {
        byte[] temp = new byte[data.length];
        int bytesCopied = 0;
        for (int i = 0; i < data.length; i++) {
            if (isValidBase64Byte(data[i])) {
                temp[bytesCopied++] = data[i];
            }
        }
        byte[] newData = new byte[bytesCopied];
        System.arraycopy(temp, 0, newData, 0, bytesCopied);
        return newData;
    }

    private static boolean isValidBase64Byte(byte b) {
        if (b == '=') {
            return true;
        } else if ((b < 0) || (b >= 128)) {
            return false;
        } else if (decodingTable[b] == -1) {
            return false;
        }
        return true;
    }

    protected static byte[] ji编码_byteArray(final byte[] ci待编码_byteArray) {
        byte[] bytes;
        int modulus = ci待编码_byteArray.length % 3;
        switch (modulus) {
            case 1:
                bytes = new byte[4 * (ci待编码_byteArray.length / 3) + 2];
                break;
            case 2:
                bytes = new byte[4 * (ci待编码_byteArray.length / 3) + 3];
                break;
            default:
                bytes = new byte[(4 * ci待编码_byteArray.length) / 3];
                break;
        }
        int dataLength = (ci待编码_byteArray.length - modulus);
        int a1;
        int a2;
        int a3;
        for (int i = 0, j = 0; i < dataLength; i += 3, j += 4) {
            a1 = ci待编码_byteArray[i] & 0xff;
            a2 = ci待编码_byteArray[i + 1] & 0xff;
            a3 = ci待编码_byteArray[i + 2] & 0xff;
            bytes[j] = (byte) ((a1 >>> 2) & 0x3f);
            bytes[j + 1] = (byte) (((a1 << 4) | (a2 >>> 4)) & 0x3f);
            bytes[j + 2] = (byte) (((a2 << 2) | (a3 >>> 6)) & 0x3f);
            bytes[j + 3] = (byte) (a3 & 0x3f);
        }
        int b1;
        int b2;
        int b3;
        int d1;
        int d2;
        switch (modulus) {
            case 1:
                d1 = ci待编码_byteArray[ci待编码_byteArray.length - 1] & 0xff;
                b1 = (d1 >>> 2) & 0x3f;
                b2 = (d1 << 4) & 0x3f;
                bytes[bytes.length - 4] = (byte) b1;
                bytes[bytes.length - 3] = (byte) b2;
                bytes = java.util.Arrays.copyOf(bytes, bytes.length - 2);
                break;
            case 2:
                d1 = ci待编码_byteArray[ci待编码_byteArray.length - 2] & 0xff;
                d2 = ci待编码_byteArray[ci待编码_byteArray.length - 1] & 0xff;
                b1 = (d1 >>> 2) & 0x3f;
                b2 = ((d1 << 4) | (d2 >>> 4)) & 0x3f;
                b3 = (d2 << 2) & 0x3f;
                bytes[bytes.length - 4] = (byte) b1;
                bytes[bytes.length - 3] = (byte) b2;
                bytes[bytes.length - 2] = (byte) b3;
                bytes = java.util.Arrays.copyOf(bytes, bytes.length - 1);
                break;
            default: /* nothing left to do */

                break;
        }
        return bytes;
    }

    protected static byte[] ji解码_byteArray(final byte[] ci待解码_byteArray) {
        byte[] ji_byteArray, ji待解码_byteArray;
        byte b1;
        byte b2;
        byte b3;
        byte b4;
        ji待解码_byteArray = ci待解码_byteArray.clone();
        int modulus = ji待解码_byteArray.length % 4;
        switch (modulus) {
            case 0:
                ji_byteArray = new byte[((ji待解码_byteArray.length / 4) * 3)];
                break;
            case 2:
                ji_byteArray = new byte[((ji待解码_byteArray.length / 4) * 3) + 1];
                break;
            case 3:
                ji_byteArray = new byte[((ji待解码_byteArray.length / 4) * 3) + 2];
                break;
            default:
                return null;
        }
        for (int i = 0, j = 0; i < (ji待解码_byteArray.length - 4); i += 4, j += 3) {
            b1 = ji待解码_byteArray[i];
            b2 = ji待解码_byteArray[i + 1];
            b3 = ji待解码_byteArray[i + 2];
            b4 = ji待解码_byteArray[i + 3];
            ji_byteArray[j] = (byte) ((b1 << 2) | (b2 >> 4));
            ji_byteArray[j + 1] = (byte) ((b2 << 4) | (b3 >> 2));
            ji_byteArray[j + 2] = (byte) ((b3 << 6) | b4);
        }
        switch (modulus) {
            case 0:
                b1 = ji待解码_byteArray[ji待解码_byteArray.length - 4];
                b2 = ji待解码_byteArray[ji待解码_byteArray.length - 3];
                b3 = ji待解码_byteArray[ji待解码_byteArray.length - 2];
                b4 = ji待解码_byteArray[ji待解码_byteArray.length - 1];
                ji_byteArray[ji_byteArray.length - 3] = (byte) ((b1 << 2) | (b2 >> 4));
                ji_byteArray[ji_byteArray.length - 2] = (byte) ((b2 << 4) | (b3 >> 2));
                ji_byteArray[ji_byteArray.length - 1] = (byte) ((b3 << 6) | b4);
                break;
            case 2:
                b1 = ji待解码_byteArray[ji待解码_byteArray.length - 4];
                b2 = ji待解码_byteArray[ji待解码_byteArray.length - 3];
                ji_byteArray[ji_byteArray.length - 1] = (byte) ((b1 << 2) | (b2 >> 4));
                break;
            case 3:
                b1 = ji待解码_byteArray[ji待解码_byteArray.length - 4];
                b2 = ji待解码_byteArray[ji待解码_byteArray.length - 3];
                b3 = ji待解码_byteArray[ji待解码_byteArray.length - 2];
                ji_byteArray[ji_byteArray.length - 2] = (byte) ((b1 << 2) | (b2 >> 4));
                ji_byteArray[ji_byteArray.length - 1] = (byte) ((b2 << 4) | (b3 >> 2));
                break;
            default:
                return null;
        }
        return ji_byteArray;
    }
}
