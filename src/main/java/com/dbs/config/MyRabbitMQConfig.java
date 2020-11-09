package com.dbs.config;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置原理
 * RabbitAutoConfiguration
 * 1. 自动配置了连接工厂CachingConnectionFactory
 * 2. RabbitProperties 封装了rabbitMQ的相关配置
 * 3. RabbitTemplate 给Rabbit发送和接收消息
 * 4，AmqpAdmin rabbitMQ系统管理组件,声明队列创建交换器等
 * AmqpAdmin 创建和删除 Queue,Exchange,Binding
 * amqpAdmin.declareExchange(new DirectExchange("amqpadmin.exchange"));
 * amqpAdmin.declareQueue(new Queue("amqpadmin.queue",true))  队列名称 持久化
 * amqpAdmin.declareBinding(new Binding("amqpadmin.queue",Binding.DestinationType.QUEUE,
 * "amqpadmin.exchange","amqp.haha",null)); 创建绑定规则 队列名称，队列类型，交换机名称，路由键
 * 5. MessageConverter 序列化默认使用 SimpleMessageConverter
 * 6. @EnableRabbit(主程序) + @RabbitListener(监听接口) 监听消息队列的内容 e.g. DepartmentService
 */
@Configuration
public class MyRabbitMQConfig {
    // RabbitProperties
    //必须是amqp的MessageConverter
    //MessageConverter ctrl+h
    @Bean
    public MessageConverter messageConverter() {
        //使用json格式的转换器
        return new Jackson2JsonMessageConverter();
    }
}
