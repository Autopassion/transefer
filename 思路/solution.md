## 1、学员自定义@Service、@Autowired、@Transactional注解类，完成基于注解的IOC容器（Bean对象创建及依赖注入维护）和声明式事务控制，写到转账工程中，并且可以实现转账成功和转账异常时事务回滚
    
    a.定义自定义注解@Component(一般容器组件注解),@Service(业务逻辑层组件注解),@Repository(持久层组件注解),@Autowired(自动装配注解)
      @Transactional(aop事务注解)
    b.创建类扫描工具类，用于扫描指定包下面的类信息；创建注解工具类，用于判断运行时类的注解相关信息
    c.在相关组件类上面添加相关注解
    d.创建AnnoationBeanFactory，用于扫描指定包下面的类以及注解信息，创建bean放入容器中，根据类@Autowired注解决类之间的依赖，根据Transactional注解判断
      需要实现代理类的bean，使用ProxyFactory来进行创建代理类，并放回beanMap中，覆盖之前的bean实例
    总结：
        采用自定义注解的方式替换xml文件配置方式，提供类的相关信息来交由工厂来创建bean  
 
   
