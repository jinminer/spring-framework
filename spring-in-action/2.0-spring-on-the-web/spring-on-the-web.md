# Spring on the web

> 使用Spring来构建Web应用程序

## 5 Building Spring web application

> Spring MVC的基础知识，即Spring中的基础Web框架。如何编写控制器来处理请求，并使用模型数
> 据产生响应。



### 5.1 Getting started with Spring MVC



#### 5.1.1 Following the life of a request



#### 5.1.2 Setting up Spring MVC



#### 5.1.3 Introducing the Spittr application 





### 5.2 Writing a simple controller



#### 5.2.1 Testing the controller





#### 5.2.2 Defining class-level request handling



#### 5.2.3 Passing model data to the view









### 5.3 Accepting request input 



#### 5.3.1 Taking query parameters



#### 5.3.2 Taking input via path parameters





### 5.4 Processing forms



 

#### 5.4.1 Writing a form-handling controller



#### 5.4.2 Validating forms





### 5.5 Summary



## 6 Rendering web views



> 当控制器的工作完成后，模型数据必须要使用一个视图来进行渲染。在Spring中可以使用的各种视图技术，包括
> JSP、Apache Tiles以及Thymeleaf。



### 6.1 Understanding view resolution



### 6.2 Creating JSP views



#### 6.2.1 Configuring a JSP-ready view resolver



#### 6.2.2 Using Spring's JSP libraries





### 6.3 Defining a layout with Apache Tiles views





#### 6.3.1 Configuring a Tiles view resolver



### 6.4 Working with Thymeleaf





#### 6.4.1 Configuring a Thymeleaf view resolver





#### 6.4.2 Defining Thymeleaf templates





### 6.4 Working with Thymeleaf



#### 6.4.1 Configuring a Thymeleaf view resolver



#### 6.4.2 Defining Thymeleaf templates



### 6.5 Summary



## 7 Advanced Spring MVC

> 如何自定义Spring MVC配置、处理multipart类型的文件上传、处理在控制器中可能会出现的异常并且会通过flash属性
> 在请求之间传递数据。

### 7.1 Alternate Spring MVC configuration



#### 7.1.1 Customizing DispatcherServlet configuration



#### 7.1.2 Adding additional servlets add filters



#### 7.1.3 Declaring DispatcherServlet in web.xml







### 7.2 Processing multipart form data





#### 7.2.1 Configuring a multipart resolver





#### 7.2.2 Handing multipart requests





### 7.3 Handling exceptions



#### 7.3.1 Mapping exceptions to HTTP status codes



#### 7.3.2 Writing exception-handling methods







### 7.4 Advising controllers



### 7.5 Carrying data across redirect requests





#### 7.5.1 Redirecting with URL templates





#### 7.5.2 Working with flash attributes



### 7.6 Summary



## 8 Working with Spring Web Flow



> 介绍Spring Web Flow，这是Spring MVC的一个扩展，能够开发会话式的Web应用程序。
>
> 如何构建引导用户完成特定流程的Web应用程序。



### 8.1 Configuring Web Flow in Spring 



#### 8.1.1 Wiring a flow executor



#### 8.1.2 Configuring a flow registry



#### 8.1.3 Handling flow requests



### 8.2 The components of a flow



#### 8.2.1 States



#### 8.2.2 Transitions



#### 8.2.3 Flow data



### 8.3 Putting all together: the pizza flow



#### 8.3.1 Defining the base flow



#### 8.3.2 Collecting customer information



#### 8.3.3 Building an order 



#### 8.3.4 Taking payment





### 8.4 Securing web flows



### 8.5 Summary



## 9 Securing web applications



> 使用Spring Security为自己的应用程序Web层实现安全性。



### 9.1 Getting started with Spring Security



#### 9.1.1 Understanding Spring Security modules



#### 9.1.2 Filtering web requests



#### 9.1.3 Writing a simple security configuration



### 9.2 Selecting user details services



#### 9.2.1 Working with an in-memory user store





#### 9.2.2 Authenticating against database tables





#### 9.2.3 Applying LDAP-backed authentication





#### 9.2.4 Configuring a custom user service





### 9.3 Intercepting requests



#### 9.3.1 Securing with Spring Expressions



#### 9.3.2 Enforcing channel security





#### 9.3.3 Preventing cross-site request forgery





### 9.4 Authenticating users



#### 9.4.1 Adding a custom login page



#### 9.4.2 Enabling HTTP Basic authentication



#### 9.4.3 Enabling remember-me functionality



#### 9.4.4 Logging out



### 9.5 Securing the view



#### 9.5.1 Using Spring Security's JSP tag library



#### 9.5.2 Working with Thymeleaf's Spring Security dialect



### 9.6 Summary



















































