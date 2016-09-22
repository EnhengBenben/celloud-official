ALTER TABLE `celloud`.`tb_file` 
ADD COLUMN `oss_path` VARCHAR(225) NULL COMMENT '文件在oss上的路径，即oss的objectKey' AFTER `batch`,
ADD COLUMN `upload_state` INT(11) NOT NULL DEFAULT 0 COMMENT '文件上传状态:0=已上传到盒子，1=已上传到oss，2=上传oss失败' AFTER `oss_path`;
