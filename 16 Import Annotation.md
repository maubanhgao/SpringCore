# Sử dụng Annotation Import trong Spring

[TOC]

## 1. Giới thiệu @Import Annotation 

Chúng ta sử dụng Annotation Import khi muốn có một hoặc nhiều Class cấu hình sẽ được import (nhúng vào) Class.

## 2. Sử dụng import bằng XML 

Ví dụ ta có file xml sau là web.xml. Trong file web.xml này ta muốn nhúng các file xml khác là spring-common.xml, spring-dao.xml và spring-beans.

Thì ta dùng thẻ

```xml
<beans xmlns="http://www.springframework.org/schema/beans"
 xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
 xsi:schemaLocation="http://www.springframework.org/schema/beans
 http://www.springframework.org/schema/beans/spring-beans.xsd">

 <import resource="common/spring-common.xml"/>
        <import resource="dao/spring-dao.xml"/>
        <import resource="beans/spring-beans.xml"/>
 
</beans>
```

## 3. Sử dụng import bằng annotation

```java
@Configuration
public class Common {

    @Bean
    public Share share() {
        return new Share();
    }
}

@Configuration
public class Dao {

    @Bean
    public Connection connect() {
        return new Connection();
    }
}


@Configuration
@Import(value = {Common.class,Dao.class } )
public class Web {

    @Bean
    public Page page() {
        return new Page();
    }
}
```

