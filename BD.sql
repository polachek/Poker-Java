/*
SQLyog Community v13.0.1 (64 bit)
MySQL - 5.7.22-log : Database - obligatoriodda
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`obligatoriodda` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `obligatoriodda`;

/*Table structure for table `juegos` */

DROP TABLE IF EXISTS `juegos`;

CREATE TABLE `juegos` (
  `oid` int(11) NOT NULL,
  `fechaHoraInicio` datetime NOT NULL,
  `pozo` int(11) NOT NULL,
  `oid_ganador` int(11) NOT NULL,
  `cantidadManosJugadas` int(11) NOT NULL,
  `apuestaBase` int(11) NOT NULL,
  PRIMARY KEY (`oid`),
  KEY `i_oid_ganador` (`oid_ganador`),
  CONSTRAINT `i_oid_ganador` FOREIGN KEY (`oid_ganador`) REFERENCES `jugadores` (`oid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `juegos` */

insert  into `juegos`(`oid`,`fechaHoraInicio`,`pozo`,`oid_ganador`,`cantidadManosJugadas`,`apuestaBase`) values 
(169,'2018-07-06 06:44:27',300,8,0,100),
(173,'2018-07-06 09:43:21',300,8,0,100),
(177,'2018-07-06 10:35:31',300,8,0,100),
(181,'2018-07-07 04:29:44',600,8,1,100),
(185,'2018-07-07 04:36:33',600,8,1,100),
(189,'2018-07-07 04:38:30',1400,8,5,100),
(193,'2018-07-07 04:47:00',300,8,0,100),
(197,'2018-07-07 04:52:20',300,8,0,100),
(198,'2018-07-07 05:15:55',870,8,1,100),
(202,'2018-07-07 05:18:18',0,8,2,100),
(206,'2018-07-07 05:22:46',0,8,2,100),
(207,'2018-07-07 05:36:58',6270,6,7,100),
(211,'2018-07-07 05:48:05',0,6,2,100),
(215,'2018-07-07 05:55:34',0,6,2,100),
(219,'2018-07-07 06:04:11',0,6,2,100),
(223,'2018-07-07 06:11:54',0,6,1,100),
(227,'2018-07-07 06:14:32',600,6,1,100),
(231,'2018-07-07 06:22:54',600,6,1,100),
(235,'2018-07-07 06:35:38',600,6,1,100),
(239,'2018-07-07 06:38:39',4870,7,6,100),
(243,'2018-07-08 16:13:39',300,7,0,100),
(246,'2018-07-08 16:16:35',500,6,0,100),
(249,'2018-07-08 16:23:35',940,8,1,100),
(252,'2018-07-08 18:03:21',290,6,0,100),
(255,'2018-07-08 18:17:30',1080,6,2,100),
(258,'2018-07-08 18:27:34',940,6,1,100),
(261,'2018-07-08 19:17:04',900,6,2,100),
(264,'2018-07-08 20:47:23',200,8,0,100),
(267,'2018-07-09 01:19:35',3500,6,3,100),
(270,'2018-07-09 08:51:28',400,6,1,100),
(273,'2018-07-09 09:11:42',4330,8,10,100),
(276,'2018-07-09 11:04:15',200,8,1,100),
(279,'2018-07-09 11:08:46',600,7,1,100),
(282,'2018-07-09 11:09:45',3100,8,6,100),
(285,'2018-07-09 13:13:51',1000,8,2,100),
(288,'2018-07-09 13:15:59',1800,7,5,100),
(291,'2018-07-09 13:19:07',800,8,3,100),
(294,'2018-07-09 13:25:38',300,8,1,100),
(297,'2018-07-09 13:57:30',1380,8,3,100);

/*Table structure for table `jugadores` */

DROP TABLE IF EXISTS `jugadores`;

CREATE TABLE `jugadores` (
  `oid` int(11) NOT NULL,
  `nombreUsuario` varchar(50) NOT NULL,
  `saldo` int(11) NOT NULL,
  PRIMARY KEY (`nombreUsuario`),
  UNIQUE KEY `i_oid` (`oid`),
  CONSTRAINT `i_oid` FOREIGN KEY (`oid`) REFERENCES `usuarios` (`oid`),
  CONSTRAINT `i_usuario` FOREIGN KEY (`nombreUsuario`) REFERENCES `usuarios` (`nombreUsuario`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `jugadores` */

insert  into `jugadores`(`oid`,`nombreUsuario`,`saldo`) values 
(6,'1',1000),
(7,'2',1000),
(8,'3',1000),
(9,'4',40),
(2,'beto',1800),
(4,'cele',1000),
(1,'eldani',1500),
(3,'mary',1900),
(12,'Pepe',100),
(5,'Tony',3500);

/*Table structure for table `oid` */

DROP TABLE IF EXISTS `oid`;

CREATE TABLE `oid` (
  `valor` int(11) NOT NULL,
  PRIMARY KEY (`valor`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `oid` */

insert  into `oid`(`valor`) values 
(301);

/*Table structure for table `participaciones` */

DROP TABLE IF EXISTS `participaciones`;

CREATE TABLE `participaciones` (
  `oid` int(11) DEFAULT NULL,
  `oid_juego` int(11) NOT NULL,
  `oid_jugador` int(11) NOT NULL,
  `saldoInicial` int(11) NOT NULL,
  `saldoFinal` int(11) NOT NULL,
  `totalApostado` int(11) NOT NULL,
  PRIMARY KEY (`oid_juego`,`oid_jugador`),
  UNIQUE KEY `oid_part` (`oid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `participaciones` */

/*Table structure for table `usuarios` */

DROP TABLE IF EXISTS `usuarios`;

CREATE TABLE `usuarios` (
  `oid` int(11) DEFAULT NULL,
  `nombreUsuario` varchar(50) NOT NULL,
  `contrasena` varchar(50) NOT NULL,
  `nombreCompleto` varchar(50) NOT NULL,
  PRIMARY KEY (`nombreUsuario`),
  KEY `i_oid` (`oid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `usuarios` */

insert  into `usuarios`(`oid`,`nombreUsuario`,`contrasena`,`nombreCompleto`) values 
(6,'1','1','Rigoberta Menchu'),
(10,'11','1','Guillermo Polachek'),
(7,'2','1','Apolodoro de Puntaleste'),
(8,'3','1','Sara Sarasa'),
(9,'4','1','Don Sinplata'),
(2,'beto','1','Alberto Font'),
(4,'cele','1','Celeste Orona'),
(1,'eldani','1','Daniel Negreanu'),
(11,'Leo','1','Leonardo'),
(3,'mary','1','Maria Lampropulos'),
(12,'Pepe','1','Pepe Gavilan'),
(5,'Tony','1','Antonio Pacheco');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
