package com.bucketlist.bucket.service;

import com.bucketlist.bucket.domain.Bucket;
import com.bucketlist.bucket.domain.User;
import com.bucketlist.bucket.dto.bucket.*;
import com.bucketlist.bucket.repository.BucketRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BucketServiceImpl implements BucketService {

    private final BucketRepository bucketRepository;

    @Override
    public BucketResponseDto createBucket(User user, BucketRequestDto request) {
        Bucket bucket = request.toEntity(user);
        return BucketResponseDto.from(bucketRepository.save(bucket));
    }

    @Override
    public List<BucketResponseDto> getBuckets(Long userId, Boolean isCompleted) {
        List<Bucket> buckets = (isCompleted == null)
                ? bucketRepository.findAllByUserId(userId)
                : bucketRepository.findAllByUserIdAndIsCompleted(userId, isCompleted);

        return buckets.stream()
                .map(BucketResponseDto::from)
                .collect(Collectors.toList());
    }

    @Override
    public BucketResponseDto getBucket(Long bucketId) {
        Bucket bucket = findBucket(bucketId);
        return BucketResponseDto.from(bucket);
    }

    @Override
    public void updateTitle(Long bucketId, TitleUpdateRequest request) {
        Bucket bucket = findBucket(bucketId);
        bucket.updateTitle(request.getTitle());
    }

    @Override
    public void updateDescription(Long bucketId, DescriptionUpdateRequest request) {
        Bucket bucket = findBucket(bucketId);
        bucket.updateDescription(request.getDescription());
    }

    @Override
    public void updateReview(Long bucketId, ReviewUpdateRequest request) {
        Bucket bucket = findBucket(bucketId);
        bucket.updateReview(request.getReview());
    }

    @Override
    public void updateCompletion(Long bucketId, CompletionStatusUpdateRequest request) {
        Bucket bucket = findBucket(bucketId);
        if (request.isCompleted()) {
            bucket.markCompleted(request.getCompletedAt());
        } else {
            bucket.markNotCompleted();
        }
    }

    @Override
    public void deleteBucket(Long bucketId) {
        Bucket bucket = findBucket(bucketId);
        bucketRepository.delete(bucket);
    }

    private Bucket findBucket(Long id) {
        return bucketRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("버킷이 존재하지 않습니다."));
    }
}