/*!40101 SET NAMES utf8 */;

CREATE TABLE `celloud`.`tb_compute_cluster` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `code` VARCHAR(45) NOT NULL COMMENT '计算集群的编码，在代码里使用，不重复',
  `name` VARCHAR(45) NULL COMMENT '计算集群的名字，显示用',
  `topic` VARCHAR(45) NULL COMMENT '投递任务需要发送消息的topic',
  `tag` VARCHAR(45) NULL COMMENT '投递任务需要发送消息的tag(部分mq支持)',
  `remark` VARCHAR(255) NULL COMMENT '备注',
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) ,
  UNIQUE INDEX `code_UNIQUE` (`code` ASC)  );

INSERT INTO `celloud`.`tb_compute_cluster` (`id`, `code`, `name`, `topic`, `remark`) VALUES ('1', 'bc', 'batch compute', 'bc-task-delivery', '阿里云批量计算');
INSERT INTO `celloud`.`tb_compute_cluster` (`id`, `code`, `name`, `topic`, `remark`) VALUES ('2', 'emr', 'E-MapReduce', 'emr-task-delivery', '阿里云E-MapReduce');
INSERT INTO `celloud`.`tb_compute_cluster` (`id`, `code`, `name`, `topic`, `remark`) VALUES ('3', 'spark', 'spark', 'spark-task-delivery', '上海自建spark集群');
INSERT INTO `celloud`.`tb_compute_cluster` (`id`, `code`, `name`, `topic`, `remark`) VALUES ('4', 'sge', 'sge', 'sge-task-delivery', '上海自建sge集群');


ALTER TABLE `celloud`.`tb_app` 
ADD COLUMN `cluster_id` INT NOT NULL COMMENT '运行当前app的计算集群' AFTER `code`;


UPDATE `celloud`.`tb_app` SET `cluster_id`='1' WHERE `app_id`='123';