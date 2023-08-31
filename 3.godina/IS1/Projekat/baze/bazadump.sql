CREATE DATABASE  IF NOT EXISTS `artikli` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `artikli`;
-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: artikli
-- ------------------------------------------------------
-- Server version	8.0.31

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `artikal`
--

DROP TABLE IF EXISTS `artikal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `artikal` (
  `idA` int NOT NULL AUTO_INCREMENT,
  `naziv` varchar(45) NOT NULL,
  `opis` varchar(45) NOT NULL,
  `cena` int NOT NULL,
  `popust` int NOT NULL,
  `idKat` int NOT NULL,
  PRIMARY KEY (`idA`),
  UNIQUE KEY `idA_UNIQUE` (`idA`),
  KEY `FK_idKat_artikli_idx` (`idKat`),
  CONSTRAINT `FK_idKat_artikli` FOREIGN KEY (`idKat`) REFERENCES `kategorija` (`idKat`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `artikal`
--

LOCK TABLES `artikal` WRITE;
/*!40000 ALTER TABLE `artikal` DISABLE KEYS */;
INSERT INTO `artikal` VALUES (8,'banana','voce',20,5,4),(10,'jabuka','crvena',40,50,8),(11,'kruska','zuta',20,0,9),(12,'grozdje','bobice',70,3,9),(13,'sljiva','ljubicasta',55,0,9),(14,'sir','suplji',301,50,12);
/*!40000 ALTER TABLE `artikal` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `artikal_AFTER_INSERT` AFTER INSERT ON `artikal` FOR EACH ROW BEGIN
	insert into narudzbine.artikal (idA,naziv,opis,cena,popust,idKat)
    values (new.idA, new.naziv, new.opis, new.cena, new.popust, new.idKat);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `artikal_AFTER_UPDATE` AFTER UPDATE ON `artikal` FOR EACH ROW BEGIN
	UPDATE narudzbine.artikal 
    SET naziv=new.naziv,opis=new.opis,cena=new.cena,popust=new.popust,idKat=new.idKat
    WHERE narudzbine.artikal.idA = new.idA;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `artikal_AFTER_DELETE` AFTER DELETE ON `artikal` FOR EACH ROW BEGIN
	delete from narudzbine.artikal 
    where old.idA=idA;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `kategorija`
--

DROP TABLE IF EXISTS `kategorija`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `kategorija` (
  `idKat` int NOT NULL AUTO_INCREMENT,
  `naziv` varchar(45) NOT NULL,
  `potKat` int DEFAULT NULL,
  PRIMARY KEY (`idKat`),
  UNIQUE KEY `idKat_UNIQUE` (`idKat`),
  KEY `FK_potKat_kategorija_idx` (`potKat`),
  CONSTRAINT `FK_potKat_kategorija` FOREIGN KEY (`potKat`) REFERENCES `kategorija` (`idKat`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kategorija`
--

