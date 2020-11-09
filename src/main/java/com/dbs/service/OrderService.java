package com.dbs.service;

import com.dbs.entry.Order;
import com.dbs.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

import java.util.List;

@CacheConfig(cacheNames = "order", cacheManager = "RedisCacheManager")
@Service
public class OrderService {

    @Autowired
    OrderMapper orderMapper;

    public List<Order> getList() {
        return orderMapper.selectAll();
    }

    @Caching(
            put = {
                    @CachePut(/*value = "emp",*/key = "#order.orderId"),
                    @CachePut(/*value = "emp",*/key = "#order.userId")
            }
    )
    public Order insertOrder(Order order) {
        orderMapper.insert(order);
        return order;
    }

    @Cacheable(key = "#a0")
    public List<Order> selectAllByOderId(Integer id) {
        return orderMapper.selectAllByOderId(id);
    }

    @CacheEvict(key = "#a0")
    public void deleteByOrderId(Integer id) {
        orderMapper.deleteByOrderId(id);
    }

}
