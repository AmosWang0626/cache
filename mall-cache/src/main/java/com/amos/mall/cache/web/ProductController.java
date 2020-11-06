package com.amos.mall.cache.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * 模块名称: cache
 * 模块描述: 商品服务暴露接口
 *
 * @author amos.wang
 * @date 11/6/2020 6:25 PM
 */
@RestController
@RequestMapping("product")
public class ProductController {

    @Resource
    private RestTemplate restTemplate;

    @GetMapping
    public String get() {
        String url = "http://localhost:8802/product";
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity.getBody();
        }

        return "系统繁忙，请稍后重试！";
    }

}
