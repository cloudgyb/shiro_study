-- MySQL dump 10.16  Distrib 10.1.37-MariaDB, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: shiro_test
-- ------------------------------------------------------
-- Server version	10.1.37-MariaDB-0+deb9u1


--
-- Table structure for table `tb_permission`
--

DROP TABLE IF EXISTS `tb_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_permission` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `permission_name` varchar(50) DEFAULT NULL COMMENT '权限名称',
  `permission_code` varchar(30) NOT NULL COMMENT '权限代码，例如user:add',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COMMENT='权限信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tb_role`
--

DROP TABLE IF EXISTS `tb_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_role` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `role_name` varchar(20) NOT NULL COMMENT '角色名称',
  `role_desc` varchar(100) DEFAULT NULL COMMENT '角色描述',
  `max_user` int(11) DEFAULT '-1' COMMENT '该角色下最大的用户数,-1表示无限制',
  `permission_ids` varchar(200) DEFAULT NULL COMMENT '该角色拥有的权限集合，以逗号分割',
  PRIMARY KEY (`id`),
  UNIQUE KEY `tb_role_role_name_uindex` (`role_name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='用户角色信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tb_user`
--

DROP TABLE IF EXISTS `tb_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `username` varchar(30) NOT NULL COMMENT '用户名',
  `password` varchar(32) NOT NULL COMMENT '用户密码',
  `password_salt` varchar(32) DEFAULT NULL COMMENT '用于加密密码的加密盐',
  `role_ids` varchar(100) DEFAULT NULL COMMENT '用户拥有的角色id集合，以逗号分割，例如1,2,3',
  `is_locked` tinyint(1) DEFAULT NULL COMMENT '该用户是否被锁定',
  PRIMARY KEY (`id`),
  UNIQUE KEY `tb_user_username_uindex` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='用户信息表';
/*!40101 SET character_set_client = @saved_cs_client */;


-- Dump completed on 2020-10-20 20:05:29
