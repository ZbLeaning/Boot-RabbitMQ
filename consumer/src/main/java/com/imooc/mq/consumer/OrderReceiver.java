package com.imooc.mq.consumer;

import com.imooc.mq.entity.Order;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;


/**
 * @Title: OrderReceiver
 * @Description: 消费
 * @date 2019/1/2211:03
 */
@Component
public class OrderReceiver {
    /**
     * @RabbitListener 消息监听，可配置交换机、队列、路由key
     * 该注解会创建队列和交互机 并建立绑定关系
     * @RabbitHandler 标识此方法如果有消息过来，消费者要调用这个方法
     * @Payload 消息体
     * @Headers 消息头
     * @param order
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "order-queue1",declare = "true"),
            exchange = @Exchange(name = "order-exchange1",declare = "true",type = "topic"),
            key = "order.ABC"
    ))
    @RabbitHandler
    public void onOrderMessage(@Payload Order order, @Headers Map<String,Object> headers,
                               Channel channel) throws Exception{
        //消费者操作
        try {
            System.out.println("------收到消息，开始消费------");
            System.out.println("订单ID："+order.getId());

            Long deliveryTag = (Long)headers.get(AmqpHeaders.DELIVERY_TAG);
            //现在是手动确认消息 ACK
            channel.basicAck(deliveryTag,false);
        } finally {
           channel.close();
        }
    }
}
