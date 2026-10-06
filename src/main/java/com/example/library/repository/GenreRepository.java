package com.example.library.repository;

import com.example.library.entity.GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GenreRepository extends JpaRepository<GenreEntity, Long> {

    List<GenreEntity> findAllByOrderByNameAsc();
}
