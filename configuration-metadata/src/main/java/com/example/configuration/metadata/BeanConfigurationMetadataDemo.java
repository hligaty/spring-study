package com.example.configuration.metadata;

import com.example.ioc.overview.domain.User;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.util.ObjectUtils;

/**
 * Bean 配置元信息示例
 */
public class BeanConfigurationMetadataDemo {

  public static void main(String[] args) {
    BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(User.class);
    beanDefinitionBuilder.addPropertyValue("name", "h");
    AbstractBeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
    // 附加属性（不影响 Bean populate、initialize）
    beanDefinition.setAttribute("name", "d");
    // 当前 BeanDefinition 来自何方（辅助作用）
    beanDefinition.setSource(BeanConfigurationMetadataDemo.class);

    DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
    beanFactory.addBeanPostProcessor(new BeanPostProcessor() {
      @Override
      public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (ObjectUtils.nullSafeEquals(beanName, "user") && User.class.equals(bean.getClass())) {
          BeanDefinition userBean = beanFactory.getBeanDefinition("user");
          if (BeanConfigurationMetadataDemo.class.equals(userBean.getSource())) { // 通过 source 判断
            // 属性（存储）上下文
            String name = (String) userBean.getAttribute("name");
            User user = (User) bean;
            user.setName(name);
          }
        }
        return null;
      }
    });
    beanFactory.registerBeanDefinition("user", beanDefinition);
    User user = beanFactory.getBean("user", User.class);
    System.out.println(user);
  }
}
