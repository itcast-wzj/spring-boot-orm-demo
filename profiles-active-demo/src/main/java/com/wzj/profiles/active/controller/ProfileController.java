package com.wzj.profiles.active.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wzj on 2023/4/25 20:59
 */
@RestController
public class ProfileController {

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${profile.environment}")
    private String environment;

    @Value("${common.configuration}")
    private String commonConfiguration;

    /**
     * 默认为false, 如果在application.properties配置为isEnable:true, 就相当于覆盖了就会使用配置文件中的值
     */
    @Value("${isEnable:false}")
    private String isEnable;

    @GetMapping("/profiles")
    public String profiles(){
        return applicationName;
    }

    @GetMapping("/multiEnv")
    public String multiEnv(){
        String result = String.format("[%s]中的[%s]", environment, commonConfiguration);
        return result;
    }

    @GetMapping("/isEnable")
    public String isEnable(){
        String result = String.format("是否启用:[%s]",isEnable);
        return result;
    }
}
