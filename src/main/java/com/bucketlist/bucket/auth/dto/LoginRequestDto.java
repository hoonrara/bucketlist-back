package com.bucketlist.bucket.auth.dto;

// 📁 LoginRequestDto.java

import lombok.Getter;

@Getter
public class LoginRequestDto {
    private String email;
    private String password;
}