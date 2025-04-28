package com.yunmo.back.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yunmo.back.dao.IOptionsDao;
import com.yunmo.back.pojo.Questionbank;
import com.yunmo.back.dao.IQuestionbankDao;
import com.yunmo.back.service.IQuestionbankService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wwj
 * @since 2024-09-15
 */
@Service
public class QuestionbankServiceImpl extends ServiceImpl<IQuestionbankDao, Questionbank> implements IQuestionbankService {
    @Autowired
    IQuestionbankDao questionbankDao;
    @Autowired
    IOptionsDao optionsDao;
    @Override
    public List<Questionbank> questionBankList(QueryWrapper<Questionbank> wrapper) {
        return questionbankDao.questionBankList(wrapper);
    }

    @Override
    public IPage<Questionbank> pageList(Page page, QueryWrapper<Questionbank> wrapper) {
        return questionbankDao.pageList(page,wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean addAQuestionBank(Questionbank questionbank) {
        if (questionbank !=null){
            QueryWrapper<Questionbank> wrapper =new QueryWrapper<>();
            wrapper.eq("question",questionbank.getQuestion());
            Questionbank questionbank1=questionbankDao.selectOne(wrapper);
            if (questionbank1==null) {
                optionsDao.insert(questionbank.getOptions());
                Integer options_id = questionbank.getOptions().getOptions_id();
                questionbank.setOptions_id(options_id);
                questionbankDao.insert(questionbank);
                return true;
            }
                return false;
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteQuestionBank(Integer id) {
        if (id!=null) {
            QueryWrapper<Questionbank> wrapper = new QueryWrapper<>();
            wrapper.eq("questionBank_id", id);
            Questionbank questionbank = questionbankDao.selectOne(wrapper);
            optionsDao.deleteById(questionbank.getOptions_id());
            questionbankDao.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteAll(List<Integer> id) {
        if (id!=null ) {
            List<Questionbank> questionbanks = questionbankDao.selectBatchIds(id);
            List<Integer> options = new ArrayList<>();
            for (Questionbank q : questionbanks) {
                options.add(q.getOptions_id());
            }
            questionbankDao.deleteBatchIds(id);
            optionsDao.deleteBatchIds(options);
            return true;
        }
        return false;
    }

}
