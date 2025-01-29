package com.min.mpba.controller;


import com.min.mpba.domain.User;
import com.min.mpba.domain.UserLoginRequest;
import com.min.mpba.exception.UserAlreadyExistsException;
import com.min.mpba.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.min.mpba.util.JwtUtil;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;

    private final JwtUtil jwtUtil;



    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequest request) {
        System.out.println(request + "'???'");

        String token = userService.authenticateUser(request.getId(), request.getPassword());

        if (token != null) {
            return ResponseEntity.ok(token); // ✅ JWT 토큰 반환
        } else {
            return ResponseEntity.status(401).body("로그인 실패: 아이디 또는 비밀번호가 올바르지 않습니다.");
        }
    }


    // ✅ 로그인한 사용자 정보 조회
    @GetMapping("/me")
    public ResponseEntity<User> getUserInfo(@RequestHeader("Authorization") String token) {
        // "Bearer " 제거 후 토큰만 추출
        String jwt = token.replace("Bearer ", "");

        // 토큰에서 사용자 ID 추출
        String userId = jwtUtil.extractUsername(jwt);
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // 사용자 정보 가져오기
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }


    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        try {
            userService.registerUser(user);

            return ResponseEntity.ok("회원가입 성공");
        } catch (UserAlreadyExistsException e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage()); // ❗ 백엔드에서 보낸 메시지 전달
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원가입 중 서버 오류 발생");
        }
    }


    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String token) {
        String jwt = token.replace("Bearer ", ""); // 토큰 앞 "Bearer " 제거
        userService.blacklistToken(jwt);
        return ResponseEntity.ok("로그아웃 성공: 토큰이 무효화되었습니다.");
    }





}
