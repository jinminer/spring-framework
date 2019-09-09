Core spring

> Spring框架核心知识，即Spring基础原理：
>
> Spring容器、依赖注入（dependency injection，DI）和面向切面编程（aspect-oriented programming，AOP），也就是Spring框架的核心

## 1 Springing into action

> Spring初识：包括DI和AOP的概况和一些基本样例，以及它们是如何帮助读者解耦应用组件的。
>
> * Spring的bean容器
> * Spring的核心模块
> * 更为强大的Spring生态系统
> * Spring的新功能



### 1.1 Simplifying Java development

> * spring最根本的使命：简化 `java` 开发
> * 简化 `java` 开发的4中关键策略：
>   * 基于 `POJO` 的轻量级和最小侵入性编程；
>   * 通过依赖注入和面向接口实现松耦合；
>   * 基于切面和惯例进行声明式编程；
>   * 通过切面和模板减少样板式代码。

#### 1.1.1 Unleashing the power of POJOs

**激发 `POJO` 的潜能** 

* 很多框架通过强迫应用继承它们的类或实现它们的接口从而导致应用与框架绑死，比如在使用 `Struts` 进行 `MVC` 架构模型开发时，通常会让自己的 `Action` 类继承 `com.opensymphony.xwork2.ActionSupport` ，这种侵入式的编程模型会使业务代码过分依赖于框架本身，如其基于值栈的数据存取方式等机制(在 `Struts` 项目中，一个 `Action` 类中值栈对象以及其   `get/set`方法使得代码冗余度骤增，而且值栈对象类型定义以及后期业务迭代开发都会让人头疼不已)，都会使开发者在实际开发过程中过分关注于框架本身，从而阉割了业务开发的舒适度。
* `Spring` 竭力避免因自身的 `API`  而弄乱你的应用代码，不会强迫你实现`Spring`规范的接口或继承`Spring`规范的类，相反，在基于`Spring`构建的应用中，它的类通常没有任何痕迹表明你使用了`Spring`。最坏的场景是，一个类或许会使用`Spring`注解，但它依旧是`POJO` ，对于其本职工作并无任何影响。而且`Spring` 的 `DI` 机制，会使得应用对象彼此之间保持松散耦合。



#### 1.1.2 Injection dependencies

* 任何一个有实际意义的应用（肯定比Hello World示例更复杂）都会由两个或者更多的类组成，这些类相互之间进行协作来完成特定的业务逻辑。按照传统的做法，每个对象负责管理与自己相互协作的对象（即它所依赖的对象）的引用，这将会导致高度耦合和难以测试的代码。

* 耦合具有两面性（two-headed beast）。一方面，紧密耦合的代码难以测试、难以复用、难以理解，并且典型地表现出“打地鼠”式的bug特性（修复一个bug，将会出现一个或者更多新的bug）。另一方面，一定程度的耦合又是必须的——完全没有耦合的代码什么也做不了。为了完成有实际意义的功能，不同的类必须以适当的方式进行交互。总而言之，**耦合是必须的，但应当被小心谨慎地管理**。

