package com.bucketlist.bucket.dto.bucket;

import com.bucketlist.bucket.domain.Bucket;
import com.bucketlist.bucket.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BucketResponseDto {
    private Long id;
    private String title;
    private String description;
    private Category category;
    private boolean isCompleted;
    private LocalDate completedAt;
    private String review;
    private LocalDateTime createdAt;
    private boolean isPublic;
    private Long likeCount;      // 👍 좋아요 수
    private Boolean likedByMe;   // ✅ 내가 눌렀는지


    public static BucketResponseDto from(Bucket bucket, Long likeCount, Boolean likedByMe) {
        return BucketResponseDto.builder()
                .id(bucket.getId())
                .title(bucket.getTitle())
                .description(bucket.getDescription())
                .category(bucket.getCategory())
                .isCompleted(bucket.isCompleted())
                .completedAt(bucket.getCompletedAt())
                .review(bucket.getReview())
                .createdAt(bucket.getCreatedAt())
                .likeCount(likeCount)
                .likedByMe(likedByMe)
                .build();
    }

}