package com.example.bean.definition;

import com.example.bean.factory.DefaultUserFactory;
import com.example.bean.factory.UserFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * 特殊的 Bean 实例化示例
 */
public class SpecialBeanInstantiationDemo {
  public static void main(String[] args) {
    ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:/META-INF/special-bean-instantiation-context.xml");
    AutowireCapableBeanFactory beanFactory = applicationContext.getAutowireCapableBeanFactory();

    ServiceLoader<UserFactory> serviceLoader = beanFactory.getBean("userFactoryServiceLoader", ServiceLoader.class);
    displaySerciveLoader(serviceLoader);

    demoServiceLoader();

    UserFactory userFactory = beanFactory.createBean(DefaultUserFactory.class);
    System.out.println(userFactory.createUser());
  }

  //只会输出 META-INF\services\com.example.bean.factory.UserFactory 中的一个（我测试没区别？这肯定没区别啊！？）
  // todo-gaodapeng 2020/9/10 10:36
  public static void demoServiceLoader() {
    ServiceLoader<UserFactory> serviceLoader = ServiceLoader.load(UserFactory.class, Thread.currentThread().getContextClassLoader());
    displaySerciveLoader(serviceLoader);
  }

  // todo-gaodapeng 2020/9/10 10:37
  //会把 META-INF\services\com.example.bean.factory.UserFactory 所有的全输出（我测试没区别？这肯定没区别啊！？）
  public static void displaySerciveLoader(ServiceLoader<UserFactory> serviceLoader) {
    Iterator<UserFactory> iterator = serviceLoader.iterator();
    while (iterator.hasNext()) {
      UserFactory userFactory = iterator.next();
      System.out.println(userFactory.createUser());
    }
  }
}
