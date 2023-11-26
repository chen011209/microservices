# microservices


测试地址：http://localhost:10010/order/101?authorization=admin
authorization为网关配置的权限地址


copy configuration可以新运行一个service

## dev环境运行 (本地运行环境)
先启动nacos和sentinel mysql服务

nacos单机启动 startup.cmd -m standalone
默认账号密码nacos 默认端口8848
sentinel启动 Java -jar sentinel-dashboard-1.8.1.jar 可以配置账号密码
默认账号密码sentinel 默认端口8080

## dev-server (本地运行代码 中间件nacos sentinel和mysql在服务器运行)
docker拉取sentinel
docker pull bladex/sentinel-dashboard
docker run --name sentinel -p 8858:8858 -td bladex/sentinel-dashboard


带着docker文件中的dev-server中所有文件进行docker-compose运行
附带mysql文件 compose中指名了使用本地路径
## test(全部代码部署到docker中)




maven使用lifecycle的各种指令
package打包成jar包
deploy部署jar包
可以在pom文件中指定部署位置 注意如果有父工程 直接在父工程pom文件中写后打包父工程
自己写的jar包需要在仓库中 其他程序引用才能一起打成jar包


todo dev测试完成 测试dev-server运行情况
dev-server在docker中运行正常 需要修改父pom文件多环境运行
https://juejin.cn/post/7054348294083854343
之后重新测试 从dev开始测试