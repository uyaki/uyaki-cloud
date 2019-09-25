# Calling [asyncError()] is not valid for a request with Async state [MUST_DISPATCH]

- Bug 内容

```bash
java.lang.IllegalStateException: Calling [asyncError()] is not valid for a request with Async state [MUST_DISPATCH]
	at org.apache.coyote.AsyncStateMachine.asyncError(AsyncStateMachine.java:440) ~[tomcat-embed-core-9.0.21.jar:9.0.21]
	at org.apache.coyote.AbstractProcessor.action(AbstractProcessor.java:512) [tomcat-embed-core-9.0.21.jar:9.0.21]
	at org.apache.coyote.Request.action(Request.java:430) ~[tomcat-embed-core-9.0.21.jar:9.0.21]
	at org.apache.catalina.core.AsyncContextImpl.setErrorState(AsyncContextImpl.java:401) ~[tomcat-embed-core-9.0.21.jar:9.0.21]
	at org.apache.catalina.connector.CoyoteAdapter.asyncDispatch(CoyoteAdapter.java:239) ~[tomcat-embed-core-9.0.21.jar:9.0.21]
	at org.apache.coyote.AbstractProcessor.dispatch(AbstractProcessor.java:241) [tomcat-embed-core-9.0.21.jar:9.0.21]
	at org.apache.coyote.AbstractProcessorLight.process(AbstractProcessorLight.java:53) [tomcat-embed-core-9.0.21.jar:9.0.21]
	at org.apache.coyote.AbstractProtocol$ConnectionHandler.process(AbstractProtocol.java:853) [tomcat-embed-core-9.0.21.jar:9.0.21]
	at org.apache.tomcat.util.net.NioEndpoint$SocketProcessor.doRun(NioEndpoint.java:1587) [tomcat-embed-core-9.0.21.jar:9.0.21]
	at org.apache.tomcat.util.net.SocketProcessorBase.run(SocketProcessorBase.java:49) [tomcat-embed-core-9.0.21.jar:9.0.21]
	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149) [na:1.8.0_171]
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624) [na:1.8.0_171]
	at org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:61) [tomcat-embed-core-9.0.21.jar:9.0.21]
	at java.lang.Thread.run(Thread.java:748) [na:1.8.0_171]
```

- 原因

> 使用的是springboot内置的tomcat
>
> 可以替换成jetty

- 解决办法

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    <exclusions>
        <exclusion>
            <artifactId>spring-boot-starter-tomcat</artifactId>
            <groupId>org.springframework.boot</groupId>
        </exclusion>
    </exclusions>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-jetty</artifactId>
</dependency>
```

