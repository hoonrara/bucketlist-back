package com.bucketlist.bucket.dto.bucket;

import com.bucketlist.bucket.domain.Bucket;
import com.bucketlist.bucket.domain.Category;
import com.bucketlist.bucket.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BucketRequestDto {
    private String title;
    private String description;
    private Category category;

    public Bucket toEntity(User user) {
        return Bucket.builder()
                .user(user)
                .title(this.title)
                .description(this.description)
                .category(this.category)
                .isCompleted(false) // 생성 시 기본값
                .build();
    }
}