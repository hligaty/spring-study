package com.example.bean.definition;

import com.example.bean.factory.DefaultUserFactory;
import com.example.bean.factory.UserFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Bean 初始化示例
 */
@Configuration
public class BeanInitializationDemo {
  public static void main(String[] args) {
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

    applicationContext.register(BeanInitializationDemo.class);
    //启动上下文
    applicationContext.refresh();

    System.out.println("spring 上下文已启动");
    UserFactory userFactory = applicationContext.getBean(UserFactory.class);
    System.out.println(userFactory);
    //自定义初始化三中方式顺序如下
    //@PostConstruct : UserFactory 初始化中
    //InitializingBean afterPropertiesSet() : UserFactory 初始化中
    //自定义初始化方法 initUserFactory() : UserFactory 初始化中

    //关闭上下文
    applicationContext.close();
  }

//  @Lazy
  @Bean(initMethod = "initUserFactory", destroyMethod = "doDestroy")
  public UserFactory userFactory() {
    return new DefaultUserFactory();
  }

}
