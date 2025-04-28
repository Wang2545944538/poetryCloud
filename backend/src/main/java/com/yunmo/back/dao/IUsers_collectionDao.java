package com.yunmo.back.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yunmo.back.pojo.Users_collection;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wwj
 * @since 2024-09-12
 */
public interface IUsers_collectionDao extends BaseMapper<Users_collection> {
    IPage<Users_collection> getAllCollectionById(Page page, @Param(Constants.WRAPPER)QueryWrapper<Users_collection> wrapper);
    List<Users_collection> getAllCollection(@Param(Constants.WRAPPER)QueryWrapper<Users_collection> wrapper);
}
