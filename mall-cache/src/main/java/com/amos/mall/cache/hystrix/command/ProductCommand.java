package com.amos.mall.cache.hystrix.command;

import com.amos.mall.cache.config.ApplicationBeanUtils;
import com.netflix.hystrix.*;
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
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("ProductThreadPool"))
                .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter().withCoreSize(10).withQueueSizeRejectionThreshold(5)));
        this.name = name;
    }

    @Override
    protected JSONObject run() throws Exception {
        String url = "http://localhost:8802/product/" + name;
        ResponseEntity<String> responseEntity = ApplicationBeanUtils.getRestTemplate().getForEntity(url, String.class);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return new JSONObject(responseEntity.getBody());
        }

        return null;
    }

    @Override
    protected String getCacheKey() {
        return "product_cache_key_" + name;
    }
}
