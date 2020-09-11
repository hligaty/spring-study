package com.example.dependency.lookup;

import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * {@link NoUniqueBeanDefinitionException} 示例代码
 */
public class NoUniqueBeanDefinitionExceptionDemo {
  public static void main(String[] args) {
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
    applicationContext.register(NoUniqueBeanDefinitionExceptionDemo.class);
    applicationContext.refresh();
    try {
      // 由于 Spring 应用上下文存在两个 String 类型的 Bean，通过单一类型查找会出现异常
      applicationContext.getBean(String.class);
    } catch (NoUniqueBeanDefinitionException e) {
      System.err.printf("Spring 应用上下文存在%d个 %s 类型的Bean，具体原因：%s",
              e.getNumberOfBeansFound(),
              String.class.getName(),
              e.getMessage());
    }
    applicationContext.close();
  }

  @Bean
  public String bean1() {
    return "bean1";
  }

  @Bean
  public String bean2() {
    return "bean2";
  }

  @Bean
  public String bean3() {
    return "bean3";
  }
}
