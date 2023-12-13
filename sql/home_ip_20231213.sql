-- ----------------------------
-- homeIP表
-- ----------------------------
drop table if exists home_ip;
CREATE TABLE `home_ip` (
                           `id` int(11) unsigned NOT NULL AUTO_INCREMENT comment '无业务意义自增ID',
                           `home_name` varchar(20) DEFAULT NULL comment '家庭名称',
                           `home_ip` varchar(20) DEFAULT NULL comment 'IP地址',
                           `home_ua` varchar(255) DEFAULT NULL comment '访问UA',
                           `home_location` varchar(255) DEFAULT NULL comment '所属地址',
                           `gmt_creat` datetime(3) DEFAULT CURRENT_TIMESTAMP(3) comment '创建时间',
                           `gmt_modified` datetime(3) DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) comment '更新时间',
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 comment = 'IP地址表';