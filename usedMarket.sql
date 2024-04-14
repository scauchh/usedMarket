-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: usedmarket
-- ------------------------------------------------------
-- Server version	8.0.34

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES gbk */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `usedmarket`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `usedmarket` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `usedmarket`;

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
  PRIMARY KEY (`goods_id`),
  KEY `type_idx` (`type`),
  KEY `type_foreign_idx` (`type`),
  KEY `type_froeign_idx` (`type`),
  KEY `user_id_constrain` (`user_id`),
  CONSTRAINT `type_constrain` FOREIGN KEY (`type`) REFERENCES `type` (`type_name`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `user_id_constrain` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `goods`
--

LOCK TABLES `goods` WRITE;
/*!40000 ALTER TABLE `goods` DISABLE KEYS */;
INSERT INTO `goods` VALUES (37,12,'123','http://localhost:8080/pics/20240331171955407.png',12.12,'二手书','2024-03-31 21:30:38.14','测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试'),(39,28,'12345','http://localhost:8080/pics/20240326213939381.png',12.00,'其他','2024-04-02 14:20:09.65','无'),(40,12,'111111','http://localhost:8080/pics/20240326214300122.png',333.00,'电子产品','2024-04-02 12:23:29.28',''),(42,28,'qwewww','http://localhost:8080/pics/20240327132436715.png',10.00,'二手衣物','2024-04-13 21:39:35.31','qweqweqwe'),(49,28,'123','http://localhost:8080/pics/20240331211634335.png',2.00,'日用品','2024-03-31 21:24:23.55',''),(50,28,'eee','http://localhost:8080/pics/20240413214253669.png',3.00,'二手书','2024-04-13 21:42:54.40','');
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
INSERT INTO `question` VALUES (3,'您母亲的名字？'),(4,'您父亲的名字？'),(5,'您的小学的名字？'),(1,'您祖母叫什么名字？'),(2,'您祖父叫什么名字？');
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
  UNIQUE KEY `question_id_UNIQUE` (`question_id`),
  UNIQUE KEY `user_id_UNIQUE` (`user_id`),
  CONSTRAINT `question_id_constrain` FOREIGN KEY (`question_id`) REFERENCES `question` (`question_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `used_id_constrain` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `relation`
--

LOCK TABLES `relation` WRITE;
/*!40000 ALTER TABLE `relation` DISABLE KEYS */;
INSERT INTO `relation` VALUES (4,1,28,'123');
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
  `buyer_name` varchar(45) DEFAULT NULL,
  `seller_name` varchar(45) DEFAULT NULL,
  `goods_id` int DEFAULT NULL,
  `state` int DEFAULT NULL,
  `notes` varchar(200) NOT NULL,
  PRIMARY KEY (`trade_id`),
  KEY `book_id_constarin_idx` (`goods_id`),
  KEY `buyer_name_constrain_idx` (`buyer_name`),
  KEY `seller_name_constrain_idx` (`seller_name`),
  CONSTRAINT `buyer_name_constrain` FOREIGN KEY (`buyer_name`) REFERENCES `user` (`user_name`) ON DELETE SET NULL,
  CONSTRAINT `goods_id_constrain` FOREIGN KEY (`goods_id`) REFERENCES `goods` (`goods_id`) ON DELETE SET NULL,
  CONSTRAINT `seller_name_constrain` FOREIGN KEY (`seller_name`) REFERENCES `user` (`user_name`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trade`
--

LOCK TABLES `trade` WRITE;
/*!40000 ALTER TABLE `trade` DISABLE KEYS */;
INSERT INTO `trade` VALUES (9,'123123','asdasd',37,1,''),(10,'asdasd','123123',50,1,'');
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
INSERT INTO `type` VALUES (1,'二手书'),(5,'二手衣物'),(0,'其他'),(4,'日用品'),(3,'电子产品');
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
  `role_id` int NOT NULL DEFAULT '0',
  `register_time` datetime(2) NOT NULL,
  `login_time` datetime(2) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_name_UNIQUE` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'11111','11111','11111','http://localhost:8080/pics/20240403125243290.png','2','11111@111.com','19835812343',1,'2024-04-02 17:44:24.78','2024-04-03 12:50:56.26'),(2,'22222','22222','22222','http://localhost:8080/pics/20240402174442173.png','1','11223344@1234.com','19835812343',0,'2024-04-02 17:44:24.78','2024-04-02 17:44:28.24'),(12,'asdasd','asd','123123','http://localhost:8080/pics/20240403203620609.png','1',NULL,NULL,0,'2024-04-02 14:20:18.67','2024-04-13 15:00:00.25'),(28,'123123','123','123123','http://localhost:8080/pics/20240402145014962.png','2','1182814995@qq.com','19835809866',1,'2024-04-02 14:20:18.67','2024-04-14 11:23:09.66'),(33,'121212','121212','121212',NULL,'0',NULL,NULL,0,'2024-04-02 16:38:35.28','2024-04-02 16:44:44.38'),(35,'333111','333111','333111','http://localhost:8080/pics/20240402174442173.png','1','11223344@1234.com','19835812343',0,'2024-04-02 17:44:24.78','2024-04-02 17:44:28.24');
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

-- Dump completed on 2024-04-14 17:02:50
