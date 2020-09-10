package com.example.bean.definition;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.GenericBeanDefinition;

/**
 * 操作 Bean 元信息的两种方式
 */
public class BeanDefinitionDemo {
  public static void main(String[] args) {
    //1
    BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(User.class);

    beanDefinitionBuilder.addPropertyValue("id", 1);
    beanDefinitionBuilder.addPropertyValue("name", "123");

    BeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
    System.out.println(beanDefinition);

    //2
    GenericBeanDefinition genericBeanDefinition = new GenericBeanDefinition();
    genericBeanDefinition.setBeanClass(User.class);
    MutablePropertyValues propertyValues = new MutablePropertyValues();
    propertyValues.add("id", 1)
            .add("name", "123");
    genericBeanDefinition.setPropertyValues(propertyValues);
    System.out.println(genericBeanDefinition);
  }
}
