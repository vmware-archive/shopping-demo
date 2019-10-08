package io.projectriffdemo.shopping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AdRecommenderApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdRecommenderApplication.class, args);
    }

    @Bean
    public AdRecommenderFunction adRecommenderFunction() {
        return new AdRecommenderFunction();
    }
}
