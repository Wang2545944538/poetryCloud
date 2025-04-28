package com.yunmo.back.controller;


import com.yunmo.back.service.IGenreService;
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
@RequestMapping("/genre")
public class GenreController {
    @Autowired
    IGenreService genreService;
    @RequestMapping("genreList")
    ResponseMsg genres(){
        return new ResponseMsg(200,genreService.list());
    }
}

