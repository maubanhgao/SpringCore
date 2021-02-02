# Phạm vi hoạt động của Bean

[TOC]

## 1. Singleton Scope 

- Khi một Bean được khai báo là Singleton thì Bean đó là duy nhất trong Spring IoC và được share cho tất cả các Beans khác nếu cần sử dụng nó. Như vậy ta chỉ cần tạo một Bean duy nhất và sử dụng cho toàn hệ thống. Anh lấy ví dụ mình có 1 Bean về connect database thì mình chỉ tạo một lần duy nhất. Các Bean khác muốn dùng thì nhúng vào chứ không phải mình có 10 Beans khác nhau dùng Bean Connect Database thì mình tạo 10 Bean Database trong Spring IoC.
- Scope mặc định khi một Bean được tạo ra là Singleton.
- Định nghĩa Scope Singletion bằng XML.

```xml
<bean id="accountService" class="com.foo.DefaultAccountService"/>

<!-- the following is equivalent, though redundant (singleton scope is the default) -->
<bean id="accountService" class="com.foo.DefaultAccountService" scope="singleton"/>
```

- Định nghĩa Scope Singleton bằng Java base.

```java
@Configuration
public class AppConfiguration {

 @Bean
 @Scope("singleton") // default scope 
 public UserService userService(){
  return new UserService();
 }
}
```

## 2 .Prototype Scope 

Khác với Singleton Scope, Bean (đối tượng) sẽ được tạo ra mới mỗi khi có một yêu cầu tạo Bean. Như vậy mỗi lần gọi tới Bean mà có Scope là Prototype thì nó sẽ tạo ra một đối tượng (Bean) trong Spring IoC container.

- Định nghĩa Scope Singletion bằng XML.

```xml
<bean id="accountService" class="com.foo.DefaultAccountService" scope="prototype"/>
```

- Định nghĩa Scope Singleton bằng Java base.

```java
@Configuration
public class AppConfiguration {

 @Bean
 @Scope("prototype")
 public UserService userService(){
  return new UserService();
 }
}
```

## 3 .Request Session Application và WebSocket Scope

Những Scope như Request, Session, Application và Websocket thì chỉ có tồn tại ở những ứng dụng là Web Application. Nếu ta sử dụng ở những ứng dụng Spring độc lập thì sẽ nhận được thông báo lỗi IllegalExection unknow bean scope vì ứng dụng này không phải là ứng dụng web nên không có Scope nêu trên.

Để sử dụng được các Scope trên thì chúng ta phải Configure (cấu hình) dự án web thêm một vài thông số như thêm SpringDispatchServlet vào file cấu hình trong file web.xml trước khi sử dụng các Scope trên.

```xml
<web-app>
    ...
    <filter>
        <filter-name>requestContextFilter</filter-name>
        <filter-class>org.springframework.web.filter.RequestContextFilter</filter-class>
    </filter>
    <filter-mapping>
        <filter-name>requestContextFilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>
    ...
</web-app>
```

## 4 .Request Scope 

- Cấu hình Request Scope cho XML.

```xml
<bean id="loginAction" class="com.foo.LoginAction" scope="request"/>
```

- Cấu hình Request Scope cho Java.

```java
@RequestScope
@Component
public class LoginAction {
    // ...
}
```

- Spring Container sẽ tạo bean (đối tượng) LoginAction mới khi có một request (yêu cầu) từ người dùng. Sau khi Request (yêu cầu) xử lý xong thì Bean sẽ bị xoá đi.

## 5 .Session Scope 

**Scope Session sẽ tồn tại chừng nào Sesion ở HTTP. Nó sẽ bị xoá đi khỏi Spring IoC khi Session ở Web bị xoá hoặc hết hiệu lực.**

```xml
<bean id="loginAction" class="com.foo.LoginAction" scope="session"/>
```

- Cấu hình Request Scope cho Java

```java
@SessionScope
@Component
public class UserPreferences {
    // ...
}
```

## 6 .Application Scope

**Application Scope được tạo một lần cho toàn bộ ứng dụng Web Application. Application Scope được chứa đựng như một ServletContext, nó cũng gần tương tự như Singleton Scope nhưng nó là Singleton cho từng ServeletContext.**

```xml
<bean id="appPreferences" class="com.foo.AppPreferences" scope="application"/>
```

- Cấu hình Request Scope cho Java

```java
@ApplicationScope
@Component
public class AppPreferences {
    // ...
}
```