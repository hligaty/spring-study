package com.example.bean.definition;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Bean 别名
 */
public class BeanAliasDemo {
  public static void main(String[] args) {
    BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:META-INF/bean-definitions-context.xml");
    System.out.println((beanFactory.getBean("user2")) == beanFactory.getBean("user"));
  }
}
