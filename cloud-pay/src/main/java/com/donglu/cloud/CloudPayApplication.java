package com.donglu.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableCaching
public class CloudPayApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(CloudPayApplication.class);
        app.addListeners(new ApplicationPidFileWriter());
        app.run(args);
    }

}
