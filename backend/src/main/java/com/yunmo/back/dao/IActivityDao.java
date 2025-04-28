package com.yunmo.back.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yunmo.back.pojo.Activity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wwj
 * @since 2024-09-13
 */
public interface IActivityDao extends BaseMapper<Activity> {
    List<Activity> selectAllActive();
    IPage<Activity> activityIPage(Page page, @Param(Constants.WRAPPER) QueryWrapper<Activity> wrapper);
}
