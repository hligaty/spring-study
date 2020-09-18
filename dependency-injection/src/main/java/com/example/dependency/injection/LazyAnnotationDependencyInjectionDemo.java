package com.example.dependency.injection;

import com.example.ioc.overview.domain.User;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Set;

/**
 * {@link @Qualifier} 注解（限定）依赖注入
 */
public class LazyAnnotationDependencyInjectionDemo {
  @Autowired
  private User user;

  @Autowired
  private ObjectProvider<User> userObjectProvider;

  @Autowired
  private ObjectFactory<Set<User>> userObjectFactory;

  public static void main(String[] args) {
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
    applicationContext.register(LazyAnnotationDependencyInjectionDemo.class);
    XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(applicationContext);
    // 加载 XML 资源，解析并生成 BeanDefinition
    beanDefinitionReader.loadBeanDefinitions("classpath:/META-INF/dependency-lookup-context.xml");
    applicationContext.refresh();

    LazyAnnotationDependencyInjectionDemo demo = applicationContext.getBean(LazyAnnotationDependencyInjectionDemo.class);
    System.out.println("demo.user = " + demo.user);
    System.out.println("demo.userObjectProvider = " + demo.userObjectProvider.getObject());
    demo.userObjectProvider.forEach(System.out::println);
    demo.userObjectFactory.getObject().forEach(System.out::println);
    applicationContext.close();
  }
}
