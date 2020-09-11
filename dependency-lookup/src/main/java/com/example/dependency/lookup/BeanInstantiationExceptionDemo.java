package com.example.dependency.lookup;

import org.springframework.beans.BeanInstantiationException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * {@link BeanInstantiationException} 示例
 */
public class BeanInstantiationExceptionDemo {
  public static void main(String[] args) {
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

    // CharSequence 是一个接口，不能实例化
    BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(CharSequence.class);
    applicationContext.registerBeanDefinition("error", beanDefinitionBuilder.getBeanDefinition());

    applicationContext.refresh();
    applicationContext.close();
  }
}
