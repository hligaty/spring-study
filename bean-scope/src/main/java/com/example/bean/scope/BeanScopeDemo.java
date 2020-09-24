package com.example.bean.scope;

import com.example.ioc.overview.domain.User;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import java.util.Map;

/**
 * 依赖来源示例
 */
public class BeanScopeDemo implements DisposableBean {
  @Bean
  // 默认的 scope 就是 singleton
  public static User singletonUser() {
    return createUser();
  }

  @Bean
  @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
  public static User prototypeUser() {
    return createUser();
  }

  public static User createUser() {
    User user = new User();
    user.setName(String.valueOf(System.nanoTime()));
    return user;
  }

  @Autowired
  @Qualifier("singletonUser")
  private User singletonUser;

  @Autowired
  @Qualifier("singletonUser")
  private User singletonUser1;

  @Autowired
  @Qualifier("prototypeUser")
  private User prototypeUser;

  @Autowired
  @Qualifier("prototypeUser")
  private User prototypeUser1;

  @Autowired
  private Map<String, User> userMap;

  @Autowired
  private ConfigurableListableBeanFactory beanFactory;

  /**
   * 依赖注入与依赖查找的来源不同，依赖注入可以注入非托管对象，比如 BeanFactory、ResourceLoader、ApplicationContext、ApplicationEventPublisher。
   */
  public static void main(String[] args) {
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
    applicationContext.register(BeanScopeDemo.class);
    applicationContext.addBeanFactoryPostProcessor(beanFactory -> {
      beanFactory.addBeanPostProcessor(new BeanPostProcessor() {
        @Override
        public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
          System.out.println(bean.getClass() + " Bean 名称 " + beanName + "在初始化后回调...");
          return bean;
        }
      });
    });
    applicationContext.refresh();

    // 结论一：
    // singletonUser 无论是依赖查找还是依赖注入都是同一个对象
    // prototypeUser 无论是依赖查找还是依赖注入都是重新生成的对象

    // 结论二：
    // 如果依赖注入是集合类型对象， Singleton 和 Prototype 都会在其中
    // 且 Prototype 会重新生成一个

    // 结论三：
    // 无论 Singleton 还是 Prototype 都会执行初始化方法回调
    // 不过只有 Singleton Bean 才会执行销毁方法回调，因为 Prototype 的 bean 交给使用者后就离开 spring 容器了
    scopeBeansByLookup(applicationContext);
    scopeBeansByInjection(applicationContext);

    applicationContext.close();
  }

  private static void scopeBeansByLookup(AnnotationConfigApplicationContext applicationContext) {
    for (int i = 0; i < 3; i++) {
      // singletonUser 是共享的 Bean 对象
      User singletonUser = applicationContext.getBean("singletonUser", User.class);
      System.out.println("singletonUser = " + singletonUser);
      // prototypeUser 是每次依赖查找生成的 Bean 对象
      User prototypeUser = applicationContext.getBean("prototypeUser", User.class);
      System.out.println("prototypeUser = " + prototypeUser);
    }
  }

  private static void scopeBeansByInjection(AnnotationConfigApplicationContext applicationContext) {
    BeanScopeDemo demo = applicationContext.getBean(BeanScopeDemo.class);
    System.out.println("demo.singletonUser = " + demo.singletonUser);
    System.out.println("demo.singletonUser1 = " + demo.singletonUser1);
    System.out.println("demo.prototypeUser = " + demo.prototypeUser);
    System.out.println("demo.prototypeUser1 = " + demo.prototypeUser1);
    System.out.println("demo.userMap = " + demo.userMap);
  }

  @Override
  public void destroy() throws Exception {
    System.out.println("当前 BeanScopeDemo 正在销毁");
    this.prototypeUser.destory();
    this.prototypeUser1.destory();
    userMap.forEach((beanName, user) ->{
      BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
      if (beanDefinition.isPrototype()) {
        // 如果 bean 是 Prototory 就调用销毁方法
        user.destory();
      }
    });
    System.out.println("当前 BeanScopeDemo 已经销毁");
  }
}