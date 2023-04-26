package com.wzj.transaction.service.impl;

import com.wzj.transaction.entity.User;
import com.wzj.transaction.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by wzj on 2023/4/26 21:19
 */
@Slf4j
@Service
public class UserServiceImpl{

    @Autowired
    private UserMapper userMapper;

    public void failed(User user) {
        step(user);
    }

    /**
     * 被@Transactional注解修饰的方法
     * 如果不是public或者是被final修饰了(idea会有红色波浪线提示),则事务不生效
     */
    @Transactional(rollbackFor = Exception.class)
    private /*final*/ void step(User user) {
        userMapper.insertSelective(user);
        int i = 1/0;
    }

    /**
     * 方法内部调用(一个被@Transaction修饰的方法调用另外一个被@Transaction修饰的方法)
     * 我在failed2不加try...catch, 现象: failed2和doSomething2方法都没插入成功
     * 我在failed2加try...catch,  现象: failed2和doSomething2方法都插入成功!!!, 注doSomething2里面有制造异常的
     *
     * 注:在doSomething2方法上, 无论使用以下哪个注解都是这种情况
     * @Transactional(rollbackFor = Exception.class)
     * @Transactional(rollbackFor = Exception.class, propagation = Propagation.NESTED)
     * @Transactional(propagation = Propagation.NESTED)
     *
     * 注: 和noRollback4方法那里截然不同, 可能就是一个内部方法调用, 一个调用其他类的方法的区别, 这里需要看源码理解！！！
     */
    @Transactional(rollbackFor = Exception.class)
    public void failed2(User user) {
        userMapper.insertSelective(user);
        try {
            doSomething2();
        }catch(Exception e){
            log.error("e:{}",e);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void doSomething2() {
        User user = new User();
        user.setUsername("doSomething2");
        user.setPassword("doSomething2");
        user.setSex((byte) 0);
        userMapper.insertSelective(user);
        // 制造异常
        int i = 1 / 0;
    }

    /////////////////////////////////以下是事务不回滚的坑/////////////////////////////////

    @Transactional(rollbackFor = Exception.class)
    public void noRollback(User user) throws Exception {
        try {
            /**
             * 一、
             * @Transactional默认只能回滚RuntimeException及其子类(好像还有Error),不能回滚Exception 疑问:为什么,在哪里定义了这个?
             * 如何支持回滚RuntimeException, 解决办法:  @Transactional(rollbackFor = Exception.class)即可
             */
            userMapper.insertSelective(user);
            int i = 1 / 0;
        }catch(Exception e){
            throw new Exception();
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void noRollback2(User user){
        try {
            userMapper.insertSelective(user);
            int i = 1 / 0;
        }catch(Exception e){
            // 二、虽然发生了异常,但是捕获异常后只是打印了日志,没有继续抛出异常, 所以@Transaction也没有起到作用
            log.error("fail2 e:{}", e);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void noRollback3(User user){
        // 还没测试
        userMapper.insertSelective(user);
        int i = 1 / 0;
    }

    @Autowired
    private UserServiceImpl2 userServiceImpl2;

    @Transactional(rollbackFor = Exception.class)
    public void noRollback4(User user){
        userMapper.insertSelective(user);
        try {
            userServiceImpl2.doSomething();
        }catch(Exception e){
            log.error("===================e:{}", e);
        }
    }
}
