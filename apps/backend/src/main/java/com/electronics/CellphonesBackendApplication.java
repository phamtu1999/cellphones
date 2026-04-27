package com.electronics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CellphonesBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(CellphonesBackendApplication.class, args);
    }
}
