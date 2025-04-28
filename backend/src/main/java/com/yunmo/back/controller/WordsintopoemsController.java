package com.yunmo.back.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yunmo.back.pojo.Questionbank;
import com.yunmo.back.pojo.Wordsintopoems;
import com.yunmo.back.service.IWordsintopoemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wwj
 * @since 2024-09-17
 */
@RestController
@RequestMapping("/wordsintopoems")
public class WordsintopoemsController {
    @Autowired
    IWordsintopoemsService wordsintopoemsService;
    @RequestMapping("/list")
    Wordsintopoems wordsintopoems(){
        int count = wordsintopoemsService.count();
        int randomCount =(int) (Math.random()*count);
        if (randomCount > count) {
            randomCount = count;
        }
        QueryWrapper<Wordsintopoems> wrapper = new QueryWrapper<>();
        wrapper.last("limit "+ randomCount +", 1");
        return  wordsintopoemsService.getOne(wrapper);
    }
}

