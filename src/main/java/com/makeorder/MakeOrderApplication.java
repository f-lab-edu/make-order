package com.makeorder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MakeOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(MakeOrderApplication.class, args);
    }

}
