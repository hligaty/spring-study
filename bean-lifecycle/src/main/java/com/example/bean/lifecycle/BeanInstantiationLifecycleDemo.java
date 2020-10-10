package com.example.bean.lifecycle;

import com.example.ioc.overview.domain.SuperUser;
import com.example.ioc.overview.domain.User;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
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
    String[] locations = {"META-INF/dependency-lookup-context.xml", "META-INF/bean-constructor-dependency-injection.xml"};
//    Resource resource = new ClassPathResource(location);
//    EncodedResource encodedResource = new EncodedResource(resource, "UTF-8");
//    int beanNumbers = beanDefinitionReader.loadBeanDefinitions(encodedResource);
    int beanNumbers = beanDefinitionReader.loadBeanDefinitions(locations);

    System.out.println("加载的 Bean 数量：" + beanNumbers);
    User user = beanFactory.getBean("user", User.class);
    System.out.println(user);
    User superUser = beanFactory.getBean("superUser", User.class);
    System.out.println(superUser);

    // 构造器注入按照类型注入，resolveDependency
    UserHolder userHolder = beanFactory.getBean("userHolder", UserHolder.class);
    System.out.println(userHolder);
  }

  static class MyInstantiationAwareBeanPostProcessor implements InstantiationAwareBeanPostProcessor {
    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
      if (ObjectUtils.nullSafeEquals("user", beanName) && ObjectUtils.nullSafeEquals(bean.getClass(), User.class)) {
        // "User" 对象不允许属性赋值（填入）（配置元信息 -> 属性值）
        User user = (User) bean;
        user.setId(666);
        user.setName("UserName");
        return false;
      }
      return true;
    }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
      if (ObjectUtils.nullSafeEquals("superUser", beanName) && ObjectUtils.nullSafeEquals(beanClass, SuperUser.class)) {
        return new SuperUser();
      }
      return null; // 返回为空保持 Spring 原来的操作
    }
  }
}
