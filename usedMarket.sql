-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: usedMarket
-- ------------------------------------------------------
-- Server version	8.0.34

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb3 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `usedMarket`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `usedMarket` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `usedMarket`;

--
-- Table structure for table `goods`
--

DROP TABLE IF EXISTS `goods`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `goods` (
  `goods_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `goods_name` varchar(45) NOT NULL,
  `picture` varchar(50) NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `type` varchar(45) DEFAULT NULL,
  `datetime` datetime(2) NOT NULL,
  `notes` varchar(200) NOT NULL,
  `active` varchar(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`goods_id`),
  KEY `type_idx` (`type`),
  KEY `type_foreign_idx` (`type`),
  KEY `type_froeign_idx` (`type`),
  KEY `user_id_constrain` (`user_id`),
  CONSTRAINT `type_constrain` FOREIGN KEY (`type`) REFERENCES `type` (`type_name`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `user_id_constrain` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `goods`
--

LOCK TABLES `goods` WRITE;
/*!40000 ALTER TABLE `goods` DISABLE KEYS */;
INSERT INTO `goods` VALUES (1,4,'英语写作基础','http://localhost:8080/pics/20240418184508196.png',5.00,'二手书','2024-04-18 18:45:20.60','','1'),(2,4,'编译原理','http://localhost:8080/pics/20240418184444971.png',5.00,'二手书','2024-04-18 18:44:59.45','','1'),(3,4,'汇编语言程序设计','http://localhost:8080/pics/20240418184526330.png',9.00,'二手书','2024-04-18 18:45:42.91','','1'),(4,4,'大学物理实验','http://localhost:8080/pics/20240418184548815.png',5.00,'二手书','2024-04-18 18:46:00.90','','1'),(5,4,'英国文学选读','http://localhost:8080/pics/20240418184606961.png',6.00,'二手书','2024-04-18 18:46:20.85','','1'),(6,4,'Java2实用教程','http://localhost:8080/pics/20240418184625401.png',10.00,'二手书','2024-04-18 18:46:46.48','','1'),(7,4,'线性代数','http://localhost:8080/pics/20240418184651819.png',4.00,'二手书','2024-04-18 18:47:05.35','','1'),(58,1,'拖鞋','http://localhost:8080/pics/20240418192910682.jpg',10.00,'日用品','2024-04-18 19:29:32.24','','1'),(59,1,'拖鞋','http://localhost:8080/pics/20240418192943530.jpg',9.00,'日用品','2024-04-18 19:29:53.66','','1'),(60,2,'三阶魔方','http://localhost:8080/pics/20240418194041331.jpg',25.00,'日用品','2024-04-18 19:42:11.10','磁力魔方，非常好用！','1'),(61,2,'游泳眼镜','http://localhost:8080/pics/20240418194231363.jpg',20.00,'其他','2024-04-18 19:44:27.48','9成新，附带耳塞鼻塞','1'),(62,3,'窝瓜水杯','http://localhost:8080/pics/20240418194708183.jpg',10.00,'日用品','2024-04-18 19:47:55.62','全新！有完整的包装','1'),(64,4,'科学计算器','http://localhost:8080/pics/20240420214652553.jpg',10.00,'电子产品','2024-04-20 21:50:48.35','功能完整','1');
/*!40000 ALTER TABLE `goods` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `question`
--

DROP TABLE IF EXISTS `question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `question` (
  `question_id` int NOT NULL,
  `item` varchar(200) NOT NULL,
  PRIMARY KEY (`question_id`),
  UNIQUE KEY `item_UNIQUE` (`item`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `question`
--

LOCK TABLES `question` WRITE;
/*!40000 ALTER TABLE `question` DISABLE KEYS */;
INSERT INTO `question` VALUES (3,'您母亲的名字？'),(4,'您父亲的名字？'),(5,'您的真实姓名？'),(1,'您祖母叫什么名字？'),(2,'您祖父叫什么名字？');
/*!40000 ALTER TABLE `question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `relation`
--

DROP TABLE IF EXISTS `relation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `relation` (
  `relation_id` int NOT NULL AUTO_INCREMENT,
  `question_id` int NOT NULL,
  `user_id` int NOT NULL,
  `answer` varchar(200) NOT NULL,
  PRIMARY KEY (`relation_id`),
  UNIQUE KEY `user_id_UNIQUE` (`user_id`),
  KEY `question_id_constrain_idx` (`question_id`),
  CONSTRAINT `question_id_constrain` FOREIGN KEY (`question_id`) REFERENCES `question` (`question_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `used_id_constrain` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `relation`
--

LOCK TABLES `relation` WRITE;
/*!40000 ALTER TABLE `relation` DISABLE KEYS */;
INSERT INTO `relation` VALUES (6,5,3,'陈冠宇'),(7,5,4,'陈浩锾'),(8,5,1,'赵欢柯'),(9,5,2,'朱扬文');
/*!40000 ALTER TABLE `relation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trade`
--

DROP TABLE IF EXISTS `trade`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `trade` (
  `trade_id` int NOT NULL AUTO_INCREMENT,
  `buyer_id` int DEFAULT NULL,
  `goods_id` int DEFAULT NULL,
  `state` int DEFAULT NULL,
  `notes` varchar(200) NOT NULL,
  PRIMARY KEY (`trade_id`),
  KEY `goods_id_constarin_idx` (`goods_id`),
  KEY `buyer_id_constrain_idx` (`buyer_id`),
  KEY `user_id_constrain_idx` (`buyer_id`),
  CONSTRAINT `buyer_id_constrain` FOREIGN KEY (`buyer_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `goods_id_constrain` FOREIGN KEY (`goods_id`) REFERENCES `goods` (`goods_id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trade`
--

LOCK TABLES `trade` WRITE;
/*!40000 ALTER TABLE `trade` DISABLE KEYS */;
INSERT INTO `trade` VALUES (19,3,61,1,''),(20,3,59,1,''),(21,4,62,3,''),(22,3,5,3,''),(23,4,61,2,''),(24,1,3,2,''),(25,2,6,1,''),(26,4,60,1,'');
/*!40000 ALTER TABLE `trade` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `type`
--

DROP TABLE IF EXISTS `type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `type` (
  `type_id` int NOT NULL,
  `type_name` varchar(45) NOT NULL,
  PRIMARY KEY (`type_id`),
  UNIQUE KEY `type_name_UNIQUE` (`type_name`),
  KEY `type_name_idx` (`type_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `type`
--

LOCK TABLES `type` WRITE;
/*!40000 ALTER TABLE `type` DISABLE KEYS */;
INSERT INTO `type` VALUES (1,'二手书'),(0,'其他'),(4,'日用品'),(3,'电子产品');
/*!40000 ALTER TABLE `type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `user_name` varchar(45) NOT NULL,
  `nick_name` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `avatar` varchar(100) DEFAULT NULL,
  `gender` varchar(5) NOT NULL DEFAULT '0',
  `email` varchar(45) DEFAULT NULL,
  `phone` varchar(15) DEFAULT NULL,
  `wechat` varchar(45) DEFAULT NULL,
  `address` varchar(45) DEFAULT NULL,
  `role_id` int NOT NULL DEFAULT '0',
  `register_time` datetime(2) NOT NULL,
  `login_time` datetime(2) NOT NULL,
  `active` varchar(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_name_UNIQUE` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (0,'admin','管理员','e10adc3949ba59abbe56e057f20f883e','http://localhost:8080/pics/20240418170341761.png','0','','',NULL,NULL,1,'2024-04-02 14:20:18.67','2024-04-21 11:58:32.70','1'),(1,'202125310129','JoVanko','e10adc3949ba59abbe56e057f20f883e','http://localhost:8080/pics/20240418193329067.png','1','','',NULL,NULL,0,'2024-04-02 17:44:24.78','2024-04-20 22:03:45.06','1'),(2,'202125310130','yangwen','e10adc3949ba59abbe56e057f20f883e','http://localhost:8080/pics/20240418194023217.png','1',NULL,NULL,NULL,NULL,0,'2024-04-18 19:07:17.21','2024-04-20 22:04:27.98','1'),(3,'202125310201','小优秀','e10adc3949ba59abbe56e057f20f883e','http://localhost:8080/pics/20240418194544427.png','1','','',NULL,NULL,0,'2024-04-02 17:44:24.78','2024-04-20 17:34:21.36','1'),(4,'202125310202','haohuan','e10adc3949ba59abbe56e057f20f883e','http://localhost:8080/pics/20240418195704664.png','1','1182814995@qq.com','19835809866','19835809866','华山14栋',0,'2024-04-02 14:20:18.67','2024-04-21 11:59:02.49','1'),(43,'asd','asd1','123',NULL,'0',NULL,NULL,NULL,NULL,0,'2024-04-03 14:20:18.67','2024-04-03 14:20:18.67','1'),(44,'中文','asd1','123',NULL,'0',NULL,NULL,NULL,NULL,0,'2024-04-03 14:20:18.67','2024-04-03 14:20:18.67','1');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-04-21 13:14:01
