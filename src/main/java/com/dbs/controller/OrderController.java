package com.dbs.controller;

import com.dbs.entry.Order;
import com.dbs.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {
    @Autowired
    OrderService orderService;

    @GetMapping("/order/list")
    public List<Order> getList() {
        return orderService.getList();
    }

    @GetMapping("/order/add")
    public Order insertOrder(Order order) {
        return orderService.insertOrder(order);
    }

    @GetMapping("/order/getById")
    public List<Order> getById(@RequestParam Integer id) {
        return orderService.selectAllByOderId(id);
    }

    @GetMapping("/order/delete")
    public void deleteByOrderId(@RequestParam Integer id) {
        orderService.deleteByOrderId(id);
    }
}
