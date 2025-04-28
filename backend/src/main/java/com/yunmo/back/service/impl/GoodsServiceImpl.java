package com.yunmo.back.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yunmo.back.pojo.Goods;
import com.yunmo.back.dao.IGoodsDao;
import com.yunmo.back.service.IGoodsService;
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
public class GoodsServiceImpl extends ServiceImpl<IGoodsDao, Goods> implements IGoodsService {
@Autowired
IGoodsDao goodsDao;

    @Override
    public IPage<Goods> pageList(IPage<Goods> page, Wrapper<Goods> wrapper) {
        return goodsDao.pageList(page,wrapper);
    }

    @Override
    public Goods findById(int id) {
        return goodsDao.findById(id);
    }
}
