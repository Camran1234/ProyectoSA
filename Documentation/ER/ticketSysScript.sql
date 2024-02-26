-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema ticketDB
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `ticketDB` ;

-- -----------------------------------------------------
-- Schema ticketDB
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `ticketDB` DEFAULT CHARACTER SET utf8 ;
USE `ticketDB` ;

-- -----------------------------------------------------
-- Table `ticketDB`.`State_of_Ticket`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ticketDB`.`State_of_Ticket` (
  `idState` INT NOT NULL AUTO_INCREMENT,
  `state` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idState`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ticketDB`.`TicketType`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ticketDB`.`TicketType` (
  `idTicketProblem` INT NOT NULL AUTO_INCREMENT,
  `type` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idTicketProblem`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ticketDB`.`TicketPriority`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ticketDB`.`TicketPriority` (
  `idTicketPriority` INT NOT NULL AUTO_INCREMENT,
  `priority` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`idTicketPriority`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ticketDB`.`Ticket`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ticketDB`.`Ticket` (
  `ticketNumber` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(250) NOT NULL,
  `lastName` VARCHAR(250) NOT NULL,
  `phone` VARCHAR(30) NOT NULL,
  `description` TEXT NOT NULL,
  `ticketType` INT NOT NULL,
  `priority` INT NOT NULL,
  PRIMARY KEY (`ticketNumber`),
  INDEX `fx_Ticket_TicketProblem_ticketType_idx` (`ticketType` ASC) VISIBLE,
  INDEX `fx_Ticket_TicketPriority_priority_idx` (`priority` ASC) VISIBLE,
  CONSTRAINT `fx_Ticket_TicketProblem_ticketType`
    FOREIGN KEY (`ticketType`)
    REFERENCES `ticketDB`.`TicketType` (`idTicketProblem`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fx_Ticket_TicketPriority_priority`
    FOREIGN KEY (`priority`)
    REFERENCES `ticketDB`.`TicketPriority` (`idTicketPriority`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ticketDB`.`UserType`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ticketDB`.`UserType` (
  `idUserType` INT NOT NULL AUTO_INCREMENT,
  `type` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idUserType`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ticketDB`.`User`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ticketDB`.`User` (
  `idUser` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(200) NOT NULL,
  `lastName` VARCHAR(200) NOT NULL,
  `phone` VARCHAR(30) NOT NULL,
  `userType` INT NOT NULL,
  PRIMARY KEY (`idUser`),
  INDEX `fx_User_UserType_userTpe_idx` (`userType` ASC) VISIBLE,
  CONSTRAINT `fx_User_UserType_userTpe`
    FOREIGN KEY (`userType`)
    REFERENCES `ticketDB`.`UserType` (`idUserType`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ticketDB`.`TicketTracking`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ticketDB`.`TicketTracking` (
  `ticketNumber` INT NOT NULL,
  `state` INT NOT NULL,
  `prioridad` INT NOT NULL,
  `dateofCreation` DATE NOT NULL,
  `dateLastUpdate` DATE NULL,
  `agent` INT NULL,
  `problemSolved` TINYINT NOT NULL,
  PRIMARY KEY (`ticketNumber`),
  INDEX `fx_Ticket_State_of_Ticket_state_idx` (`state` ASC) VISIBLE,
  INDEX `fx_TicketTracking_TicketPriority_priority_idx` (`prioridad` ASC) VISIBLE,
  INDEX `fx_TicketTracking_User_agent_idx` (`agent` ASC) VISIBLE,
  CONSTRAINT `fx_TicketTracking_State_of_Ticket_state`
    FOREIGN KEY (`state`)
    REFERENCES `ticketDB`.`State_of_Ticket` (`idState`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fx_TicketTracking_Tiket_ticketNumber`
    FOREIGN KEY (`ticketNumber`)
    REFERENCES `ticketDB`.`Ticket` (`ticketNumber`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fx_TicketTracking_TicketPriority_priority`
    FOREIGN KEY (`prioridad`)
    REFERENCES `ticketDB`.`TicketPriority` (`idTicketPriority`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fx_TicketTracking_User_agent`
    FOREIGN KEY (`agent`)
    REFERENCES `ticketDB`.`User` (`idUser`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ticketDB`.`History_of_Communication`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ticketDB`.`History_of_Communication` (
  `idHistory` INT NOT NULL AUTO_INCREMENT,
  `ticketNumber` INT NOT NULL,
  `dateTimeContacted` DATETIME NOT NULL,
  `sent` TEXT NOT NULL,
  `recieved` TEXT NOT NULL,
  PRIMARY KEY (`idHistory`, `ticketNumber`),
  INDEX `fx_History_of_Communication_Ticket_ticketNumber_idx` (`ticketNumber` ASC) VISIBLE,
  CONSTRAINT `fx_History_of_Communication_TicketTracking_ticketNumber`
    FOREIGN KEY (`ticketNumber`)
    REFERENCES `ticketDB`.`TicketTracking` (`ticketNumber`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ticketDB`.`TicketElement`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ticketDB`.`TicketElement` (
  `idTicketElement` INT NOT NULL AUTO_INCREMENT,
  `ticketNumber` INT NOT NULL,
  `url` TEXT NOT NULL,
  PRIMARY KEY (`idTicketElement`, `ticketNumber`),
  INDEX `fx_TicketElement_Ticket_ticketNumber_idx` (`ticketNumber` ASC) VISIBLE,
  CONSTRAINT `fx_TicketElement_Ticket_ticketNumber`
    FOREIGN KEY (`ticketNumber`)
    REFERENCES `ticketDB`.`Ticket` (`ticketNumber`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `ticketDB`.`State_of_Ticket`
-- -----------------------------------------------------
START TRANSACTION;
USE `ticketDB`;
INSERT INTO `ticketDB`.`State_of_Ticket` (`idState`, `state`) VALUES (1, 'nuevo');
INSERT INTO `ticketDB`.`State_of_Ticket` (`idState`, `state`) VALUES (2, 'en curso');
INSERT INTO `ticketDB`.`State_of_Ticket` (`idState`, `state`) VALUES (3, 'resuelto');
INSERT INTO `ticketDB`.`State_of_Ticket` (`idState`, `state`) VALUES (4, 'cerrado');

COMMIT;


-- -----------------------------------------------------
-- Data for table `ticketDB`.`TicketType`
-- -----------------------------------------------------
START TRANSACTION;
USE `ticketDB`;
INSERT INTO `ticketDB`.`TicketType` (`idTicketProblem`, `type`) VALUES (1, 'tecnico');
INSERT INTO `ticketDB`.`TicketType` (`idTicketProblem`, `type`) VALUES (2, 'facturacion');
INSERT INTO `ticketDB`.`TicketType` (`idTicketProblem`, `type`) VALUES (3, 'atencion al cliente');

COMMIT;


-- -----------------------------------------------------
-- Data for table `ticketDB`.`TicketPriority`
-- -----------------------------------------------------
START TRANSACTION;
USE `ticketDB`;
INSERT INTO `ticketDB`.`TicketPriority` (`idTicketPriority`, `priority`) VALUES (1, 'alta');
INSERT INTO `ticketDB`.`TicketPriority` (`idTicketPriority`, `priority`) VALUES (2, 'media');
INSERT INTO `ticketDB`.`TicketPriority` (`idTicketPriority`, `priority`) VALUES (3, 'baja');

COMMIT;


-- -----------------------------------------------------
-- Data for table `ticketDB`.`UserType`
-- -----------------------------------------------------
START TRANSACTION;
USE `ticketDB`;
INSERT INTO `ticketDB`.`UserType` (`idUserType`, `type`) VALUES (1, 'cliente');
INSERT INTO `ticketDB`.`UserType` (`idUserType`, `type`) VALUES (2, 'agente');
INSERT INTO `ticketDB`.`UserType` (`idUserType`, `type`) VALUES (3, 'administrador');

COMMIT;

