package com.example.bookpulse.controller;

import com.example.bookpulse.dto.MemberDTO;
import com.example.bookpulse.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // 회원가입 페이지
    @GetMapping("/member/signup")
    public String signupForm(Model model) {
        model.addAttribute("member", new MemberDTO());
        return "signup";
    }

    // 회원가입 처리
    @PostMapping("/member/signup")
    public String signup(@ModelAttribute MemberDTO memberDTO) {
        memberService.save(memberDTO);
        return "login";
    }

    // 이메일 중복 확인
    @GetMapping("/member/checkEmail")
    public ResponseEntity<Boolean> checkEmailDuplicate(@RequestParam("email") String email) {
        boolean isDuplicate = memberService.checkEmailDuplicate(email);
        return ResponseEntity.ok(isDuplicate);
    }

    // 로그인 페이지
    @GetMapping("/member/login")
    public String loginForm(Model model) {
        model.addAttribute("member", new MemberDTO());
        return "login";
    }

    // 로그인 처리
    @PostMapping("/member/login")
    public String login(@ModelAttribute MemberDTO memberDTO, Model model) {
        String token = memberService.login(memberDTO.getEmail(), memberDTO.getPassword());

        if (token != null) {
            model.addAttribute("token", token);
            return "index"; // 로그인 성공 후 리다이렉트
        } else {
            model.addAttribute("error", "잘못된 이메일 또는 비밀번호입니다.");
            return "login"; // 로그인 실패 시 다시 로그인 페이지로
        }
    }
}