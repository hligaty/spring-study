package com.example.dependency.lookup;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

public class ObjectProviderDemo {
  public static void main(String[] args) {
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
    applicationContext.register(ObjectProviderDemo.class);
    applicationContext.refresh();

    //依赖查找集合对象
    lookupByObjectProvider(applicationContext);
    //关闭上下文
    applicationContext.close();
  }

  @Bean
  public String helloWorld() {
    return "Hello,World";
  }

  private static void lookupByObjectProvider(AnnotationConfigApplicationContext applicationContext) {
    ObjectProvider<String> objectProvider = applicationContext.getBeanProvider(String.class);
    System.out.println(objectProvider.getObject());
  }
}
