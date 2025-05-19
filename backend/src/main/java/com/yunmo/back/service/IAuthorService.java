package com.yunmo.back.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yunmo.back.pojo.Author;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yunmo.back.VO.DynastyAuthor;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wwj
 * @since 2024-09-12
 */
public interface IAuthorService extends IService<Author> {

    List<DynastyAuthor> findByDynastyidAuthor(Integer id);

    List<DynastyAuthor> findByDynastyAuthor();

    Page<DynastyAuthor> findDynastyAuthorByPage(Page<DynastyAuthor> pagination);
}
