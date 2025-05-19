package com.yunmo.back.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yunmo.back.pojo.Author;
import com.yunmo.back.dao.IAuthorDao;
import com.yunmo.back.service.IAuthorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yunmo.back.VO.DynastyAuthor;
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
public class AuthorServiceImpl extends ServiceImpl<IAuthorDao, Author> implements IAuthorService {
    @Autowired
    IAuthorDao authorDao;
    @Override
    public List<DynastyAuthor> findByDynastyidAuthor(Integer id) {
        return authorDao.findByDynastyidAuthor(id);
    }

    @Override
    public List<DynastyAuthor> findByDynastyAuthor() {
        return null;
    }

    @Override
    public Page<DynastyAuthor> findDynastyAuthorByPage(Page<DynastyAuthor> pagination) {
        // 直接使用传入的分页对象进行查询
        return authorDao.findDynastyAuthorByPage(pagination);
    }


}
