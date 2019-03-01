package com.legosoft.cqrs;

import org.axonframework.boot.autoconfig.AMQPAutoConfiguration;
import org.axonframework.boot.autoconfig.AxonAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration(exclude={AxonAutoConfiguration.class, AMQPAutoConfiguration.class})
public class CqrsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CqrsServiceApplication.class, args);
    }

}
