package com.example.demo.lzg.config;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RabbitListener(queues = "testDirectQueue")//监听的队列名称
public class DirectConsumer {

    @RabbitHandler
    public void process(Map testMessage){

        System.out.println("消费者收到消息："+testMessage.get("messageData"));
    }
}
