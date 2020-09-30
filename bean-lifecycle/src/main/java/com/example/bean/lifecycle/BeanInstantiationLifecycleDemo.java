package com.example.bean.lifecycle;

import com.example.ioc.overview.domain.SuperUser;
import com.example.ioc.overview.domain.User;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.util.ObjectUtils;

/**
 * Bean 实例化生命周期示例
 */
public class BeanInstantiationLifecycleDemo {
  public static void main(String[] args) {
    DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
    // 添加 BeanPostProcessor 示例（示例）
    beanFactory.addBeanPostProcessor(new MyInstantiationAwareBeanPostProcessor());
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

  static class MyInstantiationAwareBeanPostProcessor implements InstantiationAwareBeanPostProcessor {
    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
      if (ObjectUtils.nullSafeEquals("superUser", beanName) && ObjectUtils.nullSafeEquals(beanClass, SuperUser.class)) {
        return new SuperUser();
      }
      return null; // 返回为空保持 Spring 原来的操作
    }
  }
}
