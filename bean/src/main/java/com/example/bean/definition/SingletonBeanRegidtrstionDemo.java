package com.example.bean.definition;

import com.example.bean.factory.DefaultUserFactory;
import com.example.bean.factory.UserFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 注册单例的 Bean
 */
public class SingletonBeanRegidtrstionDemo {
  public static void main(String[] args) {
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

    UserFactory userFactory = new DefaultUserFactory();
    ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();
    beanFactory.registerSingleton("userFactory", userFactory);
    //启动上下文
    applicationContext.refresh();

    //自定义初始化三中方式顺序如下
    //@PostConstruct : UserFactory 初始化中
    //InitializingBean afterPropertiesSet() : UserFactory 初始化中
    //自定义初始化方法 initUserFactory() : UserFactory 初始化中

    UserFactory userFactoryByLookup = applicationContext.getBean("userFactory", UserFactory.class);
    System.out.println(userFactoryByLookup == userFactory);
    //关闭上下文
    applicationContext.close();

  }
}
