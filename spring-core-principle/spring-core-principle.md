# `spring` 核心原理

## 目录

### <a name="preparatory-knowing-head" href="#preparatory-knowing">`spring` 初识</a>



## <a name="preparatory-knowing" href="#preparatory-knowing-head">`spring` 初识</a>

### 简化开发

#### `spirng` 简化开发的基本策略

* 基于 `POJO` 的轻量级和最小侵入性编程
  * 尽可能减少依赖
* 通过依赖注入和面向接口松散耦合
* 基于切面和惯性进行声明式编程
* 通过切面和模板减少样板式代码

#### `AOP`、`IOC`、`DI` 之间的关系

![relation-aop-ioc-di](https://raw.githubusercontent.com/jinminer/docs/master/spring-framework/spring-preparatory-knowing/relation-aop-ioc-di.png)



### `spirng5.0` 模块结构

![spring-framework-5-runtime](https://raw.githubusercontent.com/jinminer/docs/master/spring-framework/spring-preparatory-knowing/spring-framework-5-runtime.png)



#### `Spring` 之核心模块

| 模块名称                 | 主要功能                                               |
| ------------------------ | ------------------------------------------------------ |
| `spring-core`            | 依赖注入 `IOC` 与 `DI` 的最基本实现                    |
| `spring-beans`           | `Bean` 工厂与 `Bean` 的装配                            |
| `spirng-context`         | 定义基础的 `Spring` 的 `Context` 上下文，即 `IOC` 容器 |
| `spring-context-support` | 对 `Spring IOC` 容器的扩展支持，以及 `IOC` 子容器      |
| `spring-context-indexer` | `Spring` 的类管理组件和 `Classpath` 扫描               |
| `spring-expression`      | `Spring` 表达式语言                                    |



#### `Spring` 之切面编程

| 模块名称            | 主要功能                                                |
| ------------------- | ------------------------------------------------------- |
| `spring-aop`        | 面向切面编程的应用模块，整合 `Asm`、`CGlib`、`JDKProxy` |
| `spring-aspects`    | 集成 `AspectJ` ，`AOP` 应用框架                         |
| `spring-instrument` | 动态 `Class Loading` 模块                               |



#### `Spring` 之数据访问与集成

| 模块名称      | 主要功能                                                     |
| ------------- | ------------------------------------------------------------ |
| `spring-jdbc` | `Spring` 提供的 `JDBC` 框架的主要实现模块，用于简化 `Spring JDBC` 操作 |
| `spring-tx`   | `Spring JDBC` 事物控制实现模块                               |
| `spring-orm`  | 主要集成 `Hibernate` 、`Java Persistence API(JPA)` 和 `Java Data Objects(JDO)` |
| `spirng-oxm`  | 将 `Java` 对象映射成 `XML` 数据，或者将 `XML` 数据映射成 `Java` 对象 |
| `spring-jms`  | `Java Messaging Service` 能够发送和接收信息                  |



#### `Spring` 之 `Web` 组件



| 模块名称           | 主要功能                                                     |
| ------------------ | ------------------------------------------------------------ |
| `spring-web`       | 提供了最基础的 `Web` 支持，主要建立于核心容器之上，通过 `Servlet` 或者 `Listeners` 来初始化 `IOC` 容器 |
| `spring-webmvc`    | 实现了 `Spring MVC(model-view-controller)` 的 `Web` 应用     |
| `spring-websocket` | 主要是与 `Web` 前端的全双工通讯的协议                        |
| `spring-webflux`   | 一个新的非阻塞函数式 `Reactive Web` 框架，可以用来建立异步、非阻塞的事件驱动服务 |



#### `Spring` 之通信报文

| 模块名称           | 主要功能                                                     |
| ------------------ | ------------------------------------------------------------ |
| `spring-messaging` | 从 `Spring4` 开始新加入的一个模块，主要职责是为 `Spring` 框架集成一些基础的报文传送应用 |



#### `Spring` 之集成测试

| 模块          | 主要功能           |
| ------------- | ------------------ |
| `spring-test` | 主要为测试提供支持 |



#### `Spring` 之集成兼容

| 模块名称               | 主要功能                                                     |
| ---------------------- | ------------------------------------------------------------ |
| `spring-framework-bom` | `Bill of Materials` 解决 `Spring` 的不同模块依赖版本不同问题 |



#### `Spring` 各模块之间的依赖关系



![spring-components-relation](https://raw.githubusercontent.com/jinminer/docs/master/spring-framework/spring-preparatory-knowing/spring-components-relation.png)



### `Spring` 版本命名规则



#### 常见的软件版本命名规则

| 软件           | 升级过程                                                   | 说明                                                      |
| -------------- | ---------------------------------------------------------- | --------------------------------------------------------- |
| `Linux Kernel` | `0.0.1`、`1.0.0`、`2.6.32`、`3.0.18`...                    | 若用 `X.Y.Z` 表示，则偶数Y表示稳定版本，奇数Y表示开发版本 |
| `Windows`      | `Windows 98`、`Windows 2000`、`Windows xp`、`Windows 7`... | 最大的特点是杂乱无章，毫无规律                            |
| `SSH Client`   | `0.9.8`                                                    |                                                           |
| `OpenStack`    | `2014.1.3` 、`2015.1.1.dev8`                               |                                                           |



#### 语义化版本命名通行规则

| 序列 | 格式要求  | 说明                                                         |
| ---- | --------- | ------------------------------------------------------------ |
| `X`  | 非负整数  | 表示主版本号 `(Major)`，当 `API` 的兼容性变化时， `X` 需要递增 |
| `Y`  | 非负整数  | 表示次版本号`(Minor)`，当增加功能时(不影响 `API` 的兼容性)，`Y` 需要递增 |
| `Z`  | f非负整数 | b表示修订号`(Patch)`，当做 `Bug` 修复时(不影响 `API` 的兼容性)，`Z` 需要递增 |



#### 商业软件中常见的修饰词

| 描述方式       | 说明   | 含义                                                         |
| -------------- | ------ | ------------------------------------------------------------ |
| `Snapshot`     | 快照版 | 尚不稳定，处于开发中版本                                     |
| `Alpha`        | 内部版 | 严重缺陷基本完成修正并通过复测，但需要完整的功能测试         |
| `Beta`         | 测试版 | 相对 `Alpha` 有很大的改进，消除了严重的错误，但还是存在一些缺陷 |
| `RC`           | 终测版 | `Release Candidate` (最终测试)，即将最为正式版发布           |
| `Demo`         | 演示版 | 只集成了正式版部分功能升级，无法升级                         |
| `SP`           | `SP1`  | 是 `service pack` 的意思表示升级包，`Windows` 系统升级时较为常见 |
| `Release`      | 稳定版 | 功能相对稳定，可以对外发行，但有时间限制                     |
| `Trial`        | 试用版 | 试用版，仅对部分用户发行                                     |
| `Full Version` | 完整版 | 即正式版，已发布                                             |
| `Unregistered` | 未注册 | 有功能或时间限制的版本                                       |
| `Standard`     | 标准版 | 能满足正常使用功能的版本                                     |
| `Lite`         | 精简版 | 只含有正式版的核心功能                                       |
| `Enhance`      | 增强版 | 正式版，功能优化的版本                                       |
| `Ultimate`     | 旗舰版 | 在标配版本基础上进行升级，体验感更好的版本                   |
| `Professional` | 专业版 | 针对公告要求功能，专业性更强的使用群体发行的版本             |
| `Free`         | 自由版 | 自由免费使用的版本                                           |
| `Upgrade`      | 升级版 | 有功能增强或修复已知 `bug`                                   |
| `Retail`       | 零售版 | 单独发售                                                     |
| `Cardware`     | 共享版 | 公用许可证(`IOS`许可证)                                      |
| `LTS`          | 维护版 | 该版本需要长期维护                                           |



#### `Spring` 版本命名规则

| 描述方式   | 说明     | 含义                                                         |
| ---------- | -------- | ------------------------------------------------------------ |
| `Snapshot` | 快照版   | 尚不稳定、仍处于开发中的版本                                 |
| `Release`  | 稳定版   | 功能相对稳定，可以对外发行，但有时间限制                     |
| `GA`       | 正式版   | 代表广发可用的稳定版(`General Availability`)                 |
| `M`        | 里程碑版 | (`M` 是 `Milestone`的意思)具有一些全新的功能或是具有里程碑意义的版本 |
| `RC`       | 终测版   | `Release Candidate` (最终测试)，即将作为正式版发布           |



### `Spring` 源码构建

* `Gradle`构建过程中的坑

  * 如果项目环境一直无法构建，项目文件夹没有变粗体字，类图无法自动生成。那么你一定是踩到了这样一个坑：

  * 第一步：首先打开`View->Tool Windows -> Gradle` 

    ![source-build-exception-with-gradle-1](https://raw.githubusercontent.com/jinminer/docs/master/spring-framework/spring-preparatory-knowing/source-build-exception-with-gradle-1.png)

  * 第二步：点击右侧`Gradle`视图中的`Refresh`，会出现如下的错误：

    ![source-build-exception-with-gradle-2](https://raw.githubusercontent.com/jinminer/docs/master/spring-framework/spring-preparatory-knowing/source-build-exception-with-gradle-2.png)

    ![source-build-exception-with-gradle-3](https://raw.githubusercontent.com/jinminer/docs/master/spring-framework/spring-preparatory-knowing/source-build-exception-with-gradle-3.png)	

  * 第三步：看错误，显然跟`Gradle`没有任何关系，是因为 `IDEA` 编辑器的 `jre` 环境和自己本地的安装的 `jre` 冲突， 解决办法：

    1. 关闭`IDEA`，打开任务管理器，结束跟`java`有关的所有进程。

    2. 找到`JAVA_HOME -> jre -> lib`目录，将 `tools.jar` 重命名 `tools.jar.bak`。

    3. 重启`IDEA`，再次点击`refresh`，等待构建完成。

















