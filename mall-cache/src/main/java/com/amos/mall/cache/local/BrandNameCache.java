package com.amos.mall.cache.local;

import java.util.HashMap;
import java.util.Map;

/**
 * DESCRIPTION: 品牌名称缓存
 *
 * @author <a href="mailto:daoyuan0626@gmail.com">amos.wang</a>
 * @date 2020/11/8
 */
public class BrandNameCache {

    private static final Map<Long, String> BRAND_MAP = new HashMap<>();

    static {
        BRAND_MAP.put(1001L, "李宁");
        BRAND_MAP.put(1002L, "阿迪");
        BRAND_MAP.put(1003L, "耐克");
        BRAND_MAP.put(1004L, "彪马");
        BRAND_MAP.put(1005L, "斯凯奇");
    }

    /**
     * 获取品牌名称
     *
     * @param id 品牌 ID
     * @return 品牌名称
     */
    public static String getBrandName(Long id) {

        return BRAND_MAP.get(id);
    }

}
