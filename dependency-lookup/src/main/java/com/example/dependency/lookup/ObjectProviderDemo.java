package com.example.dependency.lookup;

import com.example.ioc.overview.domain.User;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

/**
 * 通过 ObjectProvider 进行依赖查找
 */
public class ObjectProviderDemo {
  public static void main(String[] args) {
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
    applicationContext.register(ObjectProviderDemo.class);
    applicationContext.refresh();

    //依赖查找集合对象
    lookupByObjectProvider(applicationContext);
    lookupIfAvailable(applicationContext);
    lookupByStreamOps(applicationContext);
    //关闭上下文
    applicationContext.close();
  }

  private static void lookupByStreamOps(AnnotationConfigApplicationContext applicationContext) {
    ObjectProvider<String> objectProvider = applicationContext.getBeanProvider(String.class);
    objectProvider.stream().forEach(System.out::println);
  }


  private static void lookupIfAvailable(AnnotationConfigApplicationContext applicationContext) {
    ObjectProvider<User> objectProvider = applicationContext.getBeanProvider(User.class);
    User user = objectProvider.getIfAvailable(() -> User.createMethod());
    System.out.println(user);
  }

  @Bean
  @Primary
  public String helloWorld() {
    return "Hello,World";
  }

  @Bean
  public String message() {
    return "Message";
  }

  private static void lookupByObjectProvider(AnnotationConfigApplicationContext applicationContext) {
    ObjectProvider<String> objectProvider = applicationContext.getBeanProvider(String.class);
    System.out.println(objectProvider.getObject());
  }
}
