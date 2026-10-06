-- =====================================================================
-- Шаг 2. Создание таблиц, ключей и начальных данных (MySQL 8)
-- Выполнить в MySQL Workbench под root
-- либо в консоли:  mysql -u root -p < 02_create_tables.sql
-- =====================================================================

USE library_db;

-- Для повторного запуска скрипта: сначала удаляем таблицы (порядок важен из-за внешних ключей)
DROP TABLE IF EXISTS books;
DROP TABLE IF EXISTS authors;
DROP TABLE IF EXISTS genres;

-- ---------------------------------------------------------------------
-- Таблица жанров (справочник)
-- ---------------------------------------------------------------------
CREATE TABLE genres (
    id   BIGINT      NOT NULL AUTO_INCREMENT,       -- автоинкремент
    name VARCHAR(50) NOT NULL,
    CONSTRAINT pk_genres      PRIMARY KEY (id),     -- первичный ключ
    CONSTRAINT uq_genres_name UNIQUE (name)         -- названия жанров не повторяются
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

-- ---------------------------------------------------------------------
-- Таблица авторов
-- ---------------------------------------------------------------------
CREATE TABLE authors (
    id   BIGINT      NOT NULL AUTO_INCREMENT,
    name VARCHAR(60) NOT NULL,
    CONSTRAINT pk_authors      PRIMARY KEY (id),
    CONSTRAINT uq_authors_name UNIQUE (name)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

-- ---------------------------------------------------------------------
-- Таблица книг (связана с авторами и жанрами внешними ключами)
-- ---------------------------------------------------------------------
CREATE TABLE books (
    id               BIGINT       NOT NULL AUTO_INCREMENT,
    title            VARCHAR(100) NOT NULL,
    author_id        BIGINT       NOT NULL,
    genre_id         BIGINT       NOT NULL,
    publication_year INT          NOT NULL,
    description      VARCHAR(500) NULL,             -- необязательное поле (может быть NULL)

    CONSTRAINT pk_books PRIMARY KEY (id),

    -- Индексы по внешним ключам ускоряют соединения (JOIN) и поиск книг автора / жанра
    INDEX idx_books_author_id (author_id),
    INDEX idx_books_genre_id  (genre_id),

    -- Внешний ключ: книга обязана ссылаться на существующего автора.
    -- RESTRICT: нельзя удалить автора, у которого есть книги.
    CONSTRAINT fk_books_author FOREIGN KEY (author_id)
        REFERENCES authors (id) ON DELETE RESTRICT,

    -- Внешний ключ: книга обязана ссылаться на существующий жанр.
    CONSTRAINT fk_books_genre FOREIGN KEY (genre_id)
        REFERENCES genres (id) ON DELETE RESTRICT,

    -- Правило проверки (то же, что @Min/@Max в Java-коде).
    -- CHECK работает в MySQL 8.0.16 и новее.
    CONSTRAINT ck_books_year CHECK (publication_year BETWEEN 1450 AND 2100)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

-- ---------------------------------------------------------------------
-- Начальные данные
-- ---------------------------------------------------------------------
INSERT INTO genres (name) VALUES
    ('Роман'),
    ('Повесть'),
    ('Фантастика'),
    ('Детектив'),
    ('Поэзия'),
    ('Научная литература');

INSERT INTO authors (name) VALUES
    ('Михаил Булгаков'),
    ('Фёдор Достоевский'),
    ('Лев Толстой');

INSERT INTO books (title, author_id, genre_id, publication_year, description) VALUES
    ('Мастер и Маргарита',
        (SELECT id FROM authors WHERE name = 'Михаил Булгаков'),
        (SELECT id FROM genres  WHERE name = 'Роман'),
        1967, 'Роман о визите дьявола в Москву 1930-х годов.'),
    ('Преступление и наказание',
        (SELECT id FROM authors WHERE name = 'Фёдор Достоевский'),
        (SELECT id FROM genres  WHERE name = 'Роман'),
        1866, 'Роман о студенте Раскольникове и цене его теории.'),
    ('Война и мир',
        (SELECT id FROM authors WHERE name = 'Лев Толстой'),
        (SELECT id FROM genres  WHERE name = 'Роман'),
        1869, 'Роман-эпопея о русском обществе эпохи наполеоновских войн.'),
    ('Идиот',
        (SELECT id FROM authors WHERE name = 'Фёдор Достоевский'),
        (SELECT id FROM genres  WHERE name = 'Роман'),
        1869, NULL);
