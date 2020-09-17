package com.example.dependency.injection;

import com.example.ioc.overview.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;

/**
 * 手动模式
 * 基于注解的依赖字段注入示例
 */
public class AnnotationDependencyFieldInjectionDemo {
  // @Autowired 会自动忽略 static 字段
  @Autowired
  private UserHolder userHolder;

  // @Resource 在 static 字段上会报错
  @Resource
  private UserHolder userHolder2;

  public static void main(String[] args) {
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
    applicationContext.register(AnnotationDependencyFieldInjectionDemo.class);
    XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(applicationContext);
    // 加载 XML 资源，解析并生成 BeanDefinition
    beanDefinitionReader.loadBeanDefinitions("classpath:/META-INF/dependency-lookup-context.xml");
    applicationContext.refresh();

    AnnotationDependencyFieldInjectionDemo demo = applicationContext.getBean(AnnotationDependencyFieldInjectionDemo.class);
    System.out.println(demo.userHolder);
    System.out.println(demo.userHolder2);
    System.out.println(demo.userHolder == demo.userHolder2);
    applicationContext.close();
  }

  @Bean
  public UserHolder userHolder(User user) {
    return new UserHolder(user);
  }
}
