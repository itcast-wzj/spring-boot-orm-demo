package com.wzj.dynamic.mutil.datasource.service;

import com.wzj.dynamic.mutil.datasource.entity.User;

/**
 * Created by wzj on 2023/3/26 19:44
 */
public interface UserService {
    User findById(String id);

    User findById2(String id);

    User findById3(String id);
}
