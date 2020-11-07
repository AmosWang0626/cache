package com.amos.mall.product.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.util.Random;
import java.util.UUID;

/**
 * DESCRIPTION: 商品
 *
 * @author <a href="mailto:daoyuan0626@gmail.com">amos.wang</a>
 * @date 2020/11/5
 */
@RestController
@RequestMapping("product")
public class ProductController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);


    @GetMapping
    public String get() {

        return random();
    }

    @GetMapping("{model}")
    public String get(@PathVariable("model") String model) {

        try {
            // 根据方法名调用本类中的其他方法
            return (String) this.getClass().getMethod("mac" + model).invoke(this);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            LOGGER.error("获取信息异常 [{}], 详细信息: [{}]",e.getClass().getSimpleName(),  e.getMessage());
        }

        return random();
    }

    private static String random() {
        JSONObject object = new JSONObject();
        try {
            object.put("ID", UUID.randomUUID().toString().replace("-", ""));
            object.put("名字", new Random().nextBoolean() ? "MacBook Pro 13英寸机型" : "MacBook Pro 16英寸机型");
            object.put("视网膜显示屏", new Random().nextBoolean() ? "13.3 英寸" : "16 英寸");
            object.put("处理器", new Random().nextBoolean() ? "4 核 Intel Core i7" : "8 核 Intel Core i9");
            object.put("内存", new Random().nextBoolean() ? "32GB" : "64GB");
            object.put("磁盘", new Random().nextBoolean() ? "4TB" : "8TB");
            object.put("图形处理器", new Random().nextBoolean() ? "Intel Iris Plus Graphics" : "最高可选配 AMD Radeon Pro 5600M (配备 8GB HBM2 显存)");
            object.put("电池续航", new Random().nextBoolean() ? "最长可达 10 小时" : "最长可达 11 小时");
            object.put("键盘和触控板", "背光妙控键盘、触控栏、触控 ID 和力度触控板");
        } catch (JSONException ignored) {
        }

        return object.toString();
    }

    public String mac13() {
        JSONObject object = new JSONObject();
        try {
            object.put("ID", UUID.randomUUID().toString().replace("-", ""));
            object.put("名字", "MacBook Pro 13英寸机型");
            object.put("视网膜显示屏", "13.3 英寸");
            object.put("处理器", "4 核 Intel Core i7");
            object.put("内存", "32GB");
            object.put("磁盘", "4TB");
            object.put("图形处理器", "Intel Iris Plus Graphics");
            object.put("电池续航", "最长可达 10 小时");
            object.put("键盘和触控板", "背光妙控键盘、触控栏、触控 ID 和力度触控板");
        } catch (JSONException ignored) {
        }

        return object.toString();
    }

    public String mac16() {
        JSONObject object = new JSONObject();
        try {
            object.put("ID", UUID.randomUUID().toString().replace("-", ""));
            object.put("名字", "MacBook Pro 16英寸机型");
            object.put("视网膜显示屏", "16 英寸");
            object.put("处理器", "8 核 Intel Core i9");
            object.put("内存", "64GB");
            object.put("磁盘", "8TB");
            object.put("图形处理器", "最高可选配 AMD Radeon Pro 5600M (配备 8GB HBM2 显存)");
            object.put("电池续航", "最长可达 11 小时");
            object.put("键盘和触控板", "背光妙控键盘、触控栏、触控 ID 和力度触控板");
        } catch (JSONException ignored) {
        }

        return object.toString();
    }

}
