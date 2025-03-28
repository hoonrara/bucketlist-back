package com.bucketlist.bucket.dto.user;


import com.bucketlist.bucket.domain.User;
import lombok.Getter;

@Getter
public class UserRequestDto {
    private String email;
    private String password;
    private String nickname;

    public User toEntity() {
        return User.builder()
                .email(this.email)
                .password(this.password)
                .nickname(this.nickname)
                .build();
    }
}
