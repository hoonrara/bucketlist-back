package com.bucketlist.bucket.auth.service;

import com.bucketlist.bucket.auth.dto.LoginRequestDto;
import com.bucketlist.bucket.auth.dto.LoginResponseDto;
import com.bucketlist.bucket.auth.dto.SignupRequestDto;
import com.bucketlist.bucket.dto.user.UserResponseDto;

public interface AuthService {
    UserResponseDto signup(SignupRequestDto request);
    LoginResponseDto login(LoginRequestDto request);
}