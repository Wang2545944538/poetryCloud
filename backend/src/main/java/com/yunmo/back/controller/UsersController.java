package com.yunmo.back.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yunmo.back.common.ResponseMsg;
import com.yunmo.back.pojo.Users;
import com.yunmo.back.service.IUsersService;
import com.yunmo.back.util.JwtUtils;
import com.yunmo.back.util.RedisUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wwj
 * @since 2024-09-11
 */
@RestController
@RequestMapping("/users")
public class UsersController {
    @Autowired
    IUsersService usersService;
    @Autowired
    RedisUtils redisUtils;
    @RequestMapping("/login")
    ResponseMsg login(@RequestBody Users users){
        QueryWrapper<Users> wrapper = new QueryWrapper<>();
        wrapper.eq("username",users.getUsername());
        wrapper.eq("password",users.getPassword());
        Users u = usersService.getOne(wrapper);
        if (u != null)
        {
            //jwt token
            String uuid = UUID.randomUUID().toString();
            String subject = "{'userid':" + u.getUser_id() +
                    ",'account':'" + u.getUsername() +
                    "','nickname':'" + u.getNickname() +
                    "','avatar':'" + u.getAvatar() +
                    "','type_id':" + u.getType_id() + "}";

            String jwtToken = JwtUtils.createJWT(uuid, subject, 1000 * 60 * 3000); // 3000分钟
            redisUtils.set(jwtToken, users, 100000);
            return ResponseMsg.loginSuccess(jwtToken);
        }else {
            return ResponseMsg.loginFail();
        }
    }
    @RequestMapping("/register")
    ResponseMsg regist(@RequestBody Users users){
      Boolean u= usersService.save(users);
      if (u){
          return ResponseMsg.success("注册成功");
      }else {
          return ResponseMsg.fail("注册失败");
      }
    }
    @RequestMapping("/checkUsername")
    ResponseMsg checkUsername(String username){
        QueryWrapper<Users> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        Users users = usersService.getOne(wrapper);
        if (users==null){
            return ResponseMsg.success("用户名不存在");
        }else {
            return ResponseMsg.fail("该用户名已被注册");
        }
    }
    @RequestMapping("/checkPhoneNumber")
    ResponseMsg checkPhoneNumber(String phoneNumber){
        QueryWrapper<Users> wrapper = new QueryWrapper<>();
        wrapper.eq("phoneNumber",phoneNumber);
        Users users = usersService.getOne(wrapper);
        if (users==null){
            return ResponseMsg.success("电话号码不存在");
        }else {
            return ResponseMsg.fail("该电话号码已被注册");
        }
    }
    @RequestMapping("/setAvatar")
    ResponseMsg setAvatar(String files, HttpServletRequest request){
        String token=request.getHeader("token");
        try {
            // 从请求头中获取token，并解析以获取用户ID
            Claims claims = JwtUtils.parseJWT(token);
            int userId = (JSONObject.parseObject(claims.getSubject(), Users.class)).getUser_id();
            UpdateWrapper<Users> wrapper = new UpdateWrapper<>();
            wrapper.set("avatar",files);
            wrapper.eq("user_id",userId);
            usersService.update(wrapper);
            return new ResponseMsg(200,files);
        }catch (Exception exception){
            exception.printStackTrace();
            return new ResponseMsg(500,"服务器异常");
        }

    }
    @RequestMapping("/list")
    IPage<Users> usersIPage(@RequestParam(defaultValue = "1") Integer page,
                            @RequestParam(defaultValue = "5") Integer limit,
                            Users users,
                            HttpServletRequest request){
        Page<Users> usersPage = new Page<>(page,limit);
        String token = request.getHeader("token");
        Claims claims = JwtUtils.parseJWT(token);
        Integer userId =JSONObject.parseObject(claims.getSubject(), Users.class).getUser_id();
        QueryWrapper<Users> wrapper =new QueryWrapper<>();
        wrapper.select("user_id","username","avatar","nickname","money","type_id","phoneNumber","point");
        if (users.getUsername()!=null && !users.getUsername().isEmpty()){
            wrapper.like("username",users.getUsername());
        }if (users.getNickname()!=null && !users.getNickname().isEmpty()){
            // 如果已经有一个条件存在，则使用 or()
            if (wrapper.getExpression() != null) {
                wrapper.or();
            }
            wrapper.like("nickname",users.getNickname());
        }
        wrapper.ne("user_id",userId);
        return usersService.page(usersPage,wrapper);
    }

    @RequestMapping("/list1")
    List<Users> usersIPage1(){
        QueryWrapper<Users> wrapper =new QueryWrapper<>();
        wrapper.select("user_id","username","avatar","nickname","money","type_id","phoneNumber");
        return usersService.list(wrapper);
    }
    @RequestMapping("/updateUser")
    ResponseMsg updateUser(@RequestBody Users users){
        if (users!=null){
            UpdateWrapper<Users> wrapper=new UpdateWrapper<>();
           if (users.getAvatar()!=null && users.getAvatar().isEmpty()) {
               wrapper.set("avatar",users.getAvatar());
           }
           if (users.getNickname()!=null && users.getNickname().isEmpty()) {
               wrapper.set("nickname",users.getNickname());
           }if (users.getPhoneNumber()!=null && users.getPhoneNumber().isEmpty()) {
               wrapper.set("phoneNumber",users.getPhoneNumber());
           }if (users.getMoney()!=null ) {
               wrapper.set("money",users.getMoney());
           }if (users.getPoint()!=null) {
               wrapper.set("point",users.getPoint());
           }
           wrapper.eq("user_id",users.getUser_id());
           usersService.update(wrapper);
            return new ResponseMsg(200,"修改成功");
        }
        return new ResponseMsg(201,"修改失败");
    }
    @RequestMapping("/addScore")
    void addScore(Integer point,HttpServletRequest request){
        String token = request.getHeader("token");
        Claims claims = JwtUtils.parseJWT(token);
        Integer userId =JSONObject.parseObject(claims.getSubject(), Users.class).getUser_id();
        UpdateWrapper<Users> wrapper =new UpdateWrapper<>();

        wrapper.setSql("point = point + " + point);
        wrapper.eq("user_id",userId);
        usersService.update(wrapper);
    }
}

