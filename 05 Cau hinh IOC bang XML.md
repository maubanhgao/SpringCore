# Cấu hình IOC bằng XML

[TOC]

## 1. Tạo dự án Maven 

## 2 .Thêm các thư viện Spring vào Maven 

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
 xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
 xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>net.javaguides.spring</groupId>
    <artifactId>spring-ioc-example</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <url>http://maven.apache.org</url>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <dependencies>
        <!-- https://mvnrepository.com/artifact/org.springframework/spring-context -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>5.1.0.RELEASE</version>
        </dependency>
    </dependencies>
    <build>
        <sourceDirectory>src/main/java</sourceDirectory>
        <plugins>
            <plugin>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.5.1</version>
                <configuration>
                    <source>1.8</source>
                    <target>1.8</target>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```

## 3 .Cấu hình HelloWorld Spring Bean bằng Java 

```java
package com.levunguyen.spring.ioc;

public class HelloWorld {
    private String message;

    public void setMessage(String message) {
        this.message = message;
    }

    public void getMessage() {
        System.out.println("My Message : " + message);
    }
}
```

## 4 .Cấu hình Metadata cho HelloWorld Spring Java 

- Như bài 1 giới thiệu về Spring IOC container, ta có thể dùng XML để tạo các bean.

```xml
<?xml version = "1.0" encoding = "UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
 xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
 xsi:schemaLocation="http://www.springframework.org/schema/beans
   http://www.springframework.org/schema/beans/spring-beans-3.0.xsd">
   
 <bean id="helloWorld" class="com.levunguyen.spring.ioc">
  <property name="message" value="Hello World!" />
 </bean>
</beans>
```

- Chúng ta khai báo xml tên bean và đặt cho nó định danh là id=helloworl, sau đó chỉ đường dẫn tới file java com.levunguyen.spring.ioc như sau :
- Gán giá trị cho thuộc tính message trong lớp HelloWorld.
- Ta sử dụng thẻ . Chú ý trong thẻ property có thuộc tính là name thì cái tên name này phải giống như thuộc tính trong lớp java HelloWorld.

## 5 .Tạo Spring Container 

```java
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Application {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
    }
}
```

## 6 .Lấy đối tượng bean HelloWorld và gọi phương thức

- Chúng ta sử dụng phương thức getBean để lấy đối tượng bean từ container.

```java
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Application {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        HelloWorld obj = (HelloWorld) context.getBean("helloWorld");
        obj.getMessage();
    }
}
```

- Kết quả ta nhận được là text : Hello World