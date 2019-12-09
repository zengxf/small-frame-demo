CREATE DATABASE hellospeedment;
USE hellospeedment;


CREATE TABLE IF NOT EXISTS `user` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `name` varchar(32) NOT NULL,
    `age` int(5) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1;

INSERT INTO `user` VALUES (NULL, 'zxf-01', 23);
INSERT INTO `user` VALUES (NULL, 'zxf-02', 33);
INSERT INTO `user` VALUES (NULL, 'wlw-01', 43);
INSERT INTO `user` VALUES (NULL, 'wlw-02', 45);


CREATE TABLE IF NOT EXISTS `position` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `name` varchar(32) NOT NULL,
    `desc` varchar(32) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1;

INSERT INTO `position` VALUES (NULL, 'Java 开发工程师', 'test');
INSERT INTO `position` VALUES (NULL, 'C++ 开发工程师', 'non');
INSERT INTO `position` VALUES (NULL, '测试工程师', 'non');
INSERT INTO `position` VALUES (NULL, '维护人员', 'non');