package com.yunmo.back.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yunmo.back.dao.IOptionsDao;
import com.yunmo.back.pojo.Options;
import com.yunmo.back.pojo.Questionbank;
import com.yunmo.back.service.IOptionsService;
import com.yunmo.back.service.IQuestionbankService;
import com.yunmo.util.ResponseMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wwj
 * @since 2024-09-15
 */
@RestController
@RequestMapping("/questionbank")
public class QuestionbankController {
    @Autowired
    IQuestionbankService questionbankService;
    @Autowired
    IOptionsService optionsService;
    @RequestMapping("/list")
    List<Questionbank> questionbankList(){
        // 总记录数
        int count = questionbankService.count();
        int randomCount =(int) (Math.random()*count);
        if (randomCount > count-10) {
            randomCount = count-10;
        }
        QueryWrapper<Questionbank> wrapper = new QueryWrapper<>();
        wrapper.last("limit "+ randomCount +", 10");
        return questionbankService.questionBankList(wrapper);
    }
    @RequestMapping("page")
    IPage<Questionbank> questionbankIPage(@RequestParam(defaultValue = "1") Integer page,
                                          @RequestParam(defaultValue = "2") Integer limit){
        Page<Questionbank> page1 = new Page<>(page,limit);
        QueryWrapper<Questionbank> wrapper = new QueryWrapper<>();
       return questionbankService.pageList(page1,wrapper);
    }
    @RequestMapping("addQuestion")
    ResponseMsg addQuestionbank(@RequestBody Questionbank questionbank){
        if (questionbankService.addAQuestionBank(questionbank)){
            return new ResponseMsg(200,"添加成功");
        }
        return new ResponseMsg(201,"添加失败");
    }
    @RequestMapping("deleteQuestionBank")
    ResponseMsg deleteQuestionBank(Integer id){
        if (questionbankService.deleteQuestionBank(id)){
            return new ResponseMsg(200,"删除成功");
        }
        return new ResponseMsg(201,"删除失败");
    }
    //修改题库
    @RequestMapping("/updateQuestion")
    ResponseMsg updateQuestion(@RequestBody Questionbank questionbank){
        UpdateWrapper<Questionbank> wrapper = new UpdateWrapper<>();
        if ( questionbank.getQuestion()!=null && !questionbank.getQuestion().isEmpty()){
            wrapper.set("question",questionbank.getQuestion());
        }
        if (questionbank.getAnswer()!=null && !questionbank.getAnswer().isEmpty()){
            wrapper.set("answer",questionbank.getAnswer());
        }
        wrapper.eq("questionBank_id",questionbank.getQuestionBank_id());
        Boolean a=questionbankService.update(wrapper);

        UpdateWrapper<Options> wrapper1 = new UpdateWrapper<>();
        if (questionbank.getOptions().getA()!=null && !questionbank.getOptions().getA().isEmpty()){
            wrapper1.set("A",questionbank.getOptions().getA());
        }
        if (questionbank.getOptions().getB()!=null && !questionbank.getOptions().getB().isEmpty()){
            wrapper1.set("B",questionbank.getOptions().getB());
        }
        if (questionbank.getOptions().getC()!=null && !questionbank.getOptions().getC().isEmpty()){
            wrapper1.set("C",questionbank.getOptions().getC());
        }
        if (questionbank.getOptions().getD()!=null && !questionbank.getOptions().getD().isEmpty()){
            wrapper1.set("D",questionbank.getOptions().getD());
        }
        wrapper1.eq("options_id",questionbank.getOptions().getOptions_id());
        Boolean b=optionsService.update(wrapper1);
        if (a|| b){
            return  new ResponseMsg(200,"修改成功");
        }
        return  new ResponseMsg(201,"修改失败");
  }
    @RequestMapping("deleteAll")
    ResponseMsg deleteAll(@RequestBody List<Integer> id){
        if (questionbankService.deleteAll(id)){
            return new ResponseMsg(200,"删除成功");
        }
        return new ResponseMsg(201,"删除失败");
    }
}

