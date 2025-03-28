package com.bucketlist.bucket.service;


import com.bucketlist.bucket.domain.User;
import com.bucketlist.bucket.dto.user.UserRequestDto;
import com.bucketlist.bucket.dto.user.UserResponseDto;
import com.bucketlist.bucket.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserResponseDto signup(UserRequestDto request) {
        User user = request.toEntity();
        return UserResponseDto.from(userRepository.save(user));
    }

    @Override
    public UserResponseDto getUserById(Long id) {
        return userRepository.findById(id)
                .map(UserResponseDto::from)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));
    }
}