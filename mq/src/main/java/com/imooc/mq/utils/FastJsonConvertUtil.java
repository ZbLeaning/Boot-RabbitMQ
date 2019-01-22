package com.imooc.mq.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.imooc.mq.entity.Order;

/**
 * @Title: FastJsonConvertUtil
 * @Description: JSON格式转换
 * @date 2019/1/2215:43
 */
public class FastJsonConvertUtil {
    /**
     * JSON转对象
     * @param message
     * @param obj
     * @return
     */
    public static Order convertJSONToObject(String message, Object obj){
        Order order = JSON.parseObject(message, new TypeReference<Order>() {});
        return order;
    }

    /**
     * 对象转JSON
     * @param obj
     * @return
     */
    public static String convertObjectToJSON(Object obj){
        String text = JSON.toJSONString(obj);
        return text;
    }
}
