package com.min.mpba.controller;

import com.min.mpba.domain.User;
import com.min.mpba.domain.UserLoginRequest;
import com.min.mpba.exception.UserAlreadyExistsException;
import com.min.mpba.service.UserService;
import com.min.mpba.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    // ✅ 로그인 (Access Token + Refresh Token 반환)
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequest request) {
        System.out.println("🟢 [Login] 요청 수신: " + request);

        if (request == null || request.getUsername() == null || request.getPassword() == null) {
            System.out.println("❌ [Login] 요청 데이터가 null입니다.");
            return ResponseEntity.status(400).body("로그인 실패: 요청 데이터가 올바르지 않습니다.");
        }

        String username = request.getUsername();
        String password = request.getPassword();
        System.out.println("🟢 [Login] 입력된 ID: " + username);

        // ✅ 사용자 인증 시도
        String accessToken = userService.authenticateUser(username, password);

        if (accessToken != null) {
            System.out.println("✅ [Login] 사용자 인증 성공 - Access Token 발급됨: " + accessToken);

            userService.lastLogin(username);

            // ✅ Refresh Token 생성 및 저장
            String refreshToken = jwtUtil.generateRefreshToken(username);
            System.out.println("🟢 [Login] 생성된 Refresh Token: " + refreshToken);

            // ✅ Access Token이 유효한지 즉시 검증
            try {
                String extractedUserId = jwtUtil.validateToken(accessToken);
                System.out.println("🟢 [Login] Access Token 검증 성공 - User ID: " + extractedUserId);
            } catch (JwtException e) {
                System.out.println("❌ [Login] Access Token 검증 실패: " + e.getMessage());
                return ResponseEntity.status(500).body("서버 오류: 생성된 토큰이 유효하지 않습니다.");
            }

            userService.updateRefreshToken(username, refreshToken); // DB에 저장
            userService.updateAccessToken(username, accessToken); // DB에 저장

            // ✅ Access Token & Refresh Token 반환
            Map<String, String> tokens = new HashMap<>();
            tokens.put("accessToken", accessToken);
            tokens.put("refreshToken", refreshToken);

            return ResponseEntity.ok(tokens);
        } else {
            System.out.println("❌ [Login] 사용자 인증 실패 - 아이디 또는 비밀번호가 올바르지 않음");
            return ResponseEntity.status(401).body("로그인 실패: 아이디 또는 비밀번호가 올바르지 않습니다.");
        }
    }



    @GetMapping("/me")
    public ResponseEntity<?> getUserInfo(@RequestHeader("Authorization") String token) {
        String jwt = token.replace("Bearer ", "");

        try {
            String userId = jwtUtil.extractUsername(jwt);
            if (userId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 토큰입니다.");
            }

            User user = userService.getUserById(userId);
            userService.lastLogin(userId);
            return ResponseEntity.ok(user);

        } catch (ExpiredJwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 만료되었습니다.");
        } catch (JwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 토큰입니다.");
        }
    }



    // ✅ 회원가입
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {

        System.out.println(user);
        try {
            userService.registerUser(user);
            return ResponseEntity.ok("회원가입 성공");
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원가입 중 서버 오류 발생");
        }
    }

    // ✅ 로그아웃 (Refresh Token 삭제)
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String token) {
        String jwt = token.replace("Bearer ", "");
        String userId = jwtUtil.extractUsername(jwt);

        if (userId != null) {
            userService.deleteRefreshToken(userId); // `person` 테이블에서 Refresh Token 삭제
            return ResponseEntity.ok("로그아웃 성공: Refresh Token이 삭제되었습니다.");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 토큰");
    }

    // ✅ Refresh Token을 이용한 Access Token 재발급
    @PostMapping("/refresh")
    public ResponseEntity<Map<String, String>> refreshAccessToken(@RequestParam String refreshToken) {
        Optional<User> userOptional = userService.findByRefreshToken(refreshToken);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // ✅ Refresh Token이 만료되지 않았는지 확인
            if (user.getRefreshToken().equals(refreshToken)) {
                String newAccessToken = jwtUtil.generateAccessToken(user.getUsername());

                Map<String, String> tokens = new HashMap<>();
                tokens.put("accessToken", newAccessToken);
                return ResponseEntity.ok(tokens);
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid or expired refresh token"));
    }
}
