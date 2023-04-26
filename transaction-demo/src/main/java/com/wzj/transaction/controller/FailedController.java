package com.wzj.transaction.controller;

import com.wzj.transaction.entity.User;
import com.wzj.transaction.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wzj on 2023/4/26 23:01
 * 事务不生效: 没有生效@Transactional加了相当于没加
 */
@RestController
@RequestMapping("/transaction")
public class FailedController {

    @Autowired
    private UserServiceImpl userService;  // 为了方便测试直接用的实现类

    @PostMapping("/failed")
    public void failed(@RequestBody User user){
        userService.failed(user);
    }

    @PostMapping("/failed2")
    public void failed2(@RequestBody User user){
        userService.failed2(user);
    }
}
