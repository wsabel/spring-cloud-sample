package com.kevin.scsr.consumer;

import com.kevin.scsr.channel.MyChannel;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.LongString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@EnableBinding(value = {MyChannel.class})
public class MySimpleReceiver {
    private final Logger logger = LoggerFactory.getLogger(MySimpleReceiver.class);


    // the other way of normal listening, parameter type receives a Message instance directly
//    @StreamListener("myinput")
//    public void handle(Message message) throws IOException {
//        Channel channel = (com.rabbitmq.client.Channel) message.getHeaders().get(AmqpHeaders.CHANNEL);
//        Long deliveryTag = (Long) message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
//        logger.info("my receiver: {}",message);
//        channel.basicAck(deliveryTag,false);
//    }
    /**
     * Normal listening
     * */
//    @RabbitListener(queues = "ministream.mygroup")
    public void handle(@Payload String message,
                       Channel channel,
                       @Header("messageId") String msgId,
                       @Header(AmqpHeaders.DELIVERY_TAG) Long deliveryTag) throws IOException, InterruptedException {
        logger.info("my receiver: {}",message);
        logger.info("message id: {}",msgId);
//        throw new RuntimeException("Message consumer failed!");
//        Thread.sleep(60000*90);
        channel.basicAck(deliveryTag,false);
    }
    @RabbitListener(queues = "biz-queue")
    public void handle1(String msg,Channel channel,Message message) throws IOException, InterruptedException {
        try {
            logger.info("my receiver: {}",msg);
            logger.info("message id: {}",message.getMessageProperties().getMessageId());
//            throw new RuntimeException("Message consumer failed!");
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        } catch (RuntimeException e) {
            e.printStackTrace();
            channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,false);
        }
//        Thread.sleep(60000*90);
    }
    @RabbitListener(queues = "dlx-queue")
    public void handle2(String msg,Channel channel,Message message) throws IOException, InterruptedException {
        logger.info("my dlx receiver: {}",msg);
        logger.info("message id: {}",message.getMessageProperties().getMessageId());
//        throw new RuntimeException("Message consumer failed!");
//        Thread.sleep(60000*90);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }

    /**
     * listen a queue with RabbitListener
     * */
//    @RabbitListener(queues = {"ministream.mygroup.dlq"})
//    public void dlqhandle(  @Payload String message,Channel channel,
//                                                 @Header(AmqpHeaders.DELIVERY_TAG) Long deliveryTag) throws IOException {
//        logger.info("dlq my receiver: {}", message);
//        channel.basicAck(deliveryTag,false);
//    }

    /**
     * listen dead letter queue
     * */
    @RabbitListener(queues = "mydlx.mydlq")
    public void handledlq(@Payload String message,
                        @Header(AmqpHeaders.CHANNEL) Channel channel,
                        @Header(AmqpHeaders.DELIVERY_TAG) Long deliveryTag,
                        @Header("x-exception-message") String exceptionMessage,
                        @Header("x-exception-stacktrace") LongString exceptionStacktrace) throws IOException {
        //original message
        logger.info("my custom dlq receiver: {}",message);
        //exception message
        logger.info("my custom dlq receiver: {}",exceptionMessage);
        //exception stack trace
        logger.info("my custom dlq receiver: {}",exceptionStacktrace);
        channel.basicAck(deliveryTag,false);
        logger.info("dlq process end");
    }
}
