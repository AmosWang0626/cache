package com.amos.mall.cache.web.cache;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * DESCRIPTION: Redis
 *
 * @author <a href="mailto:daoyuan0626@gmail.com">amos.wang</a>
 * @date 2020/11/5
 */
@RestController
@RequestMapping("redis")
public class RedisController {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * test jvm prometheus
     */
    private static final List<String> NAME_LIST = new ArrayList<>();

    @GetMapping("save/{name}")
    public String save(@PathVariable("name") String name) {

        redisTemplate.opsForList().rightPush("name_list", name);

        NAME_LIST.add("name:" + UUID.randomUUID());

        return name;
    }

    @GetMapping("all")
    public List<Object> all() {

        NAME_LIST.addAll(new ArrayList<>(NAME_LIST));
        System.out.println(NAME_LIST);

        return redisTemplate.opsForList().range("name_list", 0, -1);
    }

}
