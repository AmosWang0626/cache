# Hystrix短路器执行原理

1. 经过短路器流量超过一定的阈值

   默认10秒，流量达到20个。
   10s内经过短路器的流量达到20个，如果达不到20个，就不会判短是否要走短路器。

2. 异常调用占比超过一定阈值

    默认50%。
    满足短路器流量超过一定阈值，同时异常访问，达到一定比例，就会开启短路器。

3. 短路器从close状态转换到open状态

4. 短路器打开时，所有经过该短路器的请求全部短路，直接走fallback降级

5. 短路恢复

    经过一段时间后，会触发half-open，短路器会让一条请求通过短路器，看能不能正常调用，
    如果调用成功了，就会自动将短路器状态转到close状态。

## 参数配置

- circuitBreakerRequestVolumeThreshold 时间窗口内，最少多少个请求，才触发开启短路器，默认20

   `HystrixCommandProperties.Setter().withCircuitBreakerRequestVolumeThreshold(20)`

- circuitBreakerSleepWindowInMilliseconds 需要在多次时间内直接reject请求，这段时间之后，会到half-open，默认5000毫秒
   `HystrixCommandProperties.Setter().withCircuitBreakerSleepWindowInMilliseconds(5000)`

- circuitBreakerErrorThresholdPercentage 异常请求量占比，达到这个百分比后，会打开短路器，默认50，也即50%

   `HystrixCommandProperties.Setter().withCircuitBreakerErrorThresholdPercentage(50)`

- circuitBreakerEnabled 是否允许短路器工作，默认true

   `HystrixCommandProperties.Setter().withCircuitBreakerEnabled(true)`

- circuitBreakerForceOpen 强制打开短路器

   `HystrixCommandProperties.Setter().withCircuitBreakerForceOpen(false)`

- circuitBreakerForceClosed 强制关闭短路器

   `HystrixCommandProperties.Setter().withCircuitBreakerForceClosed(false)`