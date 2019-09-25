# Caused by: java.lang.IllegalStateException: PathVariable annotation was empty on param 0.

```java
@FeignClient(value = "microservices-hello",configuration = FeignConfiguration.class, fallbackFactory = HiFactoryFeignApiHystrix.class)
public interface HiFactoryFeignApi {
    /**
     * say hi
     * @param somebody sb
     * @return wrapper
     */
    @GetMapping("/api/hi/{somebody}")
    Wrapper<String> sayHi(@PathVariable String somebody);
}
```

修改如下：

```java
@FeignClient(value = "microservices-hello",configuration = FeignConfiguration.class, fallbackFactory = HiFactoryFeignApiHystrix.class)
public interface HiFactoryFeignApi {
    /**
     * say hi
     * @param somebody sb
     * @return wrapper
     */
    @GetMapping("/api/hi/{somebody}")
    Wrapper<String> sayHi(@PathVariable("somebody") String somebody);
}
```

