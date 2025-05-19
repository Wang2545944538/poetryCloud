package com.yunmo.back.controller;


import com.yunmo.back.common.ResponseMsg;
import com.yunmo.back.service.IDynastyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wwj
 * @since 2024-09-12
 */
@RestController
@RequestMapping("/dynasty")
public class DynastyController {
    @Autowired
    IDynastyService dynastyService;
    @RequestMapping("dynastyList")
    ResponseMsg dynasties(){
        return new ResponseMsg(200,dynastyService.list());
    }
}

