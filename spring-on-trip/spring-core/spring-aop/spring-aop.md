# Spring AOP

## AOP 组件流程

![](https://raw.githubusercontent.com/jinminer/docs/master/spring-framework/spring-on-trip/spring-aop/1.0-spring-aop-component-flow.png)

* `AdvisedSupport` 
  * 工具类：解析`AOP` 配置信息(如spring expression表达式)，并构建切面与切点之间的关系
* `AopConfig` 
  * 保存 `AOP` 的配置信息
* `Advice`
  * 通知，完成切面回调的封装
* `JdkDynamicAopProxy` - 针对接口实现类的代理
  * 使用 `JDK` 生成代理类的工具
  * 也可能是 `Cglib` 代理类生成工具 `CglibAopProxy` 等 - 针对无接口实现类的代理
  * spring根据bean的具体情况最终由`DefaultAopProxyFactory` 来生成代理类



