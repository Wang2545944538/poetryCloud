package com.yunmo.back.service;

import com.yunmo.back.pojo.Collection;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wwj
 * @since 2024-09-12
 */
public interface ICollectionService extends IService<Collection> {
    Boolean addPoems(int userId,Collection collection);
}
