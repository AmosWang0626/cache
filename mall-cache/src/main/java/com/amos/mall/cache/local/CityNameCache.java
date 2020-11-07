package com.amos.mall.cache.local;

import java.util.HashMap;
import java.util.Map;

/**
 * DESCRIPTION: 城市名字缓存
 *
 * @author <a href="mailto:daoyuan0626@gmail.com">amos.wang</a>
 * @date 2020/11/7
 */
public class CityNameCache {

    private static final Map<Long, String> CITY_MAP = new HashMap<>();

    static {
        CITY_MAP.put(1000L, "北京");
        CITY_MAP.put(2000L, "上海");
        CITY_MAP.put(3000L, "广州");
    }

    /**
     * 获取城市名称
     *
     * @param id 城市 ID
     * @return 城市名称
     */
    public static String getCityName(Long id) {

        return CITY_MAP.get(id);
    }

}
