create table `merchandise`
(
    `id`          bigint    NOT NULL auto_increment,
    `name`        varchar(255)       DEFAULT NULL,
    `price`       double,
    `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `status`      VARBINARY(64),
    PRIMARY KEY (`id`)
) engine = InnoDB;

create table `user`
(
    `id`          bigint    NOT NULL auto_increment,
    `name`        varchar(255)       DEFAULT NULL,
    `gender`      tinyint            DEFAULT NULL,
    `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `status`      VARBINARY(64),
    PRIMARY KEY (`id`)
) engine = InnoDB;

create table `order`
(
    `id`          bigint    NOT NULL auto_increment,
    `user_id`     bigint    NOT NULL,
    `merchandise` bigint    NOT NULL,
    `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `status`      VARBINARY(64),
    PRIMARY KEY (`id`)
) engine = InnoDB;


