动态数据源
#### 大概步骤
1、建立两个数据库ds1,ds2
2、继承AbstractRoutingDataSource类,实现determineCurrentLookupKey方法,自定义路由规则key(ThreadLocal) 
3、配置对应的DynamicDataSourceConfig
4、AOP+注解 切面拦截

#### FAQ:
##### 问题1: 循环依赖
```text
Description:

The dependencies of some of the beans in the application context form a cycle:

   userController (field private com.wzj.dynamic.mutil.datasource.service.UserService com.wzj.dynamic.mutil.datasource.controller.UserController.userService)
      ↓
   userServiceImpl (field private com.wzj.dynamic.mutil.datasource.mapper.UserMapper com.wzj.dynamic.mutil.datasource.service.impl.UserServiceImpl.userMapper)
      ↓
   userMapper defined in file [F:\MyProject\myOpenSource\spring-boot-orm-demo\dynamic-mutil-datasource-demo\target\classes\com\wzj\dynamic\mutil\datasource\mapper\UserMapper.class]
      ↓
   sqlSessionFactory defined in class path resource [tk/mybatis/mapper/autoconfigure/MapperAutoConfiguration.class]
┌─────┐
|  dynamicDataSource defined in class path resource [com/wzj/dynamic/mutil/datasource/compoent/DynamicDataSourceConfig.class]
↑     ↓
|  ds1 defined in class path resource [com/wzj/dynamic/mutil/datasource/compoent/DynamicDataSourceConfig.class]
↑     ↓
|  org.springframework.boot.autoconfigure.jdbc.DataSourceInitializerInvoker
```
解决: 加上@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class })

##### 问题2: 配置的数据源(url参数)有问题
```text
There was an unexpected error (type=Internal Server Error, status=500).
nested exception is org.apache.ibatis.exceptions.PersistenceException: ### Error querying database. Cause: java.lang.IllegalArgumentException: jdbcUrl is required with driverClassName. ### The error may exist in com/wzj/dynamic/mutil/datasource/mapper/UserMapper.java (best guess) ### The error may involve com.wzj.dynamic.mutil.datasource.mapper.UserMapper.selectByPrimaryKey ### The error occurred while executing a query ### Cause: java.lang.IllegalArgumentException: jdbcUrl is required with driverClassName.
```
解决:
```properties
# 数据源1, driver-class-name -> driverClassName, url -> jdbc-url
spring.datasource.ds1.driverClassName=com.mysql.cj.jdbc.Driver
spring.datasource.ds1.jdbc-url=jdbc:mysql://127.0.0.1:3306/ds1?serverTimezone=UTC&useUnicode=true&charaterEncoding=utf-8&useSSL=false

# 数据源2, driver-class-name -> driverClassName, url -> jdbc-url
spring.datasource.ds2.driverClassName=com.mysql.cj.jdbc.Driver
spring.datasource.ds2.jdbc-url=jdbc:mysql://127.0.0.1:3306/ds2?serverTimezone=UTC&useUnicode=true&charaterEncoding=utf-8&useSSL=false
```
注: 约定大于配置, 应该是自动装配进去的, TODO查看源码