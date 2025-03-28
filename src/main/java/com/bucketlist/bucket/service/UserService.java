package com.bucketlist.bucket.service;

import com.bucketlist.bucket.dto.user.UserRequestDto;
import com.bucketlist.bucket.dto.user.UserResponseDto;

public interface UserService {
    UserResponseDto signup(UserRequestDto request);
    UserResponseDto getUserById(Long id);
}