* 通过DI，对象的依赖关系将由系统中负责协调各对象的第三方组件在创建对象的时候进行设定。对象无需自行创建或管理它们的依赖关系，如图1.1所示，依赖关系将被自动注入到需要它们的对象当中去。

  ![1.1.2-di](https://raw.githubusercontent.com/jinminer/docs/master/spring-framework/spring-in-action/1.0-spring-core/1.1.2-di.png)

* 依赖注入方式

  * 构造器注入 `constructor injection` 

* 单元测试便捷：

  * 依赖注入模型中，构造方法/ `set` 方法入参是一个顶层的接口或抽象类，一个对象只通过接口（而不是具体实现或初始化过程）来表明依赖关系，那么这种依赖就能够在对象本身毫不知情的情况下，用不同的具体实现进行替换。

  * 对依赖进行替换的一个最常用方法就是在测试的时候使用`mock`实现，比如使用`mock`框架`Mockito`去创建一个被注入接口的`mock`实现，再编写相关的单元测试案例，进行便捷的测试。

    ![di-mock](https://raw.githubusercontent.com/jinminer/docs/master/spring-framework/spring-in-action/1.0-spring-core/1.1.2-di-mock.png)

* 创建应用组件之间协作的行为通常称为装配 `wiring` 

  * Spring有多种装配bean的方式，采用XML是很常见的一种装配方式。

    * `Spring Expression Language` `SlayDragonQuest bean`的声明使用了`Spring`表达式语言，将`System.out`（这是一个`PrintStream`）传入到了`SlayDragonQuest`的构造器中。

    ![di-bean-xml](https://raw.githubusercontent.com/jinminer/docs/master/spring-framework/spring-in-action/1.0-spring-core/1.1.2-di-bean-xml.png)

  * 基于`Java`的注解配置，可作为`XML`的替代方案

    ![di-bean-annotation](https://raw.githubusercontent.com/jinminer/docs/master/spring-framework/spring-in-action/1.0-spring-core/1.1.2-di-bean-annotation.png)



#### 1.1.3 Applying aspects

基于切面进行声明式编程。

> ​	`DI`能够让相互协作的软件组件保持松散耦合，而面向切面编程（`aspect-oriented programming`，`AOP`）允许开发人员把遍布应用各处的功能分离出来形成可重用的组件。
>
> ​	面向切面编程往往被定义为促使软件系统实现关注点的分离的一项技术。系统由许多不同的组件组成，每一个组件各负责一块特定功能。除了实现自身核心的功能之外，这些组件还经常承担着额外的职责。诸如日志、事务管理和安全这样的系统服务经常融入到自身具有核心业务逻辑的组件中去，这些系统服务通常被称为横切关注点，因为它们会跨越系统的多个组件。 如果将这些关注点分散到多个组件中去，你的代码将会带来双重的复杂性。
>
> * 实现系统关注点功能的代码将会重复出现在多个组件中。这意味着如果你要改变这些关注点的逻辑，必须修改各个模块中的相关实现。即使你把这些关注点抽象为一个独立的模块，其他模块只是调用它的方法，但方法的调用还是会重复出现在各个模块中。
> * 组件会因为那些与自身核心业务无关的代码而变得混乱。如一个向地址簿增加地址条目的方法应该只关注如何添加地址，而不应该关注它是不是安全的或者是否需要支持事务。



图1.2展示了这种复杂性。左边的业务对象与系统级服务结合得过于紧密。每个对象不但要知道它需要记日志、进行安全控制和参与事务，还要亲自执行这些服务。

![aop-general](https://raw.githubusercontent.com/jinminer/docs/master/spring-framework/spring-in-action/1.0-spring-core/1.1.3-aop-general.png)

> ​	`AOP`能够使这些服务模块化，并以声明的方式将它们应用到它们需要影响的组件中去。所造成的结果就是这些组件会具有更高的内聚性并且会更加关注自身的业务，完全不需要了解涉及系统服务所带来复杂性。总之，`AOP`能够确保`POJO`的简单性。



如图1.3所示，我们可以把切面想象为覆盖在很多组件之上的一个外壳。应用是由那些实现各自业务功能的模块组成的。借助AOP，可以使用各种功能层去包裹核心业务层。这些层以声明的方式灵活地应用到系统中，你的核心应用甚至根本不知道它们的存在。这是一个非常强大的理念，可以将安全、事务和日志关注点与核心业务逻辑相分离。

![aop-best](https://raw.githubusercontent.com/jinminer/docs/master/spring-framework/spring-in-action/1.0-spring-core/1.1.3-aop-best.png)

* AOP 应用举例

  * 目的：业务系统不需要显示调用切面服务
  * 结构：**before** 、**after** 、**切入点** 
  * 场景：假设我们需要使用吟游诗人这个服务类来记载骑士的所有事迹。
  * xml配置：这里使用了Spring的aop配置命名空间把Minstrel bean声明为一个切面。首先，需要把Minstrel声明为一bean，然后在元素中引用该bean。为了进一步定义切面，声明（使用）在embarkOnQuest()方法执行前调用Minstrel的singBeforeQuest()方法。这种方式被称为前置通知（**before advice**）。同时声明（使用）在embarkOnQuest()方法执行后调用singAfter Quest()方法。这种方式被称为后置通知（**after advice**）。在这两种方式中，pointcut-ref属性都引用了名字为embank的**切入点**。该切入点是在前边的元素中定义的，并配置expression属性来选择所应用的通知。表达式的语法采用的是AspectJ的切点表达式语言。

  ![aop-example-xml](https://raw.githubusercontent.com/jinminer/docs/master/spring-framework/spring-in-action/1.0-spring-core/1.1.3-aop-example-xml.png)



#### 1.1.4 Eliminating boilerplate code with templates

使用模板消除样板式代码

* 许多Java API，例如JDBC，会涉及编写大量的样板式代码，如：

  ![boilerplate-jdbc](https://raw.githubusercontent.com/jinminer/docs/master/spring-framework/spring-in-action/1.0-spring-core/1.1.4-template-boilerplate-jdbc-1.png)

* 使用Spring的JdbcTemplate（利用了 Java 5特性的JdbcTemplate实现）模板能够让你的代码关注于自身的职责



### 1.2 Containing your beans

* Spring容器负责创建对象，装配它们，配置它们并管理它们的整个生命周期，从生存到死亡（在这里，可能就是new到
  finalize()）。在Spring应用中，对象由Spring容器创建和装配，并存在容器之中：

![containing-beans](https://raw.githubusercontent.com/jinminer/docs/master/spring-framework/spring-in-action/1.0-spring-core/1.2-containing-beans.png)

* 容器是Spring框架的核心。Spring容器使用DI管理构成应用的组件，它会创建相互协作的组件之间的关联，使这些对象更简单干净，更易于理解，更易于重用并且更易于进行单元测试。
* Spring容器并不是只有一个。Spring自带了多个容器实现，可以归为两种不同的类型：
  * bean工厂，由`org.springframework. beans.factory.BeanFactory`接口定义，是最简单的容器，提供基本的DI支持。
  * 应用上下文，由`org.springframework.context.ApplicationContext`接口定义，基于BeanFactory构建，并提供应用框架级别的服务，例如从属性文件解析文本信息以及发布应用事件给感兴趣的事件监听者。



#### 1.2.1 Working with an application context

* 常见地Spring应用上下文：
  * `AnnotationConfigApplicationContext`：从一个或多个基于Java的配置类中加载Spring应用上下文。
  * `AnnotationConfigWebApplicationContext`：从一个或多个基于Java的配置类中加载Spring Web应用上下文。
  * `ClassPathXmlApplicationContext`：从类路径下的一个或多个XML配置文件中加载上下文定义，把应用上下文的定义文件作为类资源。
  * `FileSystemXmlapplicationcontext`：从文件系统下的一个或多个XML配置文件中加载上下文定义。
  * `XmlWebApplicationContext`：从Web应用下的一个或多个XML配置文件中加载上下文定义。	



#### 1.2.2 A bean's life

* Spring bean的生命周期

  ![beans-lifecycle](https://raw.githubusercontent.com/jinminer/docs/master/spring-framework/spring-in-action/1.0-spring-core/1.2.2-beans-lifecycle-1.png)

* bean在Spring容器中从创建到销毁经历了若干阶段，每一阶段都可以针对Spring如何管理bean进行个性化定制

  * Spring对bean进行实例化；
  * Spring将值和bean的引用注入到bean对应的属性中；
  * 如果bean实现了BeanNameAware接口，Spring将bean的ID传递给setBean-Name()方法；
  * 如果bean实现了BeanFactoryAware接口，Spring将调用setBeanFactory()方法，将BeanFactory容器实例传入；
  * 如果bean实现了ApplicationContextAware接口，Spring将调用setApplicationContext()方法，将bean所在的应用上下文的引用传入进来；
  * 如果bean实现了BeanPostProcessor接口，Spring将调用它们的post-ProcessBeforeInitialization()方法；
  * 如果bean实现了InitializingBean接口，Spring将调用它们的after-PropertiesSet()方法。类似地，如果bean使用initmethod声明了初始化方法，该方法也会被调用；
  * 如果bean实现了BeanPostProcessor接口，Spring将调用它们的post-ProcessAfterInitialization()方法；
  * 此时，bean已经准备就绪，可以被应用程序使用了，它们将一直驻留在应用上下文中，直到该应用上下文被销毁；
  * 如果bean实现了DisposableBean接口，Spring将调用它的destroy()接口方法。同样，如果bean使用destroy-method声明了销毁方法，该方法也会被调用。



### 1.3 Surveying the Spring landscape



#### 1.3.1 Spring modules

* spring模块划分

  * 在Spring 4.0中，Spring框架的发布版本包括了20个不同的模块，每个模块会有3个JAR文件（二进制类库、源码的JAR文件以及JavaDoc的JAR文件）。完整的库JAR文件如图1.6所示。

  ![spring-modules-1](https://raw.githubusercontent.com/jinminer/docs/master/spring-framework/spring-in-action/1.0-spring-core/1.3.1-spring-modules-1.png)

  * spring各个模块依据其所属的功能可以划分为6类不同的功能

  ![spring-modules-2](https://raw.githubusercontent.com/jinminer/docs/master/spring-framework/spring-in-action/1.0-spring-core/1.3.1-spring-modules-2.png)

* spring模块详解
  * Spring核心容器
    * 容器是Spring框架最核心的部分，它管理着Spring应用中bean的创建、配置和管理。在该模块中，包括了Spring bean工厂，它为Spring提供了DI的功能。基于bean工厂，我们还会发现有多种Spring应用上下文的实现，每一种都提供了配置Spring的不同方式。
    * 除了bean工厂和应用上下文，该模块也提供了许多企业服务，例如Email、JNDI访问、EJB集成和调度。
    * 所有的Spring模块都构建于核心容器之上。当你配置应用时，其实你隐式地使用了这些类。
  * Spring的AOP模块
    * 在AOP模块中，Spring对面向切面编程提供了丰富的支持。这个模块是Spring应用系统中开发切面的基础。与DI一样，AOP可以帮助应用对象解耦。借助于AOP，可以将遍布系统的关注点（例如事务和安
      全）从它们所应用的对象中解耦出来。
  * 数据访问与集成
    * 使用JDBC编写代码通常会导致大量的样板式代码，例如获得数据库连接、创建语句、处理结果集到最后关闭数据库连接。Spring的JDBC和DAO（Data Access Object）模块抽象了这些样板式代码，使我们的数据库代码变得简单明了，还可以避免因为关闭数据库资源失败而引发的问题。该模块在多种数据库服务的错误信息之上构建了一个语义丰富的异常层，以后我们再也不需要解释那些隐晦专有的SQL错误信息了！
    * 对于那些更喜欢ORM（Object-Relational Mapping）工具而不愿意直接使用JDBC的开发者，Spring提供了ORM模块。Spring的ORM模块建立在对DAO的支持之上，并为多个ORM框架提供了一种构建DAO的简便方式。Spring没有尝试去创建自己的ORM解决方案，而是对许多流行的ORM框架进行了集成，包Hibernate、Java Persisternce API、Java Data Object和iBATIS SQLMaps。Spring的事务管理支持所有的ORM框架以及JDBC。
  * Web与远程调用
    * MVC（Model-View-Controller）模式是一种普遍被接受的构建Web应用的方法，它可以帮助用户将界面逻辑与应用逻辑分离。Java从来不缺少MVC框架，Apache的Struts、JSF、WebWork和Tapestry都是可选的最流行的MVC框架。
    * 虽然Spring能够与多种流行的MVC框架进行集成，但它的Web和远程调用模块自带了一个强大的MVC框架，有助于在Web层提升应用的松耦合水平
    * 除了面向用户的Web应用，该模块还提供了多种构建与其他应用交互的远程调用方案。Spring远程调用功能集成了RMI（Remote Method Invocation）、Hessian、Burlap、JAX-WS，同时Spring还自带了一个远程调用框架：HTTP invoker。Spring还提供了暴露和使用REST API的良好支持。
  * Instrumentation
    * Spring的Instrumentation模块提供了为JVM添加代理（agent）的功能。具体来讲，它为Tomcat提供了一个织入代理，能够为Tomcat传递类文件，就像这些文件是被类加载器加载的一样。
  * 测试
    * 通过该模块，你会发现Spring为使用JNDI、Servlet和Portlet编写单元测试提供了一系列的mock对象实现。对于集成测试，该模块为加载Spring应用上下文中的bean集合以及与Spring上下文中的bean进行交互提供了支持。



#### 1.3.2 Spring portfolio

Spring Portfolio包括多个构建于核心Spring框架之上的框架和类库，整个Spring Portfolio几乎为每一个领域的Java开发都提供了Spring编程模型。

* Spring Web Flow
  * Spring Web Flow建立于Spring MVC框架之上，它为基于流程的会话式Web应用（可以想一下购物车或者向导功能）提供了支持。
* Spring Web Service
  * 虽然核心的Spring框架提供了将Spring bean以声明的方式发布为Web Service的功能，但是这些服务是基于一个具有争议性的架构（拙劣的契约后置模型）之上而构建的。这些服务的契约由bean的接口来决定。 Spring Web Service提供了契约优先的Web Service模型，服务的实现都是为了满足服务的契约而编写的。
* Spring Security
  * 安全对于许多应用都是一个非常关键的切面。利用Spring AOP，Spring Security为Spring应用提供了声明式的安全机制。
* Spring Integration
  * 许多企业级应用都需要与其他应用进行交互。Spring Integration提供了多种通用应用集成模式的Spring声明式风格实现。
* Spring Batch
  * 当我们需要对数据进行大量操作时，没有任何技术可以比批处理更胜任这种场景。如果需要开发一个批处理应用，你可以通过Spring Batch，使用Spring强大的面向POJO的编程模型。
* Spring Data
  * Spring Data使得在Spring中使用任何数据库都变得非常容易。
  * 不管你使用文档数据库，如MongoDB，图数据库，如Neo4j，还是传统的关系型数据库，Spring Data都为持久化提供了一种简单的编程模型。这包括为多种数据库类型提供了一种自动化的Repository机制，它负责为你创建Repository的实现。
* Spring Social
  * Spring Social并不仅仅是tweet和好友。尽管名字是这样，但Spring Social更多的是关注连接（connect），而不是社交（social）。它能够帮助你通过REST API连接Spring应用，其中有些Spring应用可能原本并没有任何社交方面的功能目标。
* Spring Mobile
  * Spring Mobile是Spring MVC新的扩展模块，用于支持移动Web应用开发。
* Spring for Android
  * Spring Android旨在通过Spring框架为开发基于Android设备的本地应用提供某些简单的支持。

* Spring Boot
  * Spring极大地简化了众多的编程任务，减少甚至消除了很多样板式代码，如果没有Spring的话，在日常工作中你不得不编写这样的样板代码。Spring Boot是一个崭新的令人兴奋的项目，它以Spring的视角，致力于简化Spring本身。
  * Spring Boot大量依赖于自动配置技术，它能够消除大部分（在很多场景中，甚至是全部）Spring配置。它还提供了多个Starter项目，不管你使用Maven还是Gradle，这都能减少Spring工程构建文件的大小。



### 1.4 What's new in Spring?

#### 1.4.1 What was new in Spring 3.1?

Spring 3.1(基于3.0.5版本)带来了多项有用的新特性和增强，其中有很多都是关于如何简化和改善配置的。除此之外，Spring 3.1还提供了声明式缓存的支持以及众多针对Spring MVC的功能增强。

* Spring 3.1重要的功能升级：
  * 为了解决各种环境下（如开发、测试和生产）选择不同配置的问题，Spring 3.1引入了环境profile功能。借助于profile，就能根据应用部署在什么环境之中选择不同的数据源bean；
  * 在Spring 3.0基于Java的配置之上，Spring 3.1添加了多个enable注解，这样就能使用这个注解启用Spring的特定功能；
  * 添加了Spring对声明式缓存的支持，能够使用简单的注解声明缓存边界和规则，这与你以前声明事务边界很类似；
  * 新添加的用于构造器注入的c命名空间，它类似于Spring 2.0所提供的面向属性的p命名空间，p命名空间用于属性注入，它们都是非常简洁易用的；
  * Spring开始支持Servlet 3.0，包括在基于Java的配置中声	明Servlet和Filter，而不再借助于web.xml；
  * 改善Spring对JPA的支持，使得它能够在Spring中完整地配置JPA，不必再使用persistence.xml文件。
* Spring 3.1针对Spring MVC的功能增强：
  * 自动绑定路径变量到模型属性中；
  * 提供了@RequestMappingproduces和consumes属性，用于匹配请求中的Accept和Content-Type头部信息；
  * 提供了@RequestPart注解，用于将multipart请求中的某些部分绑定到处理器的方法参数中；
  * 支持Flash属性（在redirect请求之后依然能够存活的属性）以及用于在请求间存放flash属性的RedirectAttributes类型。

* Spring 3.1不再支持的功能。
  * 为了支持原生的EntityManager，Spring的JpaTemplate和JpaDaoSupport类被废弃掉了。
  * 尽管它们已经被废弃了，但直到Spring 3.2版本，它依然是可以使用的。但最好不要再使用它们了，因为它们不会进行更新以支持JPA 2.0，并且已经在Spring 4中移除掉了。



#### 1.4.2 What was new in Spring 3.2?

Spring 3.1在很大程度上聚焦于配置改善以及其他的一些增强，包括Spring MVC的增强，而Spring 3.2是主要关注Spring MVC的一个发布版本。

* Spring MVC 3.2带来了如下的功能提升：
  * Spring 3.2的控制器（Controller）可以使用Servlet 3.0的异步请求，允许在一个独立的线程中处理请求，从而将Servlet线程解放出来处理更多的请求；
  * 尽管从Spring 2.5开始，Spring MVC控制器就能以POJO的形式进行很便利地测试，但是Spring 3.2引入了Spring MVC测试框架，用于为控制器编写更为丰富的测试，断言它们作为控制器的行为行为是否正确，而且在使用的过程中并不需要Servlet容器；
  * 除了提升控制器的测试功能，Spring 3.2还包含了基于RestTemplate的客户端的测试支持，在测试的过程中，不需要往真正的REST端点上发送请求；
  * @ControllerAdvice注解能够将通用的@ExceptionHandler、@ InitBinder和@ModelAttributes方法收集到一个类中，并应用到所有控制器上；
  * 在Spring 3.2之前，只能通过ContentNegotiatingViewResolver使用完整的内容协商（full content negotiation）功能。但是在Spring 3.2中，完整的内容协商功能可以在整个Spring MVC中使用，即便是依赖于消息转换器（message converter）使用和产生内容的控制器方法也能使用该功能；
  * Spring MVC 3.2包含了一个新的@MatrixVariable注解，这个注解能够将请求中的矩阵变量（matrix variable）绑定到处理器的方法参数中；
  * 基础的抽象类AbstractDispatcherServletInitializer能够非常便利地配置DispatcherServlet，而不必再使用web.xml。与之类似，当你希望通过基于Java的方式来配置Spring的时候，可以使用Abstract- AnnotationConfigDispatcherServletInitializer的子类；
  * 新增了ResponseEntityExceptionHandler，可以用来替代Default- HandlerExceptionResolver。ResponseEntityExceptionHandler方法会返回ResponseEntity，而不是ModelAndView；
  * RestTemplate和@RequestBody的参数可以支持范型；
  * RestTemplate和@RequestMapping可以支持HTTP PATCH方法；
  * 在拦截器匹配时，支持使用URL模式将其排除在拦截器的处理功能之外。
* 下面列出了Spring 3.2中几项最为有意思的新特性（非MVC的功能改善）：
  * @Autowired、@Value和@Bean注解能够作为元注解，用于创建自定义的注入和bean声明注解；
  * @DateTimeFormat注解不再强依赖JodaTime。如果提供了JodaTime，就会使用它，否则的话，会使
    用SimpleDateFormat；
  * Spring的声明式缓存提供了对JCache 0.5的支持；
  * 支持定义全局的格式来解析和渲染日期与时间；
  * 在集成测试中，能够配置和加载WebApplicationContext；
  * 在集成测试中，能够针对request和session作用域的bean进行测试。



#### 1.4.3 What's new in Spring 4.0?

Spring 4.0中包含了很多令人兴奋的新特性，包括：

* Spring提供了对WebSocket编程的支持，包括支持JSR-356——Java API for WebSocket；
* 鉴于WebSocket仅仅提供了一种低层次的API，急需高层次的抽象，因此Spring 4.0在WebSocket之上提供了一个高层次的面向消息的编程模型，该模型基于SockJS，并且包含了对STOMP协议的支持；
* 新的消息（messaging）模块，很多的类型来源于Spring Integration项目。这个消息模块支持Spring的SockJS/STOMP功能，同时提供了基于模板的方式发布消息；
* Spring是第一批（如果不说是第一个的话）支持Java 8特性的Java框架，比如它所支持的lambda表达式。别的暂且不说，这首先能够让使用特定的回调接口（如RowMapper和JdbcTemplate）更加简洁，代码更加易读；
* 与Java 8同时得到支持的是JSR-310——Date与Time API，在处理日期和时间时，它为开发者提供了比java.util.Date
  或java.util.Calendar更丰富的API；
* 为Groovy开发的应用程序提供了更加顺畅的编程体验，尤其是支持非常便利地完全采用Groovy开发Spring应用程序。随这些一起提供的是来自于Grails的BeanBuilder，借助它能够通过Groovy配置Spring应用；
* 添加了条件化创建bean的功能，在这里只有开发人员定义的条件满足时，才会创建所声明的bean；
* Spring 4.0包含了Spring RestTemplate的一个新的异步实现，它会立即返回并且允许在操作完成后执行回调；
* 添加了对多项JEE规范的支持，包括JMS 2.0、JTA 1.2、JPA 2.1和Bean Validation 1.1。



### 1.5 Summary

* DI是组装应用对象的一种方式，借助这种方式对象无需知道依赖来自何处或者依赖的实现方式。不同于自己获取依赖对象，对象会在**运行期**赋予它们所依赖的对象。依赖对象通常会通过接口了解所注入的对象，这样的话就能确保低耦合。
* AOP可以帮助应用将散落在各处的逻辑汇集于一处——切面。当Spring装配bean的时候，这些切面能够在**运行期**编织起来，这样就能非常有效地赋予bean新的行为。



## 2 Wiring beans

详细地介绍DI，展现应用程序中的各个组件（bean）如何装配在一起。这包括基于XML装配、基于Java装配以及Spring所提供的自动装配。主要内容：

* 声明bean
* 构造器注入和Setter方法注入
* 装配bean
* 控制bean的创建和销毁

> 在Spring中，对象无需自己查找或创建与其所关联的其他对象。相反，容器负责把需要相互协作的对象引用赋予各个对象。
>
> 创建应用对象之间协作关系的行为通常称为装配（wiring），这也是依赖注入（DI）的本质。



### 2.1 Exploring Spring's configuration options

> Spring容器负责创建应用程序中的bean并通过DI来协调这些对象之间的关系。

* Spring bean 的三种主要的装配机制：
  * 在XML中进行显式配置。
  * 在Java中进行显式配置。
  * 隐式的bean发现机制和自动装配。
* Spring的配置风格是可以互相搭配，混合使用
  * 可以选择使用XML装配一些bean，使用Spring基于Java的配置（JavaConfig）来装配另一些bean，而将剩余的bean让Spring去自动发现。
* 建议是尽可能地使用自动配置的机制
  * 显式配置越少越好。当必须要显式配置bean的时候（比如，有些源码不是由你来维护的，而当你需要为这些代码配置bean的时候），我推荐使用类型安全并且比XML更加强大的JavaConfig。最后，只有当你想要使用便利的XML命名空间，并且在JavaConfig中没有同样的实现时，才应该使用XML。



### 2.2 Automatically wiring beans

* Spring从两个角度来实现自动化装配：
  * 组件扫描（component scanning）：Spring会自动发现应用上下文中所创建的bean。
  * 自动装配（autowiring）：Spring自动满足bean之间的依赖。



#### 2.2.1 Creating discoverable beans

* `@Component` - 用于组件类，实际被应用上下文装配的bean
  * 表明当前类会作为组件类，并告知Spring要为这个类创建bean。
  * 组件扫描默认是不启用的。我们还需要显式配置一下Spring，从而命令它去寻找带有@Component注解的类，并为其创建bean。
* `@ComponentScan` - 用于配置加载类
  * 注解启用了组件扫描，表明加载当前类时会进行组件扫描
* `@Configuration` - 用于配置加载类
  * 表明当前类会作为配置类
* `@ContextConfiguration` - 用于应用层调用者或启动类
  * 告诉当前类需要在被`@Configuration` 注解的类中加载配置，当前类运行后，最终会在应用上下文中装配相应的bean
* `@Autowired` - 用于应用层调用者
  * 在当前类中声明需要调用的bean，并自动装配应用上下文中管理的目标bean



#### 2.2.2 Naming a component-scanned bean

* `@Component` - Spring 的组件命名
  * Spring应用上下文中所有的bean都会给定一个ID。
  * 如果没有明确地为bean设置ID，Spring就会根据类名为其指定一个ID。具体来讲，这个bean所给定的ID
    就是将类名的第一个字母变为小写。
  * 如果想为bean设置自定义的ID，可以将期望的ID作为值传递给@Component注解：`@Component("testBean")` 
* `@Named` - Java 的组件命名
  * 可以使用Java依赖注入规范（Java Dependency Injection）中所提供的@Named注解来为bean设置ID
  * Spring支持将@Named作为@Component注解的替代方案。两者之间有一些细微的差异，但是在大多数场景中，它们是可以互相替换的。
* 推荐使用 `@Component` 注解
  * 支持度、兼容性：是Spring自己提供的bean注解，与框架本身更加契合
  * 见名知意：相较于 `@Named` 注解，`@Component` 清楚地表明当前类定位和用途



#### 2.2.3 Setting a base package for component scanning

设置组件扫描的基础包：

* String类型

  * 当不为`@ComponentScan`设置任何属性时，按照默认规则，它会以配置类所在的当前包作为基础包（base package）来扫描组件。
  * 如果想要将配置类放在单独的包中，使其与其他的应用代码区分开来。指定不同的基础包，只需要在`@ComponentScan`的`value`属性中指明包的名称即可，如：`@ComponentScan("com.xxx")` 
  * 如果想更加清晰地表明你所设置的是基础包，可以通过basePackages属性进行配置：`@ComponentScan(basePackages="com.xxx")` 
  * 设置多个基础包，将basePackages属性设置为要扫描包
    的一个数组：`@ComponentScan(basePackages={"com.alipay"),"come.wechat"}` 

* 类或接口

  * 以String类型设置的基础包的方法是类型不安全（not type-safe）的。如果重构代码的话，那么所指定的基础包可能就会出现错误了，需要去修改旧的基础包配置

  * 将扫描的基础包指定为包中所包含的类或接口：

    `@ComponentScan(basePackageClasses={AlipayService.class,WechatService.class}`  

  * basePackageClasses属性所设置的数组中包含了类，**这些类所在的包将会作为组件扫描的基础包**。

  * 可以考虑在包中创建一个用来进行扫描的空标记接口（marker interface）。通过标记接口的方式，你依然能够保持对重构友好的接口引用，但是可以避免引用任何实际的应用程序代码（在稍后重构中，这些应用代码有可能会从想要扫描的包中移除掉）。



#### 2.2.4 Annotating beans to automatically wired

* `@Autowired` - Spring依赖注入：

  * 自动装配

    * 自动装配就是让Spring自动满足bean依赖的一种方法，在满足依赖的过程中，会在Spring应用上下文中寻找匹配某个bean需求的其他bean。为了声明要进行自动装配，我们可以借助Spring的`@Autowired`注解。

  * 注入方式

    * `constructor`  构造器注入

      ![autowired-constructor](https://raw.githubusercontent.com/jinminer/docs/master/spring-framework/spring-in-action/1.0-spring-core/2.2.4-autowired-constructor.png)

    * `setter` 方法注入

      ![autowired-setter](https://raw.githubusercontent.com/jinminer/docs/master/spring-framework/spring-in-action/1.0-spring-core/2.2.4-autowired-setter.png)

    * 任意方法注入

      * `@Autowired` 注解可以用在类的任何方法上。

      ![autowired-anymetod](https://raw.githubusercontent.com/jinminer/docs/master/spring-framework/spring-in-action/1.0-spring-core/2.2.4-autowired-anymetod.png)

  * 应用上下文进行bean匹配

    * 不管是构造器、Setter方法还是其他的方法，Spring都会尝试满足方法参数上所声明的依赖。假如有且只有一个bean匹配依赖需求的话，那么这个bean将会被装配进来。

    * `required`

      * 如果没有匹配的bean，那么在应用上下文创建的时候，Spring会抛出一个异常。为了避免异常的出现，你可以将`@Autowired`的`required`属性设置为false：

      ![autowired-require](https://raw.githubusercontent.com/jinminer/docs/master/spring-framework/spring-in-action/1.0-spring-core/2.2.4-autowired-require.png)

      * 将required属性设置为false时，Spring会尝试执行自动装配，但是如果没有匹配的bean的话，Spring将会让这个bean处于未装配的状态。
      * 注意：当required属性设置为false时，如果在代码中没有进行null检查的话，这个处于未装配状态的属性有可能会出现NullPointerException
      * 如果有多个bean都能满足依赖关系的话，Spring将会抛出一个异常，表明没有明确指定要选择哪个bean进行自动装配。

* `@Inject` - 基于Java依赖注入规范

  * `@Inject` 注解来源于Java依赖注入规范，在自动装配中，Spring同时支持`@Inject`和`@Autowired`。尽管二者之间有着一些细微的差别，但是在大多数场景下，它们都是可以互相替换的。



#### 2.2.5 Verifying automatic configuration



### 2.3 Wiring beans with Java

* 尽管在很多场景下通过组件扫描和自动装配实现Spring的自动化配置是更为推荐的方式，但有时候自动化配置的方案行不通，因此需要明确配置Spring。比如说，你想要将第三方库中的组件装配到你的应用中，在这种情况下，是没有办法在它的类上添加`@Component`和`@Autowired`注解的，因此就不能使用自动化装配的方案了。
* 在这种情况下，你必须要采用显式装配的方式。在进行显式配置的时候，有两种可选方案：Java和XML。



#### 2.3.1 Creating a configuration class

* `@Configuration` 注解表明这个类是一个配置类，该类应该包含在Spring应用上下文中如何创建bean的细节。
* 发现Spring应该创建的bean
  * `@ComponentScan` - 组件扫描
  * `@Configuration` - 显式配置

#### 2.3.2 Declaring a simple bean

* `@Bean` - 声明简单的bean

  * 要在JavaConfig中声明bean，我们需要编写一个方法，这个方法会创建所需类型的实例，然后给这个方法添加`@Bean`注解。比方说，下面的代码声明了CompactDisc bean：

    ![declaring-bean-1](https://raw.githubusercontent.com/jinminer/docs/master/spring-framework/spring-in-action/1.0-spring-core/2.3.2-declaring-bean-1.png)

  * 默认情况下，bean的ID与带有`@Bean`注解的方法名是一样的。如果想为其设置成一个不同
    的名字的话，那么可以重命名该方法，也可以通过`name`属性指定一个不同的名字：

    ![declaring-bean-2](https://raw.githubusercontent.com/jinminer/docs/master/spring-framework/spring-in-action/1.0-spring-core/2.3.2-declaring-bean-2.png)



#### 2.3.3 Injecting with JavaConfig

借助JavaConfig实现注入，将多个bean进行组合装配

* 显示调用的方式引用bean

  * 在JavaConfig中装配bean的最简单方式就是引用创建bean的方法。如通过构造注入的方式，将需要组合装配的bean进行关联：

    ![composite-beans-1](https://raw.githubusercontent.com/jinminer/docs/master/spring-framework/spring-in-action/1.0-spring-core/2.3.3-composite-beans-1.png)

  * 在进行bean的组合装配时，Spring将会拦截所有对它的调用，并确保直接返回该方法所创建的bean，而不是每次都对其进行实际的调用。

    * 即如果Spring上下文中已经有该bean的实例，则直接返回，如果没有就创建一个。
    * Spring中的bean都是单例的，我们可以将同一个SgtPeppers实例注入到任意数量的其他bean之中。也就是说下面代码中两个CDPlayer bean会得到相同的SgtPeppers实例。

    ![composite-beans-2](https://raw.githubusercontent.com/jinminer/docs/master/spring-framework/spring-in-action/1.0-spring-core/2.3.3-composite-beans-2.png)

* DI 方式引用bean

  * 构造器注入

    ![composite-beans-3](https://raw.githubusercontent.com/jinminer/docs/master/spring-framework/spring-in-action/1.0-spring-core/2.3.3-composite-beans-3.png)

  * Setter方法注入

    ![composite-beans-4](https://raw.githubusercontent.com/jinminer/docs/master/spring-framework/spring-in-action/1.0-spring-core/2.3.3-composite-beans-4.png)

  * 通过这种方式引用其他的bean通常是最佳的选择，因为它不会要求将CompactDisc声明到同一个配置类之中。在这里甚至没有要求CompactDisc必须要在JavaConfig中声明，实际上它可以通过组件扫描功能自动发现或者通过XML来进行配置。你可以将配置分散到多个配置类、XML文件以及自动扫描和装配bean之中，只要功能完整健全即可。



### 2.4 Wiring beans with XML



#### 2.4.1 Creating an XML configuration specification

* 配置描述

  * `@Configuration` 

    * 在JavaConfig中需要创建一个带有@Configuration注解的类

  * `Spring XML` 

    * 创建一个XML文件，并且要以元素为根。
    * 在使用XML时，需要在配置文件的顶部声明多个XML模式（XSD）文件，这些文件定义了配置Spring的XML元素。

    ![configuration-spirngxml-1](https://raw.githubusercontent.com/jinminer/docs/master/spring-framework/spring-in-action/1.0-spring-core/2.4.1-configuration-spirngxml-1.png)



#### 2.4.2 Declaring a simple < bean > 

* 声明一个bean

  * javaConfig - `@Bean` 
  * xml - `<bean class="com.jinm.test.TestBean" />` 
    * 通过`class`属性来指定的需要创建bean，并且要使用全限定的类名。
* 自动化bean命名
    * 因为没有明确给定ID，所以这个bean将会根据全限定类名来进行命名。比如上面的例子中，bean的ID将会是 " `com.jinm.test.TestBean#0` "。
    * 其中，“#0”是一个计数的形式，用来区分相同类型的其他bean。如果你声明了另外一个 `TestBean`，并且没有明确进行标识，那么它自动得到的ID将会是“com.jinm.test.TestBean#1”。
  * 自定义bean命名 
    *  `<bean id="testBean" class="com.jinm.test.TestBean" />` 
  
* XML配置的缺点

  * bean 创建方式
    * `XML` - 当Spring解析XML配置文件，发现 `<bean>` 这个元素时，它将会调用在 `class`  属性中定义的类的默认构造器来创建bean。
    * `JavaConfig` - 在JavaConfig配置方式中，你可以通过任何可以想象到的方法来创建bean实例。
  * 类型检查
    * `XML` - 在XML配置模式中，bean的类型以字符串的形式设置在了class属性中，这个机制导致Spring并不能在编译期的类型检查中，确定bean定义的合法性。如果类名或类路径变了就会抛出异常。
    * `JavaConfig` - 而JavaConfig基于Java 注解实现，直接作用于目标bean上，不会存在这种问题

  

#### 2.4.3 Initializing a bean with constructor injection

以构造器注入的方式初始化bean

* 在XML中声明DI时，会有多种可选的配置方案和风格。具体到构造器注入，有两种基本的配置方案可供选择：
  * `<constructor-arg>` 元素
  * 使用Spring 3.0所引入的`c-命名空间` 
* 两者的区别在很大程度就是是否冗长烦琐。`<constructor-arg>` 元素比使用`c-命名空间`会更加冗长，从而
  导致XML更加难以读懂。另外，有些事情 `<constructor-arg>` 可以做到，但是使用c-命名空间却无法实现。
* 构造器注入bean引用
  * 





#### 2.4.4 Setting properties







### 2.5 Importing and mixing configurations



#### 2.5.1 Referencing XML configuration in JavaConfig





#### 2.5.2 Referencing JavaConfig in XML configuration





### 2.6 Summary



## 3 Advanced Wiring

> 高级装配技术，包括：条件化装配、处理自动装配时的歧义性、作用域以及Spring表达式语言，还有如何发挥Spring容器最强大的威力。

### 3.1 Environments and profiles





#### 3.1.1 Configuring profiles bean





#### 3.1.2 Activating profiles





### 3.2 Conditional beans



### 3.3 Addressing ambiguity in autowiring



#### 3.3.1 Designating a primary bean



#### 3.3.2 Qualifying autowired beans



### 3.4 Scoping beans



#### 3.4.1 Working with request and session scope



#### 3.4.2 Declaring scoped proxies in XML





### 3.5 Runtime value injection



#### 3.5.1 Injecting external values





#### 3.5.2 Wiring with the Spring Expression Language





### 3.6 Summary



## 4 Aspect-oriented Spring



> 使用Spring的AOP来为对象解耦那些对其提供服务的横切性关注点，即使用Spring的AOP特性把系统级的服务（例如安全和审计）从它们所服务的对象中解耦出来。这部分内容是后面各章节：使用AOP来提供声明式服务，如事务、安全和缓存等内容的基础。



### 4.1 What is aspect-oriented programming?



#### 4.1.1 Defining AOP terminology



#### 4.1.2 Spring's AOP support



### 4.2 Selecting join points with pointcuts



#### 4.2.1 Writing pointcuts



#### 4.2.2 Selecting beans in pointcuts



### 4.3 Creating annotated aspects



#### 4.3.1 Defining an aspect





#### 4.3.2 Creating around advice



#### 4.3.3 Handling parameters in advice



#### 4.3.4 Annotating introductions



### 4.4 Declaring aspects in XML



#### 4.4.1 Declaring before and after advice





#### 4.4.2 Declaring around advice





#### 4.4.3 Passing parameters to advice





#### 4.4.4 Introducing new functionality with aspects





### 4.5 Injecting AspectJ aspects



### 4.6 Summary



















































