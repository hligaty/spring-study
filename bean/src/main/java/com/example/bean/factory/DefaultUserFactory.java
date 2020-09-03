package com.example.bean.factory;

import com.example.bean.definition.User;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class DefaultUserFactory implements UserFactory, InitializingBean, DisposableBean {
  @Override
  public User createUser()  {
    return new User();
  }

  @PostConstruct
  public void init() {
    System.out.println("@PostConstruct : UserFactory 初始化中");
  }

  @Override
  public void initUserFactory() {
    System.out.println("自定义初始化方法 initUserFactory() : UserFactory 初始化中");
  }


  @Override
  public void afterPropertiesSet() throws Exception {
    System.out.println("InitializingBean afterPropertiesSet() : UserFactory 初始化中");
  }


  @PreDestroy
  public void preDestory() {
    System.out.println("@PreDestroy : UserFactory 销毁中");
  }

  @Override
  public void doDestroy() {
    System.out.println("自定义销毁方法 doDestory() : UserFactory 销毁中");
  }


  @Override
  public void destroy() throws Exception {
    System.out.println("DisposableBean : destroy() 销毁中");
  }

  @Override
  protected void finalize() throws Throwable {
    System.out.println("当前 DefaultUserFactory 对象正在被回收");
  }
}
