package com.yunmo.back.service.impl;

import com.yunmo.back.pojo.Models;
import com.yunmo.back.dao.IModelsDao;
import com.yunmo.back.service.IModelsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wwj
 * @since 2024-09-12
 */
@Service
public class ModelsServiceImpl extends ServiceImpl<IModelsDao, Models> implements IModelsService {

    @Autowired
    IModelsDao modelsDao;


    @Override
    public List<Models> getModelsById(Integer userId) {
        List<Models> allModels = modelsDao.getModelsById(userId);
        return allModels.stream()
                .filter(model -> !Arrays.asList(4, 15, 16, 17).contains(model.getModelId()))
                .collect(Collectors.toList());
    }


}
