package com.example.bookpulse.service;

import com.example.bookpulse.dto.MemberDTO;
import com.example.bookpulse.entity.MemberEntity;
import com.example.bookpulse.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtService jwtService;

    // 이메일을 통해 MemberEntity를 찾고, MemberDTO로 변환하여 반환
    public MemberDTO findByEmail(String email) {
        MemberEntity memberEntity = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("해당 이메일의 사용자가 없습니다."));
        return MemberDTO.toMemberDTO(memberEntity);
    }

    // 회원 저장
    public void save(MemberDTO memberDTO) {
        MemberEntity memberEntity = MemberEntity.toMemberEntity(memberDTO);
        memberRepository.save(memberEntity);
    }

    public String registerMember(MemberDTO memberDTO) {
        // 이메일 중복 확인
        Optional<MemberEntity> existingMember = memberRepository.findByEmail(memberDTO.getEmail());
        if (existingMember.isPresent()) {
            return "이미 사용 중인 이메일입니다.";
        }

        // 비밀번호 8자리 이상 확인
        if (memberDTO.getPassword().length() < 8) {
            return "비밀번호는 8자리 이상이어야 합니다.";
        }

        MemberEntity member = MemberEntity.builder()
                .name(memberDTO.getName())
                .email(memberDTO.getEmail())
                .password(memberDTO.getPassword())
                .phoneNumber(memberDTO.getPhoneNumber())
                .build();

        memberRepository.save(member);

        // JWT 토큰 생성 후 반환
        String token = jwtService.getToken("id", member.getId());
        return token;
    }

    // 로그인 처리
    public String login(String email, String password) {
        Optional<MemberEntity> memberOptional = memberRepository.findByEmail(email);

        if (memberOptional.isPresent()) {
            MemberEntity member = memberOptional.get();

            // 비밀번호를 평문 그대로 비교
            if (password.equals(member.getPassword())) {
                // JWT 토큰 생성 시 이메일 추가
                return jwtService.getToken("email", member.getEmail());
            }
        }
        return null; // 로그인 실패 시
    }
    public boolean checkEmailDuplicate(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("유효하지 않은 이메일입니다.");
        }
        return memberRepository.findByEmail(email).isPresent();
    }

    public boolean validateToken(String token) {
        try {
            // JwtService를 사용해 토큰의 유효성을 확인
            return jwtService.isValid(token);
        } catch (Exception e) {
            // 예외 발생 시 토큰이 유효하지 않다고 판단
            return false;
        }
    }
}