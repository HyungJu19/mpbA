package com.min.mpba.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.min.mpba.util.JwtUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Autowired
    public JwtAuthFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
// ✅ 요청과 응답의 인코딩을 UTF-8로 강제 설정
        request.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        System.out.println("🟢 [JwtAuthFilter] 요청 감지됨: " + request.getRequestURI());

        // ✅ Authorization 헤더 가져오기
        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            try {
                String userId = jwtUtil.validateToken(token);
                System.out.println("✅ [JwtAuthFilter] 인증된 사용자: " + userId);

                if (userId != null) {
                    UserDetails userDetails = new User(userId, "", Collections.emptyList());
                    UsernamePasswordAuthenticationToken auth =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    SecurityContextHolder.getContext().setAuthentication(auth);
                    System.out.println("🟢 [JwtAuthFilter] SecurityContext에 사용자 설정 완료");
                }
            } catch (ExpiredJwtException e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json; charset=UTF-8");
                response.getWriter().write("{\"message\": \"로그인이 만료되었습니다.\"}");
                return;
            } catch (JwtException e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json; charset=UTF-8");
                response.getWriter().write("{\"message\": \"유효하지 않은 토큰입니다.\"}");
                return;
            }
        } else {
            System.out.println("⚠️ [JwtAuthFilter] Authorization 헤더 없음");
        }

        chain.doFilter(request, response);
    }
}
