package com.wzj.dynamic.mutil.datasource.compoent;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wzj on 2023/3/26 19:53
 */
@Configuration
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class })
public class DynamicDataSourceConfig {

    @Primary
    @Bean
    public DataSource dynamicDataSource(){
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        dynamicDataSource.setTargetDataSources(createTargetDataSources());
        dynamicDataSource.setDefaultTargetDataSource(ds1());
        return dynamicDataSource;
    }

    private Map createTargetDataSources() {
        Map<Object, Object> targetDataSources = new HashMap<>(2);
        targetDataSources.put("ds1", ds1());
        targetDataSources.put("ds2", ds2());
        return targetDataSources;
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.ds1")
    public DataSource ds1() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.ds2")
    public DataSource ds2() {
        return DataSourceBuilder.create().build();
    }
}
