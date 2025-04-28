package com.yunmo.back.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yunmo.back.pojo.Activity;
import com.yunmo.back.dao.IActivityDao;
import com.yunmo.back.service.IActivityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wwj
 * @since 2024-09-13
 */
@Service
public class ActivityServiceImpl extends ServiceImpl<IActivityDao, Activity> implements IActivityService {
    @Autowired
    IActivityDao activityDao;
    @Override
    public List<Activity> selectAllActive() {
        return activityDao.selectAllActive();
    }

    @Override
    public IPage<Activity> activityIPage(Page page, QueryWrapper<Activity> wrapper) {
        return activityDao.activityIPage(page,wrapper);
    }
}
