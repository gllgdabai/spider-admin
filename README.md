# 爬虫管理系统
## 创建数据库
把sql文件夹中springboot_vue.sql导入数据库

## 配置数据库信息
在 application.yml 配置数据库信息 username和password

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/springboot_vue?useUnicode=true&characterEncoding=UTF-8&serverTimeZone=UTC
    username: 
    password: 
    driver-class-name: com.mysql.cj.jdbc.Driver
```

## 导入ruoyi.common工具包
由于com.ruoyi.common在maven仓库无法下载，需要手动下载jar包并导入项目。
jar放在了ruoyi-common文件夹中
![lok9g.png](https://s1.328888.xyz/2022/05/26/lok9g.png)


## ElasticSearch

### 安装ElasticSearch

使用的版本为：elasticsearch-7.15.2-windows



注意：先在ElasticSearch目录下的plugins文件夹下 创建一个文件夹 ，文件夹名称必须为 ik

然后把elasticsearch-analysis-ik-7.15.2.zip解压到 ik文件夹下

E:\elasticsearch\elasticsearch-7.15.2\plugins\ik



### 启动ElastiSearch

打开 E:\elasticsearch\elasticsearch-7.15.2\bin目录下的elasticsearch.bat 即可



关闭的话，直接关就可以了



## 运行前端

进入到vue-demo文件夹，命令行输入：

```
npm install
```

```
npm run serve
```


localhost:8080 进入后台管理系统

用户名：admin 密码：123456

或者点击注册



   

