#box-api
##介绍
使用SpringBoot开发的微服务，盒子的内置服务，1.0版本主要解决用户上传问题：
将用户上传的数据先上传到盒子，盒子上传到OSS上，然后web端再从OSS同步到上海的存储。
##打包
```java
mvn clean package
```
##运行
```java
java -jar box-api-{version}.jar 
```
##运行参数
###spring.profiles.active
启动springboot的profile，默认值dev，可选值test、qa、prod，分别对应开发环境、测试环境、qa环境和生产环境
```
 java -jar box-api-{version}.jar --spring.profiles.active=prod
```
###server.port
启动springboot使用的端口号，默认9090
```
 java -jar box-api-{version}.jar --server.port=8080
```
###box.uploadPath
文件上传的根路径，默认 ```/share/data/upload```
```
 java -jar box-api-{version}.jar --box.uploadPath=/share/data/upload/
```
