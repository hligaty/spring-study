package com.example.dependency.source;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.ResourceLoader;

import javax.annotation.PostConstruct;

/**
 * 依赖来源示例
 */
public class DependencySourceDemo {
  // 注入在 postProcessorProperties 方法执行，早于 setter 注入，也早于 @PostConstruct
  @Autowired
  private BeanFactory beanFactory;
  @Autowired
  private ResourceLoader resourceLoader;
  @Autowired
  private ApplicationContext applicationContext;
  @Autowired
  private ApplicationEventPublisher applicationEventPublisher;

  @PostConstruct
  public void init() {
    System.out.println("beanFactory == applicationContext " + (beanFactory == applicationContext));
    System.out.println("beanFactory == applicationContext.getBeanFactory() " + (beanFactory == applicationContext.getAutowireCapableBeanFactory()));
    System.out.println("resourceLoader == applicationContext " + (resourceLoader == applicationContext));
    System.out.println("applicationEventPublisher == applicationContext " + (applicationEventPublisher == applicationContext));
  }

  @PostConstruct
  public void initByLookup() {
    getBean(BeanFactory.class);
    getBean(ResourceLoader.class);
    getBean(ApplicationContext.class);
    getBean(ApplicationEventPublisher.class);
  }

  public <T> T getBean(Class<T> beanType) {
    try {
      return beanFactory.getBean(beanType);
    } catch (BeansException e) {
      System.err.println("当前类型" + beanType.getName() + "无法在 BeanFactory 中查找！");
    }
    return null;
  }

  /**
   * 依赖注入与依赖查找的来源不同，依赖注入可以注入非托管对象，比如 BeanFactory、ResourceLoader、ApplicationContext、ApplicationEventPublisher。
   */
  public static void main(String[] args) {
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
    applicationContext.register(DependencySourceDemo.class);
    applicationContext.refresh();
    DependencySourceDemo demo = applicationContext.getBean(DependencySourceDemo.class);

    applicationContext.close();
  }
}