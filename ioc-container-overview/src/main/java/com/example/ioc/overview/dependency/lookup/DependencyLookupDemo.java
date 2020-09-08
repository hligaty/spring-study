package com.example.ioc.overview.dependency.lookup;

import com.example.ioc.overview.annotation.Super;
import com.example.ioc.overview.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

/**
 * 依赖查找示例
 */
public class DependencyLookupDemo {
  public static void main(String[] args) {
    BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:/META-INF/dependency-lookup-context.xml");

    lookupInRealTime(beanFactory);
    lookupInLazy(beanFactory);
    lookupByType(beanFactory);
    lookupCollectionByType(beanFactory);
    lookupByAnnotationType(beanFactory);
  }

  private static void lookupByAnnotationType(BeanFactory beanFactory) {
    if (beanFactory instanceof ListableBeanFactory) {
      ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
      Map<String, User> userMap = (Map) listableBeanFactory.getBeansWithAnnotation(Super.class);
      System.out.println("查找标注 @Super 的 User 集合对象" + userMap);
    }
  }

  private static void lookupCollectionByType(BeanFactory beanFactory) {
    if (beanFactory instanceof ListableBeanFactory) {
      ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
      Map<String, User> userMap = listableBeanFactory.getBeansOfType(User.class);
      System.out.println("查找所有的 User 集合对象" + userMap);
    }
  }

  public static void lookupByType(BeanFactory beanFactory) {
    User user = beanFactory.getBean(User.class);
    System.out.println("类型查找: " + user);
  }

  public static void lookupInRealTime(BeanFactory beanFactory) {
    User user = beanFactory.getBean("user", User.class);
    System.out.println("实时查找: " + user);
  }

  public static void lookupInLazy(BeanFactory beanFactory) {
    ObjectFactory<User> objectFactory = beanFactory.getBean("objectFactory", ObjectFactory.class);
    User user = objectFactory.getObject();
    System.out.println("延迟查找: " + user);
  }
}
