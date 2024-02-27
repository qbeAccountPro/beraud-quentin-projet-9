-- Active: 1695052083510@@127.0.0.1@3306
CREATE DATABASE IF NOT EXISTS patientmanager;

USE patientmanager;

CREATE TABLE IF NOT EXISTS 
	patient (
		id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
		first_name VARCHAR(30) NOT NULL,
		last_name VARCHAR(30) NOT NULL,
		gender VARCHAR(1) NOT NULL,
		date_of_birth VARCHAR(10) NOT NULL,
		address VARCHAR(250),
		phone VARCHAR(10));

/*CREATE TABLE
	user (
		Id tinyint(4) NOT NULL AUTO_INCREMENT,
		username VARCHAR(125),
		password VARCHAR(125),
		fullname VARCHAR(125),
		role VARCHAR(125),
		PRIMARY KEY (Id));*/