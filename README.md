# 高并发秒杀设计与优化 #


## **目录** ##
- [<font size=4>**秒杀业务特点**</font>](http://github.com/njupt-tj/spike/#秒杀业务特点)
- [<font size=4>**秒杀架构**</font>](http://github.com/njupt-tj/spike/#秒杀架构)
   - [<font size=4>**前端架构**</font>](http://github.com/njupt-tj/spike/#前端架构)
   - [<font size=4>**后端架构**</font>](http://github.com/njupt-tj/spike/#后端架构)
- [<font size=4>**数据库表设计**</font>](http://github.com/njupt-tj/spike/#数据库表设计)
- [<font size=4>**技术选型**</font>](http://github.com/njupt-tj/spike/#技术选型)
   - [<font size=4>**前端技术**</font>](http://github.com/njupt-tj/spike/#前端技术)
   - [<font size=4>**后端技术**</font>](http://github.com/njupt-tj/spike/#后端技术)
- [<font size=4>**开发工具**</font>](http://github.com/njupt-tj/spike/#开发工具)
- [<font size=4>**开发环境**</font>](http://github.com/njupt-tj/spike/#开发环境)
- [<font size=4>**项目结构**</font>](http://github.com/njupt-tj/spike/#项目结构)
- [<font size=4>**实现功能**</font>](http://github.com/njupt-tj/spike/#实现功能)
- [<font size=4>**效果展示**</font>](http://github.com/njupt-tj/spike/#效果展示)
- [<font size=4>**高并发优化**</font>](http://github.com/njupt-tj/spike/#高并发优化)
- [<font size=4>**待完善的功能**</font>](http://github.com/njupt-tj/spike/#待完善功能)

## 秒杀业务特点 ##
1. <font size=4 color=black >高并发读，即在秒杀的一瞬间有大量的用户抢购同一个商品</font>
2. <font size=4 color=black >高并发写，对于秒杀成功的用户来说，需要减库存，以及下订单</font>

## 秒杀架构 ##
### 前端架构 ###

![miaosha1](https://github.com/njupt-tj/spike/raw/master/images/miaosha1.jpg)

### 后端架构 ###

![miaosha1](https://github.com/njupt-tj/spike/raw/master/images/miaosha2.jpg)

## 数据库表设计 ##
- user 用户表
- spike_goods 秒杀商品表
- success_spike 成功秒杀的用户表
- goods_info 商品表

## 技术选型 ##
### 前端技术 ###
- Bootstrap+js
### 后端技术 ###
- Spring
- Spring MVC
- Mybatis
- MySQL
- Redis
- RabbitMQ
- jackson
- log4j
- lombok
- jedis

## 开发工具 ##
- Idea 主要开发工具
- SQLyog 可视化的数据库管理
- RedisDesktop redis客户端
- Rabbitmq-management 可视化的rabbitmq管理工具

## 开发环境 ##
- JDK 1.8
- MySQL 8.16
- Redis 3.2
- RabbitMQ 3.7.16

## 组织结构 ##
spike  
|——contoller  
|——dao  
|——dto  
|——entity  
|——enums  
|——exception  
|——services  
|——utils  

## 实现功能 ##
- 用户登录验证
- 秒杀列表页展示
- 秒杀详情页展示
- 秒杀接口暴露
- 秒杀订单处理

## 效果展示 ##


## 高并发优化 ##
### 如何处理超卖现象？ ###

### 防止重复秒杀？ ###

### 如何抗高并发读 ###

### 如何抗高并发写 ###

## 待完善的功能 ##
- <font size=4 color=blue>订单支付</font>



