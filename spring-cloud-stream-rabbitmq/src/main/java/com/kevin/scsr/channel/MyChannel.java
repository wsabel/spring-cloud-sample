package com.kevin.scsr.channel;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;


public interface MyChannel {
    @Input("dlqinput")
    SubscribableChannel dlqinput();
    @Input("myinput")
    SubscribableChannel input();
    @Output("myoutput")
    MessageChannel output();
}
