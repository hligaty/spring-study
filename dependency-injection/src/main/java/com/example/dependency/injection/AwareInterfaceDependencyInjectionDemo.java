package com.example.dependency.injection;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 手动模式
 * 基于 {@link org.springframework.beans.factory.Aware} 接口回调的依赖注入示例
 */
public class AwareInterfaceDependencyInjectionDemo implements BeanFactoryAware, ApplicationContextAware {
  /**
   * 为了避免再写一个类才写成 static，但正常使用尽量不要这样写
   */
  private static BeanFactory beanFactory;
  private static ApplicationContext applicationContext;

  public static void main(String[] args) {
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
    applicationContext.register(AwareInterfaceDependencyInjectionDemo.class);
    applicationContext.refresh();

    System.out.println(beanFactory == applicationContext.getBeanFactory());
    System.out.println(AwareInterfaceDependencyInjectionDemo.applicationContext == applicationContext);

    applicationContext.close();
  }

  @Override
  public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
    AwareInterfaceDependencyInjectionDemo.beanFactory = beanFactory;
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    AwareInterfaceDependencyInjectionDemo.applicationContext = applicationContext;
  }
}
