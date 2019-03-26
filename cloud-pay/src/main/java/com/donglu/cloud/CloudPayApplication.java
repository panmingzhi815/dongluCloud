package com.donglu.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;

@SpringBootApplication
public class CloudPayApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(CloudPayApplication.class);
        app.addListeners(new ApplicationPidFileWriter());
        app.run(args);
    }

}
