CREATE DATABASE  IF NOT EXISTS `config_service` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `config_service`;
-- MySQL dump 10.13  Distrib 5.7.9, for linux-glibc2.5 (x86_64)
--
-- Host: localhost    Database: token_service
-- ------------------------------------------------------
-- Server version	5.6.30-0ubuntu0.15.10.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `cmxcompany`
--

DROP TABLE IF EXISTS `cmxcompany`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cmxcompany` (
  `companydid` int(11) NOT NULL AUTO_INCREMENT,
  `id` varchar(100) NOT NULL,
  `createdtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`companydid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

 

--
-- Table structure for table `tsttoken`
--

DROP TABLE IF EXISTS `cmxgroup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cmxgroup` (
  `groupdid` int(11) NOT NULL AUTO_INCREMENT,
  `companydid` int(11) NOT NULL,
  `id` varchar(100) NOT NULL,
  `description` varchar(100) NOT NULL,
  `isvalid` BIT(1) NOT NULL DEFAULT 1,
  `createdtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT `pk01cmxgroup` PRIMARY KEY (`groupdid`),
  CONSTRAINT `fk01groupdid` FOREIGN KEY (`companydid`) REFERENCES `cmxcompany` (`companydid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

-- 
-- Table structure for table `tsxwho`
--

DROP TABLE IF EXISTS `cmxprofile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cmxprofile` (
  `profiledid` int(11) NOT NULL AUTO_INCREMENT,
  `groupdid` int(11) NOT NULL ,
  `companydid` int(11) NOT NULL,
  `id` varchar(100) NOT NULL,
  `description` varchar(200) NOT NULL,
  `isvalid` BIT(1) NOT NULL DEFAULT 1,
  `createddate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT `pkcmxprofile` PRIMARY KEY (`profiledid`), 
  CONSTRAINT `fk01cmxprofile` FOREIGN KEY (`companydid`) REFERENCES `cmxcompany` (`companydid`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk02cmxprofile` FOREIGN KEY (`groupdid`) REFERENCES `cmxgroup` (`groupdid`) ON DELETE NO ACTION ON UPDATE NO ACTION 
  
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


DROP TABLE IF EXISTS `cmxattribute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cmxattribute` (
  `attributedid` int(11) NOT NULL AUTO_INCREMENT,
  `companydid` int(11) NOT NULL,
  `id` varchar(100) NOT NULL,
  `description` varchar(200) NOT NULL,
  `isvalid` BIT(1) NOT NULL DEFAULT 1,
  `createddate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT `pkcmxattribute` PRIMARY KEY (`attributedid`), 
  CONSTRAINT `fk01cmxattribute` FOREIGN KEY (`companydid`) REFERENCES `cmxcompany` (`companydid`) ON DELETE NO ACTION ON UPDATE NO ACTION
  
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


DROP TABLE IF EXISTS `cmxattributedistribution`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cmxattributedistribution` (
  `attributedistributiondid` int(11) NOT NULL AUTO_INCREMENT,
  `companydid` int(11) NOT NULL,
   `groupdid` int(11) NOT NULL,
   `attributedid` int(11) NOT NULL ,
  `isvalid` BIT(1) NOT NULL DEFAULT 1,
  `createddate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT `pkcmxattributedistribution` PRIMARY KEY (`attributedistributiondid`), 
  CONSTRAINT `fk01cmxattributedistribution` FOREIGN KEY (`companydid`) REFERENCES `cmxcompany` (`companydid`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk02cmxattributedistribution` FOREIGN KEY (`groupdid`) REFERENCES `cmxgroup` (`groupdid`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk03cmxattributedistribution` FOREIGN KEY (`attributedid`) REFERENCES `cmxattribute` (`attributedid`) ON DELETE NO ACTION ON UPDATE NO ACTION
  
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



DROP TABLE IF EXISTS `cmxattributevalue`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cmxattributevalue` (
  `attributevaluedid` int(11) NOT NULL AUTO_INCREMENT,
  `attributedistributiondid` int(11) NOT NULL  ,
  `attribVal` varchar(200) NOT NULL,
   `profiledid` int(11) NOT NULL,
  `isvalid` BIT(1) NOT NULL DEFAULT 1,
  `createddate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT `pkcmxattributevalue` PRIMARY KEY (`attributevaluedid`), 
  CONSTRAINT `fk01cmxattributevalue` FOREIGN KEY (`attributedistributiondid`) REFERENCES `cmxattributedistribution` (`attributedistributiondid`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk02cmxattributevalue` FOREIGN KEY (`profiledid`) REFERENCES `cmxprofile` (`profiledid`) ON DELETE NO ACTION ON UPDATE NO ACTION
  
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-06-23 10:18:38
