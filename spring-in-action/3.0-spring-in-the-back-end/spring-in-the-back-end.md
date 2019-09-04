# spring in the back end

> 如何处理和持久化数据。

## 10 Hitting the database with Spring and JDBC



> 如何使用Spring对JDBC的抽象实现关系型数据库中的数据持久化。



### 10.1 Learning Spring's data-access philosophy



#### 10.1.1 Getting to know Spring's data-access exception hierarchy





#### 10.1.2 Templating data access





### 10.2 Configuring a data source



#### 10.2.1 Using JNDI data sources





#### 10.2.2 Using a pooled data source





#### 10.2.3 Using JDBC driver-based data sources





#### 10.2.4 Using an embedded data source





### 10.3 Using JDBC with Spring





#### 10.3.1 Tacking runaway JDBC code



#### 10.3.2 Working with JDBC templates





### 10.4 Summary



## 11 Persisting data with object-relational mapping



> 从另外一个角度介绍数据持久化，也就是使用Java持久化API（JPA）存储关系型数据库中的数据。



### 11.1 Integrating Hibernate with Spring





#### 11.1.1 Declaring a Hibernate session factory



#### 11.1.2 Building Spring-free Hibernate



### 11.2 Spring and the Java Persistence API



#### 11.2.1 Configuring an entity manager factory



#### 11.2.2 Writing a JPA-based repository





### 11.3 Automatic JPA repositories with Spring Data



#### 11.3.1 Defining query methods





#### 11.3.2 Declaring custom queries





#### 11.3.3 Mixing in custom functionality





### 11.4 Summary





## 12 Working with NoSQL databases



> 如何将Spring与非关系型数据库结合使用，如MongoDB和Neo4j。



### 12.1 Persisting documents with MongoDB



#### 12.1.1 Enabling MongoDB



#### 12.1.2 Annotating model types for MongoDB persistence





#### 12.1.3 Accessing MongoDB with MongoTemplate



#### 12.1.4 Writing a MongoDB repository





### 12.2 Working with graph data in Neo4j





#### 12.2.1 Configuring Spring Data Neo4j



#### 12.2.2 Annotating graph entities





#### 12.2.3 Working with Neo4jTemplate





#### 12.2.4 Creating automatic Neo4j repositories



### 12.3 Working with key-value data in Redis



#### 12.3.1 Connecting to Redis



#### 12.3.2 Working with RedisTemplate





#### 12.3.3 Setting key and value serializers



### 12.4 Summary





## 13 Caching data



> 不管数据存储在什么地方，缓存都有助于性能的提升，这是通过只有在必要的时候才去查询数据库实现的。介绍Spring对声明式缓存的支持。



### 13.1 Enabling cache support



#### 13.1.1 Configuring a cache manager



### 13.2 Annotating methods for caching 



#### 13.2.1 Populating the cache



#### 13.2.2 Removing cache entries





### 13.3 Declaring caching in XML





### 13.4 Summary



## 14 Securing methods



> Spring Security，如何通过AOP将安全性应用到方法级别。



### 14.1 Securing methods with annotations





#### 14.1.1 Restricting method access with @Secured





#### 14.1.2 Using JSR-250'S @RolesAllowed with Spring Security





### 14.2 Using expressions for method-level security





#### 14.2.1 Expressing method access rules





#### 14.2.2 Filtering method inputs and outputs





### 14.3 summary 













































