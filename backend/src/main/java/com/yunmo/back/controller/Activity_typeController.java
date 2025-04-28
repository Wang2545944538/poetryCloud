package com.yunmo.back.controller;


import com.yunmo.back.pojo.Activity_type;
import com.yunmo.back.service.IActivity_typeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wwj
 * @since 2024-09-13
 */
@RestController
@RequestMapping("/activity_type")
public class Activity_typeController {
    @Autowired
    IActivity_typeService typeService;
    @RequestMapping("/list")
    List<Activity_type> typeList(){
        return  typeService.list();
    }
}

