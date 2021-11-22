package com.kevin.scsr;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableFeignClients
public class SpringCloudStreamRabbitmqApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudStreamRabbitmqApplication.class, args);
    }
    @Bean
    public DirectExchange dlxExchange(){
        return new DirectExchange("dlx-exchange");
    }
    @Bean
    public Queue dlxQueue(){
        return  new Queue("dlx-queue");
    }
    @Bean
    public Binding dlxBiding(){
        return BindingBuilder.bind(dlxQueue()).to(dlxExchange()).with("dlx-queue");
    }
    @Bean
    public DirectExchange bizExchange(){
        return new DirectExchange("biz-exchange");
    }
    @Bean
    public Queue bizQueue(){
        Map<String,Object> map = new HashMap<>();
        Queue queue = new Queue("biz-queue",false);
        queue.addArgument("x-dead-letter-exchange","dlx-exchange");
        queue.addArgument("x-dead-letter-routing-key","dlx-queue");
        return  queue;
    }
    @Bean
    public Binding bizBiding(){
        return BindingBuilder.bind(bizQueue()).to(bizExchange()).with("");
    }
}
