package com.example.dependency.injection;

import com.example.dependency.injection.annotation.UserGroup;
import com.example.ioc.overview.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.List;

/**
 * {@link @Qualifier} 注解（限定）依赖注入
 */
public class QualifierAnnotationDependencyInjectionDemo {
  @Autowired
  private User user;

  @Autowired
  @Qualifier("user")
  private User nameUser;

  /**
   * 一共四个 User 类型的 Bean
   * superUser
   * user
   * user1
   * user2
   */
  @Autowired
  private List<User> allUser; // 2 Beans = user + superUser

  @Autowired
  @Qualifier
  private List<User> qualifierUser; // 4 Beans = user1 + user2 + user3 + user4

  @Autowired
  @UserGroup
  private List<User> groupUser; // 2 Beans = user3 +user4

  @Bean
  @Qualifier // 进行逻辑分组
  public User user1() {
    User user = new User();
    user.setId(1);
    return user;
  }

  @Bean
  @Qualifier // 进行逻辑分组
  public User user2() {
    User user = new User();
    user.setId(2);
    return user;
  }

  @Bean
  @UserGroup // 进行逻辑分组
  public User user3() {
    User user = new User();
    user.setId(3);
    return user;
  }

  @Bean
  @UserGroup // 进行逻辑分组
  public User user4() {
    User user = new User();
    user.setId(4);
    return user;
  }

  public static void main(String[] args) {
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
    applicationContext.register(QualifierAnnotationDependencyInjectionDemo.class);
    XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(applicationContext);
    // 加载 XML 资源，解析并生成 BeanDefinition
    beanDefinitionReader.loadBeanDefinitions("classpath:/META-INF/dependency-lookup-context.xml");
    applicationContext.refresh();

    QualifierAnnotationDependencyInjectionDemo demo = applicationContext.getBean(QualifierAnnotationDependencyInjectionDemo.class);
    System.out.println("demo.user = " + demo.user);
    System.out.println("demo.nameUser = " + demo.nameUser);
    System.out.println("demo.allUser = " + demo.allUser);
    System.out.println("demo.qualifierUser = " + demo.qualifierUser);
    System.out.println("demo.groupUser = " + demo.groupUser);

    applicationContext.close();
  }
}
