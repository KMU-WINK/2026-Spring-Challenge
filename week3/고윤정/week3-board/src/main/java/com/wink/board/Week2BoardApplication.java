package com.wink.board;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Week2BoardApplication {

    public static void main(String[] args) {
        SpringApplication.run(Week2BoardApplication.class, args);
    }
}