package com.example.bookpulse.repository;

import com.example.bookpulse.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<BookEntity, Long> {

    // 신작 도서만 조회
    List<BookEntity> findByIsNewBookTrue();

    // 베스트셀러 도서만 조회
    List<BookEntity> findByIsBestsellerTrue();

    // 제목으로 책을 검색하는 메서드
    List<BookEntity> findByTitleContaining(String title);


}