package com.yunmo.back.service.impl;

import com.yunmo.back.pojo.Options;
import com.yunmo.back.dao.IOptionsDao;
import com.yunmo.back.service.IOptionsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wwj
 * @since 2024-09-15
 */
@Service
public class OptionsServiceImpl extends ServiceImpl<IOptionsDao, Options> implements IOptionsService {
    @Autowired
    IOptionsDao optionsDao;

}
