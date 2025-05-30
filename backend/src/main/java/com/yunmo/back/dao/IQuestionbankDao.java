package com.yunmo.back.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yunmo.back.pojo.Questionbank;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wwj
 * @since 2024-09-15
 */
public interface IQuestionbankDao extends BaseMapper<Questionbank> {
    List<Questionbank> questionBankList(@Param(Constants.WRAPPER) QueryWrapper<Questionbank> wrapper);
    IPage<Questionbank> pageList(Page page, @Param(Constants.WRAPPER) QueryWrapper<Questionbank> wrapper);
}
