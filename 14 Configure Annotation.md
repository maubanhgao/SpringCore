# Sử dụng Annotation Configure trong Spring

[TOC]

## 1. Giới thiệu @Configure Annotation 

Spring @Configuration là một thành phần trong Spring Core Framework. @Configuration Annotation chỉ ra rằng trong Class đó có @Bean. Vì vậy khi Spring IoC quét qua các Class mà có Annotation là @Configuration nó sẽ hiểu trong Class đó có khai báo một số bean và vào đó tạo các bean.

Thường những cấu hình file cấu hình dự án mình sẽ dùng Annotation @Configuration để đánh dấu cho Spring IoC biết.

## 2. Khai báo @Configure Annotation

- Chúng ta sử dụng Annotation @Configuration để đánh dấu là Class đó là Class dùng để cấu hình và có các bean cấu hình trong đó.

```java
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.companyname.projectname.customer.CustomerService;
import com.companyname.projectname.order.OrderService;

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

- Class Application trên được khai báo bằng Java. Nếu chuyển sang XML thì có dạng như sau

```xml
<beans>
        <bean id="customerService" class="com.companyname.projectname.CustomerService"/>
        <bean id="orderService" class="com.companyname.projectname.OrderService"/>
</beans>
```

