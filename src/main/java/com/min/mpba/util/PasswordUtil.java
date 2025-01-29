package com.min.mpba.util;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class PasswordUtil {


    // 비밀번호 해싱 (bcrypt)
    public static String encryptBcrypt(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12)); // 12번 반복 연산 (보안 수준 조절 가능)
    }

    // 비밀번호 비교 (입력한 비밀번호와 저장된 해시 비교)
    public static boolean checkPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }

    public static void main(String[] args) {
        String password = "1234";

        // 비밀번호 해싱
        String hashedPassword = encryptBcrypt(password);
        System.out.println("해싱된 비밀번호: " + hashedPassword);

        // 비밀번호 검증
        System.out.println("비밀번호 일치 여부: " + checkPassword(password, hashedPassword));
    }
}

