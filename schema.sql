-- ============================================================
-- Script de creacion de base de datos para FoodSystem
-- Motor: MySQL 8.x
-- Ejecutar como: mysql -u root -p < schema.sql
-- ============================================================

CREATE DATABASE IF NOT EXISTS foodsystem
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE foodsystem;

-- Tabla de clientes
CREATE TABLE IF NOT EXISTS clientes (
    id        INT          PRIMARY KEY,
    nombre    VARCHAR(100) NOT NULL,
    telefono  VARCHAR(20),
    direccion VARCHAR(200)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabla de productos de comida rapida
CREATE TABLE IF NOT EXISTS comidas_rapidas (
    id           INT            PRIMARY KEY,
    nombre       VARCHAR(100)   NOT NULL,
    ingredientes TEXT,
    precio       DECIMAL(10,2)  NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;