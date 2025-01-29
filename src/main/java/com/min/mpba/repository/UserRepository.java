package com.min.mpba.repository;


import com.min.mpba.domain.User;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface UserRepository {

    User loginUser (@Param("id") String id);

    void insertUser(User user);

    int existsByNameAndBirth( String persName, String birth);

    int existsByEmail(String email);

    int existsById(String id);


}
