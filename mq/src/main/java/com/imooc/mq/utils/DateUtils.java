package com.imooc.mq.utils;

import java.util.Date;

/**
 * @Title: DateUtils
 * @Description: 时间工具类
 * @date 2019/1/2215:42
 */
public class DateUtils {
    public static Date addMinutes(Date orderTime, int orderTimeout) {
        Date afterDate = new Date(orderTime.getTime() + 60000*orderTimeout);
        return afterDate;
    }
}
