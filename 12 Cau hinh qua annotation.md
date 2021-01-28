# Cấu hình Bean bằng annotation trong Spring

[TOC]

## 1. Bật chức năng auto wire bean 

**Chức năng autowire (nhúng bean phụ thuộc DI) không bật lên mặc định mà chúng ta phải khai báo và bật nó lên trước khi sử dụng các Annotation để nhúng các bean. Để bật chức năng này lên ta khai báo như sau:**

```xml
<?xml version = "1.0" encoding = "UTF-8"?>

<beans xmlns = "http://www.springframework.org/schema/beans"
   xmlns:xsi = "http://www.w3.org/2001/XMLSchema-instance"
   xmlns:context = "http://www.springframework.org/schema/context"
   xsi:schemaLocation = "http://www.springframework.org/schema/beans
   http://www.springframework.org/schema/beans/spring-beans-3.0.xsd
   http://www.springframework.org/schema/context
   http://www.springframework.org/schema/context/spring-context-3.0.xsd">

   <context:annotation-config/>
   <!-- bean definitions go here -->

</beans>
```

- Chúng ta khai báo . Khi chức năng này được bật lên chúng ta có thể nhúng các giá trị vào thuộc tính, phương thức và constructor của các bean phụ thuộc vào Class mà ta mong muốn.
- Spring cung cấp một số Annotation sau.

## 2. Annotation @Required 

**@Required annotation nói rằng các method set phải bắt buộc phải nhúng bean phụ thuộc vào.**

```java
@Component
public class EmailClient {

    private EmailService emailService;

    @Required
    public void setEmailService(EmailService emailService) {
        this.emailService = emailService
    }
    

}
```

## 3. Annotation @Autowire 

Chúng ta sử dụng @Autowire để nhúng một bean phụ thuộc vào bean. Có thể nhúng qua constructor, setter hoặc qua các biến.

- Nhúng qua constructor

```java
class Car {
    private Engine engine;
 
    @Autowired
    Car(Engine engine) {
        this.engine = engine;
    }
}
```

- Nhúng qua setter

```
class Car {
    private Engine engine;
 
    @Autowired
    void setEngine(Engine engine) {
        this.engine = engine;
    }
}
```

- Nhúng thông qua biến

```java
class Car {
    private Engine engine;
 
    @Autowired
    void setEngine(Engine engine) {
        this.engine = engine;
    }
}
```

## 4. Annotation @Primary 

Sử dụng @Primary khi ta có nhiều beans cùng kiểu dữ liệu và ta muốn ưu tiên một kiểu nào đó. Trong ví dụ dưới đây ta viết 1 chương trình gửi thư. Có 2 dịch vụ gửi thư là Facebook và Email.

Cả 2 services FacebookMessageService và EmailMessageService cùng cài đặt chung 1 interface MessageService. Như vậy ta có 2 bean FacebookMessageService và EmailMessageService có cùng kiểu dữ liệu là MessageService. Vậy cái nào sẽ được ưu tiên đầu tiên. Chúng ta sẽ đi tiếp ví dụ sau.

- Tạo file pom cấu hình cho dự án

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
 xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
 xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>net.javaguides.spring</groupId>
    <artifactId>spring-primary-annotation</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>spring-scope-example</name>
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

- Tạo Interface MessageService.java

```java
public interface MessageService {
 public void sendMsg(); 
}
```

- Tạo FacebookMessageService implements MessageService

```java
import org.springframework.stereotype.Component;

@Component
public class FacebookMessageService implements MessageService {
    @Override
    public void sendMsg() {
        System.out.println("gửi bằng facebook");
    }
}
```

- Tạo EmailService.java implements MessageService

import org.springframework.stereotype.Component;

```java
@Primary
@Component
public class EmailMessageService implements MessageService {

    @Override
    public void sendMsg() {
        System.out.println("gửi bằng email");
    }
}
```

Như vậy ta thấy rằng cả 2 service FacebookMessageService và EmailMessageService cùng implement 1 interface là MessageService.

Nhưng ta thấy lớp EmailMessageService có thêm Annotation là @Primary ở đây.

- Bật chế độ cấu hình Annotation

```java
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "net.javaguides.spring.primary")
public class AppConfig {
 
}
```

- Test ứng dụng

```java
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        MessageService messageService = context.getBean(MessageService.class);
        messageService.sendMsg();
        context.close();
    }
}
```

Kết quả là : gửi bằng email được in ra. Bởi vì khi ta gọi getBean(MessageService.class) nó sẽ ưu tiên EmailMessageService vì lớp này có annotation là @Primary. Nên nó ưu tiên dùng thằng này, mặc dù EmailMessageService và FacebookMessageService cùng kiểu dữ liệu là MessageService

## 5. Annotation @Qualifier 

Chúng ta sử dụng @Qualifier để xác định chính xác loại dữ liệu nào được đưa vào. Như các em thấy ví dụ @Primary chúng ta có EmailMessageService và FacebookMessageService có cùng 1 kiểu dữ liệu là MessageService. Vì chúng cùng kiểu nên khi nhúng bean vào thì Spring IoC không biết nên nhúng cái nào là đúng. Nên để giúp Spring IoC nhúng đúng bean thì ta cần dùng @Qualifier để chỉ ra bean nhúng vào chính là EmailMessageService hay FacebookMessageService

```java
@Autowired 
@Qualifier("emailMessageService") 
private MessageService emailMessageService; 

@Autowired 
@Qualifier("facebookMessageService") 
private MessageService facebookMessageService;
```

## 6. @PostConstruct and @PreDestroy

Trong bài vòng đời của bean. Chúng ta sử dụng InitializingBean, DisposableBean để can thiệp vào bean lúc tạo và phá huỷ. Ngoài cách đó chúng ta có thể sử dụng annotation @PostConstruct and @PreDestroy

```java
@Component
public class DatabaseInitiaizer {

    private List < User > listOfUsers = new ArrayList < > ();

    @PostConstruct
    public void init() {
        User user = new User(1, "User");
        User user1 = new User(2, "Admin");
        User user2 = new User(3, "SuperAdmin");

        listOfUsers.add(user);
        listOfUsers.add(user1);
        listOfUsers.add(user2);
        System.out.println("-----------List of users added in init() method ------------");
        for (Iterator < User > iterator = listOfUsers.iterator(); iterator.hasNext();) {
            User user3 = (User) iterator.next();
            System.out.println(user3.toString());
        }
        // save to database

    }

    @PreDestroy
    public void destroy() {
        // Delete from database
        listOfUsers.clear();
        System.out.println("-----------After of users removed from List in destroy() method ------------");
        for (Iterator < User > iterator = listOfUsers.iterator(); iterator.hasNext();) {
            User user3 = (User) iterator.next();
            System.out.println(user3.toString());
        }
    }
}
```

