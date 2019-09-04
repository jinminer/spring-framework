# Core spring

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

    * `Spring Expression Language` SlayDragonQuest bean的声明使用了Spring表达式语言，将System.out（这是一个PrintStream）传入到了SlayDragonQuest的构造器中。

    ![di-bean-xml](https://raw.githubusercontent.com/jinminer/docs/master/spring-framework/spring-in-action/1.0-spring-core/1.1.2-di-bean-xml.png)

  * 基于Java的注解配置，可作为`XML`的替代方案

    ![](https://raw.githubusercontent.com/jinminer/docs/master/spring-framework/spring-in-action/1.0-spring-core/1.1.2-di-bean-xml.png)







#### 1.1.3 Applying aspects





#### 1.1.4 Eliminating boilerplate code with templates





### 1.2 Containing your beans

#### 1.2.1 Working with an application context





#### 1.2.2 A bean's life





### 1.3 Surveying the Spring landscape

#### 1.3.1 Spring modules





#### 1.3.2 Spring portfolio



### 1.4 What's new in Spring?

#### 1.4.1 What was new in Spring 3.1?



#### 1.4.2 What was new in Spring 3.2?





#### 1.4.3 What's new in Spring 4.0?



### 1.5 Summary



## 2 Wiring beans

> 详细地介绍DI，展现应用程序中的各个组件（bean）如何装配在一起。这包括基于XML装配、基于Java装配以及Spring所提供的自动装配。



### 2.1 Exploring Spring's configuration options

> 

### 2.2 Automatically wiring beans





#### 2.2.1 Creating discoverable beans





#### 2.2.2 Naming a component-scanned bean







#### 2.2.3 Setting a base package for component scanning





#### 2.2.4 Annotating beans to automatically wired





#### 2.2.5 Verifying automatic configuration





### 2.3 Wiring beans with Java



#### 2.3.1 Creating a configuration class





#### 2.3.2 Declaring a simple bean





### 2.4 Wiring beans with XML



#### 2.4.1 Creating an XML configuration specification







#### 2.4.2 Declaring a simple < bean > 





#### 2.4.3 Initializing a bean with constructor injection







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



















































