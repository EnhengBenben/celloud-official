#box-api
##一、介绍
使用SpringBoot开发的微服务，盒子的内置服务，1.0版本主要解决用户上传问题：
将用户上传的数据先上传到盒子，盒子上传到OSS上，然后web端再从OSS同步到上海的存储。
##二、打包
```java
cd /{git仓库目录}/celloud/box-api
```
```java
mvn clean package
```
##三、运行
```java
java -jar box-api-{version}.jar 
```
##四、运行参数
###4.1 spring.profiles.active
启动springboot的profile，默认值dev，可选值test、qa、prod，分别对应开发环境、测试环境、qa环境和生产环境
```
 java -jar box-api-{version}.jar --spring.profiles.active=prod
```
###4.2 server.port
启动springboot使用的端口号，默认9090
```
 java -jar box-api-{version}.jar --server.port=8080
```
###4.3 box.uploadPath
文件上传的根路径，linux默认为 ```/share/data/upload```，建议手动创建此目录，并将目录权限授予启动程序的用户；windows下默认为```System.getProperty("user.home")```，即用户的home路径，强烈建议windows下重新指定此参数。
```
 java -jar box-api-{version}.jar --box.uploadPath=/share/data/upload/
```
###4.4 web apis
celloud的web端提供给盒子的接口地址：
####api.newfile
celloud的web端提供给盒子的创建文件的http接口地址
####api.updatefile
celloud的web端提供给盒子的更新文件状态的http接口地址
####默认值
不同的profile对应不同的默认值，dev指向localhost，test指向genecode.cn，qa指向celloud.cc，prod指向celloud.cn。一般不需要改动。如需重新指定，使用下面的方式：
```
java -jar box-api-{version}.jar  --api.newfile=XXXXX --api.updatefile=XXXXXX
```
###4.5 oss config
阿里云OSS对象存储的配置，包含四个：
####oss.bucketName
####oss.endpoint
####oss.accessKeyId
####oss.accessKeySecret
参数值一般不需要改动，如需重新指定，使用下面的方式：
```
java -jar box-api-{version}.jar  --oss.bucketName=XXXXX --oss.endpoint=XXXXX --oss.accessKeyId=XXXXX --oss.accessKeySecret=XXXXX
```
###4.6 Common application properties
Spring Boot的启动参数，一般使用默认值即可，如需要改动，参考：

http://docs.spring.io/spring-boot/docs/1.2.3.RELEASE/reference/html/common-application-properties.html
##五、建议的启动脚本：
```
java -jar box-api-{version}.jar  --spring.profiles.active=dev|test|qa|prod --server.port=80 --box.uploadPath=/share/data/upload/
```
