package com.yunmo.back.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yunmo.back.dao.IUsers_collectionDao;
import com.yunmo.back.pojo.Collection;
import com.yunmo.back.dao.ICollectionDao;
import com.yunmo.back.pojo.Users_collection;
import com.yunmo.back.service.ICollectionService;
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
 * @since 2024-09-12
 */
@Service
public class CollectionServiceImpl extends ServiceImpl<ICollectionDao, Collection> implements ICollectionService {
    @Autowired
    ICollectionDao collectionDao;
    @Autowired
    IUsers_collectionDao usersCollectionDao;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean addPoems(int userId, Collection collection) {
        QueryWrapper<Users_collection> wrapper =new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        List<Users_collection> usersCollections = usersCollectionDao.getAllCollection(wrapper);
        List<Collection> collections = new ArrayList<>();
        for (Users_collection u:usersCollections) {
            collections.addAll(u.getCollection());
        }
        for (Collection c:collections) {
            if (c.getCollection_name().equals(collection.getCollection_name())){
                return false;
            }
        }
        if (userId!=0 && collection!=null){
            collectionDao.insert(collection);
            int collectionId = collection.getCollection_id();
            usersCollectionDao.insert( new Users_collection(collectionId,userId));
            return true;
        }
        return false;
    }
}
