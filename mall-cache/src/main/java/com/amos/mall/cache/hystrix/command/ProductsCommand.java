package com.amos.mall.cache.hystrix.command;

import com.amos.mall.cache.config.ApplicationBeanUtils;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixObservableCommand;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.ResponseEntity;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * DESCRIPTION: 商品集合 Command
 *
 * @author <a href="mailto:daoyuan0626@gmail.com">amos.wang</a>
 * @date 2020/11/7
 */
public class ProductsCommand extends HystrixObservableCommand<JSONObject> {

    private final String[] names;

    public ProductsCommand(String[] names) {
        super(HystrixCommandGroupKey.Factory.asKey("ProductCommand"));
        this.names = names;
    }

    @Override
    protected Observable<JSONObject> construct() {
        return Observable.unsafeCreate((Observable.OnSubscribe<JSONObject>) subscriber -> {
            try {
                for (String name : names) {
                    String url = "http://localhost:8802/product/" + name;
                    ResponseEntity<String> responseEntity = ApplicationBeanUtils.getRestTemplate().getForEntity(url, String.class);
                    if (responseEntity.getStatusCode().is2xxSuccessful()) {
                        subscriber.onNext(new JSONObject(responseEntity.getBody()));
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            subscriber.onCompleted();
        }).subscribeOn(Schedulers.io());
    }
}
