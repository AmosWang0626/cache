package com.amos.mall.cache.hystrix.command;

import com.amos.mall.cache.config.ApplicationBeanUtils;
import com.netflix.hystrix.*;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.ResponseEntity;

/**
 * DESCRIPTION: 商品 Command
 *
 * @author <a href="mailto:daoyuan0626@gmail.com">amos.wang</a>
 * @date 2020/11/7
 */
public class ProductCommand extends HystrixCommand<JSONObject> {

    private final String name;

    public ProductCommand(String name) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ProductGroup"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("ProductCommand"))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        // 时间窗口内，通过短路器的流量达到10个，就判断是否要开启短路器
                        .withCircuitBreakerRequestVolumeThreshold(10)
                        // 异常率达到40%，就打开短路器
                        .withCircuitBreakerErrorThresholdPercentage(40)
                        // 打开短路器6秒后，half-open，让一个请求通过短路器，试探一下
                        .withCircuitBreakerSleepWindowInMilliseconds(6000)
                        // 超时配置
                        .withExecutionTimeoutInMilliseconds(1000)
                        // 降级 隔离 信号量 最大并发请求数
                        .withFallbackIsolationSemaphoreMaxConcurrentRequests(30))
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("ProductThreadPool"))
                .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter()
                        // 线程池大小
                        .withCoreSize(10)
                        // 下边这两个参数，取较小的值
                        // 1. 等待队列，缓存队列最大容量
                        .withMaxQueueSize(12)
                        // 2. 等待队列达到这个容量，就会触发拒绝逻辑
                        .withQueueSizeRejectionThreshold(15))
        );
        this.name = name;
    }

    @Override
    protected JSONObject run() throws Exception {
        if ("1".equals(name)) {
            throw new RuntimeException("The Product not found!");
        }

        if ("2".equals(name)) {
            Thread.sleep(1000);
        }

        String url = "http://localhost:8802/product/" + name;
        ResponseEntity<String> responseEntity = ApplicationBeanUtils.getRestTemplate().getForEntity(url, String.class);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return new JSONObject(responseEntity.getBody());
        }

        return null;
    }

//    @Override
//    protected String getCacheKey() {
//        return "product_cache_key_" + name;
//    }

    @Override
    protected JSONObject getFallback() {
        JSONObject object = new JSONObject();

        try {
            object.put("名字", "MacBook Pro 2020");
            object.put("处理器", "8 核 Intel Core i9");
            object.put("内存", "64GB");
            object.put("磁盘", "8T SSD");
            object.put("请求状态", "请求异常，降级逻辑");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return object;
    }
}
