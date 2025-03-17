CREATE TABLE `image_server` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(100) NOT NULL COMMENT '服务名称',
  `interface_type` varchar(50) NOT NULL COMMENT '接口类型',
  `host` varchar(255) NOT NULL COMMENT '服务地址',
  `path` varchar(255) NOT NULL COMMENT '接口路径',
  `api_key` varchar(255) DEFAULT NULL COMMENT 'API密钥',
  `api_secret` varchar(255) DEFAULT NULL COMMENT 'API密钥',
  `app_id` varchar(100) DEFAULT NULL COMMENT '应用ID',
  `model` varchar(100) DEFAULT NULL COMMENT '模型名称',
  `temperature` double DEFAULT NULL COMMENT '温度参数',
  `max_tokens` int(11) DEFAULT NULL COMMENT '最大token数',
  `active` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否激活',
  `template_name` varchar(100) DEFAULT NULL COMMENT '模板名称',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='图像模型服务配置表'; 