package com.yunmo.back.controller;


import com.yunmo.back.common.ResponseMsg;
import com.yunmo.back.service.IThemeService;
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
@RequestMapping("/theme")
public class ThemeController {
    @Autowired
    IThemeService themeService;
    @RequestMapping("themeList")
    ResponseMsg themes(){
        return new ResponseMsg(200,themeService.list());
    }
}

