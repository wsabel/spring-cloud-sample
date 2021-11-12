package com.kevin.scsr.producer;

import com.kevin.scsr.channel.MyChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("producer")
public class MySimpleProducer {
    @Autowired
    private MyChannel channel;
    @PostMapping("/send")
    public void send(String message){
        channel.output().send(MessageBuilder.withPayload(message).build());
    }
}
