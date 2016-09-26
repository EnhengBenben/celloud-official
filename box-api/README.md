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
