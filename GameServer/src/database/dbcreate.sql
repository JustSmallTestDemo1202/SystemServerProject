CREATE DATABASE IF NOT EXISTS `gameserver` DEFAULT CHARACTER SET utf8  COLLATE utf8_general_ci;

GRANT ALL ON gameserver.* TO 'phoenixtest'@'127.0.0.%' IDENTIFIED BY 'acY5qmGKVcRs4nST';
GRANT ALL ON mysql.* TO 'phoenixtest'@'127.0.0.%' IDENTIFIED BY 'acY5qmGKVcRs4nST';

USE `gameserver`;

/*
 * table character
 *
 */
CREATE TABLE `character` (
    `indexId` INT(11) NOT NULL AUTO_INCREMENT,    
    `charId` INT(11) NOT NULL,
    `charIndex` INT(11) NOT NULL,
    `charName` VARCHAR(36) NOT NULL,
    `charJob` INT(11) NOT NULL,   
    `charGender` INT(11) NOT NULL,
    `charLevel` INT(11) NOT NULL DEFAULT '1',     
    `charExp` INT(11) NOT NULL DEFAULT '0',
    `gold` INT(11) NOT NULL DEFAULT '0',    
    `silver` BIGINT(20) NOT NULL DEFAULT '0',
    `energy` INT(11) NOT NULL DEFAULT '0',    
    `level` INT(11) NOT NULL DEFAULT '1',    
    `vipLv` INT(11) NOT NULL DEFAULT '0',
    `vipExp` INT(11) NOT NULL DEFAULT '0',
    `totalOnlineTime` BIGINT(20) NOT NULL DEFAULT '0',
    `createtime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `leavetime` TIMESTAMP NULL DEFAULT NULL,
    PRIMARY KEY (`indexId`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

DROP PROCEDURE IF EXISTS `Get_Char_Num`;
DELIMITER $$
    CREATE PROCEDURE `Get_Char_Num`(
        pCharId	INT(11)
    )
    BEGIN
        SELECT COUNT(*) AS numOfChar
        FROM `character`
        WHERE `charId`=pCharId;
    END$$
DELIMITER ;

DROP PROCEDURE IF EXISTS `Get_Char_Brief`;
DELIMITER $$
    CREATE PROCEDURE `Get_Char_Brief`(
        pCharId	INT(11)
    )
    BEGIN
        SELECT `indexId`,`charId`,`charIndex`,`charName`,`charGender`,`charJob`,`charLevel`
        FROM `character`
        WHERE `charId`=pCharId;
    END$$
DELIMITER ;
