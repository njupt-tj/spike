# 高并发秒杀设计与优化 #


## **目录** ##
- [<font size=4>**秒杀业务特点**</font>](#秒杀业务特点)
- [<font size=4>**秒杀架构**</font>](#秒杀架构)
   - [<font size=4>**前端架构**</font>](#前端架构)
   - [<font size=4>**后端架构**</font>](#后端架构)
- [<font size=4>**数据库表设计**</font>](#数据库表设计)
- [<font size=4>**技术选型**</font>](#技术选型)
   - [<font size=4>**前端技术**</font>](#前端技术)
   - [<font size=4>**后端技术**</font>](#后端技术)
- [<font size=4>**开发工具**</font>](#开发工具)
- [<font size=4>**开发环境**</font>](#开发环境)
- [<font size=4>**项目结构**</font>](#项目结构)
- [<font size=4>**实现功能**</font>](#实现功能)
- [<font size=4>**效果展示**</font>](#效果展示)
- [<font size=4>**高并发优化**</font>](#高并发优化)
- [<font size=4>**待完善的功能**</font>](#待完善的功能)

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
- sql语句判断库存：多个请求同时减库存，则在数据库层面判断库存
### 防止重复秒杀？ ###
- redis缓存标记：将用户id和商品id进行绑定，进行内存标记
- 数据库表将用户id和商品id设为唯一索引
- 秒杀按钮禁用：用户在进行秒杀一次后，禁用按钮
### 如何抗高并发读 ###
- 将热点数据（秒杀商品、库存）放入到缓存系统中，减少对数据库的大量请求
### 如何抗高并发写 ###
- redis预减库存：不直接访问数据库进行减库存，先用redis减库存，且数据库库存不需要与缓存库存保持一致性，缓存只是为了抗高并发请求，保持最终一致性就好了。
- rabbitmq削峰：请求先入队列，异步进行订单的处理，同时减数据库的库存。


## 待完善的功能 ##
- <font size=4 color=blue>订单支付</font>



