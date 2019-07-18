/*
 创建秒杀表
 */
create database spikeDB;
create table spike_t
(
    spike_id bigint not null auto_increment comment '商品库存id',
    name varchar(255) not null comment '商品名称',
    number int(12) not null comment '库存数量',
    start_time timestamp not null comment '秒杀开始时间',
    end_time timestamp not null comment '秒杀结束时间',
    create_time timestamp not null DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    primary key (spike_id),
    key idx_start_time(start_time),
    key idx_end_time(end_time),
    key idx_create_time(create_time)
)ENGINE=InnoDB auto_increment=1000 default charset=utf8 comment '秒杀库存表';
/*
 初始化
 */
 insert into
    spike_t(name, number, start_time, end_time)
values
    ('2000元秒杀华为mate30',1000,'2019-07-16 00:00:00','2019-07-17 00:00:00'),
    ('1000元秒杀小米8',3000,'2019-07-16 00:00:00','2019-07-17 00:00:00'),
    ('2000元秒杀荣耀20',4000,'2019-07-16 00:00:00','2019-07-17 00:00:00'),
    ('4000元秒杀iphone8',1000,'2019-07-16 00:00:00','2019-07-17 00:00:00');

/**
  秒杀成功明细表，用户登录认证的信息
 */

create table success_spike_t
(
    spike_id bigint not null comment '商品库存id',
    user_phone int(11) not null comment '用户手机号码',
    status tinyint not null default -1 comment '状态：-1：未登录 0：已经登录 1：秒杀成功 2：已发货',
    create_time timestamp not null comment '订单创建时间',
    primary key (spike_id,user_phone),
    key idx_create_time(create_time)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '秒杀成功明细表';
