package com.example.bean.definition;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 垃圾回收
 */
public class BeanGarbageCollectionDemo {
  public static void main(String[] args) throws InterruptedException {
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

    applicationContext.register(BeanInitializationDemo.class);
    //启动上下文
    applicationContext.refresh();

    //关闭上下文
    applicationContext.close();
    Thread.sleep(5000L);
    //强制触发 GC
    System.gc();
    Thread.sleep(5000L);
  }
}
