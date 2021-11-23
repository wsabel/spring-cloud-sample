package com.kevin.scsr.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitConfig {
    //dead letter exchange
    @Bean
    public DirectExchange dlxExchange(){
        return new DirectExchange("dlx-exchange");
    }
    //dead letter queue
    @Bean
    public Queue dlxQueue(){
        return  new Queue("dlx-queue");
    }
    //dead letter binding
    @Bean
    public Binding dlxBiding(){
        return BindingBuilder.bind(dlxQueue()).to(dlxExchange()).with("dlx-queue");
    }
    //business exchange
    @Bean
    public DirectExchange bizExchange(){
        return new DirectExchange("biz-exchange");
    }
    //business queue
    @Bean
    public Queue bizQueue(){
        Map<String,Object> map = new HashMap<>();
        Queue queue = new Queue("biz-queue",false);
        queue.addArgument("x-dead-letter-exchange","dlx-exchange");
        queue.addArgument("x-dead-letter-routing-key","dlx-queue");
        return  queue;
    }
    //business exchange binding
    @Bean
    public Binding bizBiding(){
        return BindingBuilder.bind(bizQueue()).to(bizExchange()).with("");
    }
}
