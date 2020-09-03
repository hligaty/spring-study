package com.example.bean.factory;

import com.example.bean.definition.User;

public interface UserFactory {
  public User createUser();

  void initUserFactory();

  void doDestroy();
}
