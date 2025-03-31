package com.bucketlist.bucket.auth.controller;

import com.bucketlist.bucket.auth.dto.LoginRequestDto;
import com.bucketlist.bucket.auth.dto.LoginResponseDto;
import com.bucketlist.bucket.auth.dto.SignupRequestDto;
import com.bucketlist.bucket.auth.service.AuthService;
import com.bucketlist.bucket.dto.user.UserResponseDto;
import com.bucketlist.bucket.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // ✅ 회원가입 API
    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> signup(@RequestBody SignupRequestDto request) {
        return ResponseEntity.ok(authService.signup(request));
    }

    // ✅ 로그인 API
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto request) {
        return ResponseEntity.ok(authService.login(request));
    }


    // ✅ 현재 로그인한 유저 확인 (테스트용)
    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getCurrentUser(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(UserResponseDto.from(userDetails.getUser()));
    }

}