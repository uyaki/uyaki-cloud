# JDK8下maven使用maven-javadoc-plugin问题

Failed to execute goal org.apache.maven.plugins:maven-javadoc-plugin:2.10.4:jar (attach-javadocs) on project p-jiazhuang-api: MavenReportException: Error while generating Javadoc:xxxx**: MavenReportException: Error while generating Javadoc:**

## 方案

在JDK 8中，Javadoc中添加了doclint，而这个工具的主要目的是旨在获得符合W3C HTML 4.01标准规范的HTML文档，在JDK 8中，已经无法获取如下的Javadoc，除非它满足doclint：

```sh
    不能有自关闭的HTML tags，例如<br/>或者<a id="x"/>
    不能有未关闭的HTML tags，例如有<ul>而没有</ul>
    不能有非法的HTML end tags，例如</br>
    不能有非法的HTML attributes，需要符合doclint基于W3C HTML 4.01的实现
    不能有重复的HTML id attribute
    不能有空的HTML href attribute
    不能有不正确的嵌套标题，例如类的文档说明中必须有<h3>而不是<h4>
    不能有非法的HTML tags，例如List<String>需要用<>对应的实体符号
    不能有损坏的@link references
    不能有损坏的@param references，它们必须匹配实际的参数名称
    不能有损坏的@throws references，第一个词必须是一个类名称
```

注意违反这些规则的话，将不会得到Javadoc的输出。 
虽然标准很好，但是在实际开发中，一般都无需这么严格的，这样就要做降级处理的。关闭doclint即可

```xml
<profiles>
  <profile>
    <id>disable-javadoc-doclint</id>
    <activation>
      <jdk>[1.8,)</jdk>
    </activation>
    <properties>
      <javadoc.opts>-Xdoclint:none</javadoc.opts>
    </properties>
  </profile>
</profiles>

<build>
  <plugins>
    <plugin>
      <groupId>org.apache.maven.plugins</groupId>
      <artifactId>maven-javadoc-plugin</artifactId>
      <version>2.10.4</version>
      <executions>
        <execution>
          <id>attach-javadocs</id>
          <phase>package</phase>
          <goals>
            <goal>jar</goal>
          </goals>
          <configuration>
            <additionalparam>${javadoc.opts}</additionalparam>
          </configuration>
        </execution>
      </executions>
    </plugin>
  </plugins>
</build>
```

idea 设置参考

[https://blog.csdn.net/zcl111/article/details/78086066](https://blog.csdn.net/zcl111/article/details/78086066)

