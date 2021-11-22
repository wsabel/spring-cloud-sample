package com.kevin.scsr.producer;

import com.kevin.scsr.channel.MyChannel;
import com.kevin.scsr.feign.Test;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@RestController
@RequestMapping("producer")
public class MySimpleProducer {
//    @Autowired
//    private MyChannel channel;
    @Autowired
    private Test test;
    @Autowired
    RabbitTemplate rabbitTemplate;

    @PostMapping("/send")
    public void send(String message){
        Message msg = MessageBuilder.withBody(message.getBytes(StandardCharsets.UTF_8)).setMessageId(UUID.randomUUID().toString()).build();
        rabbitTemplate.convertAndSend("biz-exchange","",msg);
//        channel.output().send(MessageBuilder.withPayload(message).build());
    }
    @GetMapping("/feignTest")
    public void feignTest(String id){
        System.out.println(test.get(id));
    }
}
