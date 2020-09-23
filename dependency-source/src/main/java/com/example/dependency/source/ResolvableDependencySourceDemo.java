package com.example.dependency.source;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.annotation.PostConstruct;

/**
 * ResolvableDependency 作为依赖来源，只能作为依赖注入，不能依赖查找，并且只能类型注入
 */
public class ResolvableDependencySourceDemo {
  @Autowired
  private String value;

  @PostConstruct
  public void init() {
    System.out.println(value);
  }

  public static void main(String[] args) {
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
    applicationContext.register(ResolvableDependencySourceDemo.class);
    applicationContext.addBeanFactoryPostProcessor(beanFactory -> beanFactory.registerResolvableDependency(String.class, "Hello World"));
    applicationContext.refresh();

    ConfigurableListableBeanFactory beanFactory = (ConfigurableListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();

    applicationContext.close();
  }
}
