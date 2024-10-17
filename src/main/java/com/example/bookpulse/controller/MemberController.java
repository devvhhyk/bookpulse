package com.example.bookpulse.controller;

import com.example.bookpulse.dto.MemberDTO;
import com.example.bookpulse.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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
    @PostMapping(value = "/member/signup", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public Map<String, Object> signup(@RequestBody MemberDTO memberDTO) {
        Map<String, Object> response = new HashMap<>();
        try {
            memberService.save(memberDTO);
            response.put("success", true);
            response.put("message", "회원가입이 완료되었습니다.");
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "회원가입 중 오류가 발생했습니다.");
        }
        return response;
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
    @PostMapping(value = "/member/login", produces = "application/json")
    @ResponseBody
    public Map<String, Object> login(@RequestBody MemberDTO memberDTO) {
        Map<String, Object> response = new HashMap<>();

        String token = memberService.login(memberDTO.getEmail(), memberDTO.getPassword());

        if (token != null) {
            response.put("success", true);
            response.put("token", token);
            response.put("message", "로그인에 성공하였습니다.");
        } else {
            response.put("success", false);
            response.put("message", "잘못된 이메일 또는 비밀번호입니다.");
        }

        return response; // JSON 응답
    }

}