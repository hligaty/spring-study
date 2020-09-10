package com.example.dependency.lookup;

import com.example.ioc.overview.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.HierarchicalBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;

/**
 * 层次依赖查找示例
 */
public class HierarchicalDependencyLookupDemo {
  public static void main(String[] args) {
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
    applicationContext.register(ObjectProviderDemo.class);

    // 1.获取 HierarchicalBeanFactory <- ConfigurableBeanFactory <- ConfigurableListableBeanFactory
    ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();
    System.out.println("当前 BeanFactory 的 parent BeanFactory:" + beanFactory.getParentBeanFactory());

    // 2.设置 Parent BeanFactory
    ConfigurableListableBeanFactory parentBeanFactory = createBeanFactory();
    beanFactory.setParentBeanFactory(parentBeanFactory);
    System.out.println("当前 BeanFactory 的 parent BeanFactory:" + beanFactory.getParentBeanFactory());

    // 按名称查找
    displayContainsLocalBean(beanFactory, "user");
    displayContainsLocalBean(parentBeanFactory, "user");
    displayContainsBean(beanFactory, "user");
    displayContainsBean(parentBeanFactory, "user");

    // 按类型查找 beanOfTypeIncludingAncestors 是查单个，beansOfTypeIncludingAncestors 是查集合
    Map<String, User> userMap = BeanFactoryUtils.beansOfTypeIncludingAncestors(beanFactory, User.class);
    System.out.println(userMap);

    // 查找名称列表
    String[] strings = BeanFactoryUtils.beanNamesForTypeIncludingAncestors(beanFactory, User.class);
    for (String string : strings) {
      System.out.printf(string + " ");
    }

    applicationContext.refresh();
    applicationContext.close();
  }

  private static void displayContainsBean(HierarchicalBeanFactory beanFactory, String beanName) {
    System.out.printf("当前 BeanFactory[%s] 是否包含 bean[name : %s] : %s\n", beanFactory, beanName, containsBean(beanFactory, beanName));
  }

  private static boolean containsBean(HierarchicalBeanFactory beanFactory, String beanName) {
    // 需要手动递归
    BeanFactory parentBeanFactory = beanFactory.getParentBeanFactory();
    if (parentBeanFactory instanceof HierarchicalBeanFactory) {
      HierarchicalBeanFactory parentHierarchicalBeanFactory = HierarchicalBeanFactory.class.cast(parentBeanFactory);
      if (containsBean(parentHierarchicalBeanFactory, beanName)) {
        return true;
      }
    }
    return beanFactory.containsLocalBean(beanName);
  }

  private static void displayContainsLocalBean(HierarchicalBeanFactory beanFactory, String beanName) {
    System.out.printf("当前 BeanFactory[%s] 是否包含 bean[name : %s] : %s\n", beanFactory, beanName, beanFactory.containsLocalBean(beanName));
  }

  private static ConfigurableListableBeanFactory createBeanFactory() {
    // 创建 BeanFactory 容器
    DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
    // 加载 Bean
    XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
    // XML 配置文件 classpath 路径
    String location = "classpath:/META-INF/dependency-lookup-context.xml";
    reader.loadBeanDefinitions(location);
    return beanFactory;
  }
}
