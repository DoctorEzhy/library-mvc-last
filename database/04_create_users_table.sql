-- =====================================================================
-- Шаг 4. Таблица пользователей для Spring Security (MySQL 8)
-- Выполнить в MySQL Workbench в базе library_db
-- Пользователи admin и user создаются автоматически при первом запуске
-- приложения (пароли сохраняются в виде BCrypt-хеша, а не открытым текстом).
-- =====================================================================

USE library_db;

DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id       BIGINT       NOT NULL AUTO_INCREMENT,
    username VARCHAR(50)  NOT NULL,                  -- логин
    password VARCHAR(100) NOT NULL,                  -- BCrypt-хеш пароля (60 символов)
    role     VARCHAR(20)  NOT NULL,                  -- ADMIN или USER

    CONSTRAINT pk_users          PRIMARY KEY (id),
    CONSTRAINT uq_users_username UNIQUE (username),  -- логины не повторяются
    CONSTRAINT ck_users_role     CHECK (role IN ('ADMIN', 'USER'))
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

-- После первого запуска приложения проверьте:
-- SELECT id, username, password, role FROM users;
