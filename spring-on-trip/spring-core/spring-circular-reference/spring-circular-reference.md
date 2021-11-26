# 循环依赖

## Bean的初始化

* 初始化过程
  * bean实例 初始化
  * bean属性 初始化(依赖注入)
* 详解
  * spring bean的初始化过程实际分为两步，即当前Bean实例的初始化，和当前Bean属性的初始化
  * 相对于一般java bean而言，这两个动作并不是同时进行，spring bean的属性并非立即赋值，也就是说spring bean在完成它自己实例化之后，得到的对象其实是一个半成品，它所依赖的reference属性(被@Autowired注解，或者bean.xml配置的类)在第一步完成时并没有赋值，还是空值
  * 正是因为spring bean实例化机制，使得bean的循环依赖问题得以解决

![](https://raw.githubusercontent.com/jinminer/docs/master/spring-framework/spring-on-trip/spring-circular-reference/1.0-spring-bean-init-step.png)



## 循环依赖

### 什么是循环依赖

> 一个或多个对象实例之间存在直接或间接的相互依赖关系，这种相互依赖关系构成了一个环形调用。
>
> 循环依赖也就是循环引用，两个或则两个以上的bean互相持有对方的引用，最终形成闭环。

### 分类

* spring中按照注入方式不同分为：
  * 构造器循环依赖
  * 属性循环依赖
  * spring 可以解决属性循环依赖

* java bean按照依赖方式不同分为：

  * 同一个bean依赖闭环

    ![](https://raw.githubusercontent.com/jinminer/docs/master/spring-framework/spring-on-trip/spring-circular-reference/1.1-bean-circular-reference-1.png)

  * 两个bean依赖闭环

    <img src="https://raw.githubusercontent.com/jinminer/docs/master/spring-framework/spring-on-trip/spring-circular-reference/1.2-bean-circular-reference-2.jpg" style="zoom: 67%;" />

  * 多个bean依赖闭环

    <img src="https://raw.githubusercontent.com/jinminer/docs/master/spring-framework/spring-on-trip/spring-circular-reference/1.3-bean-circular-reference-3.jpg" style="zoom:67%;" />



## spring 循环依赖

### 场景

spring中出现循环依赖的主要场景：

* 构造器注入
  * 不能解决
* 单例的 setter 注入
  * 能解决
* 多例的 setter 注入
  * 不能解决
* 单例的代理对象 setter 注入
  * 有可能解决

* DependsOn 依赖闭环
  * 不能解决

### spring 三级缓存

* singletonObjects 一级缓存，用于保存实例化、注入、初始化完成的bean实例

* earlySingletonObjects 二级缓存，用于保存实例化完成的bean实例(未进行属性注入)

* singletonFactories 三级缓存，用于保存bean创建工厂，以便于后面扩展有机会创建代理对象

> 基于spring的bean 初始化和 java 类引用的特点，可以有效的解决非强依赖的循环引用问题：
>
> * 三级缓存
> * 依赖注入 DI





### 单例的 setter 注入

```java
@Service
public class TestService1 {

    @Autowired
    private TestService2 testService2;

    public void test1() {
    }
}

@Service
public class TestService2 {

    @Autowired
    private TestService1 testService1;

    public void test2() {
    }
}
```

* 依赖注入过程

  ![](https://raw.githubusercontent.com/jinminer/docs/master/spring-framework/spring-on-trip/spring-circular-reference/1.4-spring-bean-di-process-4.jpg)

* spring在初始化bean过程中，通过DI和缓存机制，间接性的解决循环依赖问题

  * DI 

    * spring 在初始化bean时，通过递归调用 `getBean()`方法， 对于bean中的依赖属性(被@Autowired注解修饰)进行逐层初始化

  * 缓存

    * 缓存bean的实例，在bean的初始化过程，多级缓存存储着不同实例化程度的bean实例，相当于对bean进行了按类型标记；
    * 在进行DI时，bean循环引用属性，即使未完成各自的属性初始化的类，也可以被其引用者注入，虽然注入的只是个半成品，但是通过 `getBean()`方法的循环调用，最终得到的还是完全体的成品bean

    

### 二级缓存

单论循环依赖问题，其实用一级缓存做中继就可以解决，但是某些场景仅用一级缓存会出现问题：

```java
@Service
public class TestService1 {

    @Autowired
    private TestService2 testService2;
    @Autowired
    private TestService3 testService3;

    public void test1() {
    }
}

@Service
public class TestService2 {

    @Autowired
    private TestService1 testService1;

    public void test2() {
    }
}

@Service
public class TestService3 {

    @Autowired
    private TestService1 testService1;

    public void test3() {
    }
}
```



* 无二级缓存初始化流程

  ![](https://raw.githubusercontent.com/jinminer/docs/master/spring-framework/spring-on-trip/spring-circular-reference/1.5-spring-bean-cache-1.png)

  * 在 `testService3` 完成初始化注入依赖属性时，会创建 `testService1` 的 `objectFactory` 实例对象，并存储到三级缓存中，在这个过程中 `testService1` 的初始化过程是独立、互不影响的，所以 `testService2` 在注入 `testService1` 时，可能也会创建另一个  `testService1` 的 `objectFactory` 实例对象，这样会出现同一个 bean 多个实例的现象
  * bean通过反射实例化是在从三级缓存获取实例时进行的

* 有二级缓存初始化流程

  ![](https://raw.githubusercontent.com/jinminer/docs/master/spring-framework/spring-on-trip/spring-circular-reference/1.5-spring-bean-cache-2.png)

  * 增加了二级缓存之后，当从三级缓存获取bean的实例之后，会将其保存到二级缓存，这样当其他 bean需要注入已经有实例化对象的 bean时，先去从二级缓存获取，如果存在则不在去走从三级缓存获取的步骤(反射实例化bean，并且放入三级缓存)



### 三级缓存

三级缓存中存放的bean实例为 `ObjectFactory` 对象，这是为了对bean进行增强处理，比如AOP代理等等



### 多例的 setter 注入

```java
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Service
public class TestService1 {

    @Autowired
    private TestService2 testService2;

    public void test1() {
    }
}

@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Service
public class TestService2 {

    @Autowired
    private TestService1 testService1;

    public void test2() {
    }
}
```

这种循环依赖问题是无法解决的，因为它没有用缓存，每次都会生成一个新对象。



### 构造器注入

```java
@Service
public class TestService1 {

    public TestService1(TestService2 testService2) {
    }
}

@Service
public class TestService2 {

    public TestService2(TestService1 testService1) {
    }
}
```



![](https://raw.githubusercontent.com/jinminer/docs/master/spring-framework/spring-on-trip/spring-circular-reference/1.6-bean-construct-setter-1.png)





构造器注入，没有使用缓存，无法解决循环依赖问题



### 单例的代理对象setter注入

```java
@Service
public class TestService1 {

    @Autowired
    private TestService2 testService2;

    @Async
    public void test1() {
    }
}

@Service
public class TestService2 {

    @Autowired
    private TestService1 testService1;

    public void test2() {
    }
}
```

使用`@Async`注解的场景，通过`AOP`自动生成代理对象。

![](https://raw.githubusercontent.com/jinminer/docs/master/spring-framework/spring-on-trip/spring-circular-reference/1.7-bean-singleton-proxy-inject-1.png)



bean初始化完成之后，后面还有一步去检查：第二级缓存 和 原始对象是否相等，发现第二级缓存 和 原始对象不相等，所以抛出了循环依赖的异常。

* 修改代码：把TestService1改个名字，改成：TestService6，可以完成注入

```
@Service
public class TestService6 {

    @Autowired
    private TestService2 testService2;

    @Async
    public void test1() {
    }
}
```

![](https://raw.githubusercontent.com/jinminer/docs/master/spring-framework/spring-on-trip/spring-circular-reference/1.7-bean-singleton-proxy-inject-2.png)



* spring的bean加载顺序:，默认情况下，spring是按照文件完整路径递归查找的，按路径+文件名排序，排在前面的先加载。所以TestService1比TestService2先加载，而改了文件名称之后，TestService2比TestService6先加载。
* testService6中其实第二级缓存是空的，不需要跟原始对象判断，所以不会抛出循环依赖。
* bean是在从三级缓存中存在时，才会放到二级缓存



### DependsOn循环依赖

```java
@DependsOn(value = "testService2")
@Service
public class TestService1 {

    @Autowired
    private TestService2 testService2;

    public void test1() {
    }
}

@DependsOn(value = "testService1")
@Service
public class TestService2 {

    @Autowired
    private TestService1 testService1;

    public void test2() {
    }
}
```

根据先后顺序实例化bean，这是spring会自动检查DependsOn注解中的bean有无循环依赖，如果有，则注入失败



























