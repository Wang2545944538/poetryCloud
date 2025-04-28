package com.yunmo.back.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yunmo.back.pojo.Questionbank;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wwj
 * @since 2024-09-15
 */
public interface IQuestionbankService extends IService<Questionbank> {
    List<Questionbank> questionBankList(QueryWrapper<Questionbank> wrapper);
    IPage<Questionbank> pageList(Page page, QueryWrapper<Questionbank> wrapper);
    Boolean addAQuestionBank(Questionbank questionbank);
    Boolean deleteQuestionBank(Integer id);
    Boolean deleteAll(List<Integer> id);

}
