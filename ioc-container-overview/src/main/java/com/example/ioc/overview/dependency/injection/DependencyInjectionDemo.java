package com.example.ioc.overview.dependency.injection;

import com.example.ioc.overview.repository.UserRepository;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.Environment;

/**
 * 依赖注入示例
 */
public class DependencyInjectionDemo {
  public static void main(String[] args) {
    /**
     * BeanFactory 实现了基本的容器
     * ApplicationContext 使用组合的方式包含了 BeanFactory，并实现了 BeanFactory，类似于代理模式。
     *
     */
    ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:/META-INF/dependency-injection-context.xml");

    // 依赖来源一: 自定义 Bean
    UserRepository userRepository = applicationContext.getBean("userRepository", UserRepository.class);
//    System.out.println(userRepository.getUsers());

    // 依赖来源二: 依赖注入（内建依赖）
    System.out.println(userRepository.getBeanFactory());
    whoIsIocContainer(userRepository, applicationContext);

    ObjectFactory<ApplicationContext> objectFactory = userRepository.getObjectFactory();
    System.out.println(objectFactory.getObject() == applicationContext);

    // 依赖查找（错误）
//    System.out.println(beanFactory.getBean(BeanFactory.class));

    // 依赖来源三: 容器内建 Bean
    Environment environment = applicationContext.getBean(Environment.class);
    System.out.println("获取 Environment 类型的 bean:" + environment);
  }

  private static void whoIsIocContainer(UserRepository userRepository, BeanFactory beanFactory) {
    System.out.println(userRepository.getBeanFactory() == beanFactory);
    // applicationContext.getBeanFactory() 这个才是真正的容器（beanFactory）
//    System.out.println(applicationContext.getBeanFactory() == userRepository.getBeanFactory());
  }
}
