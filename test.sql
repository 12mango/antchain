-- MySQL dump 10.16  Distrib 10.1.26-MariaDB, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: db
-- ------------------------------------------------------
-- Server version	10.1.26-MariaDB-0+deb9u1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `course`
--

DROP TABLE IF EXISTS `course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `course` (
  `id` smallint(6) DEFAULT NULL,
  `curName` varchar(11) DEFAULT NULL,
  `credit` tinyint(4) DEFAULT NULL,
  `nowRemain` smallint(6) DEFAULT NULL,
  `maxRemain` smallint(6) DEFAULT NULL,
  `teaName` varchar(5) DEFAULT NULL,
  `field` tinyint(4) DEFAULT NULL,
  `day` tinyint(4) DEFAULT NULL,
  `startWeek` tinyint(4) DEFAULT NULL,
  `endWeek` tinyint(4) DEFAULT NULL,
  `weeklyTimes` tinyint(4) DEFAULT NULL,
  `startSection` tinyint(4) DEFAULT NULL,
  `endSection` tinyint(4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course`
--

LOCK TABLES `course` WRITE;
/*!40000 ALTER TABLE `course` DISABLE KEYS */;
INSERT INTO `course` VALUES (106,'创业运营管理',2,7,60,'徐晓慧',1,1,1,11,1,11,13),(107,'当代世界经济与政治',2,3,75,'冯永平',1,1,1,1,1,1,13),(108,'方言与中国文化',2,0,100,'阮桂君',3,1,1,11,1,11,13),(109,'经典广告案例分析',2,40,130,'James',3,1,1,11,1,5,8),(110,'大数据与信息社会',2,4,200,'赵一鸣',1,2,1,11,1,11,13),(111,'古籍版本鉴赏',2,115,200,'李明杰',3,2,1,11,1,11,13),(112,'舞踏表演艺术审美体验',2,12,80,'刘丹丽',4,2,1,11,1,3,5),(113,'国际关系视角下的国际法',3,7,125,'周晓明',1,2,1,12,1,11,13),(114,'人文地理学与社会生活',2,77,99,'吴有江',3,3,1,11,1,1,3),(115,'数字媒体技术基础',2,76,100,'刘全乡',1,3,1,11,1,7,8),(116,'世界文明史',5,5,150,'潘迎春',3,3,1,11,1,6,8),(117,'国际关系与新闻传播',3,17,130,'林杰',3,4,1,11,1,11,13),(118,'社交礼仪',2,14,10,'李帅男',3,4,1,11,1,8,10),(119,'女性健康管理',1,14,150,'程燕翔',4,4,1,7,1,11,13),(120,'跨文化交际',2,4,30,'金林',3,4,2,12,1,11,13);
/*!40000 ALTER TABLE `course` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sqlite_sequence`
--

DROP TABLE IF EXISTS `sqlite_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sqlite_sequence` (
  `name` varchar(6) DEFAULT NULL,
  `seq` smallint(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sqlite_sequence`
--

LOCK TABLES `sqlite_sequence` WRITE;
/*!40000 ALTER TABLE `sqlite_sequence` DISABLE KEYS */;
INSERT INTO `sqlite_sequence` VALUES ('course',120);
/*!40000 ALTER TABLE `sqlite_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stu_cou`
--

DROP TABLE IF EXISTS `stu_cou`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `stu_cou` (
  `sid` varchar(0) DEFAULT NULL,
  `cid` varchar(0) DEFAULT NULL,
  `task1` varchar(0) DEFAULT NULL,
  `task2` varchar(0) DEFAULT NULL,
  `task3` varchar(0) DEFAULT NULL,
  `final` varchar(0) DEFAULT NULL,
  `total` varchar(0) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stu_cou`
--

LOCK TABLES `stu_cou` WRITE;
/*!40000 ALTER TABLE `stu_cou` DISABLE KEYS */;
/*!40000 ALTER TABLE `stu_cou` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `student` (
  `name` varchar(0) DEFAULT NULL,
  `sid` varchar(0) DEFAULT NULL,
  `sex` varchar(0) DEFAULT NULL,
  `school` varchar(0) DEFAULT NULL,
  `major` varchar(0) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `teacher`
--

DROP TABLE IF EXISTS `teacher`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teacher` (
  `name` varchar(0) DEFAULT NULL,
  `tid` varchar(0) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teacher`
--

LOCK TABLES `teacher` WRITE;
/*!40000 ALTER TABLE `teacher` DISABLE KEYS */;
/*!40000 ALTER TABLE `teacher` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-08-22 15:26:15
