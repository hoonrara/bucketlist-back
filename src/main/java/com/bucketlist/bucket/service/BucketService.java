package com.bucketlist.bucket.service;

import com.bucketlist.bucket.domain.Category;
import com.bucketlist.bucket.domain.User;
import com.bucketlist.bucket.dto.bucket.*;

import java.util.List;

public interface BucketService {
    BucketResponseDto createBucket(User user, BucketRequestDto request);
    List<BucketResponseDto> getBuckets(Long userId, Boolean completed);
    BucketResponseDto getBucket(Long bucketId, User user);

    void updateTitle(Long bucketId, TitleUpdateRequest request, Long userId);
    void updateDescription(Long bucketId, DescriptionUpdateRequest request, Long userId);
    void updateReview(Long bucketId, ReviewUpdateRequest request, Long userId);
    void updateCompletion(Long bucketId, CompletionStatusUpdateRequest request, Long userId);
    void deleteBucket(Long bucketId, Long userId);
    void updatePublicity(Long bucketId, boolean isPublic, Long userId);
    List<BucketResponseDto> getPublicBuckets();
    // 인터페이스
    List<BucketResponseDto> searchPublicBuckets(String keyword, Category category, String sort);

    // 인터페이스에 추가
    void toggleLike(Long bucketId, User user);

    // 인터페이스
    long getLikeCount(Long bucketId);

    // 인터페이스
    boolean hasLiked(Long bucketId, User user);



}