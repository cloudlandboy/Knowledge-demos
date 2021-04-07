package cn.clboy.demo.shiro.spring.utils;


import org.apache.shiro.codec.Base64;
import org.apache.shiro.crypto.AesCipherService;
import org.apache.shiro.util.ByteSource;
import org.junit.Test;

import java.security.Key;

public class AesCipherGenerateTest {

    @Test
    public void generateAesCipher() {
        final AesCipherService aesCipherService = new AesCipherService();
        final Key var = aesCipherService.generateNewKey();
        String key = Base64.encodeToString(var.getEncoded());
        System.out.println(key);
    }

    @Test
    public void decryptTest() {
        String key = "BZlRrp6inW53SYySUOle2Q==";
        String text = "y8xOT6Mp8hWl0B3GIBzLhatZHFDr6da52njd/6a4dhm6jLZQaBfmjnqozOCGKHM=";
        String errText = "y9xOT6Mp8hWl0B3GIBzLhatZHFDr6da52njd/6a4dhm6jLZQaBfmjnqozOCGKHM=";
        final AesCipherService aesCipherService = new AesCipherService();
        final ByteSource decrypt1 = aesCipherService.decrypt(Base64.decode(text), Base64.decode(key));
        //final ByteSource decrypt2 = aesCipherService.decrypt(Base64.decode(errText), Base64.decode(key));
        System.out.println(new String(decrypt1.getBytes()));
        //System.out.println(new String(decrypt2.getBytes()));
    }
}