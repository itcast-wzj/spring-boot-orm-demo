package com.wzj.transaction.controller;

import com.wzj.transaction.entity.User;
import com.wzj.transaction.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by wzj on 2023/4/26 23:00
 * 事务不回滚
 */
@RestController
@RequestMapping("transaction")
public class NoRollbackController {

    @Autowired
    private UserServiceImpl userService; // 为了方便测试直接用的实现类

    @PostMapping("/noRollback")
    public void noRollback(@RequestBody User user) throws Exception {
        userService.noRollback(user);
    }

    @PostMapping("/noRollback2")
    public void noRollback2(@RequestBody User user) throws Exception {
        userService.noRollback2(user);
    }

    @PostMapping("/noRollback3")
    public void noRollback3(@RequestBody User user) throws Exception {
        userService.noRollback3(user);
    }

    @PostMapping("/noRollback4")
    public void noRollback4(@RequestBody User user) throws Exception {
        userService.noRollback4(user);
    }
}
