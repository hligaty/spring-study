package com.example.dependency.injection;

import com.example.ioc.overview.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.inject.Inject;
import java.util.Map;
import java.util.Optional;

/**
 * 注解驱动的依赖注入处理过程
 */
public class AnnotationDependencyInjectionResolutionDemo {
  @Autowired
  // 加上 @Lazy 就不会返回真正的 user，而是返回一个 CGlib 代理的对象，因为延迟没用到 user 对象
  private User user; // 实时注入 + 通过 User.class 依赖查找（处理）
                     // DependencyDescriptor ->
                     // required = true 必须
                     // eager = true 实时注入
                     // fieldName = user 字段名称
                     // field.type = User.class 字段类型

  @Autowired
  private Map<String, User> userMap;

  @Autowired
  private Optional<User> userOptional;

  @Inject
  private User injectUser;

  public static void main(String[] args) {
    /**
     * 1.  postProcessMergedBeanDefinition：构建元数据
     * 2.  postProcessProperties：注入
     * 2.1 inject：把 resolveDependency 返回的对象注入到字段
     * 2.2 resolveDependency：初始化 DependencyDescriptor，调用 doResolveDependency 获取注入的对象
     * 2.3 doResolveDependency：集合类型返回 multipleBeans，单个返回 resolveCandidate(autowiredBeanName, type, this)
     */
    /**
     * @Autowired 和 @Inject 使用 AutowiredAnnotationBeanPostProcessor 注入
     * @Resource 使用 CommonAnnotationBeanPostProcessor 注入
     * 其中 CommonAnnotationBeanPostProcessor 还包括 @PostConstruct 和 @PreDestroy 的（Java 本身的注解）生命周期管理
     *
     * ps：
     * AutowiredAnnotationBeanPostProcessor 的优先级 order = Ordered.LOWEST_PRECEDENCE - 2 （最小优先级减 2）
     * CommonAnnotationBeanPostProcessor 的优先级 order = Ordered.LOWEST_PRECEDENCE - 3 （最小优先级减 3）
     * 所以 AutowiredAnnotationBeanPostProcessor 比 CommonAnnotationBeanPostProcessor 先执行
     */
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
    applicationContext.register(AnnotationDependencyInjectionResolutionDemo.class);
    XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(applicationContext);
    // 加载 XML 资源，解析并生成 BeanDefinition
    beanDefinitionReader.loadBeanDefinitions("classpath:/META-INF/dependency-lookup-context.xml");
    applicationContext.refresh();

    AnnotationDependencyInjectionResolutionDemo demo = applicationContext.getBean(AnnotationDependencyInjectionResolutionDemo.class);
    // 输出 superUser
    System.out.println("demo.user = " + demo.user);
    // 输出 user superBean
    System.out.println("demo.userMap = " + demo.userMap);
    // 输出 userOptional
    System.out.println("demo.userOptional = " + demo.userOptional);
    System.out.println("demo.injectUser = " + demo.injectUser);

    applicationContext.close();
  }
}
