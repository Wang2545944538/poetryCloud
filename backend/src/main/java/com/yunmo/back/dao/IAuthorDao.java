package com.yunmo.back.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yunmo.back.pojo.Author;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yunmo.back.utileClass.DynastyAuthor;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wwj
 * @since 2024-09-12
 */
public interface IAuthorDao extends BaseMapper<Author> {

    List<DynastyAuthor> findByDynastyidAuthor(Integer id);

    Page<DynastyAuthor> findDynastyAuthorByPage(Page<DynastyAuthor> pagination);

}
