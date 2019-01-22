package com.imooc.mq.producer;


import com.imooc.mq.constant.Constans;
import com.imooc.mq.entity.Order;
import com.imooc.mq.mapper.BrokerMessageLogMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Title: RabbitOrderSender
 * @Description: 消息发送
 * @date 2019/1/2214:52
 */
@Component
public class RabbitOrderSender {
    private static Logger logger = LoggerFactory.getLogger(RabbitOrderSender.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private BrokerMessageLogMapper brokerMessageLogMapper;

    /**
     * Broker应答后，会调用该方法区获取应答结果
     */
    final RabbitTemplate.ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback() {
        @Override
        public void confirm(CorrelationData correlationData, boolean ack, String cause) {
            logger.info("correlationData："+correlationData);
            String messageId = correlationData.getId();
            logger.info("消息确认返回值："+ack);
            if (ack){
                //如果返回成功，则进行更新
                brokerMessageLogMapper.changeBrokerMessageLogStatus(messageId, Constans.ORDER_SEND_SUCCESS,new Date());
            }else {
                //失败进行操作：根据具体失败原因选择重试或补偿等手段
                logger.error("异常处理,返回结果："+cause);
            }
        }
    };

    /**
     * 发送消息方法调用: 构建自定义对象消息
     * @param order
     * @throws Exception
     */
    public synchronized void  sendOrder(Order order) throws Exception {
        // 通过实现 ConfirmCallback 接口，消息发送到 Broker 后触发回调，确认消息是否到达 Broker 服务器，也就是只确认是否正确到达 Exchange 中
        rabbitTemplate.setConfirmCallback(confirmCallback);
        //消息唯一ID
        CorrelationData correlationData = new CorrelationData(order.getMessageId());
        rabbitTemplate.convertAndSend("order-exchange1", "order.ABC", order, correlationData);
    }
}
