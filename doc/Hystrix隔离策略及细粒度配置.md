# Hystrix 隔离策略
- 线程隔离
- 信号量隔离


## 1、线程隔离（默认策略）
   > 线程池最大的好处就是对于网络访问请求，如果有超时的话，可以避免调用线程被hang住。

- 每个command运行在一个线程中，限流通过线程池的大小来控制。

   适合绝大多数的场景（99%），对依赖服务的网络请求的调用或访问，有timeout的问题。

- 设置隔离策略：
   ```java
   HystrixCommandProperties
   .Setter()
   .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.THREAD)
   ```


## 2、信号量隔离
   > 一般信号量适用于纯内存的一些业务逻辑服务，不涉及任何的网络访问请求。

- 每个command运行在调用线程中，通过信号量的容量来进行限流。

   通常针对超大并发量的场景下，每个服务实例每秒都几百的QPS。此时用线程池的话，线程一般不会太多，可能撑不住
   太高的并发，即便要撑住，可能要耗费大量的线程资源，那么就是用信号量，来进行限流保护。

   主要是对内部的一些比较复杂的业务逻辑的访问，不涉及网络请求、不需要捕获timeout之类的问题。
   比如算法 + 数据结构稍微耗时一些，需要做一个基本的资源隔离和访问，避免大量请求被hang住。

- Netflix 实际案例

   Netflix 有100+的command运行在40+的线程池中，只有少数的command是不运行在线程池中，就是从纯内存中获取
   一些元数据，或者是对多个command包装起来的facade command，是用信号量来限流的。

- 设置隔离策略：
   ```java
   HystrixCommandProperties
   .Setter()
   .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE)
   ```

## 3、细粒度配置

- `command group --> command key`
- `command thread pool --> command key`

   ```java
   // 注意: HystrixObservableCommand 不能设置 ThreadPoolKey; 
   Setter
   .withGroupKey(HystrixCommandGroupKey.Factory.asKey("ProductGroup"))
   .andCommandKey(HystrixCommandKey.Factory.asKey("ProductCommand"))
   .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("ProductThreadPool"))
   ```

- command key（Command，对应一个接口）

   一般代表底层依赖服务的一个接口

- command group（分组，对应一个服务）

   默认情况下，就是通过它来定义一个线程池，同时来聚合一些监控、统计和报警信息。

- command thread pool（线程池，特殊接口自定义线程池）

   默认为command group，当然也可以手动设置，做更细粒度的拆分。

   设置的话，就通过它来定义一个线程池，同时来聚合一些监控、统计和报警信息。

---

- coreSize（线程池大小）

   一般来说默认的10个就够了。

   ```java
   Setter
   .withGroupKey(HystrixCommandGroupKey.Factory.asKey("ProductGroup"))
   .andCommandKey(HystrixCommandKey.Factory.asKey("ProductCommand"))
   .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("ProductThreadPool"))
   .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter().withCoreSize(20))
   ```


- queueSizeRejectionThreshold（缓存队列大小）

   默认大小为5，如果线程池满了，会把后续请求放到队列中，队列满了，就会触发reject了。

   ```java
   HystrixThreadPoolProperties.Setter()
       .withCoreSize(20)
       .withQueueSizeRejectionThreshold(10)
   ```
  
- executionIsolationSemaphoreMaxConcurrentRequests（信号量大小）

   一般来说默认的10个就够了。

   ```java
   HystrixCommandProperties.Setter()
       .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE)
       .withExecutionIsolationSemaphoreMaxConcurrentRequests(10)
   ```







