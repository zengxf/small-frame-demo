-- -------------------
-- xx
-- -------------------

-- 创建库
CREATE
DATABASE `source_db` CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_bin';
CREATE
DATABASE `target_db` CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_bin';


-- 创建用户
CREATE
USER 'src_u'@'%' IDENTIFIED BY '1122';
GRANT ALL
ON `source_db`.* TO 'src_u'@'%';


-- 授权
CREATE
USER 'tar_u'@'%' IDENTIFIED BY '1122';
GRANT ALL
ON `target_db`.* TO 'tar_u'@'%';


-- 建表
USE
source_db;

CREATE TABLE `test_01`
(
    `id`               int NOT NULL COMMENT 'id xx',
    `t_name`           varchar(50) DEFAULT NULL COMMENT 't name v1',
    `create_date`      date        DEFAULT NULL COMMENT '创建日期',
    `create_time`      time        DEFAULT NULL COMMENT '创建时间',
    `update_date_time` datetime    DEFAULT NULL COMMENT '修改时间'
) ENGINE=InnoDB COMMENT='测试 1 v1'
;

--------------------------------
-- 测试
--------------------------------
-- 改长度
ALTER TABLE `test_01`
    CHANGE COLUMN `t_name` `t_name` VARCHAR (65) NULL DEFAULT NULL
    COMMENT 't name v1'
;
-- 加列
ALTER TABLE `test_01`
    ADD COLUMN `price` DECIMAL(10, 3) NULL DEFAULT NULL
        COMMENT '单价'
;

