package com.min.mpba.repository;

import com.min.mpba.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Select;
import java.util.Optional;

@Mapper
public interface UserRepository {

    User loginUser(@Param("id") String id);

    void insertUser(User user);

    int existsByNameAndBirth(@Param("persName") String persName, @Param("birth") String birth);

    int existsByEmail(@Param("email") String email);

    int existsById(@Param("id") String id);

    void lastLogin(@Param("id") String id);

    // ✅ Refresh Token 업데이트

    void updateRefreshToken(@Param("id") String id, @Param("refreshToken") String refreshToken);

    // ✅ Refresh Token으로 사용자 조회

    Optional<User> findByRefreshToken(@Param("refreshToken") String refreshToken);

    void updateAccessToken(@Param("id") String id, @Param("accessToken") String accessToken);
}
