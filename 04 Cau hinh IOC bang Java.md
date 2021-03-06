# Cấu hình IOC bằng Java

[TOC]

## 1. Tạo dự án Maven 

## 2 .Thêm các thư viện Spring vào Maven 

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<groupId>com.01projectmaven.spring</groupId>
	<artifactId>01-project-maven</artifactId>
	<version>0.0.1-SNAPSHOT</version>

	<dependencies>
		<!-- https://mvnrepository.com/artifact/org.springframework/spring-context -->
		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-context</artifactId>
			<version>${spring.context}</version>
		</dependency>
		<!-- https://mvnrepository.com/artifact/org.springframework/spring-core -->
		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-core</artifactId>
			<version>5.3.3</version>
		</dependency>
		<!-- https://mvnrepository.com/artifact/org.springframework/spring-beans -->
		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-beans</artifactId>
			<version>5.0.3.RELEASE</version>
		</dependency>
		<!-- https://mvnrepository.com/artifact/org.springframework/spring-aop -->
		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-aop</artifactId>
			<version>5.3.3</version>
		</dependency>
		<!-- https://mvnrepository.com/artifact/org.springframework/spring-expression -->
		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-expression</artifactId>
			<version>5.3.3</version>
		</dependency>


	</dependencies>

	<properties>
		<spring.context>5.0.3.RELEASE</spring.context>
	</properties>
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

## 4 .Cấu hình Metadata Java 

- Như bài 1 giới thiệu về Spring IOC container ta có thể dùng code Java để tạo các bean. Code Java bình thường như bước 3 chỉ là 1 lớp java thường, để trở thành bean thì ta phải thêm các cấu hình bằng annotaion @ như sau:

```java
package com.levunguyen.spring.ioc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public HelloWorld helloWorld() {
        HelloWorld helloWorld = new HelloWorld();
        helloWorld.setMessage("Hello World!");
        return helloWorld;
    }
}
```

- Chúng ta sử dụng @Congiguration và @Bean. Khi container load lên nó sẽ nhận biết các annotation @ này để tạo bean (đối tượng).

## 5 .Tạo Spring Container 

```java
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Application {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext  context = new AnnotationConfigApplicationContext(AppConfig.class);
        context.close();
    }
}
```

## 6 .Lấy đối tượng bean HelloWorld và gọi phương thức

- Chúng ta sử dụng phương thức getBean để lấy đối tượng bean từ container.

```java
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {
    public static void main(String[] args) {
        // ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        HelloWorld obj = (HelloWorld) context.getBean("helloWorld");
        obj.getMessage();
        context.close();
    }
}
```

- Kết quả ta nhận được là text : Hello World