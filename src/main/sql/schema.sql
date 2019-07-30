/*
 创建秒杀表
 */
create database spikeDB;

drop table IF EXISTS 'goods_info';
create table goods_info
(
good_id bigint(20) not null auto_increment comment '商品id',
good_name varchar(50) not null comment '商品名称',
good_price decimal(10,2) default 0.0 not null comment '商品价格',
good_stock int(12) null comment '商品库存',
purchase_time datetime not null comment '采购时间',
primary key (good_id)
)ENGINE=InnoDB,AUTO_INCREMENT=4 default CHARSET=utf8 comment '商品表';

-- ---------------------
-- INIT
-- ----------------------
insert into goods_info(good_id, good_name, good_price, good_stock,purchase_time)
values (1,'华为平板',2445.23,1000,'2019-07-19'),
       (2,'小米手环',100.25,500,'2019-07-19'),
       (3,'iphoneXS',5000.56,1000,'2019-07-19');
-- -------------------------------
-- TABLE STRUCTURE FOR SPIKE_GOODS
-- -------------------------------
drop table if exists 'spike_goods';
create table spike_goods
(
    id bigint(20) not null auto_increment,
    good_id bigint(20) not null comment '商品id',
    spike_stock int(12) not null comment '秒杀库存数量',
    spike_price decimal(10,2) not null default 0.0 comment '秒杀价',
    start_time timestamp not null comment '秒杀开始时间',
    end_time timestamp not null comment '秒杀结束时间',
    create_time timestamp not null DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    primary key (id),
    key idx_good_id(good_id),
    key idx_start_time(start_time),
    key idx_end_time(end_time)
)ENGINE=InnoDB auto_increment=4 default charset=utf8 comment '秒杀商品表';

-- ---------
-- 初始化
-- ----------
insert into
    spike_goods(id, good_id, spike_stock, spike_price, start_time, end_time, create_time)
values (1,1,100,899.0,'2019-07-26 00:00:00','2019-07-27 00:00:00','2019-07-25 00:00:00'),
       (2,2,100,899.0,'2019-07-26 00:00:00','2019-07-27 00:00:00','2019-07-25 00:00:00'),
       (3,3,100,899.0,'2019-07-26 00:00:00','2019-07-27 00:00:00','2019-07-25 00:00:00');

-- -------------------------------
-- TABLE STRUCTURE FOR 'success_spike_user'
-- -------------------------------

create table success_spike_user
(
    id bigint(20) NOT null auto_increment comment '订单编号',
    good_id bigint(20) not null comment '商品id',
    user_id int(11) not null comment '用户id',
    status tinyint not null default -1 comment '状态：-1：未登录 0：已经登录 1：秒杀成功 2：已发货',
    create_time timestamp not null comment '订单创建时间',
    primary key (id),
    unique 'idx_ug' (user_id,good_id) using btree,
    key idx_create_time(create_time)
)ENGINE=InnoDB auto_increment=10000 DEFAULT CHARSET=utf8 comment '秒杀成功用户表';

-- ------
-- 初始化
-- -------
insert into success_spike_user(id, good_id, user_id, status, create_time)
values (8888,1,1,-1,'2019-07-20 12:44:23'),
        (8889,2,2,-1,'2019-07-20 12:44:23'),
        (8898,3,3,-1,'2019-07-20 12:44:23');
-- -------------------
-- TABLE STRUCTURE FOR user
--    ---------------
create table user
(
    user_id int(11) not null auto_increment comment '用户id',
    user_name varchar(20) not null comment '用户姓名',
    user_password varchar(40) not null comment '用户密码',
    user_phone varchar(11) not null comment '电话号码',
    user_address varchar(50) not null comment '收货地址',
    user_email varchar(50) not null comment '邮箱',
    primary key (user_id)
)ENGINE=InnoDB AUTO_INCREMENT=4,DEFAULT CHARSET=utf8 comment '用户信息表';

-- -------------------
-- 初始化
-- ----------------
insert into user(user_id, user_name, user_password, user_phone, user_address, user_email)
values (1,'汤吉','19950127tj',18329949244,'江苏省南京市南京邮电大学','tangji2019@163.com');