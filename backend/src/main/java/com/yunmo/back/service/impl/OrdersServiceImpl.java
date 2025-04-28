package com.yunmo.back.service.impl;

import com.yunmo.back.pojo.Orders;
import com.yunmo.back.dao.IOrdersDao;
import com.yunmo.back.service.IOrdersService;
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
public class OrdersServiceImpl extends ServiceImpl<IOrdersDao, Orders> implements IOrdersService {

}
