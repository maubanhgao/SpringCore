package com.projectmaven.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		HelloWorld obj = (HelloWorld) context.getBean("helloWorld ");
		obj.getMessage();
		context.close();
	}

}
