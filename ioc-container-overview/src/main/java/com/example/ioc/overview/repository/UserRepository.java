package com.example.ioc.overview.repository;

import com.example.ioc.overview.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.ApplicationContext;

import java.util.List;

public class UserRepository {
  private List<User> users;
  private BeanFactory beanFactory;
  private ObjectFactory<ApplicationContext> objectFactory;

  public ObjectFactory<ApplicationContext> getObjectFactory() {
    return objectFactory;
  }

  public void setObjectFactory(ObjectFactory<ApplicationContext> objectFactory) {
    this.objectFactory = objectFactory;
  }

  public BeanFactory getBeanFactory() {
    return beanFactory;
  }

  public void setBeanFactory(BeanFactory beanFactory) {
    this.beanFactory = beanFactory;
  }

  public List<User> getUsers() {
    return users;
  }

  public void setUsers(List<User> users) {
    this.users = users;
  }
}
