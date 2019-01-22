package com.imooc.mq.task;

import com.imooc.mq.constant.Constans;
import com.imooc.mq.entity.BrokerMessageLog;
import com.imooc.mq.entity.Order;
import com.imooc.mq.mapper.BrokerMessageLogMapper;
import com.imooc.mq.producer.RabbitOrderSender;
import com.imooc.mq.utils.FastJsonConvertUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @Title: RetryMessageTasker
 * @Description: 定时任务
 * @date 2019/1/2215:45
 */
@Component
public class RetryMessageTasker {
    private static Logger logger = LoggerFactory.getLogger(RetryMessageTasker.class);
    @Autowired
    private RabbitOrderSender rabbitOrderSender;

    @Autowired
    private BrokerMessageLogMapper brokerMessageLogMapper;

    /**
     * 定时任务
     */
    @Scheduled(initialDelay = 5000, fixedDelay = 10000)
    public void reSend(){
        logger.info("-----------定时任务开始-----------");
        //抽取消息状态为0且已经超时的消息集合
        List<BrokerMessageLog> list = brokerMessageLogMapper.query4StatusAndTimeoutMessage();
        list.forEach(messageLog -> {
            //投递三次以上的消息
            if(messageLog.getTryCount() >= 3){
                //更新失败的消息
                brokerMessageLogMapper.changeBrokerMessageLogStatus(messageLog.getMessageId(), Constans.ORDER_SEND_FAILURE, new Date());
            } else {
                // 重试投递消息，将重试次数递增
                brokerMessageLogMapper.update4ReSend(messageLog.getMessageId(),  new Date());
                Order reSendOrder = FastJsonConvertUtil.convertJSONToObject(messageLog.getMessage(), Order.class);
                try {
                    rabbitOrderSender.sendOrder(reSendOrder);
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("-----------异常处理-----------");
                }
            }
        });
    }

}
