package com.bucketlist.bucket.service;

import com.bucketlist.bucket.domain.User;
import com.bucketlist.bucket.dto.bucket.*;

import java.util.List;

public interface BucketService {

    // 버킷 생성
    BucketResponseDto createBucket(User user, BucketRequestDto request);

    // 전체 목록 조회 (필터: 전체 / 완료 / 미완료)
    List<BucketResponseDto> getBuckets(Long userId, Boolean isCompleted);

    // 단건 조회
    BucketResponseDto getBucket(Long bucketId);

    // 제목 수정
    void updateTitle(Long bucketId, TitleUpdateRequest request);

    // 설명 수정
    void updateDescription(Long bucketId, DescriptionUpdateRequest request);

    // 후기 수정
    void updateReview(Long bucketId, ReviewUpdateRequest request);

    // 완료 상태 수정
    void updateCompletion(Long bucketId, CompletionStatusUpdateRequest request);

    // 삭제
    void deleteBucket(Long bucketId);
}
