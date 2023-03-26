package com.wzj.dynamic.mutil.datasource.service.impl;

import com.wzj.dynamic.mutil.datasource.compoent.Ds;
import com.wzj.dynamic.mutil.datasource.compoent.DynamicDataSourceConstants;
import com.wzj.dynamic.mutil.datasource.compoent.DynamicDataSourceContextHolder;
import com.wzj.dynamic.mutil.datasource.entity.User;
import com.wzj.dynamic.mutil.datasource.mapper.UserMapper;
import com.wzj.dynamic.mutil.datasource.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wzj on 2023/3/26 19:45
 */
@Ds(DynamicDataSourceConstants.DS2_ROUTEING_KEY)
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findById(String id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public User findById2(String id) {
        /**
         * 没使用AOP之前: 手动设置路由到哪个数据源, 然后移除
         */
        DynamicDataSourceContextHolder.set(DynamicDataSourceConstants.DS2_ROUTEING_KEY);
        User user = userMapper.selectByPrimaryKey(id);
        DynamicDataSourceContextHolder.remove();
        return user;
    }

    @Ds
    @Override
    public User findById3(String id) {
        /**
         * 使用AOP之后
         */
        return userMapper.selectByPrimaryKey(id);
    }
}
