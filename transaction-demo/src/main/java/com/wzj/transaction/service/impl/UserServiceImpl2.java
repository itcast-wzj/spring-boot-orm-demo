package com.wzj.transaction.service.impl;

import com.wzj.transaction.entity.User;
import com.wzj.transaction.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by wzj on 2023/4/26 22:37
 */
@Service
public class UserServiceImpl2 {

    @Autowired
    private UserMapper userMapper;

    //@Transactional(propagation = Propagation.NESTED) // 调用方fail4(没有异常插入成功), 被调用方(被回滚了)
    @Transactional(rollbackFor = Exception.class) // 调用方fail4(没有异常也被回滚掉了!!!!)
    public void doSomething(){
        User user = new User();
        user.setUsername("doSomething");
        user.setPassword("doSomething");
        user.setSex((byte) 0);
        userMapper.insertSelective(user);
        // 制造异常
        int i = 1/0;
    }
}
