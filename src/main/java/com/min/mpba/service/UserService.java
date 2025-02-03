package com.min.mpba.service;

import com.min.mpba.domain.User;
import com.min.mpba.exception.UserAlreadyExistsException;
import com.min.mpba.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import com.min.mpba.util.JwtUtil;
import com.min.mpba.util.PasswordUtil;

import java.time.LocalDate;
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
    public String authenticateUser(String username, String password) {
        User user = userRepository.loginUser(username);

        if (user != null && BCrypt.checkpw(password, user.getPassword())) {
            return jwtUtil.generateAccessToken(user.getUsername());
        } else {
            return null;
        }
    }

    // ✅ 회원가입
    public void registerUser(User user) {
        // 중복 검사
        if (userRepository.existsById(user.getUsername()) > 0) {
            throw new UserAlreadyExistsException("이미 존재하는 아이디입니다.");
        }
        if (userRepository.existsByEmail(user.getEmail()) > 0) {
            throw new UserAlreadyExistsException("이미 존재하는 이메일입니다.");
        }
        if (userRepository.existsByNameAndBirth(user.getPersName(), user.getBirth()) > 0) {
            throw new UserAlreadyExistsException("동일한 명의의 회원이 이미 존재합니다.");
        }

        // ✅ 생년월일에서 나이 계산
        String age = String.valueOf(calculateAge(user.getBirth()));
        user.setAge(age);

        // ✅ NULL 값 처리 (프론트에서 값이 없는 경우)
        user.setEmail(user.getEmail() != null ? user.getEmail() : null);
        user.setPhone(user.getPhone() != null ? user.getPhone() : null);
        user.setCarrier(user.getCarrier() != null ? user.getCarrier() : null);
        user.setProfileImage(user.getProfileImage() != null ? user.getProfileImage() : null);
        user.setAccessToken(user.getAccessToken() != null ? user.getAccessToken() : null);
        user.setRefreshToken(user.getRefreshToken() != null ? user.getRefreshToken() : null);

        // ✅ 비밀번호 암호화
        user.setPassword(passwordUtil.encryptBcrypt(user.getPassword()));

        // 회원 정보 저장
        userRepository.insertUser(user);
    }

    // ✅ 나이 계산 로직 (YYYYMMDD 형식 지원)
    private int calculateAge(String birth) {
        if (birth == null || birth.length() < 8) {
            return 0; // 생년월일이 없으면 기본값 0
        }
        try {
            int birthYear = Integer.parseInt(birth.substring(0, 4)); // "YYYYMMDD" 형식에서 연도 추출
            int currentYear = LocalDate.now().getYear();
            return currentYear - birthYear;
        } catch (NumberFormatException e) {
            return 0; // 예외 발생 시 기본값 0
        }
    }
    // ✅ 마지막 로그인 시간 업데이트
    public void lastLogin(String username) {
        userRepository.lastLogin(username);
    }

    // ✅ ID로 사용자 정보 조회
    public User getUserById(String username) {
        return userRepository.loginUser(username);
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
    public void updateRefreshToken(String username, String refreshToken) {
        userRepository.updateRefreshToken(username, refreshToken);
    }

    // ✅ Refresh Token 삭제 (로그아웃 시)
    public void deleteRefreshToken(String username) {
        userRepository.updateRefreshToken(username, null);
    }

    // ✅ Refresh Token으로 사용자 조회
    public Optional<User> findByRefreshToken(String refreshToken) {
        return userRepository.findByRefreshToken(refreshToken);
    }

    public void updateAccessToken(String username, String accessToken) {
        userRepository.updateAccessToken(username, accessToken);
    }
}
