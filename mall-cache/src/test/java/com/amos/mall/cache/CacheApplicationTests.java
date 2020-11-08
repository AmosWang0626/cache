package com.amos.mall.cache;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * 模块名称: cache
 * 模块描述: 缓存服务
 *
 * @author amos.wang
 * @date 11/6/2020 10:24 AM
 */
@SpringBootTest
public class CacheApplicationTests {

    @Resource
    private RestTemplate restTemplate;

    /**
     * 测试短路器
     * 1、一个时间窗口内，达到10个请求
     * 2、异常率达到40%，短路直接降级
     * 3、隔6秒之后，half-open请求试探
     */
    @Test
    void testCircuitBreaker() throws InterruptedException {
        String name = "13";
        String url = "http://localhost:8801/product/";

        getProductInfo(name, url, 6);

        // name = "1" 会触发异常逻辑
        name = "1";
        getProductInfo(name, url, 4);

        System.out.println("休眠一会儿，等待达到一个时间窗口（短路器一个时间窗口内会触发一次统计，check是否要开启短路器）");
        Thread.sleep(8000);

        name = "13";
        getProductInfo(name, url, 2);

        System.out.println("短路器已开启，再休眠一会儿，看看是否会尝试自动恢复");
        Thread.sleep(6000);

        name = "1";
        getProductInfo(name, url, 2);

        System.out.println("短路器已开启，再休眠一次，看看是否会尝试自动恢复");
        Thread.sleep(6000);

        name = "13";
        getProductInfo(name, url, 2);

    }

    private void getProductInfo(String name, String url, int pollNum) {
        for (int i = 0; i < pollNum; i++) {
            ResponseEntity<String> response = restTemplate.getForEntity(url + name, String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println(response.getBody());
            }
        }
    }

}
