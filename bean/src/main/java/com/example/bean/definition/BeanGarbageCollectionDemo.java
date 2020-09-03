package com.example.bean.definition;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class BeanGarbageCollectionDemo {
  public static void main(String[] args) {
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

    applicationContext.register(BeanInitializationDemo.class);
    //启动上下文
    applicationContext.refresh();

    //关闭上下文
    applicationContext.close();
    System.gc();
  }
}
