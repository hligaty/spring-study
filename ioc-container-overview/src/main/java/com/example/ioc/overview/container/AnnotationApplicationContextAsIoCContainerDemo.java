package com.example.ioc.overview.container;

import com.example.ioc.overview.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Map;

/**
 * 注解作为 IoC 容器示例
 */
public class AnnotationApplicationContextAsIoCContainerDemo {
  public static void main(String[] args) {
    // 创建 BeanFactory 容器
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
    // 将当前类作为 AnnotationApplicationContextAsIoCContainerDemo 作为配置类（Configuration.class）
    applicationContext.register(AnnotationApplicationContextAsIoCContainerDemo.class);
    // 启动应用上下文
    applicationContext.refresh();
    lookupCollectionByType(applicationContext);
  }

  @Bean
  public User user() {
    User user = new User();
    user.setId(1);
    user.setName("111");
    return user;
  }

  private static void lookupCollectionByType(BeanFactory beanFactory) {
    if (beanFactory instanceof ListableBeanFactory) {
      ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
      Map<String, User> userMap = listableBeanFactory.getBeansOfType(User.class);
      System.out.println("查找所有的 User 集合对象" + userMap);
    }
  }
}
