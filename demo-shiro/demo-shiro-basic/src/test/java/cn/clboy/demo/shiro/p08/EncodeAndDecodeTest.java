package cn.clboy.demo.shiro.p08;


import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.Hex;
import org.junit.Test;

/**
 * 编码/解码
 * Shiro 提供了 base64 和 16 进制字符串 编码/解码 的 API 支持，方便一些编码解码操作
 * Shiro 内部的一些数据的 存储/表示 都使用了 base64 和 16 进制字符串
 */
public class EncodeAndDecodeTest {

    @Test
    public void base64Test() {
        String str = "hello";
        String base64Encoded = Base64.encodeToString(str.getBytes());
        String base64Decoded = Base64.decodeToString(base64Encoded);

        System.out.println("base64Encoded：" + base64Encoded);
        System.out.println("base64Decoded：" + base64Decoded);
    }

    @Test
    public void hexTest() {
        String str = "hello";
        String hexEncoded = Hex.encodeToString(str.getBytes());
        String hexDecoded = new String(Hex.decode(hexEncoded.getBytes()));

        System.out.println("hexEncoded：" + hexEncoded);
        System.out.println("hexDecoded：" + hexDecoded);
    }
}