package com.example.library.repository;

import com.example.library.entity.AuthorEntity;
import com.example.library.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<BookEntity, Long> {

    List<BookEntity> findAllByOrderByIdAsc();

    boolean existsByAuthor(AuthorEntity author);

    @Query("select b from BookEntity b "
            + "where lower(b.title) like :pattern or lower(b.author.name) like :pattern "
            + "order by b.id")
    List<BookEntity> search(@Param("pattern") String pattern);
}
