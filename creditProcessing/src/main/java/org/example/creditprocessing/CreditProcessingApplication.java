package org.example.creditprocessing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class CreditProcessingApplication {

    public static void main(String[] args) {
        SpringApplication.run(CreditProcessingApplication.class, args);
    }
}
