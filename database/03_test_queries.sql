-- =====================================================================
-- Шаг 3 (необязательно). Проверочные запросы - удобно для скриншотов в отчёт
-- =====================================================================

USE library_db;

-- Все книги вместе с автором и жанром (соединение трёх таблиц)
SELECT b.id, b.title, a.name AS author, g.name AS genre, b.publication_year
FROM books b
JOIN authors a ON a.id = b.author_id
JOIN genres  g ON g.id = b.genre_id
ORDER BY b.id;

-- Сколько книг у каждого автора
SELECT a.name AS author, COUNT(b.id) AS books_count
FROM authors a
LEFT JOIN books b ON b.author_id = a.id
GROUP BY a.name
ORDER BY a.name;

-- Сколько книг в каждом жанре
SELECT g.name AS genre, COUNT(b.id) AS books_count
FROM genres g
LEFT JOIN books b ON b.genre_id = g.id
GROUP BY g.name
ORDER BY g.name;

-- Проверка ограничений (каждый запрос должен завершиться ОШИБКОЙ):
-- INSERT INTO books (title, author_id, genre_id, publication_year) VALUES ('Тест', 999, 1, 2000);   -- нет такого автора (FK)
-- INSERT INTO books (title, author_id, genre_id, publication_year) VALUES ('Тест', 1, 1, 1000);     -- год вне 1450..2100 (CHECK)
-- INSERT INTO genres (name) VALUES ('Роман');                                                         -- дубликат (UNIQUE)
-- DELETE FROM authors WHERE name = 'Лев Толстой';                                                     -- у автора есть книги (RESTRICT)
