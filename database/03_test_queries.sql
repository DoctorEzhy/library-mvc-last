USE library_db;

SELECT b.id, b.title, a.name AS author, g.name AS genre, b.publication_year
FROM books b
JOIN authors a ON a.id = b.author_id
JOIN genres  g ON g.id = b.genre_id
ORDER BY b.id;

SELECT a.name AS author, COUNT(b.id) AS books_count
FROM authors a
LEFT JOIN books b ON b.author_id = a.id
GROUP BY a.name
ORDER BY a.name;

SELECT g.name AS genre, COUNT(b.id) AS books_count
FROM genres g
LEFT JOIN books b ON b.genre_id = g.id
GROUP BY g.name
ORDER BY g.name;
