package com.wzj.dynamic.mutil.datasource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * Created by wzj on 2023/3/26 19:39
 */
@MapperScan("com.wzj.dynamic.mutil.datasource.mapper")
@SpringBootApplication
public class DynamicMutilDataSourceApplication {
    public static void main(String[] args) {
        SpringApplication.run(DynamicMutilDataSourceApplication.class, args);
    }
}
