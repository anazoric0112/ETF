CREATE DATABASE `CityHopper`
;

USE CityHopper;

CREATE TABLE `Administrator`
( 
	`idA`                integer  NOT NULL 
)
;

CREATE TABLE `Comment`
( 
	`idC`                integer  NOT NULL ,
	`comment`            varchar(255)  NOT NULL ,
	`idT`                integer  NOT NULL ,
	`idDr`               integer  NOT NULL ,
	`grade`              integer  NOT NULL
)
;

CREATE TABLE `Drive`
(
	`idDr`               integer  NOT NULL ,
	`DateTime`           datetime  NOT NULL ,
	`estDateTime`        datetime  DEFAULT NULL ,
	`status`             varchar(20)  NOT NULL DEFAULT 'UNFINISHED',
	`price`              float  NOT NULL ,
	`seats`              integer  NOT NULL ,
	`available`          integer  NOT NULL ,
	`idD`                integer  NOT NULL ,
	`idTo1`              integer  NOT NULL ,
	`idTo2`              integer  NOT NULL
)
;

CREATE TABLE `Driver`
(
	`idD`                integer  NOT NULL
)
;

CREATE TABLE `Moderator`
(
	`idM`                integer  NOT NULL
)
;

CREATE TABLE `Request`
(
	`idR`                integer  NOT NULL ,
	`idU`                integer  NOT NULL
)
;

CREATE TABLE `Rezervation`
(
	`idRez`              integer  NOT NULL ,
	`seats`              integer  NOT NULL ,
	`price`              float  NOT NULL ,
	`idT`                integer  NOT NULL ,
	`status`             varchar(20)  NOT NULL DEFAULT 'BOOKED' ,
	`idDr`               integer  NOT NULL
)
;

CREATE TABLE `Road`
(
	`idRo`               integer  NOT NULL ,
	`length`             integer  NOT NULL ,
	`idTo1`              integer  NOT NULL ,
	`idTo2`              integer  NOT NULL
)
;

CREATE TABLE `Town`
(
	`idTo`               integer  NOT NULL ,
	`Name`               varchar(40)  NOT NULL
)
;

CREATE TABLE `Traveller`
(
	`idT`                integer  NOT NULL
)
;

CREATE TABLE `User`
(
	`idU`                integer  NOT NULL ,
	`username`           varchar(40)  NOT NULL ,
	`password`           varchar(40)  NOT NULL ,
	`name`               varchar(25)  NOT NULL ,
	`surname`            varchar(25)  NOT NULL ,
	`email`              varchar(40)  NOT NULL ,
	`phone`              varchar(15)  NOT NULL ,
	`gender`             bit  NOT NULL ,
	`image`              binary  NULL
)
;

CREATE TABLE `ShortestPath`
(
	`idSP`               integer  NOT NULL ,
	`idTo1`              integer  NOT NULL ,
	`idTo2`              integer  NOT NULL ,
	`shortest_path`      integer  NOT NULL 
)
;

ALTER TABLE `Administrator`
	ADD CONSTRAINT `XPKAdministrator` PRIMARY KEY  CLUSTERED (`idA` ASC)
;

ALTER TABLE `Comment`
	ADD CONSTRAINT `XPKComment` PRIMARY KEY  CLUSTERED (`idC` ASC)
;

ALTER TABLE `Drive`
	ADD CONSTRAINT `XPKDrive` PRIMARY KEY  CLUSTERED (`idDr` ASC)
;

ALTER TABLE `Driver`
	ADD CONSTRAINT `XPKDriver` PRIMARY KEY  CLUSTERED (`idD` ASC)
;

ALTER TABLE `Moderator`
	ADD CONSTRAINT `XPKModerator` PRIMARY KEY  CLUSTERED (`idM` ASC)
;

ALTER TABLE `Request`
	ADD CONSTRAINT `XPKRequest` PRIMARY KEY  CLUSTERED (`idR` ASC)
;

ALTER TABLE `Rezervation`
	ADD CONSTRAINT `XPKRezervation` PRIMARY KEY  CLUSTERED (`idRez` ASC,`seats` ASC)
;

ALTER TABLE `Road`
	ADD CONSTRAINT `XPKRoad` PRIMARY KEY  CLUSTERED (`idRo` ASC)
;

ALTER TABLE `Town`
	ADD CONSTRAINT `XPKTown` PRIMARY KEY  CLUSTERED (`idTo` ASC)
