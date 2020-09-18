package com.example.ioc.overview.domain;

import com.example.ioc.overview.enums.City;
import org.springframework.core.io.Resource;

import java.util.Arrays;
import java.util.List;


public class User {
  private int id;
  private String name;
  private City city;
  private Resource configFileLocation;
  private City[] workCities;
  private List<City> lifeCities;

  @Override
  public String toString() {
    return "User{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", city=" + city +
            ", configFileLocation=" + configFileLocation +
            ", workCities=" + Arrays.toString(workCities) +
            ", lifeCities=" + lifeCities +
            '}';
  }

  public List<City> getLifeCities() {
    return lifeCities;
  }

  public void setLifeCities(List<City> lifeCities) {
    this.lifeCities = lifeCities;
  }

  public City[] getWorkCities() {
    return workCities;
  }

  public void setWorkCities(City[] workCities) {
    this.workCities = workCities;
  }

  public Resource getConfigFileLocation() {
    return configFileLocation;
  }

  public void setConfigFileLocation(Resource configFileLocation) {
    this.configFileLocation = configFileLocation;
  }

  public City getCity() {
    return city;
  }

  public void setCity(City city) {
    this.city = city;
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
