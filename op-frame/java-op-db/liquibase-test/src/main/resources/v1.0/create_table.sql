-- 广告设计
-- DROP TABLE IF EXISTS `site_poster_design`;
CREATE TABLE `site_poster_design` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键 ID',

    -- 关联字段
    `apply_id` BIGINT NOT NULL COMMENT '广告申请 ID',

    -- 业务字段
    `designer_id` BIGINT NOT NULL COMMENT '设计负责人 ID',
    `designer_name` VARCHAR(64) NOT NULL COMMENT '设计负责人名称',

    -- 系统字段
    `create_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',

    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_apply_id` (`apply_id`) USING BTREE
) COMMENT='广告设计' ENGINE=InnoDB;