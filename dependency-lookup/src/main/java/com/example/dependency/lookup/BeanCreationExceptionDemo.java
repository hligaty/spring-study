package com.example.dependency.lookup;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.annotation.PostConstruct;

/**
 * {@link BeanCreationException} 示例
 */
public class BeanCreationExceptionDemo {
  public static void main(String[] args) {
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

    // CharSequence 是一个接口，不能实例化
    BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(POJO.class);
    applicationContext.registerBeanDefinition("error", beanDefinitionBuilder.getBeanDefinition());

    applicationContext.refresh();
    applicationContext.close();
  }

  static class POJO implements InitializingBean {
    @PostConstruct
    public void init() throws Exception {
      throw new Exception("init():初始换错误");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
      throw new Exception("afterPropertiesSet():初始换错误");
    }
  }

}
