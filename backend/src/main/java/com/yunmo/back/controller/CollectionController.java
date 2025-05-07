package com.yunmo.back.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yunmo.back.dao.ICollectionDao;
import com.yunmo.back.pojo.*;
import com.yunmo.back.pojo.Collection;
import com.yunmo.back.service.ICollectionService;
import com.yunmo.back.service.ILikeService;
import com.yunmo.back.service.IUsers_collectionService;
import com.yunmo.back.service.impl.PoemServiceImpl;
import com.yunmo.back.service.impl.Poem_collectionServiceImpl;
import com.yunmo.back.utileClass.UpdatePoems;
import com.yunmo.util.JwtUtils;
import com.yunmo.util.ResponseMsg;
import com.yunmo.back.utileClass.UCollection;
import io.jsonwebtoken.Claims;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wwj
 * @since 2024-09-12
 */
@RestController
@RequestMapping("/collection")
public class CollectionController {

    @Autowired
    ICollectionService collectionService;
    @Autowired
    ICollectionDao collectionDao;
    @Autowired
    Poem_collectionServiceImpl poem_collectionService;
    @Autowired
    IUsers_collectionService users_collectionService;

    @Autowired
    PoemServiceImpl poemService;

    @Autowired
    ILikeService likeService;

    @RequestMapping("/select")
    ResponseMsg list(@RequestParam(defaultValue = "1") Integer page,
                      @RequestParam(defaultValue = "10000") Integer limit, Collection collection){

        QueryWrapper<Collection> wrapper = new QueryWrapper<>();
        if (collection.getCollection_id()!=null){
            wrapper.eq("collection_id",collection.getCollection_id());
        }
        if (collection.getCollection_name()!=null){
            wrapper.like("collection_name",collection.getCollection_name());
        }
        IPage<Collection> page1 = new Page<>(page,limit);

        return new  ResponseMsg(200,collectionService.page(page1,wrapper)) ;
    }


    @RequestMapping("/getAllPoemsByCollection_id")
    ResponseMsg getAllPoemsByCollection_id(Collection collection,
                                           @RequestParam(defaultValue = "1")Integer page,@RequestParam(defaultValue = "3")Integer limit){
        QueryWrapper<Poem_collection> wrapper1 = new QueryWrapper<>();
        QueryWrapper<Poem> wrapper2 = new QueryWrapper<>();
        wrapper1.eq("collection_id",collection.getCollection_id());

        IPage<Poem> iPage = new Page<>(page,limit);
         List<Poem_collection> list1 =  poem_collectionService.list(wrapper1);
        System.out.println("list1 = " + list1);
        if (list1 != null && !list1.isEmpty()) {
            wrapper2.in("poem_id", list1.stream().map(Poem_collection::getPoem_id).toArray(Integer[]::new));
            return new ResponseMsg(200,poemService.page(iPage,wrapper2));
        }
        return new ResponseMsg(200,"");
    }


    @RequestMapping("/getCollectionByUserId")
    ResponseMsg getCollectionByUserId(HttpServletRequest request){
        String token = request.getHeader("token");
        Claims claims = JwtUtils.parseJWT(token);
        Integer user_id = JSONObject.parseObject(claims.getSubject(), Users.class).getUser_id();
        QueryWrapper<Users_collection> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",user_id);
        List<Users_collection> list1 = users_collectionService.list(wrapper);
        List<Integer> list2 = new ArrayList<>();
        for (Users_collection u:list1){
            list2.add(u.getCollection_id());
        }
        return new ResponseMsg(200,collectionService.listByIds(list2));
    }
    //收藏
    /*@RequestMapping("/doCollection")
    ResponseMsg doCollection(@RequestBody doCl doCl){
        Integer collection_id = doCl.getSelectedCollectionId();
        Integer poem_id = doCl.getPoem_id();
        QueryWrapper<Poem_collection> wrapper = new QueryWrapper<>();
        wrapper.eq("collection_id",collection_id);
        wrapper.eq("poem_id",poem_id);
        Poem_collection isExist = poem_collectionService.getOne(wrapper);
        if(isExist!=null){
            return new ResponseMsg(500,"诗已经存在于该诗集中");
        }else {
            return new ResponseMsg(200,poem_collectionService.save(new Poem_collection(collection_id,poem_id)));
        }
    }*/
    @RequestMapping("/doCollection")
    @Transactional(rollbackFor = Exception.class)
    public ResponseMsg doCollection(@RequestBody doCl doCl){
        Integer collection_id = doCl.getSelectedCollectionId();
        Integer poem_id = doCl.getPoem_id();

        QueryWrapper<Poem_collection> wrapper = new QueryWrapper<>();
        wrapper.eq("collection_id", collection_id);
        wrapper.eq("poem_id", poem_id);

        Poem_collection isExist = poem_collectionService.getOne(wrapper);
        if (isExist != null) {
            return new ResponseMsg(500, "诗已经存在于该诗集中");
        } else {
            boolean saved = poem_collectionService.save(new Poem_collection(collection_id, poem_id));
            if (saved) {
                // 更新 collection 表中的 collection_count +1
                UpdateWrapper<Collection> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("collection_id", collection_id).setSql("collection_count = collection_count + 1");
                collectionService.update(updateWrapper);
                return new ResponseMsg(200, "收藏成功");
            } else {
                return new ResponseMsg(201, "收藏失败");
            }
        }
    }
    @Data
    static class doCl{
        Integer poem_id;
        Integer selectedCollectionId;
    }
    @RequestMapping("addPoems")
    ResponseMsg addPoetryCollection(@RequestBody Collection collection,HttpServletRequest request){
        String token=request.getHeader("token");
        Claims claims = JwtUtils.parseJWT(token);
        int userId = JSONObject.parseObject(claims.getSubject(), Users.class).getUser_id();
        Boolean results=collectionService.addPoems(userId,collection);
        if (results){
            return new ResponseMsg(200,"创建成功");
        }
        return new ResponseMsg(201,"创建失败");
    }


