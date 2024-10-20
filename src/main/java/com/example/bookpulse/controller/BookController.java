package com.example.bookpulse.controller;

import com.example.bookpulse.dto.BookDTO;
import com.example.bookpulse.dto.MemberDTO;
import com.example.bookpulse.entity.BookEntity;
import com.example.bookpulse.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    // 전체 도서 목록
    @GetMapping("/books")
    public String getAllBooks(Model model) {
        List<BookEntity> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        return "books";
    }

    // 신작 도서 전체 목록 표시
    @GetMapping("/books/newbook")
    public String getNewBooks(Model model) {
        List<BookEntity> newBooks = bookService.getNewBooks();
        model.addAttribute("newBooks", newBooks);
        return "newbook";
    }

    // 베스트셀러 전체 목록 표시
    @GetMapping("/books/bestseller")
    public String getBestsellers(Model model) {
        List<BookEntity> bestsellers = bookService.getBestsellers();
        model.addAttribute("bestsellers", bestsellers);
        return "bestseller";
    }

    // 도서 검색
    @GetMapping("/books/search")
    public String searchBooks(@RequestParam("query") String query, Model model) {
        List<BookDTO> books = bookService.searchBooks(query);
        model.addAttribute("books", books);
        model.addAttribute("query", query);
        return "search";
    }
}