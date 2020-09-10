package com.example.bean.definition;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

/**
 * BeanDefinition 注册的三种方式
 */
@Import(AnnotationBeanDefinitionDemo.Config.class)
public class AnnotationBeanDefinitionDemo {
  public static void main(String[] args) {
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
    //1
    applicationContext.register(AnnotationBeanDefinitionDemo.class);
    //2
    registerUserBeanDefinition(applicationContext, "user3");
    //3
    registerUserBeanDefinition(applicationContext);
    applicationContext.refresh();

    System.out.println(applicationContext.getBeansOfType(Config.class));
    System.out.println(applicationContext.getBeansOfType(User.class));
    applicationContext.close();
  }

  /**
   * 命名bean的注册方式
   *
   * @param registry
   * @param beanName
   */
  public static void registerUserBeanDefinition(BeanDefinitionRegistry registry, String beanName) {
    BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(User.class);
    beanDefinitionBuilder.addPropertyValue("id", 1)
            .addPropertyValue("name", "123");

    //注册
    registry.registerBeanDefinition(beanName, beanDefinitionBuilder.getBeanDefinition());
  }

  /**
   * 不命名bean的注册方式
   *
   * @param registry
   */
  public static void registerUserBeanDefinition(BeanDefinitionRegistry registry) {
    BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(User.class);
    beanDefinitionBuilder.addPropertyValue("id", 1)
            .addPropertyValue("name", "123");

    BeanDefinitionReaderUtils.registerWithGeneratedName(beanDefinitionBuilder.getBeanDefinition(), registry);
  }

  @Component
  public static class Config {
    @Bean(name = {"user1", "user2"})
    public User user() {
      User user = new User();
      user.setId(1);
      user.setName("123");
      return user;
    }
  }

}
