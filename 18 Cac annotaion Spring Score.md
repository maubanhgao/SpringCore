# Tổng hợp các Annotation trong Spring

[TOC]

## 1. @Autowire Annotation 

Sử dụng để nhúng các Bean vào Bean cần dùng. Có thể nhúng qua Constructor, Setter và Biến.

- Constructor

```java
@RestController
public class CustomerController {
    private CustomerService customerService;
 
    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }
}
```

- Setter

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {
    private CustomerService customerService;
 
    @Autowired
    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }
}
```

- Field (biến)

```java
@RestController
public class CustomerController {
    @Autowired
    private CustomerService customerService;
}
```

## 2. @Bean Annotation 

- Sử dụng để tạo các Bean trong ứng dụng Spring.

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

## 3. @Qualifier Annotation 

- Khi có nhiều Bean có cùng kiểu dữ liệu thì @Qualifier giúp chúng ta xác định nó là kiểu gì. Ví dụ như ta có 2 loại gửi tin nhắn là Email và SMS cùng implements 1 interface là Message Service. Chúng ta sử dụng @Qualifier để xác định đó là loại Email hay SMS vì chúng cùng 1 kiểu Message Service.

```java
public interface MessageService {
    public void sendMsg(String message);
}
```

```java
public class EmailService implements MessageService{

    public void sendMsg(String message) {
         System.out.println(message);
    }
}
```

```java
public class SMSService implements MessageService{

    public void sendMsg(String message) {
         System.out.println(message);
    }
}
```

```java
public interface MessageProcessor {
    public void processMsg(String message);
}

public class MessageProcessorImpl implements MessageProcessor {

    private MessageService messageService;

    // setter based DI
    @Autowired
    @Qualifier("emailService")
    public void setMessageService(MessageService messageService) {
        this.messageService = messageService;
    }
 
    // constructor based DI
    @Autowired
    public MessageProcessorImpl(@Qualifier("emailService") MessageService messageService) {
        this.messageService = messageService;
    }
 
    public void processMsg(String message) {
        messageService.sendMsg(message);
    }
}
```

## 4. @Require Annotation 

Sử dụng @Require thường dùng ở phương thức Setter nhằm bắt buộc phải nhúng giá trị vào.

```java
@Required
void setColor(String color) {
    this.color = color;
}
```

## 5. @Value Annotation 

**Được sử dụng để lấy giá trị từ file properties.**

```java
@Value("${APP_NAME_NOT_FOUND}")

private String defaultAppName;
```

## 6. @Lazy Annotation 

Sử dụng @Lazy để ngăn Spring IoC tạo Bean, chỉ tạo khi mình cần.

```java
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

## 7. @Primary Annotation 

Khi nhiều Bean có cùng một kiểu (type). Chúng ta có quyền ưu tiên cho Bean nào được load lên đầu tiên.

```java
@Component
@Primary
class Car implements Vehicle {}
 
@Component
class Bike implements Vehicle {}
 
@Component
class Driver {
    @Autowired
    Vehicle vehicle;
}
 
@Component
class Biker {
    @Autowired
    @Qualifier("bike")
    Vehicle vehicle;
}
```

## 8. @Scope Annotation 

Dùng để nói lên phạm vi tồn tại của một Bean.

```java
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class TwitterMessageService implements MessageService {
}

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TwitterMessageService implements MessageService {
}
```

## 9. @Import Annotation 

Dùng để nhúng 1 hoặc nhiều file configure lại với nhau.

```java
@Configuration
public class ConfigA {

    @Bean
    public A a() {
        return new A();
    }
}

@Configuration
@Import(ConfigA.class)
public class ConfigB {

    @Bean
    public B b() {
        return new B();
    }
}
```

## 10. PropertySource

Dùng để nạp các file properties từ bên ngoài vào Bean.

```java
@Configuration
@PropertySource("classpath:config.properties")
public class ProperySourceDemo implements InitializingBean {

    @Autowired
    Environment env;

    @Override
    public void afterPropertiesSet() throws Exception {
        setDatabaseConfig();
    }

    private void setDatabaseConfig() {
        DataSourceConfig config = new DataSourceConfig();
        config.setDriver(env.getProperty("jdbc.driver"));
        config.setUrl(env.getProperty("jdbc.url"));
        config.setUsername(env.getProperty("jdbc.username"));
        config.setPassword(env.getProperty("jdbc.password"));
        System.out.println(config.toString());
    }
}
```