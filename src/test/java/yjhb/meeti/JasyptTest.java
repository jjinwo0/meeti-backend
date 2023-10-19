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

        String content = "";
        String encryptedContent = encryptor.encrypt(content);
        String decryptedContent = encryptor.decrypt("FeN8u+sqcu4sKMG8fTZ/4nSQJ3fkghB3gpmXPDRAdvGtnWXZtDo0S1NvANniFtAubYgH3m2/jAagdLGNYAflpK/eL4jgQPD0u6NlI7b1KEH+52XHVIggynIRLfRtwItwnc+X9wU3u6TzGLi+YPh78A==");

        System.out.println("Enc : " + encryptedContent + ", Dec : " + decryptedContent);
    }
}
