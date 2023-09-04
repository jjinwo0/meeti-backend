package yjhb.meeti;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.junit.jupiter.api.Test;

public class JasyptTest {

    @Test
    public void jasyptTest(){

        String password = "aiejoaifp##@*!&@^aoweuiai372786";

        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        encryptor.setPoolSize(4);
        encryptor.setPassword(password);
        encryptor.setAlgorithm("PBEWithMD5AndTripleDES");

        String content = "/Users/parkjw/Downloads";
        String encryptedContent = encryptor.encrypt(content);
        String decryptedContent = encryptor.decrypt(encryptedContent);

        System.out.println("Enc : " + encryptedContent + ", Dec : " + decryptedContent);
    }
}
