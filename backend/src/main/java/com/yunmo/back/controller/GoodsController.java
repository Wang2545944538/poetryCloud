package com.yunmo.back.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yunmo.back.pojo.Goods;
import com.yunmo.back.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wwj
 * @since 2024-09-12
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    IGoodsService goodsService;
    @RequestMapping("/page")
    IPage<Goods> page(@RequestParam(defaultValue = "1") int page,
                      @RequestParam(defaultValue = "5") int limit){
        IPage<Goods> page1=new Page<>(page,limit);
        QueryWrapper<Goods> wrapper=new QueryWrapper<>();
        return goodsService.pageList(page1,wrapper);
    }
    @RequestMapping("/list")
    List<Goods> list(){
        return goodsService.list();
    }
    @RequestMapping("/delete/{id}")
    void delete(@RequestParam int id){
        goodsService.removeById(id);
    }
    @PostMapping("/update")
    Integer update(@RequestBody Goods goods){
        return goodsService.updateById(goods)?1:0;
    }
    @RequestMapping("/findById")
    Goods findById(@RequestParam int id){
        return goodsService.findById(id);
    }
    @RequestMapping("/add")
    void add(@RequestBody Goods goods){
        goodsService.save(goods);
    }

    @RequestMapping("/delete")
    boolean delete(Integer id){
         return goodsService.removeById(id);
    }
}

