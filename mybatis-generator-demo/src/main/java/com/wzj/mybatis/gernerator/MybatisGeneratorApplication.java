package com.wzj.mybatis.gernerator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * Created by wzj on 2023/3/26 12:27
 */

@MapperScan("com.wzj.mybatis.gernerator.mapper")
@SpringBootApplication
public class MybatisGeneratorApplication {
    public static void main(String[] args) {
        SpringApplication.run(MybatisGeneratorApplication.class, args);
    }
}
