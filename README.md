# microservices 


copy configuration可以新运行一个service

测试是否能跑通
测试从网关进去 到orderservice 到userservice
能不能访问 并看sentinel有没有记录

访问nacos ip:端口/nacos
访问sentinel 直接ip:端口 localhost:8080

sentinel页面一片空白可能原因
还未有服务经过
配置文件中连接sentinel端口错误

建议都使用dev-server方式测试
后续也只会维护dev-server的配置文件

## dev环境运行 (本地运行环境)
先启动nacos和sentinel mysql服务

mysql运行resources下的两个sql文件

nacos单机启动 
到nacos目录下的bin文件夹中执行
startup.cmd -m standalone
默认账号密码nacos 默认端口8848

sentinel启动 
执行jar包
Java -jar sentinel-dashboard-1.8.1.jar 
可以配置账号密码
默认账号密码sentinel 默认端口8080

测试地址：http://localhost:10010/order/101?authorization=admin
authorization为网关配置的权限地址


## dev-server (本地运行代码 中间件nacos sentinel和mysql在服务器运行)
带着docker文件中的dev-server中所有文件(附带mysql文件)进行docker-compose运行
docker-compose up -d
停止 docker-compose down

docker拉取sentinel
docker pull bladex/sentinel-dashboard
docker run --name sentinel -p 8858:8858 -td bladex/sentinel-dashboard
不拉取直接运行compose文件也可以 会再拉取

注意：mysql修改root密码在compose文件中修改还是不够的 怎么修改还没去学

测试地址
http://localhost:10010/order/101?authorization=admin
## test(全部代码部署到docker中)




## 项目运行
### 直接运行
正常运行后springboot日志第一行就会输出运行环境
多环境切换后如果有bug 运行环境没有指定 可以maven reload

### maven打包
maven使用lifecycle的各种指令
package打包成jar包
deploy部署jar包到maven仓库
可以在pom文件中指定部署位置 注意如果有父工程 直接在父工程pom文件中写后打包父工程 不然可能报错
自己写的jar包需要在仓库中 其他程序在pom中引用才能一起打成jar包
### 多环境切换常见bug
解决这些问题意义并不大 请不要浪费太多时间在这上面

#### 来自IDEA 多环境切换后直接运行报错
Failed to load property source from 'file:/D:/workplace/microservices/user-service/target/classes/application.yml' (classpath:/application.yml)
1切换后点击reload旁边的regenerate sources可以解决
2给env加'
#### 来自nacos配置
pom文件中引入nacos的配置管理
<!--nacos的配置管理依赖-->
<dependency>
<groupId>com.alibaba.cloud</groupId>
<artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
</dependency>
这个引用是为了做线上多环境配置管理
需要配合bootstrap文件使用
之前使用的时候会自动连本地缓存中的配置文件 有bug很难使用
即启动时连接LOCAL_SNAPSHOT_PATH:C:\Users\user\nacos\config
导致自己写的配置文件无法生效 找bug太麻烦了而且学习价值很低 浪费时间
所以删除掉这个依赖

未来有需要做多环境配置时可以再学习

## todo
todo dev测试完成 测试dev-server运行情况
dev-server在docker中运行正常 需要修改父pom文件多环境运行
https://juejin.cn/post/7054348294083854343
之后重新测试 从dev开始测试

# sentinel
sentinel默认只有controller是需要监控的资源
可以对其他方法加@SentinelResource("goods")来加入资源监控中 
同时修改配置文件
web-context-unify: false # 关闭context整合 默认每个controller为一个触点 关闭后可以将service等也作为一个触点

## 限流
希望对谁进行限流访问就对什么进行配置资源

三种限流模式
直接  
直接对服务访问进行限制 超过后block掉
关联  
两个服务关联 一个服务优先级高 一个服务优先级低 
两个服务竞争同一个资源 在服务优先级高的服务达到阈值后
对低优先级服务进行限流 确保高优先级服务运行
如查询和更新 都需要数据库锁 优先确保更新
链路
a b触点访问同一个触点c 可以对a触点路径访问c进行限制 b触点路径访问c不进行限制

可以设置热点参数 通过参数来进行不同参数请求的隔离限流

限流可以使用每秒并发量即 QPS 和线程数实现隔离
### QPS 流控模式
qps query per second
快速失败      达到阈值后就抛异常限制访问
warm-up预热   设置阈值 时间 一开始阈值为 阈值/factor 后续到达时间后逐步到达最大阈值
超过阈值的访问同样和快速失败一样限制访问
排队等待    设置qps和timeout 在排队时间超出timeout的情况下 新进来的请求会限制并抛异常
起到流量平整的作用 即使实时qps大于设置的qps 也会逐步放入队列中 直到超出队列长度

### 隔离
有线程池隔离和信号量隔离两种方式
sentinel使用信号量隔离
线程池隔离是给每个服务开一个独立的线程池
信号量隔离没有创建线程池 而是使用信号量进行资源的限制

线程池隔离适合比较复杂的场景 需要用到超时、异常处理
信号量适合比较简单的场景 比如网关进行转发
## 熔断策略
有三种状态
close open half-open
初始是close 关闭熔断的状态

在达到熔断条件后 进行熔断open状态 
熔断时间达到一段时间后 进入half-open状态
放入一条请求 如果成功 回到close状态 如果失败 继续open状态

熔断条件有三种 
慢调用比例 超出调用时长的请求比例
异常比例 抛出异常的比例
异常数 抛出异常的数量


## 其他
sentinel可以实现授权规则和错误返回
springmvc和springsecurity也可以实现
具体实践选择?