第六周作业

```sql


create table `t_merchandise`
(
    `id`          bigint    NOT NULL auto_increment comment 'id',
    `name`        varchar(255)       DEFAULT NULL comment '商品名称',
    `price`       double comment '商品价格',
    `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP comment '创建时间',
    `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
    `status`      VARBINARY(64) comment '商品状态',
    PRIMARY KEY (`id`)
) engine = InnoDB
  default CHARSET = utf8 comment '商品表';

create table `t_user`
(
    `id`          bigint    NOT NULL auto_increment comment '用户id',
    `name`        varchar(255)       DEFAULT NULL comment '用户名',
    `gender`      tinyint            DEFAULT NULL comment '用户性别',
    `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP comment '用户创建时间',
    `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '用户更新时间',
    `status`      VARBINARY(64) comment '用户状态',
    PRIMARY KEY (`id`)
) engine = InnoDB
  DEFAULT CHARSET = utf8 comment '用户表';

create table `t_order`
(
    `id`             bigint    NOT NULL auto_increment comment '订单ID',
    `user_id`        bigint    NOT NULL comment '用户ID',
    `merchandise_id` bigint    NOT NULL comment '商品ID',
    `create_time`    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP comment '商品创建时间',
    `update_time`    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '商品更新时间',
    `status`         VARBINARY(64) comment '商品状态',
    PRIMARY KEY (`id`)
) engine = InnoDB
  DEFAULT CHARSET = utf8 comment '订单表';




```