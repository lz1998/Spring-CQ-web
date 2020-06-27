# Spring-CQ-web

## 简介
这是Spring-CQ框架的一个demo，实现了基本的学习、入群欢迎功能，并提供移动端自适应的web控制页面方便查询机器人在线状态、管理学习内容、设置入群欢迎内容等。

框架项目地址：https://github.com/lz1998/spring-cq

前端项目地址：https://github.com/lz1998/spring-cq-web-ui

## 项目环境
- Java8
- Intellij IDEA
- Maven
- Windows/Docker
- H2/MySQL

## 技术栈
### 机器人
- 酷Q
- CQHTTP插件

### 后端
- SpringBoot 基本框架
- SpringCQ 用于收发QQ消息
- SpringDataJpa 用于数据库访问
- SpringSecurity 用于登陆认证
- jjwt 用于生成json web token

### 数据库
- H2 默认数据库(为了新手直接运行)
- MySQL 可选

### 前端
- Vue 框架
- Vuetify 可以自适应移动端的组件库
- Axios 用于HTTP通信
- VueRouter 前端路由管理
- js-cookie 用于存储jwt

## 功能
### 机器人功能
- 前缀 过滤所有不带有前缀的信息，默认“.”
- 授权 未授权的群不处理任何指令
- 学习 给机器人添加自定义回复内容
- 欢迎 设置新人入群欢迎内容
- 注册 接受验证码，配合页面进行注册
- 监控 接受心跳包，记录机器人状态

### 页面功能
- 登陆/注册 QQ必须与账号相同，密码随意设置。输入密码正确为登陆，错误为注册/修改密码（需要发送验证码）
- 状态监控 根据监控插件收到的心跳包，显示机器人在线状态
- 学习管理 在页面上对机器人学习内容进行增删改查，超级管理员/群主/群管理员有权限
- 欢迎管理 在页面上对机器人学习内容进行增删改查，超级管理员/群主/群管理员有权限

## 总体设计
- Controller层 用于接受HTTP请求
- Plugin层 用于接受机器人消息，并处理消息逻辑
- Service层 用于处理服务
- Repository层 用于操作数据库（JPA）
- Entity 实体类 映射数据库表
- Security 登陆认证，使用JWT验证是否登陆

## 注意
- 项目正式使用，尽量采用MySQL，不要使用H2数据库
- 如果基于本项目进行开发，发布时请说明基于Spring-CQ开发，并附带Spring-CQ框架链接
- 顺手点个星星吧~
