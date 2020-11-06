package com.amos.mall.product.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * DESCRIPTION: hello
 *
 * @author <a href="mailto:daoyuan0626@gmail.com">amos.wang</a>
 * @date 2020/11/5
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
