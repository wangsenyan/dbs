package com.dbs.service;

import com.dbs.entry.Department;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService {
    //RabbitListener监听队列queues(数组)
    //@EnableRabbit开启基于注解的RabbitMQ
    @RabbitListener(queues = "own.news")
    public void receive(Department department) {
        System.out.println("收到消息" + department);
    }

    /**
     * 获取消息头 org.springframework.amqp.core.Message  头为类似这样的 [B@5b201306 内容为 MessageProperties
     *
     * @param message
     */
    @RabbitListener(queues = "own")
    public void receive02(Message message) {
        System.out.println(message.getBody());
        System.out.println(message.getMessageProperties());
    }
}