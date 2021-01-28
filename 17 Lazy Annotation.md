# Sử dụng Annotation Lazy trong Spring

[TOC]

## 1. Giới thiệu @Lazy Annotation 

Mặc định khi Spring IoC bật lên (start) nó sẽ tạo các Beans mà ta khai báo. Tuy nhiên chúng ta hoàn toàn có thể nói cho Spring IoC không khởi tạo các Bean ngay mà khởi tạo khi ta cần dùng thông qua Annotaion @Lazy.

Chúng ta hãy xem ví dụ sau.

## 2. Tạo Maven 

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
 xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
 xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
 <modelVersion>4.0.0</modelVersion>
 <groupId>net.javaguides.spring</groupId>
 <artifactId>spring-lazy-annotation</artifactId>
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

## 3. Tạo Java Class 

Trong ví dụ hôm nay chúng ta tạo 2 Beans là Bean1 và Bean2.

```java
public class FirstBean {

    public FirstBean() {
        System.out.println("Bean1");
    }

    public void test() {
        System.out.println("Bean1");
    }
}
```

```java
public class SecondBean {

    public SecondBean() {
        System.out.println("Bean2");
    }

    public void test() {
        System.out.println("Bean2");
    }
}
```

## 4. Tạo 2 Beans Class 

- Chú ý trong Class Configure này anh sử dụng Annotation @Lazy cho Bean1. Như vậy khi Spring IoC khởi động Bean1 sẽ chưa được tạo ngay mà Bean2 tạo trước.

```java
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class AppConfig {

    @Lazy(value = true)
    @Bean
    public FirstBean firstBean() {
        return new FirstBean();
    }

    @Bean
    public SecondBean secondBean() {
        return new SecondBean();
    }
}
```

## 5. Chạy Test

```java
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        FirstBean firstBean = context.getBean(FirstBean.class);
        firstBean.test();
        context.close();
    }
}
```

