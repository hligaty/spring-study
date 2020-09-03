package com.example.bean.factory;

import com.example.bean.definition.User;

public class SetIdUserFactory implements UserFactory {
  @Override
  public User createUser() {
    User user = new User();
    user.setId(1);
    return user;
  }

  @Override
  public void initUserFactory() {

  }

  @Override
  public void doDestroy() {

  }
}
