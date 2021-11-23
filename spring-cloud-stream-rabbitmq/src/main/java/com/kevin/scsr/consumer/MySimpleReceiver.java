package com.kevin.scsr.consumer;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MySimpleReceiver {
    private final Logger logger = LoggerFactory.getLogger(MySimpleReceiver.class);



    @RabbitListener(queues = "biz-queue")
    public void handle1(String msg,Channel channel,Message message) throws Exception {
        try {
            logger.info("my receiver: {}",msg);
            logger.info("message id: {}",message.getMessageProperties().getMessageId());
//            throw new RuntimeException("Message consumer failed!");
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        } catch (RuntimeException e) {
            try {
//                channel.getConnection().close();
//                channel.close();
                channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,false);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }
    @RabbitListener(queues = "dlx-queue")
    public void handle2(String msg,Channel channel,Message message) throws IOException, InterruptedException {
        logger.info("my dlx receiver: {}",msg);
        logger.info("message id: {}",message.getMessageProperties().getMessageId());
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }

}
