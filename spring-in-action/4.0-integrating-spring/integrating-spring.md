# Integrating Spring



> 如何将Spring应用程序与其他系统进行集成。



## 15 Working with remote services



> 如何创建与使用远程服务，包括RMI、Hessian、Burlap以及基于SOAP的服务。



### 15.1 An overview of Spring remoting





### 15.2 Working with RMI



#### 15.2.1 Exporting an RMI service





#### 15.2.2 Wiring an RMI service





### 15.3 Exposing remote services with Hessian and Burlap





#### 15.3.1 Exposing bean functionality with Hessian/Burlap



#### 15.3.2 Accessing Hessian/Burlap services









### 15.4 Using Spring's HttpInvoker



#### 15.4.1 Exposing beans as HTTP services



#### 15.4.2 Accessing services via HTTP



### 15.5 Publishing and consuming web services



#### 15.5.1 Creating Spring-enabled JAX-WS endpoints



#### 15.5.2 Proxying JAX-WS services on the client side



### 15.6 Summary





## 16 Creating REST APIs with Spring MVC



> Spring MVC：如何创建RESTful服务，在这个过程中所使用的编程模型与之前在第5章中所描述的是一致的。



### 16.1 Getting REST





#### 16.1.1 The fundamentals of REST





#### 16.1.2 How Spring supports REST



### 16.2 Creating your first REST endpoint



#### 16.2.1 Negotiating resource representation





#### 16.2.2 Working with HTTP message converters





### 16.3 Serving more than resources



#### 16.3.1 Communicating errors to the client





#### 16.3.2 Setting headers in the response





### 16.4 Consuming REST resources



#### 16.4.1 Exploring RestTemplate's operations



#### 16.4.2 GETting resources



#### 16.4.3 Retrieving resources



#### 16.4.4 Extracting response metadata



#### 16.4.5 PUTting resources



#### 16.4.6 DELETEting resources



#### 16.4.7 POSTting resources data



#### 16.4.8 Receiving object responses from POST requests



#### 16.4.9 Receiving a resource location after a POST request



#### 16.4.10 Exchanging resources



### 16.5 Summary

## 17 Messaging in Spring



> 探讨Spring对异步消息的支持，本章将会包括Java消息服务（Java Message Service，JMS）以及高级消息队列协议
> （Advanced Message Queuing Protocol，AMQP）。



### 17.1 A brief introduction to asynchronous messaging



#### 17.1.1 Sending messages



#### 17.1.2 Assessing the benefits of asynchronous messaging







### 17.2 Sending messages with JMS





#### 17.2.1 Setting up a message broker in Spring



#### 17.2.2 Using Spring's JMS template





#### 17.2.3 Creating message-driven POJOs





#### 17.2.4 Using message-based RPC



### 17.3 Messaging with AMQP



#### 17.3.1 A brief introduction to AMQP



#### 17.3.2 Configuring Spring for AMQP messaging





#### 17.3.3 Setting messages with RabbitTemplate





#### 17.3.4 Receiving messages AMQP



### 17.4 Summary





## 18 Messaging with WebSocket and STOMP



> 异步消息有了新的花样，在这一章中读者会看到如何将Spring与WebSocket和STOMP结合起来，实现服务端与客户
> 端之间的异步通信。



### 18.1 Working with Spring's low-level WebSocket API







### 18.2 Coping with a lack of WebSocket support





### 18.3 Working with STOMP messaging



#### 18.3.1 Enabling STOMP messaging



#### 18.3.2 Handling STOMP messaging from the client



#### 18.3.3 Sending messages to the client





### 18.4 Working with user-targeted messages





#### 18.4.1 Working with user messages in a controller



#### 18.4.2 Sending messages to a specific user



### 18.5 Handling message exceptions



### 18.6 Summary



## 19 Sending email with Spring



> 介绍如何使用Spring发送E-mail。



### 19.1 Configuring Spring to send email





#### 19.1.1 Configuring a mail sender





#### 19.1.2 Wiring and using the mail sender





### 19.2 Constructing rich email messages





#### 19.2.1 Adding attachments





#### 19.2.2 Sending email with rich content





### 19.3 Generating email with templates



#### 19.3.1 Constructing email messages with Velocity





#### 19.3.2 Using Thymeleaf to create email messages



### 19.4 summary 





## 20 Managing Spring beans with JMS



> 关注于Spring对Java管理扩展（Java ManagementExtensions，JMX）功能的支持，借助这项功能可以对Spring应用
> 程序进行监控和修改运行时配置。



### 20.1 Exporting Spring beans as MBeans



#### 20.1.1 Exposing methods by name



#### 20.1.2 Using interfaces to define MBean operations and attributes



#### 20.1.3 Working with annotation-driven MBeans



#### 20.1.4 Handling MBean collisions





### 20.2 Remoting MBeans



#### 20.2.1 Exposing remote MBeans



#### 20.2.2 Accessing remote MBeans



#### 20.2.3 Proxying MBeans



### 20.3 Handling notifications







#### 20.3.1 Listening for notifications





### 20.4 Summary





## 21 Simplifying Spring development with Spring Boot



> 一个全新并且会改变游戏规则的Spring使用方式，名为Spring Boot。我们将会看到Spring Boot如何将Spring应用中样板式的配置移除掉，这样就能让读者更加专注
> 于业务功能。



### 21.1 Introducing Spring Boot





#### 21.1.1 Adding starter dependencies





#### 21.1.2 Autoconfiguration



#### 21.1.3 The Spring Boot CLI



#### 21.1.4 The Actuator



### 21.2 Building an application with Spring Boot



#### 21.2.1 Handling requests



#### 21.2.2 Creating the view



#### 21.2.3 Adding static artifacts



#### 21.2.4 Persisting the data



#### 21.2.5 Try it out



#### 21.3 Going Groovy with the Spring Boot CLI



#### 21.3.1 Writing a Groovy controller



#### 21.3.2 Persisting with a Groovy repository



#### 21.3.3 Running the Spring Boot CLI



### 21.4 Gaining application insight with the actuator





### 21.5 Summary









