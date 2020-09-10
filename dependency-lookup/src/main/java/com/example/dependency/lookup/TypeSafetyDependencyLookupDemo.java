package com.example.dependency.lookup;

import com.example.ioc.overview.domain.User;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;

/**
 * 单一类型和集合类型 的 依赖查找 安全性
 */
public class TypeSafetyDependencyLookupDemo {
  public static void main(String[] args) {
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
    applicationContext.register(TypeSafetyDependencyLookupDemo.class);
    applicationContext.refresh();

    // 下面是单一类型查找
    // 演示 BeanFactory#getBean 方法安全性，不安全
    displayBeanFactoryGetBean(applicationContext);
    // 演示 ObjectFactory#getObject 方法安全性，不安全
    displayObjectFactoryGetObject(applicationContext);
    // 演示 ObjectProvider#getIfAvailable 方法安全性，安全
    displayObjectProviderIfAvailable(applicationContext);

    // 下面是集合类型查找，全是安全的
    // 演示 ListableBeanFactory#getBeansOfType 方法安全性
    displayListableBeanFactoryGetBeansOfType(applicationContext);
    // 验证 ObjectProvider#streamOps 方法安全性
    displayObjectProviderStreamOps(applicationContext);
    applicationContext.close();
  }

  private static void displayObjectProviderStreamOps(AnnotationConfigApplicationContext applicationContext) {
    ObjectProvider<User> userObjectProvider = applicationContext.getBeanProvider(User.class);
    userObjectProvider.stream().forEach(System.out::println);
  }

  private static void displayListableBeanFactoryGetBeansOfType(AnnotationConfigApplicationContext applicationContext) {
    Map<String, User> userMap = applicationContext.getBeansOfType(User.class);
    System.out.println(userMap);
  }

  private static void displayObjectProviderIfAvailable(AnnotationConfigApplicationContext applicationContext) {
    ObjectProvider<User> userObjectProvider = applicationContext.getBeanProvider(User.class);
    User user = userObjectProvider.getIfAvailable(() -> User.createMethod());
    System.out.println("user : " + user);
  }

  private static void displayObjectFactoryGetObject(AnnotationConfigApplicationContext applicationContext) {
    // ObjectProvoder is ObjectFactory
    ObjectFactory<User> userObjectFactory = applicationContext.getBeanProvider(User.class);
    try {
      userObjectFactory.getObject();
    } catch (BeansException e) {
      e.printStackTrace();
    }
  }

  private static void displayBeanFactoryGetBean(BeanFactory beanFactory) {
    try {
      beanFactory.getBean(User.class);
    } catch (BeansException e) {
      e.printStackTrace();
    }
  }
}
