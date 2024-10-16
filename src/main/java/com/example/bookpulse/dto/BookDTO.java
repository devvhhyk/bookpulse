package com.example.bookpulse.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDTO {
    private Long id;                     // 고유 식별자
    private String title;                // 책 제목
    private String author;               // 저자
    private int price;            // 가격
    private String imageUrl;             // 책 이미지 URL
    private String description;          // 도서 정보
    private String publisher;            // 출판사
    private boolean isNewBook;        // 신작 도서 여부
    private boolean isBestseller;        // 베스트셀러 여부
}
