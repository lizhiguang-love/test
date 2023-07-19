package com.example.demo.lzg.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DirectRabbitMQConfig {

    @Bean
    public Queue testDirectQueue(){
        /**
         * durable:是否持久化，默认false，持久化队列：会被存储在磁盘上，当消息代理
         * 重启时仍然存在，暂存队列
         */
        return new Queue("testDirectQueue",true);
    }

    @Bean
    public DirectExchange directExchange(){
        /**
         * autoDelete:是否自动删除，当没有生产者或者消费者使用此队列，该队列会自动删除
         */
        return new DirectExchange("testDirectExchange",true,false);
    }

    @Bean
    public Binding binding(){
        return BindingBuilder.bind(testDirectQueue()).to(directExchange()).with("testDirectRouting");
    }
}
