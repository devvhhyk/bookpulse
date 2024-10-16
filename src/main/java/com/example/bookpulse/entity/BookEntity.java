package com.example.bookpulse.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor       // 기본 생성자 생성
@AllArgsConstructor      // 모든 필드를 포함한 생성자 생성
@Builder                // 빌더 패턴 적용
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                     // 고유 식별자

    @Column(nullable = false, length = 255)  // 제목은 필수, 최대 길이 255자
    private String title;

    @Column(nullable = false, length = 255)  // 저자는 필수, 최대 길이 255자
    private String author;

    @Column(nullable = false)  // 가격은 필수
    private int price;

    @Column(length = 500)  // 이미지 URL 최대 길이 500자
    private String imageUrl;

    @Column(columnDefinition = "TEXT")  // 도서 정보는 TEXT 타입으로 설정
    private String description;

    @Column(length = 255)  // 출판사 정보는 최대 길이 255자
    private String publisher;

    @Column(nullable = false)  // 최신 도서 여부는 필수
    private boolean isNewBook;

    @Column(nullable = false)  // 베스트셀러 여부는 필수
    private boolean isBestseller;
}
