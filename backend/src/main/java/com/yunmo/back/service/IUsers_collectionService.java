package com.yunmo.back.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yunmo.back.pojo.Users_collection;
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
public interface IUsers_collectionService extends IService<Users_collection> {
    IPage<Users_collection> getAllCollectionById(Page page, QueryWrapper<Users_collection> wrapper);
    List<Users_collection> getAllCollection(QueryWrapper<Users_collection> wrapper);
}
