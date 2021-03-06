-- MySQL Script generated by MySQL Workbench
-- Sun Jul 12 22:36:50 2020
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema company_db
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema company_db
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `company_db` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci ;
USE `company_db` ;

-- -----------------------------------------------------
-- Table `company_db`.`account_role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `company_db`.`account_role` (
  `id` BIGINT(100) NOT NULL AUTO_INCREMENT,
  `user_account_id` BIGINT(100) NOT NULL,
  `user_role_id` BIGINT(100) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_user_account_has_user_role_user_account1`
    FOREIGN KEY (`user_account_id`)
    REFERENCES `company_db`.`user_account` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_account_has_user_role_user_role1`
    FOREIGN KEY (`user_role_id`)
    REFERENCES `company_db`.`user_role` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_user_account_has_user_role_user_role1_idx` ON `company_db`.`account_role` (`user_role_id` ASC) VISIBLE;

CREATE INDEX `fk_user_account_has_user_role_user_account1_idx` ON `company_db`.`account_role` (`user_account_id` ASC) VISIBLE;

CREATE UNIQUE INDEX `id_UNIQUE` ON `company_db`.`account_role` (`id` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `company_db`.`contract`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `company_db`.`contract` (
  `id` BIGINT(100) NOT NULL AUTO_INCREMENT,
  `user_account_id` BIGINT(100) NOT NULL,
  `customer_id` BIGINT(100) NOT NULL,
  `product_id` BIGINT(100) NOT NULL,
  `contract_product_quantity` INT(9) NOT NULL DEFAULT 1,
  `contract_price` DECIMAL(12,2) NOT NULL,
  `contract_status` VARCHAR(100) NOT NULL,
  `contract_payment_date` DATETIME NOT NULL,
  `contract_completion_date` DATETIME NOT NULL,
  `progress_progress_id` BIGINT(100) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_contract_customer1`
    FOREIGN KEY (`customer_id`)
    REFERENCES `company_db`.`customer` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_order_progress1`
    FOREIGN KEY (`progress_progress_id`)
    REFERENCES `company_db`.`progress` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_contract_user_account1`
    FOREIGN KEY (`user_account_id`)
    REFERENCES `company_db`.`user_account` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_contract_product1`
    FOREIGN KEY (`product_id`)
    REFERENCES `company_db`.`product` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE UNIQUE INDEX `id_UNIQUE` ON `company_db`.`contract` (`id` ASC) VISIBLE;

CREATE INDEX `fk_contract_customer1_idx` ON `company_db`.`contract` (`customer_id` ASC) VISIBLE;

CREATE INDEX `fk_order_progress1_idx` ON `company_db`.`contract` (`progress_progress_id` ASC) VISIBLE;

CREATE INDEX `fk_contract_user_account1_idx` ON `company_db`.`contract` (`user_account_id` ASC) VISIBLE;

CREATE INDEX `fk_contract_product1_idx` ON `company_db`.`contract` (`product_id` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `company_db`.`customer`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `company_db`.`customer` (
  `id` BIGINT(100) NOT NULL AUTO_INCREMENT,
  `customer_name` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

CREATE UNIQUE INDEX `id_UNIQUE` ON `company_db`.`customer` (`id` ASC) VISIBLE;

CREATE UNIQUE INDEX `customer_name_UNIQUE` ON `company_db`.`customer` (`customer_name` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `company_db`.`material`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `company_db`.`material` (
  `id` BIGINT(100) NOT NULL AUTO_INCREMENT,
  `material_name` VARCHAR(100) NOT NULL,
  `material_cost` DECIMAL(12,2) NOT NULL,
  `material_delivery_time` DOUBLE(8,3) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

CREATE UNIQUE INDEX `id_UNIQUE` ON `company_db`.`material` (`id` ASC) VISIBLE;

CREATE UNIQUE INDEX `material_name_UNIQUE` ON `company_db`.`material` (`material_name` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `company_db`.`operation`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `company_db`.`operation` (
  `id` BIGINT(100) NOT NULL AUTO_INCREMENT,
  `operation_type` VARCHAR(100) NOT NULL,
  `operation_cost` DECIMAL(12,2) NOT NULL,
  `operation_time` DOUBLE(8,3) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

CREATE UNIQUE INDEX `id_UNIQUE` ON `company_db`.`operation` (`id` ASC) VISIBLE;

CREATE UNIQUE INDEX `process_name_UNIQUE` ON `company_db`.`operation` (`operation_type` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `company_db`.`operations_order`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `company_db`.`operations_order` (
  `id` BIGINT(100) NOT NULL AUTO_INCREMENT,
  `technological_process_id` BIGINT(100) NOT NULL,
  `operation_id` BIGINT(100) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_technological_process_has_process_technological_process1`
    FOREIGN KEY (`technological_process_id`)
    REFERENCES `company_db`.`technological_process` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_technological_process_has_process_process1`
    FOREIGN KEY (`operation_id`)
    REFERENCES `company_db`.`operation` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_technological_process_has_process_process1_idx` ON `company_db`.`operations_order` (`operation_id` ASC) VISIBLE;

CREATE INDEX `fk_technological_process_has_process_technological_process1_idx` ON `company_db`.`operations_order` (`technological_process_id` ASC) VISIBLE;

CREATE UNIQUE INDEX `id_UNIQUE` ON `company_db`.`operations_order` (`id` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `company_db`.`product`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `company_db`.`product` (
  `id` BIGINT(100) NOT NULL AUTO_INCREMENT,
  `product_name` VARCHAR(100) NOT NULL,
  `product_weight` DOUBLE(10,3) NOT NULL,
  `material_id` BIGINT(100) NOT NULL,
  `technological_process_id` BIGINT(100) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_product_material1`
    FOREIGN KEY (`material_id`)
    REFERENCES `company_db`.`material` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_product_technological_process1`
    FOREIGN KEY (`technological_process_id`)
    REFERENCES `company_db`.`technological_process` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE UNIQUE INDEX `id_UNIQUE` ON `company_db`.`product` (`id` ASC) VISIBLE;

CREATE INDEX `fk_product_material1_idx` ON `company_db`.`product` (`material_id` ASC) VISIBLE;

CREATE INDEX `fk_product_technological_process1_idx` ON `company_db`.`product` (`technological_process_id` ASC) VISIBLE;

CREATE UNIQUE INDEX `product_name_UNIQUE` ON `company_db`.`product` (`product_name` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `company_db`.`progress`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `company_db`.`progress` (
  `id` BIGINT(100) NOT NULL AUTO_INCREMENT,
  `progress_status` INT(3) NOT NULL DEFAULT 0,
  `progress_log` VARCHAR(200) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

CREATE UNIQUE INDEX `progress_id_UNIQUE` ON `company_db`.`progress` (`id` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `company_db`.`technological_process`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `company_db`.`technological_process` (
  `id` BIGINT(100) NOT NULL AUTO_INCREMENT,
  `technological_process_name` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

CREATE UNIQUE INDEX `id_UNIQUE` ON `company_db`.`technological_process` (`id` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `company_db`.`user_account`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `company_db`.`user_account` (
  `id` BIGINT(100) NOT NULL AUTO_INCREMENT,
  `user_name` VARCHAR(100) NOT NULL,
  `user_password` VARCHAR(100) NOT NULL,
  `user_email` VARCHAR(100) NOT NULL,
  `is_active` TINYINT NOT NULL DEFAULT 1,
  `wallet_id` BIGINT(100) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_user_account_wallet1`
    FOREIGN KEY (`wallet_id`)
    REFERENCES `company_db`.`wallet` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE UNIQUE INDEX `id_UNIQUE` ON `company_db`.`user_account` (`id` ASC) VISIBLE;

CREATE UNIQUE INDEX `user_name_UNIQUE` ON `company_db`.`user_account` (`user_name` ASC) VISIBLE;

CREATE UNIQUE INDEX `user_email_UNIQUE` ON `company_db`.`user_account` (`user_email` ASC) VISIBLE;

CREATE INDEX `fk_user_account_wallet1_idx` ON `company_db`.`user_account` (`wallet_id` ASC) VISIBLE;

CREATE UNIQUE INDEX `wallet_id_UNIQUE` ON `company_db`.`user_account` (`wallet_id` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `company_db`.`user_role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `company_db`.`user_role` (
  `id` BIGINT(100) NOT NULL AUTO_INCREMENT,
  `role_name` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

CREATE UNIQUE INDEX `user_id_UNIQUE` ON `company_db`.`user_role` (`id` ASC) VISIBLE;

CREATE UNIQUE INDEX `role_name_UNIQUE` ON `company_db`.`user_role` (`role_name` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `company_db`.`wallet`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `company_db`.`wallet` (
  `id` BIGINT(100) NOT NULL AUTO_INCREMENT,
  `wallet_balance` DECIMAL(13,2) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

CREATE UNIQUE INDEX `id_UNIQUE` ON `company_db`.`wallet` (`id` ASC) VISIBLE;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
