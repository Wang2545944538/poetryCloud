package com.yunmo.back.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yunmo.back.pojo.Users;
import com.yunmo.back.pojo.Users_collection;
import com.yunmo.back.service.IUsers_collectionService;
import com.yunmo.util.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wwj
 * @since 2024-09-12
 */
@RestController
@RequestMapping("/users_collection")
public class Users_collectionController {
    @Autowired
    IUsers_collectionService usersCollectionService;

    @RequestMapping("/list")
    IPage<Users_collection> usersCollectionControllerIPage(@RequestParam(defaultValue = "1") Integer page,
                                                           @RequestParam(defaultValue = "5")Integer limit,
                                                           HttpServletRequest request){
        Page<CollectionController> page1 =new Page<>(page,limit);
        String token=request.getHeader("token");
        Claims claims = JwtUtils.parseJWT(token);
        int userId = JSONObject.parseObject(claims.getSubject(), Users.class).getUser_id();
        QueryWrapper<Users_collection> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        return usersCollectionService.getAllCollectionById(page1,wrapper);
    }
}

