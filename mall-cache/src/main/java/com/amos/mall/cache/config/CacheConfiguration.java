package com.amos.mall.cache.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * 模块名称: cache
 * 模块描述: 缓存服务配置
 *
 * @author amos.wang
 * @date 11/6/2020 6:27 PM
 */
@Configuration
public class CacheConfiguration {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
