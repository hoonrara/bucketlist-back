package com.bucketlist.bucket.auth.service;

import com.bucketlist.bucket.auth.dto.LoginRequestDto;
import com.bucketlist.bucket.auth.dto.LoginResponseDto;
import com.bucketlist.bucket.auth.dto.SignupRequestDto;
import com.bucketlist.bucket.auth.jwt.JwtTokenProvider;
import com.bucketlist.bucket.domain.User;
import com.bucketlist.bucket.dto.user.UserResponseDto;
import com.bucketlist.bucket.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public UserResponseDto signup(SignupRequestDto request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .nickname(request.getNickname())
                .build();

        return UserResponseDto.from(userRepository.save(user));
    }

    @Override
    public LoginResponseDto login(LoginRequestDto request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 이메일입니다."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        String token = jwtTokenProvider.generateToken(user.getEmail());
        return new LoginResponseDto(token);
    }
}