package com.yunmo.back.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yunmo.back.common.ResponseMsg;
import com.yunmo.back.pojo.Poem;
import com.yunmo.back.pojo.Users;
import com.yunmo.back.service.IPoemService;
import com.yunmo.back.VO.aPoem;
import com.yunmo.back.VO.uPoem;
import com.yunmo.back.util.JwtUtils;
import lombok.Data;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
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
@RequestMapping("/poem")
public class PoemController {

    @Autowired
    IPoemService poemService;

    @RequestMapping("poemList")
    ResponseMsg poemList(){
        return new ResponseMsg(200,poemService.list());
    }

    @RequestMapping("getPoems")
    ResponseMsg getPoems(@RequestBody(required = false) Filter filter) {
        IPage<Poem> poems = poemService.getPoems(filter.getCurrentPage(), filter.getPageSize(),
                filter.getAuthorIds(), filter.getDynastyIds(),
                filter.getGenreIds(), filter.getThemeIds(), filter.getTitle());
        return new ResponseMsg(200, poems);
    }

//    @RequestMapping("getPoemList")
//    ResponseMsg getPoemList() {
//        // 设置为1和Integer.MAX_VALUE，表示获取所有记录
//        int currentPage = 1;
//        int pageSize = Integer.MAX_VALUE;
//        // 调用服务层的方法获取所有诗歌，不使用任何过滤条件
//        IPage<Poem> poems = poemService.getPoems(currentPage, pageSize, null, null, null, null);
//        // 返回结果
//        return new ResponseMsg(200, poems);
//    }

    @RequestMapping("getPoemDetails")
    ResponseMsg getPoemDetails(@RequestParam("poemId") Integer poem_id) {
        return new ResponseMsg(200, poemService.getPoemDetails(poem_id));
    }
    @Data
    static class Filter {
        Integer currentPage;
        Integer pageSize;
        List<Integer> authorIds;
        List<Integer> dynastyIds;
        List<Integer> genreIds;
        List<Integer> themeIds;
        String title;
    }
    @RequestMapping("updatePoem")
    ResponseMsg updatePoem(@RequestBody uPoem uPoem){
        Integer result = poemService.updatePoem(uPoem);
        if(result>=1){
            return new ResponseMsg(200,"修改成功");
        }else {
            return new ResponseMsg(500,"修改失败");
        }
    }
    @RequestMapping("deletePoem")
    ResponseMsg deletePoem(@RequestBody dPoem dPoem){
        Integer result = poemService.deletePoem(dPoem.getPoem_id(),dPoem.getPf_id());
        if(result==2){
            return new ResponseMsg(200,"删除成功");
        }else {
            return new ResponseMsg(500,"删除失败");
        }
    }
    @RequestMapping("addPoem")
    ResponseMsg addPoem(@RequestBody aPoem aPoem){
        Integer result = poemService.addPoem(aPoem);
        if(result==2){
            return new ResponseMsg(200,"添加成功");
        }else {
            return new ResponseMsg(500,"添加失败");
        }
    }
    @Data
    static class dPoem{
        Integer poem_id;
        Integer pf_id;
    }

    @RequestMapping("/list")
    ResponseMsg list (@RequestParam(defaultValue = "1") Integer page,
                      @RequestParam(defaultValue = "10000") Integer limit,
                        Poem poem){

        QueryWrapper<Poem> wrapper = new QueryWrapper<>();

        if (poem.getTitle() != null) {
            wrapper.like("title", poem.getTitle());
        }
        if (poem.getContent() != null) {
            wrapper.like("content", poem.getContent());
        }
        if (poem.getGenre_id() != 0) {
            wrapper.eq("theme_id", poem.getTheme_id());
        }
        if (poem.getPoem_id() != null) {
            wrapper.eq("poem_id", poem.getPoem_id());
        }
        if (poem.getAuthor_id() != null) {
            wrapper.eq("author_id", poem.getAuthor_id());
        }
        if (poem.getPf_id() != 0) {
            wrapper.eq("getPf_id", poem.getPf_id());
        }
        if (poem.getStatus() != 0) {
            wrapper.eq("status", poem.getStatus());
        }
        if (poem.getIntro() != null) {
            wrapper.like("intro", poem.getIntro());
        }
        IPage<Poem> page1 = new Page<>(page, limit);



        return new ResponseMsg(200, poemService.page(page1, wrapper));

    }


        @RequestMapping("/delete")
        ResponseMsg delete (Integer...ids){
            if (poemService.removeByIds(Arrays.asList(ids))) {
                return new ResponseMsg(200, "删除成功");
            }
            return new ResponseMsg(201, "删除失败");
        }

        @RequestMapping("/add")
        ResponseMsg add (Poem poem){
            if (poemService.save(poem)) {
                return new ResponseMsg(200, "添加成功");
            }
            return new ResponseMsg(201, "添加失败");
        }

        @RequestMapping("/update")
        ResponseMsg update (Poem poem){
            if (poemService.updateById(poem)) {
                return new ResponseMsg(200, "修改成功");
            }
            return new ResponseMsg(201, "修改失败");
        }


        // 分页查询所有自创诗词
        @RequestMapping("/getAllMyPoem")
        public Object getAllMyPoem (
        @RequestParam(value = "page", defaultValue = "1") int page,
        @RequestParam(value = "limit", defaultValue = "10") int limit){
            return poemService.getAllMyPoem(page, limit);
        }

