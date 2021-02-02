# Nhúng Dependency Injection bằng Constructor trong Spring

[TOC]

## 1. Dependency Injection thông qua cơ chế Constructor 

**Ngoài cơ chế DI thông qua setter như bài post trước, Spring IOC container cũng hỗ trợ cơ chế nhúng bean phụ thuộc thông qua hàm khởi tạo (Constructor) của đối tượng.**

## 2 .Maven Pom 

- Ví dụ mình có file maven pom như sau cho dự án.

```xml
<project
    xmlns="http://maven.apache.org/POM/4.0.0"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
 xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.javadevsguide.springframework</groupId>
    <artifactId>spring-dependency-injection</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <dependencies>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>5.0.0.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.8.1</version>
            <scope>test</scope>
        </dependency>
    </dependencies>
</project>
```

## 3 .Email Service 

- Chúng ta sử dụng annotaion @Service để khi IoC container nó quét qua thì nó sẽ tạo đối tượng (bean) EmailService trong container để quản lí.

```java
package com.levunguyen.di;

@Service("EmailService")
public class EmailService implements MessageService{
    public void sendMsg(String message) {
        System.out.println(message);
    }
}
```

## 4 .Client Service 

- Chúng ta thêm annotaion @Autowire phía trên của hàm khởi tạo ClientService. Khi Spring IOC quét qua ClientService nó sẽ nhúng EmailService bean có sẵn trong container của nó vào cho ClientService dùng.

```java
package com.levunguyen.di;

@Component
public class ClientService {
    private EmailService emailService;

    @Autowired 
    public ClientService(EmailService messageService) {
        super();
        this.messageService = messageService;
    }

    public void processMsg(String message) {
        messageService.sendMsg(message);
    }


}
```

## 5 .Testing

- Chúng ta sẽ tạo file cấu hình tên là AppConfiguration và annotaion là @Configure. File này có nhiệm vụ tương tự như file xml confire ở bài trước.
- Có một annotation quan trọng là @ComponentScan là ta chỉ ra thư mục mà ta đặt file EmailService và ClientService ở đâu để Spring IOC sẽ chui vào đó và quét 2 class này để tạo bean và nhúng bean.

```java
@Configuration
@ComponentScan("com.levunguyen.di")
public class AppConfiguration {

}
```

```java
public class TestApplication {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        ClientService  client = applicationContext.getBean(ClientService.class);
        client.processMsg(" sending email ");
    }
}
```

