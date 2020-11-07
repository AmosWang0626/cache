package com.amos.mall.cache.web;

import com.amos.mall.cache.hystrix.command.ProductCommand;
import com.amos.mall.cache.hystrix.command.ProductsCommand;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixObservableCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rx.Observable;
import rx.Observer;

/**
 * 模块名称: cache
 * 模块描述: 商品服务暴露接口
 *
 * @author amos.wang
 * @date 11/6/2020 6:25 PM
 */
@RestController
@RequestMapping("product")
public class ProductController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    @GetMapping("{name}")
    public String getProduct(@PathVariable("name") String name) {
        HystrixCommand<JSONObject> command = new ProductCommand(name);

        /// command.execute() 同步方式执行; command.queue() 异步方式执行
        JSONObject object = command.execute();
        LOGGER.info("获取商品信息 >>> [{}]", object);

        /// 异步拿到接口返回结果
        // Future<JSONObject> queue = new ProductCommand(name).queue();
        // try {
        //     Thread.sleep(100);
        //     if (queue.isDone()) {
        //         LOGGER.info("feature >>> [{}]", queue.get());
        //     }
        // } catch (InterruptedException | ExecutionException e) {
        //     e.printStackTrace();
        // }

        return object.toString();
    }

    @GetMapping("many/{names}")
    public String getProducts(@PathVariable("names") String names) {
        HystrixObservableCommand<JSONObject> command = new ProductsCommand(names.split(","));

        /// command.observe() 立即执行; command.toObservable() 等observe.subscribe()订阅之后开始执行
        Observable<JSONObject> observe = command.observe();

        observe.subscribe(new Observer<JSONObject>() {
            @Override
            public void onCompleted() {
                LOGGER.info("获取商品信息完成");
            }

            @Override
            public void onError(Throwable e) {
                LOGGER.error("获取商品信息失败", e);
            }

            @Override
            public void onNext(JSONObject object) {
                LOGGER.info("获取商品信息 >>> [{}]", object);
            }
        });

        return "success";
    }

}
