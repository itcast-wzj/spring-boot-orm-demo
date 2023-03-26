#### Mybatis-generator 
目的: 可以根据表自动生成entity、mapper、xml(会自动创建包名), 解放编写简单重复的代码,提高开发的效率
大概步骤:
步骤一: 生成entity、mapper、xml
步骤二: 进行测试

##### 步骤一: 配置文件
###### 创建数据库、表
```sql
CREATE TABLE `users` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '用户名',
  `password` varchar(50) NOT NULL DEFAULT '' COMMENT '密码',
  `sex` tinyint(4) NOT NULL DEFAULT '0' COMMENT '性别 0=女 1=男 ',
  `deleted` tinyint(4) unsigned NOT NULL DEFAULT '0' COMMENT '删除标志，默认0不删除，1删除',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='用户表';


```
###### pom.xml
```xml
<dependencies>
    <!--通用spring boot mapper-->
    <dependency>
        <groupId>tk.mybatis</groupId>
        <artifactId>mapper-spring-boot-starter</artifactId>
        <version>2.0.3</version>
    </dependency>

    <!--mysql驱动-->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>8.0.13</version>
        <scope>runtime</scope>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>

    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <version>1.18.8</version>
    </dependency>
</dependencies>

<build>
    <plugins>
        <plugin>
            <!--maven生成mybatis generator的插件-->
            <groupId>org.mybatis.generator</groupId>
            <artifactId>mybatis-generator-maven-plugin</artifactId>
            <version>1.3.6</version>
            <configuration>
                <!--读取generatorConfig.xml进行生成, ${basedir}代表模块名mybatis-generator-demo, 是一个内置变量-->
                <configurationFile>${basedir}/src/main/resources/generatorConfig.xml</configurationFile>
                <overwrite>true</overwrite>
                <verbose>true</verbose>
            </configuration>
            <dependencies>
                <!--插件所依赖的jar包-->
                <dependency>
                    <groupId>mysql</groupId>
                    <artifactId>mysql-connector-java</artifactId>
                    <version>8.0.13</version>
                    <scope>runtime</scope>
                </dependency>
                <dependency>
                    <groupId>tk.mybatis</groupId>
                    <artifactId>mapper</artifactId>
                    <version>${mapper.version}</version>
                </dependency>
            </dependencies>
        </plugin>
    </plugins>
</build>
```

###### generator.properties
```properties
# 生成的包名
package.name=com.wzj.mybatis.gernerator

# 数据库配置信息
jdbc.driverClass = com.mysql.cj.jdbc.Driver
jdbc.url = jdbc:mysql://127.0.0.1:3306/boot_user?serverTimezone=UTC&useUnicode=true&charaterEncoding=utf-8&useSSL=false
jdbc.user = root
jdbc.password =123456
```

###### generatorConfig.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>


<!DOCTYPE generatorConfiguration
        PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
        "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">

<generatorConfiguration>
    <!--引入外部自定义的数据库信息-->
    <properties resource="generator.properties"/>
    <!-- 配置对象环境
    context的targetRuntime属性设置为MyBatis3Simple是为了避免生成Example相关的代码和方法。如果需要则改为Mybatis3.
    defaultModelType="flat"目的是使每个表只生成一个实体类
     -->
    <context id="Mysql" targetRuntime="MyBatis3Simple" defaultModelType="flat">
        <!-- 配置起始与结束标识符, 数据库使用mysql,所以前后的分隔符都设为”`” -->
        <property name="beginningDelimiter" value="`"/>
        <property name="endingDelimiter" value="`"/>
        <!--添加自定义的继承接口, Mapper接口会默认继承Mapper-->
        <plugin type="tk.mybatis.mapper.generator.MapperPlugin">
            <property name="mappers" value="tk.mybatis.mapper.common.Mapper"/>
            <property name="caseSensitive" value="true"/>
        </plugin>
        <!--jdbc的数据库连接 -->
        <jdbcConnection driverClass="${jdbc.driverClass}"
                        connectionURL="${jdbc.url}"
                        userId="${jdbc.user}"
                        password="${jdbc.password}">
        </jdbcConnection>

        <!-- 配置生成的实体类位置
            targetPackage     指定生成的model生成所在的包名
            targetProject     指定在该项目下所在的路径
        -->
        <javaModelGenerator targetPackage="${package.name}.entity" targetProject="src/main/java"/>
        <!--xml 映射文件生成的位置 -->
        <sqlMapGenerator targetPackage="mapper" targetProject="src/main/resources"/>
        <!-- mapper接口生成的位置 -->
        <javaClientGenerator targetPackage="${package.name}.mapper" targetProject="src/main/java" type="XMLMAPPER"/>

        <!--
          设置数据库表名
          domainObjectName: 指定实体名
          mapperName: 指定Mapper接口名
          注: 如果mapperName不指定值, 那么生成的Mapper接口名就是domainObjectName的值拼接上Mapper
          注: 生成全部表tableName设为%
        -->
        <table tableName="users" domainObjectName="User" mapperName="UserMapper">
            <generatedKey column="id" sqlStatement="JDBC"/>
        </table>
    </context>
</generatorConfiguration>
```

###### 运行maven插件
然后会发现UserMapper接口继承了tk中的Mapper接口,这是因为在generatorConfig.xml配置了插件MapperPlugin
注: 有些项目可能还需要swagger注解啥的, 也可以进行扩展

###### 测试
##### 集成tk
1、启动类上需要扫描Mapper接口
```java
@MapperScan("com.wzj.mybatis.gernerator.mapper")
@SpringBootApplication
public class MybatisGeneratorApplication {
    public static void main(String[] args) {
        SpringApplication.run(MybatisGeneratorApplication.class, args);
    }
}
```
2、application.properties配置扫描mapper.xml文件
```properties
spring.application.name=mybatis-generator-demo
server.port=8080

# 数据库连接信息
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/boot_user?serverTimezone=UTC&useUnicode=true&charaterEncoding=utf-8&useSSL=false
spring.datasource.username=root
spring.datasource.password=123456

#指定mapper.xml的位置
mybatis.mapper-locations=classpath*:mapper/*.xml
```
##### SpringBootTest测试
测试插入是否成功
```java
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MybatisGeneratorApplication.class)
public class MybatisGeneratorApplicationTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testInsert(){
        User user = new User();
        user.setUsername("wzj");
        user.setPassword("wzj");
        user.setSex((byte)0);
        user.setDeleted((byte)0);
        user.setUpdateTime(new Date());
        user.setCreateTime(new Date());
        userMapper.insert(user);
    }
}
```

参考:
https://www.macrozheng.com/mall/architect/mall_arch_01.html#%E6%B7%BB%E5%8A%A0%E9%A1%B9%E7%9B%AE%E4%BE%9D%E8%B5%96

