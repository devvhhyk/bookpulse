package com.example.bookpulse.service;

import com.example.bookpulse.dto.BookDTO;
import com.example.bookpulse.dto.MemberDTO;
import com.example.bookpulse.entity.BookEntity;
import com.example.bookpulse.entity.MemberEntity;
import com.example.bookpulse.repository.BookRepository;
import com.example.bookpulse.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    // 모든 도서 조회 (모든 사용자가 접근 가능)
    public List<BookEntity> getAllBooks() {
        return bookRepository.findAll();
    }

    // 특정 ID로 도서 조회 (모든 사용자가 접근 가능)
    public BookEntity getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID를 가진 도서를 찾을 수 없습니다: " + id));
    }

    // 신작 도서 목록 조회 (모든 사용자가 접근 가능)
    public List<BookEntity> getNewBooks() {
        return bookRepository.findByIsNewBookTrue();
    }

    // 베스트셀러 도서 목록 조회 (모든 사용자가 접근 가능)
    public List<BookEntity> getBestsellers() {
        return bookRepository.findByIsBestsellerTrue();
    }

    // 제목, 저자, 출판사로 도서 검색
    public List<BookDTO> searchBooks(String query) {
        List<BookEntity> books = bookRepository.searchBooks(query);
        return books.stream()
                .map(BookDTO::fromEntity)
                .collect(Collectors.toList());
    }
}