[![](https://jitpack.io/v/guangee/parent.svg)](https://jitpack.io/#guangee/parent)

# 使用方式

在pom.xml中加入以下代码

```xml
<parent>
<groupId>com.github.guangee.parent</groupId>
<artifactId>parent</artifactId>
<version>版本号</version>
</parent>
```

```xml

<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>


```

# SpringCloud+GRPC+NACOS公共父项目

* SpringBoot@2.1.7.RELEASE
*
SpringCloud@Greenwich.SR5 [版本建议](https://github.com/spring-projects/spring-cloud/wiki/Spring-Cloud-Greenwich-Release-Notes)
*
SpringCloudAlibaba@2.1.2.RELEASE [版本建议](https://github.com/alibaba/spring-cloud-alibaba/wiki/%E7%89%88%E6%9C%AC%E8%AF%B4%E6%98%8E)
* Grpc-starter@2.10.1.RELEASE

# 功能简介

1. 使用gateway作为网关
2. 内部服务之间调用使用Grpc调用
3. 注册中心使用阿里巴巴的nacos