package com.example.bookpulse.controller;

import com.example.bookpulse.entity.BookEntity;
import com.example.bookpulse.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final BookService bookService;

    // 홈 화면에서 신작 도서와 베스트셀러 도서 일부 표시
    @GetMapping("/")
    public String showHomePage(Model model) {
        List<BookEntity> newBooks = bookService.getNewBooks().subList(0, Math.min(5, bookService.getNewBooks().size()));
        List<BookEntity> bestsellers = bookService.getBestsellers().subList(0, Math.min(5, bookService.getBestsellers().size()));
        model.addAttribute("newBooks", newBooks);   // 신작 도서 데이터 전달
        model.addAttribute("bestsellers", bestsellers);  // 베스트셀러 데이터 전달
        return "index";
    }
}
