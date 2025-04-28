package com.yunmo.back.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.yunmo.back.pojo.Goods;
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
public interface IGoodsDao extends BaseMapper<Goods> {
    //自定义关联查询，能携带wrapper参数
    IPage<Goods> pageList(IPage<Goods> page,
                        @Param(Constants.WRAPPER) Wrapper<Goods> wrapper);
    Goods findById(int id);
}
