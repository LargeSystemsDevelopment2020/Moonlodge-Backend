-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema moonlodge
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `moonlodge` ;

-- -----------------------------------------------------
-- Schema moonlodge
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `moonlodge` ;
USE `moonlodge` ;

-- -----------------------------------------------------
-- Table `moonlodge`.`head_quarter`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `moonlodge`.`head_quarter` ;

CREATE TABLE IF NOT EXISTS `moonlodge`.`head_quarter` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `country_code` VARCHAR(3) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `moonlodge`.`hotel`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `moonlodge`.`hotel` ;

CREATE TABLE IF NOT EXISTS `moonlodge`.`hotel` (
  `id` VARCHAR(6) NOT NULL,
  `name` VARCHAR(45) NULL,
  `address` VARCHAR(45) NULL,
  `city` VARCHAR(45) NULL,
  `distance_to_center` BIGINT(20) NULL,
  `raiting` BIGINT(20) NULL,
  `head_quarter_id` INT NOT NULL,
  PRIMARY KEY (`id`, `head_quarter_id`),
  INDEX `fk_hotel_head_quarter1_idx` (`head_quarter_id` ASC),
  CONSTRAINT `fk_hotel_head_quarter1`
    FOREIGN KEY (`head_quarter_id`)
    REFERENCES `moonlodge`.`head_quarter` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `moonlodge`.`room`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `moonlodge`.`room` ;

CREATE TABLE IF NOT EXISTS `moonlodge`.`room` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `max_capacity` INT NULL,
  `price` DOUBLE NULL,
  `type` VARCHAR(1) NULL,
  `hotel_id` VARCHAR(6) NOT NULL,
  PRIMARY KEY (`id`, `hotel_id`),
  INDEX `fk_room_hotel1_idx` (`hotel_id` ASC),
  CONSTRAINT `fk_room_hotel1`
    FOREIGN KEY (`hotel_id`)
    REFERENCES `moonlodge`.`hotel` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `moonlodge`.`room_booking`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `moonlodge`.`room_booking` ;

CREATE TABLE IF NOT EXISTS `moonlodge`.`room_booking` (
  `date_of_arrival` FLOAT NOT NULL,
  `date_of_departure` FLOAT NOT NULL,
  `room_id` INT NOT NULL,
  `booking_id` INT NOT NULL,
  PRIMARY KEY (`room_id`, `booking_id`),
  INDEX `fk_room_booking_booking1_idx` (`booking_id` ASC),
  CONSTRAINT `fk_room_booking_room1`
    FOREIGN KEY (`room_id`)
    REFERENCES `moonlodge`.`room` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_room_booking_booking1`
    FOREIGN KEY (`booking_id`)
    REFERENCES `moonlodge`.`booking` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `moonlodge`.`booking`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `moonlodge`.`booking` ;

CREATE TABLE IF NOT EXISTS `moonlodge`.`booking` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `arrival_is_late` TINYINT(1) NOT NULL,
  `guest_passport_number` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id`, `guest_passport_number`),
  INDEX `fk_booking_guest1_idx` (`guest_passport_number` ASC),
  CONSTRAINT `fk_booking_guest1`
    FOREIGN KEY (`guest_passport_number`)
    REFERENCES `moonlodge`.`guest` (`passport_number`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `moonlodge`.`guest`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `moonlodge`.`guest` ;

CREATE TABLE IF NOT EXISTS `moonlodge`.`guest` (
  `passport_number` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`passport_number`))
ENGINE = InnoDB;








SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
