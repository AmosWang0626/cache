package com.amos.mall.cache.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * PROJECT: buffer
 * DESCRIPTION: note
 *
 * @author daoyuan
 * @date 2020/10/7 10:06
 */
@RestController
@RequestMapping("hello")
public class HelloController {

    @Value("${spring.application.name}")
    private String appName;

    @GetMapping
    public String hello() {
        return "Hello " + appName + "!";
    }

}
