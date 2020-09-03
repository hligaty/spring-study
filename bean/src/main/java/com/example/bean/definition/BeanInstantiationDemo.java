package com.example.bean.definition;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BeanInstantiationDemo {
  public static void main(String[] args) {
    BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:/META-INF/bean-instantiation-context.xml");
    //静态方法构建
    User userByStatic = beanFactory.getBean("user-by-static-method", User.class);
    System.out.println(userByStatic);
    //工厂方法构建
    User userByFactory = beanFactory.getBean("user-by-instance-method", User.class);
    System.out.println(userByFactory);
    //FactoryBean构建
    User userByFactoryBean = beanFactory.getBean("user-by-factory-bean", User.class);

    System.out.println(userByFactoryBean);
    System.out.println(userByStatic == userByFactory);
    System.out.println(userByFactoryBean == userByFactory);
  }
}
