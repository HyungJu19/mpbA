package com.min.mpba.service;

import com.min.mpba.domain.User;
import com.min.mpba.exception.UserAlreadyExistsException;
import com.min.mpba.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import com.min.mpba.util.JwtUtil;
import com.min.mpba.util.PasswordUtil;

import java.util.Optional;
import java.util.Set;
import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordUtil passwordUtil;
    private final JwtUtil jwtUtil;

    // ✅ 로그인 처리 및 JWT 발급
    public String authenticateUser(String id, String password) {
        User user = userRepository.loginUser(id);

        if (user != null && BCrypt.checkpw(password, user.getPassword())) {
            return jwtUtil.generateAccessToken(user.getId());
        } else {
            return null;
        }
    }

    // ✅ 회원가입
    public void registerUser(User user) {
        if (userRepository.existsById(user.getId()) > 0) {
            throw new UserAlreadyExistsException("이미 존재하는 아이디입니다.");
        }
        if (userRepository.existsByEmail(user.getEmail()) > 0) {
            throw new UserAlreadyExistsException("이미 존재하는 이메일입니다.");
        }
        if (userRepository.existsByNameAndBirth(user.getPersName(), user.getBirth()) > 0) {
            throw new UserAlreadyExistsException("동일한 명의의 회원이 이미 존재합니다.");
        }

        user.setPassword(passwordUtil.encryptBcrypt(user.getPassword()));
        userRepository.insertUser(user);
    }

    // ✅ 마지막 로그인 시간 업데이트
    public void lastLogin(String id) {
        userRepository.lastLogin(id);
    }

    // ✅ ID로 사용자 정보 조회
    public User getUserById(String id) {
        return userRepository.loginUser(id);
    }

    // ✅ 블랙리스트 토큰 관리
    private final Set<String> blacklist = new HashSet<>();

    public void blacklistToken(String token) {
        blacklist.add(token);
    }

    public boolean isTokenBlacklisted(String token) {
        return blacklist.contains(token);
    }

    // ✅ Refresh Token 저장
    public void updateRefreshToken(String userId, String refreshToken) {
        userRepository.updateRefreshToken(userId, refreshToken);
    }

    // ✅ Refresh Token 삭제 (로그아웃 시)
    public void deleteRefreshToken(String userId) {
        userRepository.updateRefreshToken(userId, null);
    }

    // ✅ Refresh Token으로 사용자 조회
    public Optional<User> findByRefreshToken(String refreshToken) {
        return userRepository.findByRefreshToken(refreshToken);
    }

    public void updateAccessToken(String userId, String accessToken) {
        userRepository.updateAccessToken(userId, accessToken);
    }
}
