package com.amos.mall.cache.hystrix.command;

import com.amos.mall.cache.local.CityNameCache;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;

/**
 * DESCRIPTION: 城市名字 Command
 *
 * @author <a href="mailto:daoyuan0626@gmail.com">amos.wang</a>
 * @date 2020/11/7
 */
public class CityNameCommand extends HystrixCommand<String> {

    private final Long cityId;

    public CityNameCommand(Long cityId) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("CityNameCommand"))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE)
                        .withExecutionIsolationSemaphoreMaxConcurrentRequests(10)));
        this.cityId = cityId;
    }

    @Override
    protected String run() throws Exception {

        // 模拟: 假设这个方法非常耗时，需要做相应的限流操作才能保证项目的高可用性
        return CityNameCache.getCityName(cityId);
    }

}
