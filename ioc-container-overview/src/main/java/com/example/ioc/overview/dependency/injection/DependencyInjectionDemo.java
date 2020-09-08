package com.example.ioc.overview.dependency.injection;

import com.example.ioc.overview.repository.UserRepository;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 依赖注入示例
 */
public class DependencyInjectionDemo {
  public static void main(String[] args) {
    BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:/META-INF/dependency-injection-context.xml");
    UserRepository userRepository = beanFactory.getBean("userRepository", UserRepository.class);
//    System.out.println(userRepository.getUsers());

    // 依赖注入
    System.out.println(userRepository.getBeanFactory());
    System.out.println(userRepository.getBeanFactory() == beanFactory);

    // 依赖查找（错误）
//    System.out.println(beanFactory.getBean(BeanFactory.class));

    ObjectFactory<ApplicationContext> objectFactory = userRepository.getObjectFactory();
    ApplicationContext applicationContext = objectFactory.getObject();
    System.out.println(applicationContext == beanFactory);
  }
}
