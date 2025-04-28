package com.yunmo.back.dao;

import com.yunmo.back.pojo.Comment;
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
public interface ICommentDao extends BaseMapper<Comment> {
    List<Comment> getComments(@Param("poemId") Integer poemId);
    Integer submitComment(@Param("poem_id") Integer poem_id,
                          @Param("content") String content,
                          @Param("user_id") Integer user_id);
}
