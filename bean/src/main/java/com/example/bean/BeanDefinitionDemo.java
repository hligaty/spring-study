package com.example.bean;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.GenericBeanDefinition;

public class BeanDefinitionDemo {
  public static void main(String[] args) {
    //操作bean的元信息，两种方式

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
