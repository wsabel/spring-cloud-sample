# 工程简介
spring cloud stream组件集成rabbitmq消息中间件的简单示例

* 官方参考文档

  https://docs.spring.io/spring-cloud-stream-binder-rabbit/docs/3.1.5/reference/html/spring-cloud-stream-binder-rabbit.html#_reference_guide
### 配置延伸说明
* auto-bind-dlq

  **true**: 自动为当前队列生成一个死信队列并绑定，名为 destination.group.dlq,适用于如果不需要监听消费死信队列或者接受直接RabbitListener监听消费
  
  **false**: 需要通过deadLetterExchange和deadLetterQueueName来声明自定义死信队列



# Introduction
A simple sample of integrating spring cloud stream with the message middleware RabbitMq

* Official Reference Documents

  https://docs.spring.io/spring-cloud-stream-binder-rabbit/docs/3.1.5/reference/html/spring-cloud-stream-binder-rabbit.html#_reference_guide
### Configuration Extend illustration
* auto-bind-dlq

  **true**: Automatically generate and bind a dead queue named destination.group.dlq  for current queue, this works if you do not need to listen  on a dead letter queue or accept to use RabbitListener to listen directly.

  **false**: This need to declare dead letter queue explicitly with deadLetterExchange and deadLetterQueueName
