package com.example.dependency.injection;

import com.example.ioc.overview.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;

/**
 * 手动模式
 * 基于注解的依赖方法注入示例
 */
public class AnnotationDependencyMethodInjectionDemo {
  private UserHolder userHolder;

  private UserHolder userHolder2;

  // @Autowired 会自动忽略 static 方法
  @Autowired
  public void init(UserHolder userHolder) {
    this.userHolder = userHolder;
  }

  // @Resource 在 static 方法上会报错
  @Resource
  public void init2(UserHolder userHolder) {
    this.userHolder2 = userHolder;
  }

  public static void main(String[] args) {
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
    applicationContext.register(AnnotationDependencyMethodInjectionDemo.class);
    XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(applicationContext);
    // 加载 XML 资源，解析并生成 BeanDefinition
    beanDefinitionReader.loadBeanDefinitions("classpath:/META-INF/dependency-lookup-context.xml");
    applicationContext.refresh();

    AnnotationDependencyMethodInjectionDemo demo = applicationContext.getBean(AnnotationDependencyMethodInjectionDemo.class);
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
