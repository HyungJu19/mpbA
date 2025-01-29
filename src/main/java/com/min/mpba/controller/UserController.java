package com.min.mpba.controller;

import com.min.mpba.domain.User;
import com.min.mpba.domain.UserLoginRequest;
import com.min.mpba.exception.UserAlreadyExistsException;
import com.min.mpba.service.UserService;
import com.min.mpba.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
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
        System.out.println(request + "'???'");

        String userId = request.getId();
        String accessToken = userService.authenticateUser(userId, request.getPassword());

        if (accessToken != null) {
            userService.lastLogin(userId);

            // ✅ Refresh Token 생성 및 `person` 테이블에 저장
            String refreshToken = jwtUtil.generateRefreshToken(userId);
            userService.updateRefreshToken(userId, refreshToken); // DB에 저장
            userService.updateAccessToken(userId, accessToken);
            // ✅ Access Token & Refresh Token 반환
            Map<String, String> tokens = new HashMap<>();
            tokens.put("accessToken", accessToken);
            tokens.put("refreshToken", refreshToken);
            return ResponseEntity.ok(tokens);
        } else {
            return ResponseEntity.status(401).body("로그인 실패: 아이디 또는 비밀번호가 올바르지 않습니다.");
        }
    }

    // ✅ 로그인한 사용자 정보 조회
    @GetMapping("/me")
    public ResponseEntity<User> getUserInfo(@RequestHeader("Authorization") String token) {
        String jwt = token.replace("Bearer ", "");
        System.out.println(jwt);
        System.out.println(token);
        String userId = jwtUtil.extractUsername(jwt);
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User user = userService.getUserById(userId);
        userService.lastLogin(userId);
        return ResponseEntity.ok(user);
    }

    // ✅ 회원가입
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
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
                String newAccessToken = jwtUtil.generateAccessToken(user.getId());

                Map<String, String> tokens = new HashMap<>();
                tokens.put("accessToken", newAccessToken);
                return ResponseEntity.ok(tokens);
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid or expired refresh token"));
    }
}