    @PostMapping("/addLike")
    ResponseMsg addLike( Integer collection_id, HttpServletRequest request){
        String token=request.getHeader("token");
        Claims claims = JwtUtils.parseJWT(token);
        Integer userId = JSONObject.parseObject(claims.getSubject(), Users.class).getUser_id();



        System.out.println(userId);
        System.out.println(collection_id);

        QueryWrapper<Like> wrapper = new QueryWrapper<>();

        if (userId!=null && collection_id!=null){
            wrapper.eq("user_id",userId)
                    .or().eq("collection_id",collection_id);

            if (likeService.getOne(wrapper)==null){
                likeService.save(new Like(userId,collection_id));
                return new ResponseMsg(200,"添加成功");
            }
        }

        return new ResponseMsg(201,"添加失败");

    }

    @RequestMapping("/setOtherAvatar")
    ResponseMsg setOtherAvatar(@RequestBody Collection collection){
        UpdateWrapper<Collection> wrapper = new UpdateWrapper<>();
        wrapper.set("collection_avatar",collection.getCollection_avatar());
        wrapper.eq("collection_id",collection.getCollection_id());
        if (collectionService.update(wrapper)){
            return new ResponseMsg(200,"修改成功");
        }
        return new ResponseMsg(201,"修改失败");
    }



    @Transactional(rollbackFor = Exception.class)
    @RequestMapping("/add")
    public ResponseMsg add(@RequestBody UCollection uCollection){

        System.out.println(uCollection);
       if (collectionService.save(new Collection(uCollection.getCollection_name(),uCollection.getCollection_avatar(),uCollection.getCollection_count()))&&
               users_collectionService.save(new Users_collection
                       (uCollection.getCollection_id(),uCollection.getUser_id()))){
           return new ResponseMsg(200,"添加成功");
       }
        return new ResponseMsg(201,"添加失败");

    }



    @Transactional(rollbackFor = Exception.class)
    @RequestMapping("/delete")
    public ResponseMsg delete(@RequestBody Integer id){

        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("collection_id", id);

        boolean removeSuccess1 = collectionService.remove(new QueryWrapper<Collection>().eq("collection_id", id));
        boolean removeSuccess2 = users_collectionService.removeByMap(columnMap);
        boolean removeSuccess3 = poem_collectionService.removeByMap(columnMap);

        if (removeSuccess1 && removeSuccess2 && removeSuccess3) {
            return new ResponseMsg(200, "删除成功");
        }
        return new ResponseMsg(201, "删除失败");

    }


    @RequestMapping("/findByCollection_id")
    ResponseMsg findByCollection_id( Collection collection ){

        QueryWrapper<Collection> wrapper1 = new QueryWrapper<>();
        QueryWrapper<Users_collection> wrapper2 = new QueryWrapper<>();
        wrapper1.eq("collection_id",collection.getCollection_id());
        wrapper2.eq("collection_id",collection.getCollection_id());

        Collection collection1 = collectionService.getOne(wrapper1);
        Users_collection users_collection = users_collectionService.getOne(wrapper2);
        return new  ResponseMsg(200,new UCollection(
                    users_collection.getUser_id(),
                    collection1.getCollection_name(),
                    collection1.getCollection_avatar(),
                    collection1.getCollection_count(),
                    collection1.getCollection_id()));
    }

    @Transactional(rollbackFor = Exception.class)
    @RequestMapping("/update")
    public ResponseMsg update(@RequestBody UCollection uCollection){

        UpdateWrapper<Users_collection> wrapper1 = new UpdateWrapper<>();
        wrapper1.eq("collection_id",uCollection.getCollection_id());
        users_collectionService.remove(wrapper1);
        users_collectionService.save(new Users_collection
                (uCollection.getCollection_id(),uCollection.getUser_id()));
        collectionService.updateById
                (new Collection(uCollection.getCollection_id(),
                                uCollection.getCollection_name(),
                                uCollection.getCollection_avatar(),
                                uCollection.getCollection_count()));

        return new ResponseMsg(200,"修改成功");
    }

    @Transactional(rollbackFor = Exception.class)
    @RequestMapping("/poemsUpdate")
    public ResponseMsg poemsUpdate(@RequestBody UpdatePoems po){
        System.out.println("poems = " + po);
        if (po!=null) {
            int i = 0;
            List<Poem_collection> poem_collections = new ArrayList<>(Collections.emptyList());
            if (po.getPoems()!=null){
            for (Poem p : po.getPoems()) {
                poem_collections.add(new Poem_collection(po.getCollection_id(), p.getPoem_id()));
                i++;
            }}

            UpdateWrapper<Collection> wrapper2 = new UpdateWrapper<>();

            Map<String, Object> columnMap = new HashMap<>();
            columnMap.put("collection_id", po.getCollection_id());
            poem_collectionService.removeByMap(columnMap);
            poem_collectionService.saveBatch(poem_collections);

            wrapper2.eq("collection_id",po.getCollection_id());
            wrapper2.set("collection_count",i);
            collectionService.update(wrapper2);
            return new ResponseMsg(200, "修改成功");
        }


        return new ResponseMsg(201,"修改失败");
    }
}

