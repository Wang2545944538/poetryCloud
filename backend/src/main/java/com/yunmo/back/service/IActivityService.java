package com.yunmo.back.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yunmo.back.pojo.Activity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wwj
 * @since 2024-09-13
 */
public interface IActivityService extends IService<Activity> {
    List<Activity> selectAllActive();
    IPage<Activity> activityIPage(Page page, QueryWrapper<Activity> wrapper);
}
