# spring

## 周四作业
- *使 Java 里的动态代理，实现一个简单的 AOP。*
  `./soda.week5.thurs.proxy`
- *写代码实现 Spring Bean 的装配，方式越多越好（XML、Annotation 都可以）, 提交到 Github。*
  `./soda.week5.thurs.spring`
  
## 周六作业
- *总结一下，单例的各种写法，比较它们的优劣*
> 
    - 懒汉(延迟加载)，doubleCheck+volatile保证线程安全，缺点是启动时加锁了
    - 饿汉(提前加载)，通过静态属性预先加载，保证线程安全，确定是不够灵活
    - 内部静态类，线程安全，无缺点，**推荐**
    - 枚举，线程安全，无缺点，只是不太常见
> 
    具体代码见：./soda.week5.sat.singleton
    
- *maven/spring 的 profile 机制，都有什么用法？*
> maven的pom.xml中的 `profiles&profile` 标签组，可以帮助实现不同部署环境的不同参数方便切换
> 如下例子，默认走dev，业务代码中读取${key1}得到aaa, 如果生产环境，添加不同的maven启动参数，即可切换至prod环境，${key1}得到bbb
```xml
<project>
 .......
 <profiles>
   	<!-- dev(default) Setting -->
   	<profile>
   		<id>dev</id>
   		<activation>
   			<activeByDefault>true</activeByDefault>
   		</activation>
        <properties><key1>aaa</key1></properties>
    </profile>

    <!-- dev(default) Setting -->
    <profile>
        <id>dev</id>
       	<activation>
    		<activeByDefault>false</activeByDefault>
       	</activation>
        <properties><key1>bbb</key1></properties>
    </profile>
</profiles>
</project>
```
> 在maven的命令行下进行profile的切换如下：
> `mvn clean package -P prod`
> -P profile_name: 指定profile的名称

- *总结 Hibernate 与 MyBatis 的各方面异同点。*
 - Hibernate 可以完全映射， 不需要写sql，与db方言耦合轻，但sql不可控，对DBA不友好
 - MyBatis 只能部分映射， 使用相对繁琐，还需写部分sql，但sql可控，DBA友好
 
- *研究一下 JDBC 接口和数据库连接池，掌握它们的设计和用法*
> 具体代码见：./soda.week5.sat.jdbc
