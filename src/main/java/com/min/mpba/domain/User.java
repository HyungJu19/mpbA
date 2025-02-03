package com.min.mpba.domain;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private Long id;                 // 자동 증가 기본 키 (AUTO_INCREMENT)
    private String username;               // 고유 식별자 (필수)
    private String password;         // 비밀번호 (일반 회원가입 사용자만 사용)
    private String naverId;          // 네이버 OAuth 사용자 ID (NULL 가능)
    private String persName;         // 사용자 이름 (필수)
    private String email;            // 이메일 (필수, UNIQUE)
    private String birth;         // 생년월일
    private String phone;         // 핸드폰번호
    private String carrier;        // 통신사
    private String profileImage;     // 프로필 이미지 URL
    private String age;              // 연령대
    private String agreement;        // 약관
    private Gender gender;           // 성별 (ENUM)
    private AuthType authType;       // 로그인 유형 (ENUM)
    private String accessToken;      // OAuth 액세스 토큰 (NULL 가능)
    private String refreshToken;     // OAuth 리프레시 토큰 (NULL 가능)
    private LocalDateTime createdAt; // 계정 생성 시간
    private LocalDateTime updatedAt; // 계정 수정 시간
    private LocalDateTime lastLogin; // 최근 로그인 시간
    private LocalDateTime deletedAt; // 계정 삭제 시간 (NULL이면 활성 상태)

    // ENUM 타입 정의
    public enum Gender {
        M, F, U
    }

    public enum AuthType {
        LOCAL, NAVER
    }
}