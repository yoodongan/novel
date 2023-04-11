package com.brad.novel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class NovelApplication {

    public static void main(String[] args) {
        S가pringApplication.run(NovelApplication.class, args);
    }

}
