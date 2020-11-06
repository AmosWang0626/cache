package com.amos.mall.cache.web;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.stats.CacheStats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * DESCRIPTION: caffeine
 *
 * @author <a href="mailto:daoyuan0626@gmail.com">amos.wang</a>
 * @date 2020/10/21
 * @see <a href="https://www.cnblogs.com/CrankZ/p/10889859.html"></a>
 */
@RestController
@RequestMapping("caffeine")
public class CaffeineController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CaffeineController.class);

    private static final Cache<String, Object> CACHE = Caffeine.newBuilder()
            .initialCapacity(256)
            // 保存缓存数量
            .maximumSize(100)
            // 从写入开始保存的时间
            .expireAfterWrite(10, TimeUnit.SECONDS)
            // 删除缓存原因
            .removalListener((key, value, cause) -> LOGGER.info(">>> 删除缓存 [{}]({}), reason is [{}]", key, value, cause))
            // 开启状态监控 [ 这样下边才能使用 CACHE.stats() ]
            .recordStats()
            .build();

    static {
        for (int i = 0; i < 150; i++) {
            CACHE.put("key" + i, "value_" + getValue());
        }
    }

    @PostMapping("save/{key}")
    public String save(@PathVariable("key") String key) {

        CACHE.put(key, UUID.randomUUID().toString());

        return String.valueOf(CACHE.getIfPresent(key));
    }

    @GetMapping("find/{key}")
    public String find(@PathVariable("key") String key) {

        LOGGER.info("缓存状态: [{}]", CACHE.stats());

        return String.valueOf(CACHE.getIfPresent(key));
    }

    @GetMapping("get/{key}")
    public String get(@PathVariable("key") String key) {

        LOGGER.info("缓存状态: [{}]", CACHE.stats());

        return String.valueOf(CACHE.get(key, s -> "GET_" + getValue()));
    }

    @GetMapping("stats")
    public String stats() throws JSONException {

        CacheStats stats = CACHE.stats();

        JSONObject object = new JSONObject();
        object.put("请求次数", stats.requestCount());
        object.put("命中次数", stats.hitCount());
        object.put("未命中次数", stats.missCount());
        object.put("加载成功次数", stats.loadSuccessCount());
        object.put("加载失败次数", stats.loadFailureCount());
        object.put("加载失败占比", stats.loadFailureRate());
        object.put("加载总耗时", stats.totalLoadTime());
        object.put("回收次数", stats.evictionCount());

        return object.toString();
    }


    private static String getValue() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
