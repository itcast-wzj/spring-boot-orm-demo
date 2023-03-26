package com.wzj.dynamic.mutil.datasource.controller;

import com.wzj.dynamic.mutil.datasource.entity.User;
import com.wzj.dynamic.mutil.datasource.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wzj on 2023/3/26 19:41
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 默认路由,走DS1库
     */
    @GetMapping("/findById1/{id}")
    public User findById1(@PathVariable("id") String id){
        return userService.findById(id);
    }

    /**
     * 手动指定路由到哪个库
     */
    @GetMapping("/findById2/{id}")
    public User findById2(@PathVariable("id") String id){
        return userService.findById2(id);
    }

    @GetMapping("/findById3/{id}")
    public User findById3(@PathVariable("id") String id){
        return userService.findById3(id);
    }
}
