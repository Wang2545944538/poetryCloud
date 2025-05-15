package com.yunmo.back.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yunmo.back.pojo.Author;
import com.yunmo.back.pojo.Poem;
import com.yunmo.back.pojo.Users;
import com.yunmo.back.service.IAuthorService;
import com.yunmo.back.service.IPoemService;
import com.yunmo.back.utileClass.DynastyAuthor;
import com.yunmo.util.JwtUtils;
import com.yunmo.util.ResponseMsg;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wwj
 * @since 2024-09-12
 */
@RestController
@RequestMapping("/author")
public class AuthorController {
    @Autowired
    IAuthorService authorService;
    @Autowired
    IPoemService poemService;
    @RequestMapping("/authorList")
    public ResponseMsg authors() {
        List<Author> allAuthors = authorService.list();
        List<Author> first19 = allAuthors.size() > 19 ? allAuthors.subList(0, 19) : allAuthors;
        return new ResponseMsg(200, first19);
    }

    //根据id查找作者
    @RequestMapping("findByIdauthor")
    Author findByIdauthor(Integer id){
        return authorService.getById(id);
    }
    //根据id查找作者后台版
    @RequestMapping("byIdAuthor")
    ResponseMsg byIdAuthor(Integer author_id){
        ResponseMsg responseMsg = new ResponseMsg();
        Author author=authorService.getById(author_id);
        if (author!=null){
            responseMsg.setCode(200);
            responseMsg.setData(author); // 可以选择返回添加成功的诗人信息
        } else {
            responseMsg.setCode(500);
        }
        return responseMsg;
    }

    //根据诗人id查找诗的集合
    @RequestMapping("findByIdPoems")
    List<Poem> findByIdPoems(Integer id){
        return poemService.findByIdPoems(id);
    }
    //根据姓名模糊查询作者
    @RequestMapping("findByNameAuthor")
    IPage<Author> findByNameAuthor(@RequestParam(defaultValue = "1") int page,
                                   @RequestParam(defaultValue = "10") int limit,
                                         String author_name){
        QueryWrapper<Author> wrapper = new QueryWrapper<>();
        if(author_name != null && !author_name.isEmpty()){
            wrapper.like("author_name",author_name);
        }
        return authorService.page(new Page<>(page,limit),wrapper);
    }
    //查找不同朝代的诗人
    @RequestMapping("findByDynastyidAuthor")
    List<DynastyAuthor> findByDynastyidAuthor(Integer id){
            return authorService.findByDynastyidAuthor(id);
    }
    //查所有朝代的诗人
    @GetMapping("findByDynastyAuthor")
    public Page<DynastyAuthor> findByDynastyAuthor(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "limit", defaultValue = "4") int limit) {
        // 创建分页对象
        Page<DynastyAuthor> pagination = new Page<>(page, limit);
        // 调用服务层的方法进行分页查询
        return authorService.findDynastyAuthorByPage(pagination);
    }
    //更新头像
    @RequestMapping("/setAuthor")
    ResponseMsg setAuthor(String files, HttpServletRequest request){
        String token=request.getHeader("token");
        try {
            // 从请求头中获取token，并解析以获取用户ID
            Claims claims = JwtUtils.parseJWT(token);
            int author_id = (JSONObject.parseObject(claims.getSubject(), Author.class)).getAuthor_id();
            UpdateWrapper<Author> wrapper = new UpdateWrapper<>();
            wrapper.set("author_img",files);
            wrapper.eq("author_id",author_id);
            authorService.update(wrapper);
            return new ResponseMsg(200,files);
        }catch (Exception exception){
            exception.printStackTrace();
            return new ResponseMsg(500,"服务器异常");
        }
    }

    //更新诗人信息
    @PostMapping("/submitEditAuthor")
    ResponseMsg submitEditAuthor(@RequestBody Author author){
        System.out.println(author);
        ResponseMsg responseMsg = new ResponseMsg();
        UpdateWrapper<Author> wrapper = new UpdateWrapper<>();
        wrapper.eq("author_id",author.getAuthor_id());
        wrapper.set("author_name",author.getAuthor_name());
        wrapper.set("author_gender",author.getAuthor_gender());
        wrapper.set("author_img",author.getAuthor_img());
        wrapper.set("dynasty_id",author.getDynasty_id());
        wrapper.set("author_intro",author.getAuthor_intro());
        wrapper.set("author_style",author.getAuthor_style());
        boolean result= authorService.update(wrapper);
        if (result) {
            responseMsg.setCode(200);
        } else {
            responseMsg.setCode(500);
        }
        return responseMsg;
    }

    @PostMapping("/addAuthor")
    public ResponseMsg addAuthor(@RequestBody Author author) {
        ResponseMsg responseMsg = new ResponseMsg();
        try {
            // 保存诗人信息到数据库
            boolean result = authorService.save(author);
            if (result) {
                responseMsg.setCode(200);
                responseMsg.setData(author); // 可以选择返回添加成功的诗人信息
            } else {
                responseMsg.setCode(500);
            }
        } catch (Exception e) {
            responseMsg.setCode(500);
        }
        return responseMsg;
    }
    @GetMapping("deleteAuthor")
    public ResponseMsg deleteAuthor(Integer author_id){
        ResponseMsg responseMsg = new ResponseMsg();
        boolean result=authorService.removeById(author_id);
        if (result) {
            responseMsg.setCode(200);
        } else {
            responseMsg.setCode(500);
        }
         return responseMsg;
    }
}

