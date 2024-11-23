package com.makeorder;

import org.assertj.core.api.Assertions;
import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JasyptEncryptorTest {

    @Autowired
    @Qualifier("jasyptStringEncryptor")
    StringEncryptor stringEncryptor;

    @Test
    @DisplayName("Jasypt Key 암.복호화 테스트")
    void jasypt(){
        String string = "jdbc:mysql://localhost:3306/test";

        String encryptedString = jasyptEncrypt(string);
        System.out.println("encryptedString : " + encryptedString);

        Assertions.assertThat(string).isEqualTo(jasyptDecrypt(encryptedString));
    }

    private String jasyptEncrypt(String input) {
        return stringEncryptor.encrypt(input);
    }

    private String jasyptDecrypt(String input){
        return stringEncryptor.decrypt(input);
    }
}