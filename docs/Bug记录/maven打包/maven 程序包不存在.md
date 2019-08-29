# 程序包不存在

## **原因分析：**

common也是SpringBoot工程，SpringBoot工程打包编译时，会生成两种jar包，一种是普通的jar，另一种是可执行jar。

默认情况下，这两种jar的名称相同，在不做配置的情况下，普通的jar先生成，可执行jar后生成，造成可执行jar会覆盖普通的jar。而projectA工程无法依赖common工程的可执行jar，所以编译失败：程序包xxx不存在。

## 解决

在common子工程下的pom.xml，添加以下配置

```xml
<build>
  <plugins>
    <plugin>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-maven-plugin</artifactId>
      <configuration>
        <classifier>exec</classifier>
      </configuration>
    </plugin>
  </plugins>
</build>
```

