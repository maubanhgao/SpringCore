# Sử dụng Annotation @Bean trong Spring

[TOC]

## 1. Giới thiệu @Bean Annotation 

**Trong các bài trước về tạo bean trong XML chúng ta sử dụng thẻ** để tạo một bean trong Spring IoC. Ngoài cách tạo bằng XML thì chúng ta hoàn toàn có thể tạo bean bằng cách sử dụng Annotation @Bean

## 2. Khai báo bean bằng cách sử dụng @Bean 

- Tạo bean sử dụng Annotation @Bean

```java
@Configuration
public class Application {

 @Bean
 public CustomerService customerService() {
  return new CustomerService();
 }
 
 @Bean
 public OrderService orderService() {
  return new OrderService();
 }
}
```

- Tạo bean bằng xml

```xml
<beans>
        <bean id="customerService" class="com.companyname.projectname.CustomerService"/>
        <bean id="orderService" class="com.companyname.projectname.OrderService"/>
</beans>
```

## 3. Vòng đời @Bean 

Annotation @Bean cũng hỗ trợ chức năng Initialization và Destruction giống như trong Spring XML là init-method và destroy-method.

```java
public class Foo {
        public void init() {
                // initialization logic via xml config
        }
}

public class Bar {
        public void cleanup() {
                // destruction logic via xml config
        }
}

@Configuration
public class AppConfig {

        @Bean(initMethod = "init")
        public Foo foo() {
                return new Foo();
        }

        @Bean(destroyMethod = "cleanup")
        public Bar bar() {
                return new Bar();
        }

}
```

## 4.Thay đổi tên của bean

**Chúng ta sử dụng thuộc tính name để đặt tên cho bean.**

```java
@Configuration
public class Application {

 @Bean(name = "cService")
 public CustomerService customerService() {
  return new CustomerService();
 }
 
 @Bean(name = "oService")
 public OrderService orderService() {
  return new OrderService();
 }
}
```

