package com.imooc.mq.mapper;

import com.imooc.mq.entity.Order;
import org.springframework.stereotype.Repository;

/**
 * @Title: OrderMapper
 * @Description: 订单接口
 * @date 2019/1/2214:45
 */
@Repository
public interface OrderMapper {
    int insert(Order record);
    int deleteByPrimaryKey(Integer id);
    int insertSelective(Order record);
    Order selectByPrimaryKey(Integer id);
    int updateByPrimaryKeySelective(Order record);
    int updateByPrimaryKey(Order record);
}
