package com.yunmo.back.controller;


import com.yunmo.back.pojo.Users;
import com.yunmo.back.service.IModelsService;
import com.yunmo.util.ResponseMsg;
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
@RequestMapping("/models")
public class ModelsController {


    @Autowired
    IModelsService modelsService;

    @RequestMapping("/getModelsById")
    ResponseMsg getModelsById(Users users){

        return new ResponseMsg(200,modelsService.getModelsById(users.getUser_id()));
    }
    @RequestMapping("/list")
    ResponseMsg modelsList(){
        return null;
    }

}

