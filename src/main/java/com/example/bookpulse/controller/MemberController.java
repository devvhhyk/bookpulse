package com.example.bookpulse.controller;

import com.example.bookpulse.dto.MemberDTO;
import com.example.bookpulse.service.MemberService;
import jakarta.servlet.http.HttpSession;
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

    @PostMapping(value = "/member/login", produces = "application/json")
    @ResponseBody
    public Map<String, Object> login(@RequestBody MemberDTO memberDTO, HttpSession session) {
        Map<String, Object> response = new HashMap<>();

        String token = memberService.login(memberDTO.getEmail(), memberDTO.getPassword());

        if (token != null) {
            session.setAttribute("user", memberDTO.getEmail());
            response.put("success", true);
            response.put("token", token);
            response.put("message", "로그인이 완료되었습니다.");
        } else {
            response.put("success", false);
            response.put("message", "잘못된 이메일 또는 비밀번호입니다.");
        }

        return response;
    }

    // 로그아웃
    @GetMapping("/member/logout")
    public String logoutForm(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }

    // 로그아웃 처리
    @PostMapping("/member/logout")
    @ResponseBody
    public Map<String, Object> logout(HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        try {
            session.invalidate();  // 세션 무효화
            response.put("success", true);
            response.put("message", "로그아웃이 완료되었습니다.");
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "로그아웃 중 오류가 발생했습니다.");
        }
        return response;
    }

    // 마이페이지 접근 (로그인 상태에서만 접근 가능)
    @GetMapping("/member/mypage")
    public String myPage(HttpSession session, Model model) {
        String email = (String) session.getAttribute("user");
        if (email != null) {
            MemberDTO member = memberService.findByEmail(email);
            model.addAttribute("member", member);
            return "mypage";
        } else {
            return "redirect:/member/login";
        }
    }

    // 토큰 유효성 검증
    @PostMapping(value = "/member/validateToken", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public Map<String, Object> validateToken(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        String token = request.get("token");
        boolean isValid = memberService.validateToken(token); // 토큰 유효성 검증 로직
        response.put("valid", isValid);
        return response;
    }
}