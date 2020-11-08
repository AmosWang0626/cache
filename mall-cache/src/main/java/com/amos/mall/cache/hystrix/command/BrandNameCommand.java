package com.amos.mall.cache.hystrix.command;

import com.amos.mall.cache.local.BrandNameCache;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

/**
 * DESCRIPTION: 品牌名称 Command
 *
 * @author <a href="mailto:daoyuan0626@gmail.com">amos.wang</a>
 * @date 2020/11/8
 */
public class BrandNameCommand extends HystrixCommand<String> {

    private final Long id;

    public BrandNameCommand(Long id) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("BrandGroup")));
        this.id = id;
    }

    @Override
    protected String run() throws Exception {

        throw new RuntimeException("The Brand not found!");
    }

    @Override
    protected String getFallback() {
        // 降级逻辑，从本地缓存中获取品牌名称
        String brandName = BrandNameCache.getBrandName(id);

        // 如果本地内存也没有
        if (brandName == null) {
            brandName = "未注册";
        }

        return brandName;
    }
}
