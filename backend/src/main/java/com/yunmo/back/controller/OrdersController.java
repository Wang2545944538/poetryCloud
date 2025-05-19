package com.yunmo.back.controller;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.yunmo.back.common.ResponseMsg;
import com.yunmo.back.pojo.Goods;
import com.yunmo.back.pojo.Orders;
import com.yunmo.back.pojo.Users;
import com.yunmo.back.service.IGoodsService;
import com.yunmo.back.service.IOrdersService;
import com.yunmo.back.service.IUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author king
 * @since 2024-09-18
 */
@RestController
@RequestMapping("/orders")
public class OrdersController {
    @Autowired
    IOrdersService ordersService;
    @Autowired
    IGoodsService goodsService;
    @Autowired
    IUsersService usersService;

    @RequestMapping("/add")
    ResponseMsg addOrder(@RequestBody Orders orders) {
        if (orders == null) {
            return ResponseMsg.fail("添加失败");
        } else {
            System.out.println("Order: " + orders);
            ordersService.save(orders);
            System.out.println("Order: " + orders);
            return ResponseMsg.success("添加成功");
        }
    }

    @PostMapping("/buy")
    ResponseEntity<Object> buyOrder(@RequestBody Orders orders) {
        Goods goods= goodsService.getById(orders.getGood_id());
        Users users=usersService.getById(orders.getUser_id());
        UpdateWrapper wrapper= new UpdateWrapper();
        wrapper.eq("user_id",orders.getUser_id());
        if (goods == null || users == null) {
            return new ResponseEntity<>(ResponseMsg.fail("购买失败"), HttpStatus.BAD_REQUEST);
        }
        if (goods.getGood_count() < orders.getBuy_count()) {
            return new ResponseEntity<>(ResponseMsg.fail("库存不足"), HttpStatus.BAD_REQUEST);
        }
        // 检查用户积分是否足够支付商品的价格
        if (goods.getPoint_price() !=null && users.getPoint() >= goods.getPoint_price() * orders.getBuy_count()) {
            // 使用积分购买
            users.setPoint(users.getPoint() - goods.getPoint_price() * orders.getBuy_count());
        } else {
            // 积分和余额都不够
            return new ResponseEntity<>(ResponseMsg.fail("积分和余额都不足"), HttpStatus.BAD_REQUEST);
        }
        goods.setGood_count(goods.getGood_count() - orders.getBuy_count());
        goodsService.updateById(goods);
        // 更新用户信息
        usersService.update(users,wrapper);
        // 创建订单对象
        Orders orders1 = new Orders();
        orders1.setBuy_count(orders.getBuy_count());
        orders1.setIspay(1);
        orders1.setGood_id(goods.getGood_id());
        orders1.setBuy_time(LocalDateTime.now()); // 假设订单创建时间为当前时间
        orders1.setUser_id(users.getUser_id());
        // 保存订单
        ordersService.save(orders1);
        return new ResponseEntity<>(ResponseMsg.success("购买成功"), HttpStatus.OK);
    }
    @PostMapping("/pay")
    ResponseEntity<Object> payOrder(@RequestBody Orders orders) {
        Goods goods= goodsService.getById(orders.getGood_id());
        Users users=usersService.getById(orders.getUser_id());
        UpdateWrapper wrapper= new UpdateWrapper();
        wrapper.eq("user_id",orders.getUser_id());
        if (goods == null || users == null) {
            return new ResponseEntity<>(ResponseMsg.fail("购买失败"), HttpStatus.BAD_REQUEST);
        }
        if (goods.getGood_count() < orders.getBuy_count()) {
            return new ResponseEntity<>(ResponseMsg.fail("库存不足"), HttpStatus.BAD_REQUEST);
        }
        if (goods.getPrice() != null && users.getMoney() >= goods.getPrice() * orders.getBuy_count()) {
            // 使用余额购买
            users.setMoney(users.getMoney() - goods.getPrice() * orders.getBuy_count());
        } else {
            // 积分和余额都不够
            return new ResponseEntity<>(ResponseMsg.fail("积分和余额都不足"), HttpStatus.BAD_REQUEST);
        }
        goods.setGood_count(goods.getGood_count() - orders.getBuy_count());
        goodsService.updateById(goods);
        // 更新用户信息
        usersService.update(users,wrapper);
        // 创建订单对象
        Orders orders1 = new Orders();
        orders1.setBuy_count(orders.getBuy_count());
        orders1.setIspay(1);
        orders1.setGood_id(goods.getGood_id());
        orders1.setBuy_time(LocalDateTime.now()); // 假设订单创建时间为当前时间
        orders1.setUser_id(users.getUser_id());
        // 保存订单
        ordersService.save(orders1);
        return new ResponseEntity<>(ResponseMsg.success("购买成功"), HttpStatus.OK);
    }
}