        // 添加创作诗
        @RequestMapping("/addMyPoem")
        Object addMyPoem (@RequestBody Poem poem, HttpServletRequest request){
            String token = request.getHeader("token");
            try {
                // 从请求头中获取token，并解析以获取用户ID
                Claims claims = JwtUtils.parseJWT(token);
                System.out.println("解密后：" + claims.getSubject());
                Users u = JSONObject.parseObject(claims.getSubject(), Users.class);
                poem.setAuthor_id(u.getUser_id());
                poem.setStatus(0);
                poem.setIsself(1); // 设置为自创诗
                // 添加诗词
                boolean result = poemService.save(poem);
                return result;
            } catch (Exception e) {
                return "处理请求时出错: " + e.getMessage();
            }
        }

        // 查询当前用户的已发布创作诗
        @RequestMapping("/getMyPoem")
        public Object getMyPoem ( @RequestParam(value = "page", defaultValue = "1") int page,
        @RequestParam(value = "limit", defaultValue = "10") int limit,
        HttpServletRequest request){
            String token = request.getHeader("token");
            try {
                // 从请求头中获取token，并解析以获取用户ID
                Claims claims = JwtUtils.parseJWT(token);
                System.out.println("解密后：" + claims.getSubject());
                Users u = JSONObject.parseObject(claims.getSubject(), Users.class);
                return poemService.getMyPoem(String.valueOf(u.getUser_id()), page, limit); // 修改参数名称
            } catch (Exception e) {
                return "处理请求时出错: " + e.getMessage();
            }
        }

        // 查询当前用户的未发布创作诗
        @RequestMapping("/selectMyNotPublishPoems")
        public Object selectMyNotPublishPoems ( @RequestParam(value = "page", defaultValue = "1") int page,
        @RequestParam(value = "limit", defaultValue = "10") int limit,
        HttpServletRequest request){
            String token = request.getHeader("token");
            try {
                // 从请求头中获取token，并解析以获取用户ID
                Claims claims = JwtUtils.parseJWT(token);
                System.out.println("解密后：" + claims.getSubject());
                Users u = JSONObject.parseObject(claims.getSubject(), Users.class);
                return poemService.selectMyNotPublishPoems(String.valueOf(u.getUser_id()), page, limit); // 修改参数名称
            } catch (Exception e) {
                return "处理请求时出错: " + e.getMessage();
            }
        }


        // 删除创作诗
        @RequestMapping("/deleteMyPoem")
        public ResponseEntity<Integer> deleteMyPoem ( @RequestParam int poem_id){
            boolean removed = poemService.removeById(poem_id);
            if (removed) {
                return ResponseEntity.ok(1);
            } else {
                return ResponseEntity.badRequest().body(0);
            }
        }

        // 修改创作诗
        @PostMapping("/updateMyPoem")
        public Integer updateMyPoem (@RequestBody Poem poem){
            return poemService.updateById(poem) ? 1 : 0;
        }

        // 根据poem_id查询创作诗
        @GetMapping("/getMyPoemById")
        public Poem getMyPoemById ( @RequestParam("poem_id") int poem_id){
            return poemService.getById(poem_id);
        }

        // 管理员查询所有未审核的自创诗
        @RequestMapping("/getUncheckedPoems")
        public Object getUncheckedPoems (
                @RequestParam(value = "page", defaultValue = "1") int page,
                @RequestParam(value = "limit", defaultValue = "10") int limit) {
            return poemService.getUncheckedPoems(page, limit);
        }

        // 管理员查询所有已审核的自创诗
        @RequestMapping("/getCheckedPoems")
        public Object getCheckedPoems (
                @RequestParam(value = "page", defaultValue = "1") int page,
                @RequestParam(value = "limit", defaultValue = "10") int limit) {
            return poemService.getCheckedPoems(page, limit);
        }

        // 管理员审核通过，将poem表中的status字段由0改为1
        @RequestMapping("/adminPassPoem")
        public Integer adminPassPoem(@RequestParam Integer poem_id) {
            return poemService.adminPassPoem(poem_id);
        }

        // 管理员审核不通过，将poem表中的status字段由0改为2
        @RequestMapping("/adminRejectPoem")
        public Integer adminRejectPoem(@RequestParam Integer poem_id) {
           return poemService.adminRejectPoem(poem_id);
        }


        @RequestMapping("/size")
        ResponseMsg size(){
        Integer psize = poemService.count();
        return new ResponseMsg(200,psize);
        }

        // 管理员查询所有自创诗
        @RequestMapping("/adminSelectAllPoem")
        public Object adminSelectAllPoem (
                @RequestParam(value = "page", defaultValue = "1") int page,
                @RequestParam(value = "limit", defaultValue = "10") int limit) {
            return poemService.adminSelectAllPoem(page, limit);
        }

        // 管理员上架诗词
        @RequestMapping("/adminShelfPoem")
        public Integer adminShelfPoem(@RequestParam Integer poem_id) {
            return poemService.adminShelfPoem(poem_id);
        }

        // 管理员下架诗词
        @RequestMapping("/adminUnShelfPoem")
        public Integer adminUnShelfPoem(@RequestParam Integer poem_id) {
            return poemService.adminUnShelfPoem(poem_id);
        }
}


