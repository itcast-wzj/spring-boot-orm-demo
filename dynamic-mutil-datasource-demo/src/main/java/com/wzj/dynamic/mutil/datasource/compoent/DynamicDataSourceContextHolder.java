package com.wzj.dynamic.mutil.datasource.compoent;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

/**
 * Created by wzj on 2023/3/26 20:01
 */
@Slf4j
public class DynamicDataSourceContextHolder {
    private static final ThreadLocal<String> DYNAMIC_DATASOURCE_CONTEXT_HOLDER = new ThreadLocal<>();

    public static void set(String routingKey){
        log.info("切换数据源: {}", routingKey);
        DYNAMIC_DATASOURCE_CONTEXT_HOLDER.set(routingKey);
    }

    public static String get(){
        String routingKey = DYNAMIC_DATASOURCE_CONTEXT_HOLDER.get();
        log.info("获取数据源: {}", routingKey);
        return StringUtils.isNotBlank(routingKey) ? routingKey : DynamicDataSourceConstants.DS1_ROUTEING_KEY;
    }

    public static void remove(){
        DYNAMIC_DATASOURCE_CONTEXT_HOLDER.remove();
    }
}
