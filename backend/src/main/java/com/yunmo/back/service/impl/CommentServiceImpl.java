package com.yunmo.back.service.impl;

import com.yunmo.back.pojo.Comment;
import com.yunmo.back.dao.ICommentDao;
import com.yunmo.back.service.ICommentService;
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
public class CommentServiceImpl extends ServiceImpl<ICommentDao, Comment> implements ICommentService {
    @Autowired
    ICommentDao commentDao;
    @Override
    public List<Comment> getComments(Integer poemId) {
        return commentDao.getComments(poemId);
    }

    @Override
    public Integer submitComment(Integer poem_id, String content, Integer user_id) {
        return commentDao.submitComment(poem_id,content,user_id);
    }
}
