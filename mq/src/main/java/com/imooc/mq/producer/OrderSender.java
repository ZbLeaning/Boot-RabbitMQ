package com.imooc.mq.producer;

import com.imooc.mq.entity.Order;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Title: OrderSender
 * @Description: 订单发送
 * @date 2019/1/2210:20
 */
@Component
public class OrderSender {
    //使用rabbitmq模板
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendOrder(Order order) throws Exception{

        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(order.getMessageId());

        rabbitTemplate.convertAndSend("order-exchange",//exchange
                "order.abcd",//routingKey
                order,//消息体内容
                correlationData); //消息唯一id
    }
}
