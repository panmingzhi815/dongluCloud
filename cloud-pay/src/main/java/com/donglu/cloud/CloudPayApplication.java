package com.donglu.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
public class CloudPayApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudPayApplication.class, args);
    }

}
