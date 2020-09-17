package com.example.ioc.overview.domain;

import com.example.ioc.overview.enums.City;

public class User {
  private int id;
  private String name;
  private City city;

  public City getCity() {
    return city;
  }

  public void setCity(City city) {
    this.city = city;
  }

  @Override
  public String toString() {
    return "User{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", city=" + city +
            '}';
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public static User createMethod() {
    return new User();
  }
}
