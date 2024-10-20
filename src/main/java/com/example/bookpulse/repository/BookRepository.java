package com.example.bookpulse.repository;

import com.example.bookpulse.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<BookEntity, Long> {

    // 신작 도서만 조회
    List<BookEntity> findByIsNewBookTrue();

    // 베스트셀러 도서만 조회
    List<BookEntity> findByIsBestsellerTrue();

    @Query("SELECT b FROM BookEntity b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(b.author) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(b.publisher) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<BookEntity> searchBooks(@Param("query") String query);
}