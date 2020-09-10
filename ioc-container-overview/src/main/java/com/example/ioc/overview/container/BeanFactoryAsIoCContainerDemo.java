package com.example.ioc.overview.container;

import com.example.ioc.overview.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

import java.util.Map;

/**
 * BeanFactory 作为 IoC 容器示例
 */
public class BeanFactoryAsIoCContainerDemo {
  public static void main(String[] args) {
    // 创建 BeanFactory 容器
    DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
    // 加载 Bean
    XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
    // XML 配置文件 classpath 路径
    String location = "classpath:/META-INF/dependency-lookup-context.xml";
    int beanDefinitionsCount = reader.loadBeanDefinitions(location);
    System.out.println("Bean 加载的数量:" + beanDefinitionsCount);

    lookupCollectionByType(beanFactory);
  }

  private static void lookupCollectionByType(BeanFactory beanFactory) {
    if (beanFactory instanceof ListableBeanFactory) {
      ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
      Map<String, User> userMap = listableBeanFactory.getBeansOfType(User.class);
      System.out.println("查找所有的 User 集合对象" + userMap);
    }
  }
}
