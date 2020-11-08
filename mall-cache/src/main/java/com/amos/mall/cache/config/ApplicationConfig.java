package com.amos.mall.cache.config;

import com.amos.mall.cache.hystrix.cache.filter.HystrixRequestCacheFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
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
public class ApplicationConfig {

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        ApplicationBeanUtils.setRestTemplate(restTemplate);

        return restTemplate;
    }

    @Bean
    public FilterRegistrationBean<HystrixRequestCacheFilter> requestCacheFilter() {
        FilterRegistrationBean<HystrixRequestCacheFilter> requestCacheFilter = new FilterRegistrationBean<>(new HystrixRequestCacheFilter());

        requestCacheFilter.addUrlPatterns("/*");

        return requestCacheFilter;
    }

}
