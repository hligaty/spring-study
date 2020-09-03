package com.example.bean.factory;

import com.example.bean.definition.User;
import org.springframework.beans.factory.FactoryBean;

public class UserFactoryBean implements FactoryBean<User> {

  @Override
  public User getObject() throws Exception {
    return User.createMethod();
  }

  @Override
  public Class<?> getObjectType() {
    return User.class;
  }
}
