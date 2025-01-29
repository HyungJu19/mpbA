package com.min.mpba.service;


import com.min.mpba.domain.User;
import com.min.mpba.exception.UserAlreadyExistsException;
import com.min.mpba.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import com.min.mpba.util.JwtUtil; // ✅ 패키지 경로 수정
import com.min.mpba.util.PasswordUtil; // ✅ 패키지 경로 수정

import java.util.HashSet;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class UserService {


    private final UserRepository userRepository;
    private final PasswordUtil passwordUtil;
    private final JwtUtil jwtUtil;


    //로그인
    // ✅ 로그인 처리 및 JWT 발급
    public String authenticateUser(String id, String password) {
        User user = userRepository.loginUser(id);

        if (user != null && BCrypt.checkpw(password, user.getPassword())) {
            return jwtUtil.generateToken(user.getId()); // ✅ JWT 토큰 반환
        } else {
            return null; // 인증 실패
        }
    }

    // 회원가입
    public void registerUser(User user) {
        // 아이디 중복 검사 (숫자 반환)
        if (userRepository.existsById(user.getId()) > 0) {
            throw new UserAlreadyExistsException("이미 존재하는 아이디입니다.");
        }

        // 이메일 중복 검사 (숫자 반환)
        if (userRepository.existsByEmail(user.getEmail()) > 0) {
            throw new UserAlreadyExistsException("이미 존재하는 이메일입니다.");
        }

        // 동일한 이름 + 생년월일을 가진 유저가 존재하는지 검사 (숫자 반환)
        if (userRepository.existsByNameAndBirth(user.getPersName(), user.getBirth()) > 0) {

            throw new UserAlreadyExistsException("동일한 명의에 회원이 이미 존재합니다.");
        }

        // 비밀번호 Bcrypt 해싱 후 저장
        user.setPassword(passwordUtil.encryptBcrypt((user.getPassword())));

        // 사용자 저장
        userRepository.insertUser(user);
    }

    // ✅ ID로 사용자 정보 조회
    public User getUserById(String id) {
        return userRepository.loginUser(id);
    }


    //블랙리스트 토큰

    private final Set<String> blacklist = new HashSet<>();

    public void blacklistToken(String token) {
        blacklist.add(token);
    }

    public boolean isTokenBlacklisted(String token) {
        return blacklist.contains(token);
    }






}
