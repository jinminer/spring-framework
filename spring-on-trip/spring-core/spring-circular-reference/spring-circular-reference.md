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

## 场景

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

































