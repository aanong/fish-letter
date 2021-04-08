package com.aihaibara.server.server;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
//使包路径下带有@Value的注解自动注入
//使包路径下带有@Autowired的类可以自动注入
@ComponentScan("com.aihaibara.server")
@SpringBootApplication
@Slf4j
public class FishLetterServerApplication {

    @Value("${server.port}")
    private int httpPort ;


    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(FishLetterServerApplication.class);

        ChatServer nettyServer =
                context.getBean(ChatServer.class);
        nettyServer.run();
    }
}
