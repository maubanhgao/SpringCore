# Tạo dự án bằng maven trong Spring

[TOC]

## 1. Maven là gì? 

Maven là một tool ta sử dụng chung với các dự án Java. Mục đích chính của Maven dùng để quản lý các thư viện được dùng chung với dự án Java. Ví dụ như mình muốn tích hợp chức năng login của facebook vào ứng dụng của mình, thì mình phải nhúng thư viện của facebook vào dự án của mình. Trong trường hợp này mình sử dụng Maven để lấy thư viện facebook và nhúng vào dự án. Từ đó code của mình viết sẽ gọi được các thư viện của facebook. Ngoài việc quản lý thư viện và version của thư viện. Thì mình dùng Maven để tự động build dự án của mình, đồng thời mình có thể thực hiện các lệnh maven để deploy sản phẩm của mình lên các con server khác nhau.

## 2. Khai báo dependency trong POM 

**Để nhúng một thư viện vào dự án thì trong file pom.xml ta sử dụng đoạn mã sau:**

```xml
<dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-api</artifactId>
        <version>${slf4j.version}</version>
        <type>jar</type>
</dependency>
```

Như vậy để nhúng một thư viện vào dự án ta cần biết 4 thông tin sau của thư viện. 

1- **GroudID** : thông thường mình **đặt tên công ty** hay nhóm phát triển ra thư viện đó. Ví dụ như facebook phát triển thư viện có tính năng login thì groudId sẽ là com.facebook. Hoặc nếu các tự tạo ra thư viện riêng cho mình thì có thể đặt groupId là com.name (trong đó name là tên của mình).

2- **artifactId** : thông thường sẽ là **tên của project**. Ví dụ như nếu mình phát triển 1 webservice là cổng thanh toán điện tử cho các ứng dụng khác có thể dùng cái của mình, mình có thể lấy tên là payment-api.

3- **version** : Khi phát triển một ứng dụng thì mình sẽ có nhiều phiên bản khác nhau. Ví dụ phiên bản đầu tiên là 1.0.0 thì phiên bản thứ 2 sẽ nhiều tính năng hơn sẽ là 2.0.0. Dựa vào các version khác nhau mà mình có thể chọn cái nào phù hợp với ứng dụng của mình.

4- **type** : Thư viện mình nhúng vào thì có thể đóng gói dạng **Jar** (thường **api**), hoặc nếu mình **phát triển ứng dụng web thì nó sẽ là war**, còn nếu nó là **cha của tất cả các module thì sẽ là pom.**

## 3. Khai báo maven hoàn chỉnh trong POM 

**Sau đây là file maven hoàn chỉnh mà anh dùng trong dự án Spring của mình.**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>

  <modules>

    <module>dao</module>
    <module>webservice</module>
    <module>common</module>
    <module>business-service</module>
  </modules>

  <parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.1.5.RELEASE</version>
  </parent>

  <groupId>codegymdanang</groupId>
  <artifactId>parent</artifactId>
  <version>1.0-SNAPSHOT</version>
  <packaging>pom</packaging>

  <properties>
    <project.version>${project.version}</project.version>
    <jdkVersion>1.8</jdkVersion>
    <slf4j.version>1.7.2</slf4j.version>
  </properties>

  <dependencyManagement>
    <dependencies>
      <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-api</artifactId>
        <version>${slf4j.version}</version>
        <type>jar</type>
      </dependency>
    </dependencies>
  </dependencyManagement>

  <dependencies>
    <dependency>
      <groupId>org.hamcrest</groupId>
      <artifactId>hamcrest-all</artifactId>
      <version>1.3</version>
      <scope>test</scope>
    </dependency>

  </dependencies>

</project>
```

- Ở đây mọi người thấy mình có thêm thì cái này mình dùng khai báo có bao nhiêu modules được dùng trong dự án. Thông thường mình chia dự án thành những modules nhỏ, mỗi module là một project spring.

## 4. Maven Repository 

**Bạn có thắc mắc vì sao ta chỉ khai báo cách dưới đây mà dự án của ta lấy được file slf4j-api và nhúng vào dự án mình không?**

FileSystemXmlApplicationContext : Cũng tương tự như ClassPathXmlApplicationContext nhưng file cấu hình chúng ta không phải là XML và chúng ta chỉ định đường dẫn để nạp file đó là ở đâu.Ví dụ như ta sử dụng ApplicationContext để tạo Spring Container cho ứng dụng Java độc lập như sau. Giả sử ta đã có file applicationContext.xml rồi.

Thực ra khi ta khai báo ở trên, khi ta chạy lệnh mvn instal thì Maven sẽ chạy lên trang chủ repository của mình (https://mvnrepository.com/). Nơi lưu trữ tất cả gói thư viện. Sau đó nó sẽ lấy cái mình muốn và download về máy của mình.

Repository của maven tại đây : https://mvnrepository.com/

Chúng ta chỉ tìm kiếm thư viện mong muốn. Sau đó ta search thì nó sẽ hiện cho chúng ta danh sách các thư viện và thẻ dependency mong muốn.

Ví dụ như mình search từ khoá facebook api để lấy các thư viện facebook về thì mình sẽ nhận được kết quả như sau. Mình chỉ cần copy và dán vào file pom là xong. Khi ứng dụng mình build bằng maven. Thì nó sẽ lên maven repository và download gói spring-social-facebook.jar về máy của mình và nhúng vô dự án của mình.

Ngoài ra mình có thể hoàn toàn tự build hệ thống **maven reposioty** ở local cho team mình dùng. Không cần public gói thư viện đó ra cho tất cả mọi người. Vì có những dự án bảo mật thì các gói thư viện mà team mình xây dựng chỉ phục vụ cho team nội bộ không công khai ra ngoài.

Nếu mình muốn xây dựng một hệ thống giống https://mvnrepository.com để quản lý các file thư viện và các phiên bản, mình hoàn toàn làm được. Trong các dự án của mình anh sẽ build một hệ thống local tên **Nexus**. Nó giống như một con server riêng chỉ team dùng, các thư viện, các phiên bản đều được quản lý bằng con Nexus này.

## 5. Kết luận

**Hầu hết các dự án Java đều sử dụng** **maven** **để quản lý thư viện, xây dựng quy trình build dự án và triển khai dự án một cách tự động. Nhờ có maven mà việc quản lý thư viện trong dự án trở nên dể dàng và linh hoạt hơn. Ngoài maven thì chúng ta còn có thể sử dụng những công cụ khác tương tự như gradle hoặc Ivy.**