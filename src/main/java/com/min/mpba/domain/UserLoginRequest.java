package com.min.mpba.domain;

import lombok.Data;

@Data
public class UserLoginRequest {
    private String id;
    private String password;
}
