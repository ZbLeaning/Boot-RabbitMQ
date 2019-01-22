package com.imooc.mq;

import com.imooc.mq.entity.Order;
import com.imooc.mq.producer.OrderSender;
import com.imooc.mq.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MqApplicationTests {
    @Autowired
    private OrderSender orderSender;

    @Autowired
    private OrderService orderService;

    @Test
    public void contextLoads() {
        Order order = new Order();
        order.setId("aaa");
        order.setName("测试消息a");
        order.setMessageId(System.currentTimeMillis() + "$" + UUID.randomUUID().toString());
        try {
            orderSender.sendOrder(order);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试订单创建
     */
    @Test
    public void createOrder(){
        Order order = new Order();
        order.setId("201901230");
        order.setName("测试订单");
        order.setMessageId(System.currentTimeMillis() + "$" + UUID.randomUUID().toString());
        try {
            orderService.createOrder(order);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

