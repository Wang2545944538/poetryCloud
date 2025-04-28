package com.yunmo.back.service.impl;

import com.yunmo.back.pojo.Message;
import com.yunmo.back.dao.IMessageDao;
import com.yunmo.back.service.IMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wwj
 * @since 2024-09-12
 */
@Service
public class MessageServiceImpl extends ServiceImpl<IMessageDao, Message> implements IMessageService {

}
