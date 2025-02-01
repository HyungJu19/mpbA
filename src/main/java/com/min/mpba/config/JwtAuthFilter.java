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
        System.out.println("🟢 [JwtAuthFilter] 요청 감지됨: " + request.getRequestURI());



        // ✅ 로그인 요청이면 필터를 건너뜀
        if (request.getRequestURI().equals("/user/login") || request.getRequestURI().equals("/user/register")) {
            System.out.println("⚠️ [JwtAuthFilter] 로그인 또는 회원가입 요청이므로 필터를 건너뜁니다.");
            chain.doFilter(request, response);
            return;
        }
        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            try {
                System.out.println("🔍 [DEBUG] jwtUtil.validateToken 호출 전");
                String userId = jwtUtil.validateToken(token);
                System.out.println("✅ [DEBUG] jwtUtil.validateToken 호출 완료, userId: " + userId);

                if (userId != null) {
                    System.out.println("✅ [JwtAuthFilter] 인증된 사용자: " + userId);
                    UserDetails userDetails = new User(userId, "", Collections.emptyList());
                    UsernamePasswordAuthenticationToken auth =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    SecurityContextHolder.getContext().setAuthentication(auth);
                    System.out.println("🟢 [JwtAuthFilter] SecurityContext에 사용자 설정 완료");

                    chain.doFilter(request, response);
                    return;
                }
            } catch (ExpiredJwtException e) {
                System.out.println("⚠️ [DEBUG] ExpiredJwtException 발생 - 토큰 만료됨");
                System.out.println("🔴 [JwtAuthFilter] 토큰 만료됨: " + e.getMessage());

                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json; charset=UTF-8");

                try {
                    response.getWriter().write("{\"message\": \"로그인이 만료되었습니다.\"}");
                    response.getWriter().flush();
                    response.flushBuffer(); // ✅ 응답 강제 전송
                    System.out.println("📢 [JwtAuthFilter] 응답 전송됨 (401 - 로그인이 만료되었습니다.)");
                } catch (IOException ioException) {
                    System.out.println("❌ [ERROR] 응답 작성 중 IOException 발생: " + ioException.getMessage());
                }
                return;
            } catch (JwtException e) {
                System.out.println("⚠️ [DEBUG] JwtException 발생 - 유효하지 않은 토큰");
                System.out.println("🔴 [JwtAuthFilter] 유효하지 않은 토큰: " + e.getMessage());

                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json; charset=UTF-8");

                try {
                    response.getWriter().write("{\"message\": \"유효하지 않은 토큰입니다.\"}");
                    response.getWriter().flush();
                    response.flushBuffer(); // ✅ 응답 강제 전송
                    System.out.println("📢 [JwtAuthFilter] 응답 전송됨 (401 - 유효하지 않은 토큰입니다.)");
                } catch (IOException ioException) {
                    System.out.println("❌ [ERROR] 응답 작성 중 IOException 발생: " + ioException.getMessage());
                }
                return;
            }
        } else {
            System.out.println("⚠️ [JwtAuthFilter] Authorization 헤더 없음");
        }

        chain.doFilter(request, response);
    }


}
