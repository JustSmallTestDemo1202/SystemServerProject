
CREATE DATABASE IF NOT EXISTS `manager` DEFAULT CHARACTER SET utf8  COLLATE utf8_general_ci;

GRANT ALL ON manager.* TO 'phoenixtest'@'127.0.0.%' IDENTIFIED BY 'acY5qmGKVcRs4nST';
GRANT ALL ON mysql.* TO 'phoenixtest'@'127.0.0.%' IDENTIFIED BY 'acY5qmGKVcRs4nST';

USE `manager`;

/*Table structure for table `thirdpartyuser` */
CREATE TABLE `thirdpartyuser` (
  `userPassportId` varchar(32) NOT NULL,
  `charId` int(11) DEFAULT NULL,
  `forbiddenTalkEndTime` datetime DEFAULT NULL,
  `forbiddenLoginEndTime` datetime DEFAULT NULL,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`userPassportId`),
  KEY `createtimeIdx` (`createtime`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Table structure for table `serverconfig` */
CREATE TABLE `serverconfig` (
  `serverId` int(11) NOT NULL,
  `serverName` varchar(50) NOT NULL,
  `dbHost` varchar(20) NOT NULL,
  `dbName` varchar(20) NOT NULL,
  `logdbHost` varchar(20) NOT NULL,
  `logdbName` varchar(20) NOT NULL,
  `host` varchar(20) NOT NULL,
  `externalHost` varchar(50) NOT NULL,
  `externalPort` int(11) NOT NULL,
  `managerPort` int(11) NOT NULL,
  `rpcPort` int(11) NOT NULL,
  `openTime` datetime NOT NULL,
  `jvmOption` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`serverId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

DROP PROCEDURE IF EXISTS `Get_Max_Char_Id`;

DELIMITER $$
CREATE PROCEDURE `Get_Max_Char_Id`(
    )
BEGIN
	SELECT MAX(charId) AS maxCharId
	FROM `thirdpartyuser`;
    END $$
DELIMITER ;

DROP PROCEDURE IF EXISTS `Get_Player_Info`;

DELIMITER $$
CREATE PROCEDURE `Get_Player_Info`(
	pUserPassportId VARCHAR(32)
    )
BEGIN
	SELECT charId,UNIX_TIMESTAMP(forbiddenTalkEndTime) AS forbiddenTalkEndTime,UNIX_TIMESTAMP(forbiddenLoginEndTime) AS forbiddenLoginEndTime
	FROM `thirdpartyuser`
	WHERE  `userPassportId` = `pUserPassportId`;
    END $$
DELIMITER ;

DROP PROCEDURE IF EXISTS `Insert_Player_Info`;

DELIMITER $$
CREATE PROCEDURE `Insert_Player_Info`(
	pUserPassportId VARCHAR(32),
	pCharId       INT(11)
    )
BEGIN
	INSERT INTO thirdpartyuser(`userPassportId`,`charId`) VALUES(`pUserPassportId`,pCharId);
    END $$
DELIMITER ;