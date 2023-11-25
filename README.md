# microservices


测试地址：http://localhost:10010/order/101?authorization=admin
authorization为网关配置的权限地址


copy configuration可以新运行一个service

nacos单机启动 startup.cmd -m standalone
sentinel启动 Java -jar sentinel-dashboard-1.8.1.jar 可以配置账号密码
默认密码sentinel

maven使用lifecycle的各种指令
package打包成jar包
deploy部署jar包
可以在pom文件中指定部署位置 注意如果有父工程 直接在父工程pom文件中写后打包父工程
自己写的jar包需要在仓库中 其他程序引用才能一起打成jar包