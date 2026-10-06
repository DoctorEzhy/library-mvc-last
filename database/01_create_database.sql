-- =====================================================================
-- Шаг 1. Создание базы данных (MySQL 8)
-- Выполнить в MySQL Workbench (File -> New Query Tab) под пользователем root
-- либо в консоли:  mysql -u root -p < 01_create_database.sql
-- =====================================================================

-- utf8mb4 - полноценная поддержка UTF-8 (русские буквы, эмодзи)
-- utf8mb4_unicode_ci - сравнение строк без учёта регистра
CREATE DATABASE IF NOT EXISTS library_db
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;
