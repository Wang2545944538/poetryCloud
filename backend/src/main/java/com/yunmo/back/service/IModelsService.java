package com.yunmo.back.service;

import com.yunmo.back.pojo.Models;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wwj
 * @since 2024-09-12
 */
public interface IModelsService extends IService<Models> {


    List<Models> getModelsById(Integer userId);
}