;

ALTER TABLE `Traveller`
	ADD CONSTRAINT `XPKTraveller` PRIMARY KEY  CLUSTERED (`idT` ASC)
;

ALTER TABLE `User`
	ADD CONSTRAINT `XPKUser` PRIMARY KEY  CLUSTERED (`idU` ASC)
;

ALTER TABLE `ShortestPath`
	ADD CONSTRAINT `XPKShortestPath` PRIMARY KEY  CLUSTERED (`idSP` ASC)
;

ALTER TABLE `User` MODIFY COLUMN idU INT auto_increment;

ALTER TABLE `Drive` MODIFY COLUMN idDr INT auto_increment;

ALTER TABLE `Town` MODIFY COLUMN idTo INT auto_increment;

ALTER TABLE `Comment` MODIFY COLUMN idC INT auto_increment;

ALTER TABLE `Request` MODIFY COLUMN idR INT auto_increment;

ALTER TABLE `Rezervation` MODIFY COLUMN idRez INT auto_increment;

ALTER TABLE `Road` MODIFY COLUMN idRo INT auto_increment;


ALTER TABLE `Administrator`
	ADD CONSTRAINT `R_2` FOREIGN KEY (`idA`) REFERENCES `User`(`idU`)
		ON DELETE CASCADE
		ON UPDATE CASCADE
;

ALTER TABLE `Comment`
	ADD CONSTRAINT `R_14` FOREIGN KEY (`idT`) REFERENCES `Traveller`(`idT`)
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
;

ALTER TABLE `Comment`
	ADD CONSTRAINT `R_15` FOREIGN KEY (`idDr`) REFERENCES `Drive`(`idDr`)
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
;


ALTER TABLE `Drive`
	ADD CONSTRAINT `R_9` FOREIGN KEY (`idD`) REFERENCES `Driver`(`idD`)
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
;

ALTER TABLE `Drive`
	ADD CONSTRAINT `R_10` FOREIGN KEY (`idTo1`) REFERENCES `Town`(`idTo`)
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
;

ALTER TABLE `Drive`
	ADD CONSTRAINT `R_11` FOREIGN KEY (`idTo2`) REFERENCES `Town`(`idTo`)
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
;


ALTER TABLE `Driver`
	ADD CONSTRAINT `R_4` FOREIGN KEY (`idD`) REFERENCES `User`(`idU`)
		ON DELETE CASCADE
		ON UPDATE CASCADE
;


ALTER TABLE `Moderator`
	ADD CONSTRAINT `R_3` FOREIGN KEY (`idM`) REFERENCES `User`(`idU`)
		ON DELETE CASCADE
		ON UPDATE CASCADE
;


ALTER TABLE `Request`
	ADD CONSTRAINT `R_6` FOREIGN KEY (`idU`) REFERENCES `User`(`idU`)
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
;


ALTER TABLE `Rezervation`
	ADD CONSTRAINT `R_13` FOREIGN KEY (`idT`) REFERENCES `Traveller`(`idT`)
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
;

ALTER TABLE `Rezervation`
	ADD CONSTRAINT `R_17` FOREIGN KEY (`idDr`) REFERENCES `Drive`(`idDr`)
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
;


ALTER TABLE `Road`
	ADD CONSTRAINT `R_7` FOREIGN KEY (`idTo1`) REFERENCES `Town`(`idTo`)
		ON DELETE CASCADE
		ON UPDATE CASCADE
;

ALTER TABLE `Road`
	ADD CONSTRAINT `R_8` FOREIGN KEY (`idTo2`) REFERENCES `Town`(`idTo`)
		ON DELETE CASCADE
		ON UPDATE CASCADE
;

ALTER TABLE `Traveller`
	ADD CONSTRAINT `R_5` FOREIGN KEY (`idT`) REFERENCES `User`(`idU`)
		ON DELETE CASCADE
		ON UPDATE CASCADE
;

ALTER TABLE `ShortestPath`
	ADD CONSTRAINT `R_18` FOREIGN KEY (`idTo1`) REFERENCES `Town`(`idTo`)
		ON DELETE CASCADE
		ON UPDATE CASCADE
;

ALTER TABLE `ShortestPath`
	ADD CONSTRAINT `R_19` FOREIGN KEY (`idTo2`) REFERENCES `Town`(`idTo`)
		ON DELETE CASCADE
		ON UPDATE CASCADE
;