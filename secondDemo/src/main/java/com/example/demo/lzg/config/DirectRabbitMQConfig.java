package com.example.demo.lzg.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DirectRabbitMQConfig {

    @Bean
    public Queue queue(){
        return new Queue("testDirectQueue",true);
    }
}
