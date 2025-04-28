package com.yunmo.back.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.yunmo.back.pojo.Goods;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wwj
 * @since 2024-09-12
 */
public interface IGoodsService extends IService<Goods> {
    IPage<Goods> pageList(IPage<Goods> page,
                          @Param(Constants.WRAPPER) Wrapper<Goods> wrapper);
    Goods findById(int id);
}
