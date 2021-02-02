# Tạo dự án bằng gradle trong Spring

[TOC]

## 1. Gradle là gì ? 

**Gradle là tool dùng để build các dự án Java. Cũng tương tự như Maven chúng ta sử dụng gradle để quản lí thư viện và build dự án. Hiện nay chúng ta có thể dùng Gradle để build các dự án về web, mobile.**

## 2. Khai báo dependency trong Gradle

**Để nhúng một thư viện vào dự án thì trong file build.gradle ta sử dụng đoạn mã sau:**

```properties
apply plugin: 'java'
apply plugin: 'eclipse'
apply plugin: 'application'

mainClassName = 'hello.HelloWorld'

// tag::repositories[]
repositories {
    mavenCentral()
}
// end::repositories[]

// tag::jar[]
jar {
    baseName = 'gs-gradle'
    version =  '0.1.0'
}
// end::jar[]

// tag::dependencies[]
sourceCompatibility = 1.8
targetCompatibility = 1.8

dependencies {
    compile "joda-time:joda-time:2.2"
    testCompile "junit:junit:4.12"
}
```

**Khác với Maven nó sử dụng XML để khai báo các dependency. Ở gradle chúng ta khai báo dạng như định dạng Json. Các thư viện sử dụng trong dự án được bỏ vào thẻ dependecies.**