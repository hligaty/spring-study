package com.example.dependency.source;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;

/**
 * 外部化配置作为依赖来源示例
 */
@Configuration
@PropertySource(value = "META-INF/default.properties", encoding = "utf-8")
public class ExternalConfigurationDependencySourceDemo {
  @Value("${user.id:-1}")
  private Long id;

  // 如果是 user.name 就会输出当前操作系统的用户名，因为系统的优先级更高
  @Value("${usr.name}")
  private String name;

  @Value("${user.resource:classpath://default.properties}")
  private Resource resource;

  public static void main(String[] args) {
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
    applicationContext.register(ExternalConfigurationDependencySourceDemo.class);
    applicationContext.refresh();

    ExternalConfigurationDependencySourceDemo demo = applicationContext.getBean(ExternalConfigurationDependencySourceDemo.class);
    System.out.println("demo.id = " + demo.id);
    System.out.println("demo.name = " + demo.name);
    System.out.println("demo.source = " + demo.resource);

    applicationContext.close();
  }
}
