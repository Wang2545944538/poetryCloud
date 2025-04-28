package com.yunmo.back.controller;


import com.alibaba.fastjson.JSONObject;
import com.yunmo.back.pojo.Users;
import com.yunmo.back.service.ICommentService;
import com.yunmo.util.JwtUtils;
import com.yunmo.util.ResponseMsg;
import io.jsonwebtoken.Claims;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wwj
 * @since 2024-09-12
 */
@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    ICommentService commentService;
    @RequestMapping("getComments")
    ResponseMsg getComments(@RequestParam("poemId") Integer poemId){
        return new ResponseMsg(200,commentService.getComments(poemId));
    }
    @RequestMapping("/submitComment")
    ResponseMsg submitComment(@RequestBody newComment comment, HttpServletRequest request){
        String token = request.getHeader("token");
        Claims claims = JwtUtils.parseJWT(token);
        Integer user_id = JSONObject.parseObject(claims.getSubject(), Users.class).getUser_id();
        return new ResponseMsg(200,commentService.submitComment(comment.getPoem_id(),comment.getContent(),user_id));
    }

    @Data
    static class newComment{
        Integer poem_id;
        String content;
    }
}

