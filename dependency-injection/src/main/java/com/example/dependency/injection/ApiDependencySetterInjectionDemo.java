package com.example.dependency.injection;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 手动模式
 * 基于 JAVA API 的依赖注入 Constructor 方法注入示例
 */
public class ApiDependencySetterInjectionDemo {
  public static void main(String[] args) {
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
    XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(applicationContext);
    // 加载 XML 资源，解析并生成 BeanDefinition
    beanDefinitionReader.loadBeanDefinitions("classpath:/META-INF/dependency-lookup-context.xml");
    // 注册 UserHolder 的 BeanDefinition
    BeanDefinition userHolderBeanDefinition = createUserHolderBeanDefinition();
    applicationContext.registerBeanDefinition("userHolder", userHolderBeanDefinition);
    applicationContext.refresh();

    UserHolder userHolder = applicationContext.getBean(UserHolder.class);
    System.out.println(userHolder);
    applicationContext.close();
  }

  /**
   * 为 {@link UserHolder} 生成 {@link BeanDefinition}
   */
  private static BeanDefinition createUserHolderBeanDefinition() {
    BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(UserHolder.class);
    beanDefinitionBuilder.addConstructorArgReference("user");
    return beanDefinitionBuilder.getBeanDefinition();
  }
}
