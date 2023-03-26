package com.wzj.dynamic.mutil.datasource.compoent;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * Created by wzj on 2023/3/26 19:56
 */
@Slf4j
public class DynamicDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        String routingKey = DynamicDataSourceContextHolder.get();
        log.info("最终路由到: {}", routingKey);
        return routingKey;
    }
}
