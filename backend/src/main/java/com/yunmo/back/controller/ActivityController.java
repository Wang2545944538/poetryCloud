package com.yunmo.back.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yunmo.back.common.ResponseMsg;
import com.yunmo.back.pojo.Activity;
import com.yunmo.back.service.IActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wwj
 * @since 2024-09-13
 */
@RestController
@RequestMapping("/activity")
public class ActivityController {
    @Autowired
    IActivityService activityService;
    @RequestMapping("list")
    List<Activity> activities(){
        return activityService.selectAllActive();
    }
    @RequestMapping("page")
    IPage<Activity> activityIPage(@RequestParam(defaultValue = "1") Integer page,
                                  @RequestParam(defaultValue = "10")Integer limit){
        Page page1 =new Page<>(page,limit);
        QueryWrapper<Activity> wrapper = new QueryWrapper<>();
        return activityService.activityIPage(page1,wrapper);
    }
    @RequestMapping("/addActivity")
    ResponseMsg addActivity(@RequestBody Activity activity){
        if (activity!=null)
        {
            activityService.save(activity);
            return new ResponseMsg(200,"添加成功");
        }
        return new ResponseMsg(201,"添加失败");
    }
    @RequestMapping("updateActive")
    ResponseMsg updateActive(@RequestBody Activity activity){
        if (activity!=null){
            UpdateWrapper<Activity> wrapper =new UpdateWrapper<>();
            if ( activity.getActive_content()!=null && !activity.getActive_content().isEmpty() ){
                wrapper.set("active_content",activity.getActive_content());
            }
            if (activity.getActive_profile()!=null && !activity.getActive_profile().isEmpty()){
                wrapper.set("active_profile",activity.getActive_profile());
            }
            if (activity.getActive_img()!=null && !activity.getActive_img().isEmpty()){
                wrapper.set("active_img",activity.getActive_img());
            }
            if (activity.getActive_type_id()!=null && !activity.getActive_type_id().isEmpty()){
                wrapper.set("active_type_id",activity.getActive_type_id());
            }
            wrapper.eq("active_id",activity.getActive_id());
            activityService.update(wrapper);
            return new ResponseMsg(200,"修改成功");
        }
        return new ResponseMsg(201,"修改失败");
    }
    @RequestMapping("/deleteActive")
    ResponseMsg deleteActive(Integer id){
        if (activityService.removeById(id)){
            return new ResponseMsg(200,"删除成功");
        }
        return new ResponseMsg(201,"删除失败");
    }
    @RequestMapping("/deleteAllActivity")
    ResponseMsg deleteAllActivity(@RequestBody List<Integer> id){
        if (activityService.removeByIds(id)){
            return new ResponseMsg(200,"删除成功");
        }
        return new ResponseMsg(201,"删除失败");
    }
}

