package com.amos.mall.cache.config;

import org.springframework.web.client.RestTemplate;

/**
 * DESCRIPTION: 应用上下文
 *
 * @author <a href="mailto:daoyuan0626@gmail.com">amos.wang</a>
 * @date 2020/11/7
 */
public class ApplicationContext {

    private static RestTemplate restTemplate;

    public static void setRestTemplate(RestTemplate restTemplate) {
        ApplicationContext.restTemplate = restTemplate;
    }

    public static RestTemplate getRestTemplate() {
        return restTemplate;
    }

}
