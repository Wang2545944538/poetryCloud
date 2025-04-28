package com.yunmo.back.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yunmo.back.pojo.Users_collection;
import com.yunmo.back.dao.IUsers_collectionDao;
import com.yunmo.back.service.IUsers_collectionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wwj
 * @since 2024-09-12
 */
@Service
public class Users_collectionServiceImpl extends ServiceImpl<IUsers_collectionDao, Users_collection> implements IUsers_collectionService {
    @Autowired
    IUsers_collectionDao usersCollectionDao;
    @Override
    public IPage<Users_collection> getAllCollectionById(Page page, QueryWrapper<Users_collection> wrapper) {
        return usersCollectionDao.getAllCollectionById(page,wrapper);
    }

    @Override
    public List<Users_collection> getAllCollection(QueryWrapper<Users_collection> wrapper) {
        return usersCollectionDao.getAllCollection(wrapper);
    }
}
