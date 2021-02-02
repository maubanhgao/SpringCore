# Sử dụng Dependency Injection trong Spring

[TOC]

## 1. Dependency Injection là gì 

Trước hết mình hãy phân tích từ Dependency (phụ thuộc) nghĩa là gì? Anh lấy ví dụ trong thực tế. Khi mình đi làm thì mình có thể dùng phương tiện di chuyển là xe máy để đến công ty. Như vậy để đạt được mục đích đến công ty thì anh phải bắt buộc có chiếc xe máy để hoàn thành nhiệm vụ của mình. Như vậy anh đang phụ thuộc vào chiếc xe máy để đạt được mục đích là đến công ty.

Tương tự như vậy trong lập trình, ví dụ anh viết một chức năng cho giáo viên có thể xem kết quả điểm thi của từng học sinh. Như vậy trong lớp giáo viên, anh phải có đối tượng sinh viên để anh có thể lấy được kết quả thi của bạn sinh viên đó. Như vậy lớp giáo viên phụ thuộc vào lớp sinh viên vì nếu không có đối tượng sinh viên được khai báo trong lớp giáo viên, thì giáo viên không thể lấy được điểm của sinh viên.

Ví dụ trong Java mình có lớp Student như sau. Gồm có tên (name), điểm thi (mark) và mình có phương thức getStudentMark() để lấy kết quả thi của sinh viên.

```java
public class Student {
    private String name;
    private float mark;

    public Student(){

    }

    public float getStudentMark() {
        return mark;
    }
}
```

**Ví dụ chúng ta có lớp Teacher như sau. Gồm có tên và phương thức getStudentMark() để lấy ra điẻm thi của Sinh Viên.**

```java
public class Teacher  {
    private String name;
    private Student student;

    public Teacher(){
        student = new Student()
    }

    public float getStudentMark() {
        return student.getStudentMark();
    }
}
```

Như các bạn thấy trong lớp Teacher dòng số 6. Mình khai báo đối tượng Student trong lớp Teacher để mình có thể sử dụng được phương thức getStudentMark() phục vụ cho việc xem điểm của giáo viên.

Tiếp đến mình đi tìm hiểu khái niệm Injection là gì? Như các bạn thấy tại dòng số 6 của class Teacher. Để sử dụng được chức năng getStudentMark() trong đối tượng sinh viên thì mình phải sử dụng toán tử new để tạo ra đối tượng Student trước khi sử dụng các phương thức có trong Student. Vậy có thể hiểu Injection là mình giao cho một ai đó (ví dụ spring framework chẳng hạn) khởi tạo object và nhúng object đó và các Dependency đang dùng.

Nói cách khác, ở ví dụ trên mình giao cho framework (Spring) khởi tạo đối tượng Student và nhúng đối tượng Student vào đối tượng Teacher mà ta không cần dùng toán tử new mà giao cho Spring quản lý.

## 2. Tại sao chúng ta cần Dependency Injection 

- Giảm sự phụ thuộc giữa các module, object giúp cho việc mở rộng code sau này của ta được dễ dàng.
- Do các module, object là độc lập nên ta có thể viết unit test dễ dàng cho từng module.
- Giúp chúng ta tập trung vào việc viết logic business (nghiệp vụ) của ứng dụng còn việc tạo và quản lí các đối tượng mình giao cho framework lo.

## 3. Các loại DI có trong lập trình 

Có 3 cách để chúng ta có thể **Dependency Injection** (nhúng một object vào một object khác).

- Thông qua constructor.
- Thông qua setter và getter method.
- Thông qua interface.

```java
class Teacher {
  private Student student;
  // Dựa vào constructor
  Teacher (Student student) {
    this.student = student;

  }

  // Dựa vào Setter method
  void setStudent(Student student){
    this.student = student;
  }

}
```

## 4. Dependency Injection trong Spring 

Trong dự án Spring chúng ta có thể nhúng đối tượng Student vào đối tượng Teacher như sau.

1- Sử dụng XML configuration

```xml
<bean id="student" class="org.levunguyen.student" /> 
<bean id="teacher" class="org.levunguyen.teacher"> 
    <constructor-arg  name="item" ref="student" /> 
</bean>
```

2- Sử dụng Annotation @autowire.

```java
public class Teacher {
    @Autowired
    private Student student; 
}
```

## 5. Nói tóm lại nhiệm vụ của Dependency Injection

- Tạo đối tượng.
- Biết được class nào cần đối tượng đó mà nhúng vào. Trong dự án Spring thì mình có Spring Container sẽ giúp mình việc tạo các beans, quản lí vòng đời các beans. Biết được các beans nào phụ thuộc vào các beans nào mà Spring sẽ tự động nhúng các bean phụ thuộc vào. Nhờ có Dependency Injection mà lập trình viên chỉ quan tâm đến việc thực hiện các nghiệp vụ của ứng dụng và giao cho spring container việc quản lí các beans.