LOCK TABLES `kategorija` WRITE;
/*!40000 ALTER TABLE `kategorija` DISABLE KEYS */;
INSERT INTO `kategorija` VALUES (4,'k1',NULL),(7,'hrana',NULL),(8,'povrce',NULL),(9,'voce',7),(10,'zelenopovrce',8),(11,'mlecni proizvodi',NULL),(12,'sirevi',11);
/*!40000 ALTER TABLE `kategorija` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `kategorija_AFTER_INSERT` AFTER INSERT ON `kategorija` FOR EACH ROW BEGIN
	insert into narudzbine.kategorija(idKat,naziv,potKat)
    values (new.idKat,new.naziv,new.potKat);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `kategorija_AFTER_UPDATE` AFTER UPDATE ON `kategorija` FOR EACH ROW BEGIN
	update narudzbine.kategorija 
    set naziv=new.naziv,potKat=new.potKat
    where idKat=new.idKat;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `kategorija_AFTER_DELETE` AFTER DELETE ON `kategorija` FOR EACH ROW BEGIN
	delete from narudzbine.kategorija where old.idKat=idKat;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `korisnik`
--

DROP TABLE IF EXISTS `korisnik`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `korisnik` (
  `idK` int NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `ime` varchar(45) NOT NULL,
  `prezime` varchar(45) NOT NULL,
  `adresa` varchar(45) NOT NULL,
  `novac` int NOT NULL,
  PRIMARY KEY (`idK`),
  UNIQUE KEY `idK_UNIQUE` (`idK`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `korisnik`
--

LOCK TABLES `korisnik` WRITE;
/*!40000 ALTER TABLE `korisnik` DISABLE KEYS */;
INSERT INTO `korisnik` VALUES (1,'marko','p123','marko','ilic','bulevar 3',100),(2,'anaz','123','ana','zoric','tomeros6',900),(3,'fcubric','123','f','c','mirkamilojkovica',300),(5,'milica','pwpw','milica','ilic','ul1',100),(6,'anazzz','123','ana','zor','tomerosandica',1200),(7,'pavler','pw123','pavle','rebic','tome rosandica 12',2000),(8,'user2001','2001','ana','ilic','ulica 1/100',780);
/*!40000 ALTER TABLE `korisnik` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `korpa`
--

DROP TABLE IF EXISTS `korpa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `korpa` (
  `idKorpa` int NOT NULL AUTO_INCREMENT,
  `idK` int NOT NULL,
  `cena` int NOT NULL,
  PRIMARY KEY (`idKorpa`),
  UNIQUE KEY `idKorpa_UNIQUE` (`idKorpa`),
  KEY `FK_idK_korpa_idx` (`idK`),
  CONSTRAINT `FK_idK_korpa` FOREIGN KEY (`idK`) REFERENCES `korisnik` (`idK`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `korpa`
--

LOCK TABLES `korpa` WRITE;
/*!40000 ALTER TABLE `korpa` DISABLE KEYS */;
INSERT INTO `korpa` VALUES (7,6,602),(8,7,130),(9,1,570);
/*!40000 ALTER TABLE `korpa` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `korpa_AFTER_INSERT` AFTER INSERT ON `korpa` FOR EACH ROW BEGIN
	insert into narudzbine.korpa(idKorpa,idK,cena)
    values (new.idKorpa,new.idK,new.cena);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `korpa_AFTER_UPDATE` AFTER UPDATE ON `korpa` FOR EACH ROW BEGIN
	update narudzbine.korpa 
    set idK=new.idK,cena=new.cena
    where idKorpa=new.idKorpa;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `prodaje`
--

DROP TABLE IF EXISTS `prodaje`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `prodaje` (
  `idK` int NOT NULL,
  `idA` int NOT NULL,
  `ocena` int DEFAULT NULL,
  `opis` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idK`,`idA`),
  KEY `FK_idA_prodaje_idx` (`idA`),
  CONSTRAINT `FK_idA_prodaje` FOREIGN KEY (`idA`) REFERENCES `artikal` (`idA`),
  CONSTRAINT `FK_idK_prodaje` FOREIGN KEY (`idK`) REFERENCES `korisnik` (`idK`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prodaje`
--

LOCK TABLES `prodaje` WRITE;
/*!40000 ALTER TABLE `prodaje` DISABLE KEYS */;
INSERT INTO `prodaje` VALUES (2,10,NULL,NULL),(3,11,NULL,NULL),(3,12,NULL,NULL),(3,13,NULL,NULL),(6,14,NULL,NULL),(8,8,NULL,NULL);
/*!40000 ALTER TABLE `prodaje` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `u_korpi`
--

DROP TABLE IF EXISTS `u_korpi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `u_korpi` (
  `idKorpa` int NOT NULL,
  `idA` int NOT NULL,
  `kolicina` int NOT NULL,
  `cena` int NOT NULL,
  PRIMARY KEY (`idKorpa`,`idA`),
  KEY `FK_idA_u_korpi_idx` (`idA`),
  CONSTRAINT `FK_idA_u_korpi` FOREIGN KEY (`idA`) REFERENCES `artikal` (`idA`),
  CONSTRAINT `FK_idKorpa_u_korpi` FOREIGN KEY (`idKorpa`) REFERENCES `korpa` (`idKorpa`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `u_korpi`
--

LOCK TABLES `u_korpi` WRITE;
/*!40000 ALTER TABLE `u_korpi` DISABLE KEYS */;
INSERT INTO `u_korpi` VALUES (7,14,2,301),(8,8,3,20),(8,12,1,70),(9,10,1,40),(9,12,6,70),(9,13,2,55);
/*!40000 ALTER TABLE `u_korpi` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `u_korpi_AFTER_INSERT` AFTER INSERT ON `u_korpi` FOR EACH ROW BEGIN
	insert into narudzbine.u_korpi(idKorpa,idA,kolicina,cena)
    values (new.idKorpa,new.idA,new.kolicina,new.cena);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `u_korpi_AFTER_UPDATE` AFTER UPDATE ON `u_korpi` FOR EACH ROW BEGIN
	update narudzbine.u_korpi
    set cena=new.cena, kolicina=new.kolicina
    where idKorpa=new.idKorpa and idA=new.idA;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-02-18  4:19:20
CREATE DATABASE  IF NOT EXISTS `narudzbine` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `narudzbine`;
-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: narudzbine
-- ------------------------------------------------------
-- Server version	8.0.31

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `artikal`
--

DROP TABLE IF EXISTS `artikal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `artikal` (
  `idA` int NOT NULL AUTO_INCREMENT,
  `naziv` varchar(45) NOT NULL,
  `opis` varchar(45) NOT NULL,
  `cena` int NOT NULL,
  `popust` int NOT NULL,
  `idKat` int NOT NULL,
  PRIMARY KEY (`idA`),
  UNIQUE KEY `idA_UNIQUE` (`idA`),
  KEY `FK_idKat_artikli_idx` (`idKat`),
  CONSTRAINT `FK_idKat_artikli` FOREIGN KEY (`idKat`) REFERENCES `kategorija` (`idKat`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `artikal`
--

LOCK TABLES `artikal` WRITE;
/*!40000 ALTER TABLE `artikal` DISABLE KEYS */;
INSERT INTO `artikal` VALUES (8,'banana','voce',20,5,4),(10,'jabuka','crvena',40,50,8),(11,'kruska','zuta',20,0,9),(12,'grozdje','bobice',70,3,9),(13,'sljiva','ljubicasta',55,0,9),(14,'sir','suplji',301,50,12);
/*!40000 ALTER TABLE `artikal` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `grad`
--

DROP TABLE IF EXISTS `grad`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `grad` (
  `idG` int NOT NULL AUTO_INCREMENT,
  `naziv` varchar(45) NOT NULL,
  PRIMARY KEY (`idG`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `grad`
--

LOCK TABLES `grad` WRITE;
/*!40000 ALTER TABLE `grad` DISABLE KEYS */;
INSERT INTO `grad` VALUES (6,'beograd'),(7,'Smederevo'),(8,'novisad'),(9,'backa palanka'),(10,'sabac'),(11,'pozarevac'),(12,'zlatibor');
/*!40000 ALTER TABLE `grad` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kategorija`
--

DROP TABLE IF EXISTS `kategorija`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `kategorija` (
  `idKat` int NOT NULL AUTO_INCREMENT,
  `naziv` varchar(45) NOT NULL,
  `potKat` int DEFAULT NULL,
  PRIMARY KEY (`idKat`),
  UNIQUE KEY `idKat_UNIQUE` (`idKat`),
  KEY `FK_potKat_kategorija_idx` (`potKat`),
  CONSTRAINT `FK_potKat_kategorija` FOREIGN KEY (`potKat`) REFERENCES `kategorija` (`idKat`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kategorija`
--

LOCK TABLES `kategorija` WRITE;
/*!40000 ALTER TABLE `kategorija` DISABLE KEYS */;
INSERT INTO `kategorija` VALUES (4,'k1',NULL),(7,'hrana',NULL),(8,'povrce',NULL),(9,'voce',7),(10,'zelenopovrce',8),(11,'mlecni proizvodi',NULL),(12,'sirevi',11);
/*!40000 ALTER TABLE `kategorija` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `korisnik`
--

DROP TABLE IF EXISTS `korisnik`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `korisnik` (
  `idK` int NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `ime` varchar(45) NOT NULL,
  `prezime` varchar(45) NOT NULL,
  `adresa` varchar(45) NOT NULL,
  `novac` int NOT NULL,
  PRIMARY KEY (`idK`),
  UNIQUE KEY `idK_UNIQUE` (`idK`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `korisnik`
--

LOCK TABLES `korisnik` WRITE;
/*!40000 ALTER TABLE `korisnik` DISABLE KEYS */;
INSERT INTO `korisnik` VALUES (1,'marko','p123','marko','ilic','bulevar 3',100),(2,'anaz','123','ana','zoric','tomeros6',900),(3,'fcubric','123','f','c','mirkamilojkovica',260),(5,'milica','pwpw','milica','ilic','ul1',100),(6,'anazzz','123','ana','zor','tomerosandica',1200),(7,'pavler','pw123','pavle','rebic','tome rosandica 12',2000),(8,'user2001','2001','ana','ilic','ulica 1/100',780);
/*!40000 ALTER TABLE `korisnik` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `korpa`
--

DROP TABLE IF EXISTS `korpa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `korpa` (
  `idKorpa` int NOT NULL AUTO_INCREMENT,
  `idK` int NOT NULL,
  `cena` int NOT NULL,
  PRIMARY KEY (`idKorpa`),
  UNIQUE KEY `idKorpa_UNIQUE` (`idKorpa`),
  KEY `FK_idK_korpa_idx` (`idK`),
  CONSTRAINT `FK_idK_korpa` FOREIGN KEY (`idK`) REFERENCES `korisnik` (`idK`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `korpa`
--

LOCK TABLES `korpa` WRITE;
/*!40000 ALTER TABLE `korpa` DISABLE KEYS */;
INSERT INTO `korpa` VALUES (7,6,602),(8,7,130),(9,1,570);
/*!40000 ALTER TABLE `korpa` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `korpa_AFTER_DELETE` AFTER DELETE ON `korpa` FOR EACH ROW BEGIN
	delete from artikli.korpa 
    where old.idKorpa=artikli.korpa.idKorpa;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `narudzbina`
--

DROP TABLE IF EXISTS `narudzbina`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `narudzbina` (
  `idN` int NOT NULL AUTO_INCREMENT,
  `cena` int NOT NULL,
  `idKorpa` int NOT NULL,
  `vreme` datetime NOT NULL,
  `adresa` varchar(45) NOT NULL,
  `idG` int NOT NULL,
  `idK` int NOT NULL,
  PRIMARY KEY (`idN`),
  UNIQUE KEY `idN_UNIQUE` (`idN`),
  KEY `FK_idG_narudzbina_idx` (`idG`),
  KEY `FK_idK_narudzbina_idx` (`idK`),
  CONSTRAINT `FK_idG_narudzbina` FOREIGN KEY (`idG`) REFERENCES `grad` (`idG`),
  CONSTRAINT `FK_idK_narudzbina` FOREIGN KEY (`idK`) REFERENCES `korisnik` (`idK`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `narudzbina`
--

LOCK TABLES `narudzbina` WRITE;
/*!40000 ALTER TABLE `narudzbina` DISABLE KEYS */;
INSERT INTO `narudzbina` VALUES (1,775,4,'2023-02-17 22:10:40','tomerosandica',6,6),(2,40,2,'2023-02-17 22:42:24','mirkamilojkovica',6,3),(3,700,3,'2023-02-17 23:01:28','ul1',6,5),(5,55,5,'2023-02-17 23:48:02','tomerosandica',6,6),(6,55,6,'2023-02-17 23:49:03','tomerosandica',6,6),(7,220,1,'2023-02-17 23:56:22','tomeros6',8,2);
/*!40000 ALTER TABLE `narudzbina` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stavka`
--

DROP TABLE IF EXISTS `stavka`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `stavka` (
  `idN` int NOT NULL,
  `idA` int NOT NULL,
  `kolicina` int NOT NULL,
  `cena` int NOT NULL,
  PRIMARY KEY (`idN`,`idA`),
  KEY `FK_idA_stavka_idx` (`idA`),
  CONSTRAINT `FK_idA_stavka` FOREIGN KEY (`idA`) REFERENCES `artikal` (`idA`),
  CONSTRAINT `FK_idN_stavka` FOREIGN KEY (`idN`) REFERENCES `narudzbina` (`idN`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stavka`
--

LOCK TABLES `stavka` WRITE;
/*!40000 ALTER TABLE `stavka` DISABLE KEYS */;
INSERT INTO `stavka` VALUES (1,13,3,55),(1,14,2,305),(2,8,2,20),(3,12,10,70),(5,13,1,55),(6,13,1,55),(7,10,5,40),(7,11,1,20);
/*!40000 ALTER TABLE `stavka` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transakcija`
--

DROP TABLE IF EXISTS `transakcija`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transakcija` (
  `idT` int NOT NULL AUTO_INCREMENT,
  `idN` int NOT NULL,
  `cena` int NOT NULL,
  `vreme` datetime NOT NULL,
  PRIMARY KEY (`idT`),
  UNIQUE KEY `idT_UNIQUE` (`idT`),
  UNIQUE KEY `idN_UNIQUE` (`idN`),
  CONSTRAINT `FK_idN_transakcija` FOREIGN KEY (`idN`) REFERENCES `narudzbina` (`idN`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transakcija`
--

LOCK TABLES `transakcija` WRITE;
/*!40000 ALTER TABLE `transakcija` DISABLE KEYS */;
INSERT INTO `transakcija` VALUES (1,1,775,'2023-02-17 22:10:40'),(2,2,40,'2023-02-17 22:42:24'),(3,3,700,'2023-02-17 23:01:28'),(4,5,55,'2023-02-17 23:48:02'),(5,6,55,'2023-02-17 23:49:03'),(6,7,220,'2023-02-17 23:56:22');
/*!40000 ALTER TABLE `transakcija` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `u_gradu`
--

DROP TABLE IF EXISTS `u_gradu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `u_gradu` (
  `idK` int NOT NULL,
  `idG` int NOT NULL,
  PRIMARY KEY (`idK`),
  KEY `FK_idG_u_gradu_idx` (`idG`),
  CONSTRAINT `FK_idG_u_gradu` FOREIGN KEY (`idG`) REFERENCES `grad` (`idG`) ON UPDATE CASCADE,
  CONSTRAINT `FK_idK_u_gradu` FOREIGN KEY (`idK`) REFERENCES `korisnik` (`idK`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `u_gradu`
--

LOCK TABLES `u_gradu` WRITE;
/*!40000 ALTER TABLE `u_gradu` DISABLE KEYS */;
INSERT INTO `u_gradu` VALUES (3,6),(5,6),(6,6),(1,7),(2,8),(7,10),(8,12);
/*!40000 ALTER TABLE `u_gradu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `u_korpi`
--

DROP TABLE IF EXISTS `u_korpi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `u_korpi` (
  `idKorpa` int NOT NULL,
  `idA` int NOT NULL,
  `kolicina` int NOT NULL,
  `cena` int NOT NULL,
  PRIMARY KEY (`idKorpa`,`idA`),
  KEY `FK_idA_u_korpi_idx` (`idA`),
  CONSTRAINT `FK_idA_u_korpi` FOREIGN KEY (`idA`) REFERENCES `artikal` (`idA`),
  CONSTRAINT `FK_idKorpa_u_korpi` FOREIGN KEY (`idKorpa`) REFERENCES `korpa` (`idKorpa`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `u_korpi`
--

LOCK TABLES `u_korpi` WRITE;
/*!40000 ALTER TABLE `u_korpi` DISABLE KEYS */;
INSERT INTO `u_korpi` VALUES (7,14,2,301),(8,8,3,20),(8,12,1,70),(9,10,1,40),(9,12,6,70),(9,13,2,55);
/*!40000 ALTER TABLE `u_korpi` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `u_korpi_AFTER_DELETE` AFTER DELETE ON `u_korpi` FOR EACH ROW BEGIN
	delete from artikli.u_korpi
    where old.idKorpa=artikli.u_korpi.idKorpa;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-02-18  4:19:20
CREATE DATABASE  IF NOT EXISTS `korisnici` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `korisnici`;
-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: korisnici
-- ------------------------------------------------------
-- Server version	8.0.31

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `grad`
--

DROP TABLE IF EXISTS `grad`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `grad` (
  `idG` int NOT NULL AUTO_INCREMENT,
  `naziv` varchar(45) NOT NULL,
  PRIMARY KEY (`idG`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `grad`
--

LOCK TABLES `grad` WRITE;
/*!40000 ALTER TABLE `grad` DISABLE KEYS */;
INSERT INTO `grad` VALUES (6,'beograd'),(7,'Smederevo'),(8,'novisad'),(9,'backa palanka'),(10,'sabac'),(11,'pozarevac'),(12,'zlatibor');
/*!40000 ALTER TABLE `grad` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `grad_BEFORE_INSERT` BEFORE INSERT ON `grad` FOR EACH ROW BEGIN
	insert into narudzbine.grad(idG,naziv)
    values (new.idG,new.naziv);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `grad_AFTER_UPDATE` AFTER UPDATE ON `grad` FOR EACH ROW BEGIN
	update narudzbine.grad
    set naziv=new.naziv
    where idG=new.idG;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `grad_AFTER_DELETE` AFTER DELETE ON `grad` FOR EACH ROW BEGIN
	delete from narudzbine.grad where idG=old.idG;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `korisnik`
--

DROP TABLE IF EXISTS `korisnik`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `korisnik` (
  `idK` int NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `ime` varchar(45) NOT NULL,
  `prezime` varchar(45) NOT NULL,
  `adresa` varchar(45) NOT NULL,
  `novac` int NOT NULL,
  PRIMARY KEY (`idK`),
  UNIQUE KEY `idK_UNIQUE` (`idK`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `korisnik`
--

LOCK TABLES `korisnik` WRITE;
/*!40000 ALTER TABLE `korisnik` DISABLE KEYS */;
INSERT INTO `korisnik` VALUES (1,'marko','p123','marko','ilic','bulevar 3',100),(2,'anaz','123','ana','zoric','tomeros6',900),(3,'fcubric','123','f','c','mirkamilojkovica',300),(5,'milica','pwpw','milica','ilic','ul1',100),(6,'anazzz','123','ana','zor','tomerosandica',1200),(7,'pavler','pw123','pavle','rebic','tome rosandica 12',2000),(8,'user2001','2001','ana','ilic','ulica 1/100',780);
/*!40000 ALTER TABLE `korisnik` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `korisnik_AFTER_INSERT` AFTER INSERT ON `korisnik` FOR EACH ROW BEGIN
	insert into artikli.korisnik(idK,username,password,ime,prezime,adresa,novac)
    values (new.idK,new.username,new.password,new.ime,new.prezime,new.adresa,new.novac);
    insert into narudzbine.korisnik(idK,username,password,ime,prezime,adresa,novac)
    values (new.idK,new.username,new.password,new.ime,new.prezime,new.adresa,new.novac);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `korisnik_AFTER_UPDATE` AFTER UPDATE ON `korisnik` FOR EACH ROW BEGIN
	update artikli.korisnik
    set ime=new.ime,prezime=new.prezime,username=new.username,password=new.password,adresa=new.adresa,novac=new.novac
    where idK=new.idK;
    update narudzbine.korisnik
    set ime=new.ime,prezime=new.prezime,username=new.username,password=new.password,adresa=new.adresa,novac=new.novac
    where idK=new.idK;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `korisnik_AFTER_DELETE` AFTER DELETE ON `korisnik` FOR EACH ROW BEGIN
	delete from artikli.korisnik where idK=old.idK;
    delete from narudzbine.korisnik where idK=old.idK;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `u_gradu`
--

DROP TABLE IF EXISTS `u_gradu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `u_gradu` (
  `idK` int NOT NULL,
  `idG` int NOT NULL,
  PRIMARY KEY (`idK`),
  KEY `FK_idG_u_gradu_idx` (`idG`),
  CONSTRAINT `FK_idG_u_gradu` FOREIGN KEY (`idG`) REFERENCES `grad` (`idG`) ON UPDATE CASCADE,
  CONSTRAINT `FK_idK_u_gradu` FOREIGN KEY (`idK`) REFERENCES `korisnik` (`idK`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `u_gradu`
--

LOCK TABLES `u_gradu` WRITE;
/*!40000 ALTER TABLE `u_gradu` DISABLE KEYS */;
INSERT INTO `u_gradu` VALUES (3,6),(5,6),(6,6),(1,7),(2,8),(7,10),(8,12);
/*!40000 ALTER TABLE `u_gradu` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `u_gradu_AFTER_INSERT` AFTER INSERT ON `u_gradu` FOR EACH ROW BEGIN
	insert into narudzbine.u_gradu(idK,idG)
    values (new.idK,new.idG);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `u_gradu_AFTER_UPDATE` AFTER UPDATE ON `u_gradu` FOR EACH ROW BEGIN
	update narudzbine.u_gradu
    set idG=new.idG
    where idK=new.idK;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `u_gradu_AFTER_DELETE` AFTER DELETE ON `u_gradu` FOR EACH ROW BEGIN
	delete from narudzbine.u_gradu 
    where idK=old.idK;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-02-18  4:19:20
