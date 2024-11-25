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

        String[] array = new String[] {
                "jdbc:mysql://localhost:8802/MAKE_ORDER?useSSL=false&serverTimezone=Asia/Seoul&characterEncoding=UTF-8?allowPublicKeyRetrieval=true&useSSL=false",
                "root",
                "ssafyssafy1!",
                "jdbc:mysql://43.200.200.151:8802/MAKE_ORDER?useSSL=false&serverTimezone=Asia/Seoul&characterEncoding=UTF-8",
                "depark",
                "ejddldieoqkr2!@"
        };

        for (int i = 0; i < array.length; i++) {
            String encryptedString = jasyptEncrypt(array[i]);
            System.out.println("encryptedString : " + encryptedString);
        }

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