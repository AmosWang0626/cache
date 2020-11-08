package com.amos.mall.cache.web.hystrix;

import com.amos.mall.cache.hystrix.command.BrandNameCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * DESCRIPTION: 品牌
 *
 * @author <a href="mailto:daoyuan0626@gmail.com">amos.wang</a>
 * @date 2020/11/8
 */
@RestController
@RequestMapping("brand")
public class BrandController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BrandController.class);

    @GetMapping
    public String name(Long id) {
        BrandNameCommand command = new BrandNameCommand(id);
        String name = command.execute();
        LOGGER.info("获取品牌名称: [{}], 来自降级结果? [{}]", name, command.isResponseFromFallback());

        return name;
    }

}
