package com.yunmo.back.service;

import com.yunmo.back.pojo.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wwj
 * @since 2024-09-12
 */
public interface ICommentService extends IService<Comment> {
    List<Comment> getComments(Integer poemId);
    Integer submitComment(Integer poem_id, String content, Integer user_id);
}
