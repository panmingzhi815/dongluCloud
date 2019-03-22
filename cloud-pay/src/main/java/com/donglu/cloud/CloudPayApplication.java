package com.donglu.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
public class CloudPayApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(CloudPayApplication.class);
        app.addListeners(new ApplicationPidFileWriter());
        app.run(args);
    }

}
