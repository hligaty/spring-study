package com.example.bean.lifecycle;

import com.example.ioc.overview.domain.User;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

/**
 * User Holder
 */
public class UserHolder implements BeanNameAware, BeanClassLoaderAware, BeanFactoryAware, EnvironmentAware {
  private final User user;
  private ClassLoader classLoader;
  private BeanFactory beanFactory;
  private String beanName;
  private Environment environment;

  public UserHolder(User user) {
    this.user = user;
  }

  @Override
  public String toString() {
    return "UserHolder{" +
            "user=" + user +
            ", beanName='" + beanName + '\'' +
            '}';
  }

  @Override
  public void setBeanClassLoader(ClassLoader classLoader) {
    this.classLoader = classLoader;
  }

  @Override
  public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
    this.beanFactory = beanFactory;
  }

  @Override
  public void setBeanName(String name) {
    beanName = name;
  }

  @Override
  public void setEnvironment(Environment environment) {
    this.environment = environment;
  }
}
