package com.amos.mall.cache.config;

import org.springframework.web.client.RestTemplate;

/**
 * DESCRIPTION: 单例 bean 工具类
 *
 * @author <a href="mailto:daoyuan0626@gmail.com">amos.wang</a>
 * @date 2020/11/7
 */
public class ApplicationBeanUtils {

    private static RestTemplate restTemplate;

    public static void setRestTemplate(RestTemplate restTemplate) {
        ApplicationBeanUtils.restTemplate = restTemplate;
    }

    public static RestTemplate getRestTemplate() {
        return restTemplate;
    }

}
