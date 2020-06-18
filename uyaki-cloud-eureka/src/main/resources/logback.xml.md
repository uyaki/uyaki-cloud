<?xml version="1.0" encoding="UTF-8"?>
<!--
说明：
    1. 文件的命名和加载顺序有关
       logback.xml早于application.yml加载，logback-spring.xml晚于application.yml加载
       如果logback配置需要使用application.yml中的属性，需要命名为logback-spring.xml
    2. logback使用application.yml中的属性
       使用springProperty才可使用application.yml中的值 可以设置默认值

-->
<configuration debug="false">
    <property resource="application.yml"/>
    <!-- log base path -->
    <springProperty scope="context" name="logstash.url" source="logstash.url" />
    <springProperty scope="context" name="logstash.levelfile" source="logstash.levelfile" />
    <springProperty scope="context" name="logstash.level" source="logstash.level" />
    <!-- 控制台输出 -->
    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
            <!--格式化输出：%d表示日期，%thread表示线程名，%-5level：级别从左显示5个字符宽度%msg：日志消息，%n是换行符-->
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50}:%L - %msg  %n</pattern>
            <charset>UTF-8</charset>
        </encoder>
    </appender>
    <!-- 按照每天生成日志文件 -->
    <appender name="FILE"  class="ch.qos.logback.core.rolling.RollingFileAppender">
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <!--日志文件输出的文件名-->
            <FileNamePattern>${logstash.levelfile}/data-center-service.log.%d{yyyy-MM-dd}.log</FileNamePattern>
            <!--日志文件保留天数-->
            <MaxHistory>30</MaxHistory>
        </rollingPolicy>
        <encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
            <!--格式化输出：%d表示日期，%thread表示线程名，%-5level：级别从左显示5个字符宽度%msg：日志消息，%n是换行符-->
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50} - %msg%n</pattern>
            <charset>UTF-8</charset>
        </encoder>
        <!--日志文件最大的大小-->
        <triggeringPolicy class="ch.qos.logback.core.rolling.SizeBasedTriggeringPolicy">
            <MaxFileSize>100MB</MaxFileSize>
        </triggeringPolicy>
    </appender>
    <appender name="logstash" class="net.logstash.logback.appender.LogstashTcpSocketAppender">
        <destination>localhost:5000</destination>
        <encoder charset="UTF-8" class="net.logstash.logback.encoder.LogstashEncoder" />
        <queueSize>1048576</queueSize>
        <keepAliveDuration>5 minutes</keepAliveDuration>
        <!--<customFields>{"application-name":"data-repo-interface"}</customFields>-->
        <!--<filter class="ch.qos.logback.classic.filter.ThresholdFilter">
            <level>INFO</level>
        </filter>
        <filter class="ch.qos.logback.core.filter.EvaluatorFilter">
            <evaluator> <!– 默认为 ch.qos.logback.classic.boolex.JaninoEventEvaluator –>
                <expression>return message.contains("billing");</expression>
            </evaluator>
            <OnMatch>ACCEPT</OnMatch>
            <OnMismatch>DENY</OnMismatch>
        </filter>-->
    </appender>

    <logger name="elk_logger" level="INFO" additivity="false">
        <appender-ref ref="logstash"/>
    </logger>

    <!--<logger name="com.zaxxer" level="${logstash.level}"/>-->
    <!--<logger name="org.apache.ibatis" level="${logstash.level}"/>-->
    <!--<logger name="org.mybatis.spring" level="${logstash.level}"/>-->
    <!--<logger name="org.springframework" level="${logstash.level}"/>-->
    <!--<logger name="java.sql.Connection" level="${logstash.level}"/>-->
    <!--<logger name="java.sql.Statement" level="${logstash.level}"/>-->
    <!--<logger name="java.sql.PreparedStatement" level="${logstash.level}"/>-->

    <!-- 日志输出级别 -->
    <root level="${logstash.level}">
        <appender-ref ref="STDOUT" />
        <appender-ref ref="FILE" />
        <!--<appender-ref ref="logstash" />-->
    </root>
</configuration>