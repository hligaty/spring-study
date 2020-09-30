package com.example.bean.lifecycle;

import com.example.ioc.overview.domain.User;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;


/**
 * BeanDefinition 合并示例
 */
public class MergedBeanDefinitionDemo {
  public static void main(String[] args) {
    /**
     * Xml 中配置的 bean 是一个 GenericBeanDefinition，是通过 ConfiguableBeanFactory 的 getMergedBeanDefinition（AbstractBeanFactory#getMergedBeanDefinition是唯一实现）
     * 实现的，具体由 getMergedBeanDefinition(String, BeanDefinitio, BeanDefinition)解决。
     * getMergedBeanDefinition 判断 beanDefinition 是否有 parent，没有就复制自己变成 RootBeanDefinition 返回，
     * 否则 和上面一样复制自己变成一个 RootBeanDefinition 返回，但在返回前用了 overrideFrom() 方法增加了 PropertyValues（新的属性，例如 superUser 的 address）等一些信息
     */
    DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
    XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
    String location = "META-INF/dependency-lookup-context.xml";
    Resource resource = new ClassPathResource(location);
    EncodedResource encodedResource = new EncodedResource(resource, "UTF-8");
    int beanNumbers = beanDefinitionReader.loadBeanDefinitions(encodedResource);
    System.out.println("加载的 Bean 数量：" + beanNumbers);
    User user = beanFactory.getBean("user", User.class);
    System.out.println(user);
    User superUser = beanFactory.getBean("superUser", User.class);
    System.out.println(superUser);
  }
}
