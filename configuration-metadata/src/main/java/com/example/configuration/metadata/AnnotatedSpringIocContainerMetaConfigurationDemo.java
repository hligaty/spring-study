package com.example.configuration.metadata;

import com.example.ioc.overview.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

import java.util.Map;

/**
 * 基于 java 注解的 SpringIoc 容器元信息配置示例
 */
// 将当前类作为 Configuration class
@ImportResource("META-INF\\dependency-lookup-context.xml")
//@Import(User.class)
@PropertySource("META-INF\\user-bean-definitions.properties")
public class AnnotatedSpringIocContainerMetaConfigurationDemo {

  @Bean
  public User configuredUser(@Value("${user.id}") int id, @Value("${user.name}") String name) {
    User user = new User();
    user.setId(id);
    user.setName(name);
    return user;
  }

  public static void main(String[] args) {
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
    // 注册当前类作为 Configuration class
    applicationContext.register(AnnotatedSpringIocContainerMetaConfigurationDemo.class);
    applicationContext.refresh();

    Map<String, User> userMap = applicationContext.getBeansOfType(User.class);
    userMap.forEach((beanName, user) -> {
      System.out.println(beanName + ":" + user);
    });

    applicationContext.close();
  }
}




