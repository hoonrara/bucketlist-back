package com.bucketlist.bucket.service;

import com.bucketlist.bucket.domain.Bucket;
import com.bucketlist.bucket.domain.BucketLike;
import com.bucketlist.bucket.domain.Category;
import com.bucketlist.bucket.domain.User;
import com.bucketlist.bucket.dto.bucket.*;
import com.bucketlist.bucket.exception.UnauthorizedException;
import com.bucketlist.bucket.repository.BucketLikeRepository;
import com.bucketlist.bucket.repository.BucketRepository;
import com.bucketlist.bucket.repository.query.BucketQueryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BucketServiceImpl implements BucketService {

    private final BucketRepository bucketRepository;
    private final BucketQueryRepository bucketQueryRepository;
    private final BucketLikeRepository bucketLikeRepository;

    @Override
    public BucketResponseDto createBucket(User user, BucketRequestDto request) {
        Bucket bucket = request.toEntity(user);
        Bucket saved = bucketRepository.save(bucket);
        return BucketResponseDto.from(saved, 0L, false);
    }

    @Override
    public List<BucketResponseDto> getBuckets(Long userId, Boolean completed) {
        List<Bucket> buckets = (completed == null)
                ? bucketRepository.findAllByUserId(userId)
                : bucketRepository.findAllByUserIdAndIsCompleted(userId, completed);
        return buckets.stream()
                .map(bucket -> {
                    long likeCount = bucketLikeRepository.countByBucket(bucket);
                    return BucketResponseDto.from(bucket, likeCount, false);
                })
                .toList();
    }

    @Override
    public BucketResponseDto getBucket(Long bucketId, User user) {
        Bucket bucket = bucketRepository.findById(bucketId)
                .orElseThrow(() -> new IllegalArgumentException("버킷이 존재하지 않습니다."));
        long likeCount = bucketLikeRepository.countByBucket(bucket);
        boolean liked = bucketLikeRepository.findByUserAndBucket(user, bucket).isPresent();
        return BucketResponseDto.from(bucket, likeCount, liked);
    }

    @Override
    public void updateTitle(Long bucketId, TitleUpdateRequest request, Long userId) {
        Bucket bucket = findBucketOrThrow(bucketId, userId);
        bucket.updateTitle(request.getTitle());
    }

    @Override
    public void updateDescription(Long bucketId, DescriptionUpdateRequest request, Long userId) {
        Bucket bucket = findBucketOrThrow(bucketId, userId);
        bucket.updateDescription(request.getDescription());
    }

    @Override
    public void updateReview(Long bucketId, ReviewUpdateRequest request, Long userId) {
        Bucket bucket = findBucketOrThrow(bucketId, userId);
        bucket.updateReview(request.getReview());
    }

    @Override
    public void updateCompletion(Long bucketId, CompletionStatusUpdateRequest request, Long userId) {
        Bucket bucket = findBucketOrThrow(bucketId, userId);
        if (request.isCompleted()) {
            bucket.markCompleted(request.getCompletedAt());
        } else {
            bucket.markNotCompleted();
        }
    }

    @Override
    public void deleteBucket(Long bucketId, Long userId) {
        Bucket bucket = findBucketOrThrow(bucketId, userId);
        bucketRepository.delete(bucket);
    }

    private Bucket findBucketOrThrow(Long bucketId, Long userId) {
        Bucket bucket = bucketRepository.findById(bucketId)
                .orElseThrow(() -> new IllegalArgumentException("버킷이 존재하지 않습니다."));

        if (!bucket.getUser().getId().equals(userId)) {
            throw new UnauthorizedException("본인의 버킷만 수정/삭제할 수 있습니다.");
        }

        return bucket;
    }

    @Override
    public void updatePublicity(Long bucketId, boolean isPublic, Long userId) {
        Bucket bucket = findBucketOrThrow(bucketId, userId);
        bucket.updatePublic(isPublic);
    }

    @Override
    public List<BucketResponseDto> getPublicBuckets() {
        List<Bucket> buckets = bucketRepository.findAllByIsPublicTrue();
        return buckets.stream()
                .map(bucket -> {
                    long likeCount = bucketLikeRepository.countByBucket(bucket);
                    return BucketResponseDto.from(bucket, likeCount, false);
                })
                .toList();
    }

    @Override
    public List<BucketResponseDto> searchPublicBuckets(String keyword, Category category, String sort) {
        List<Bucket> buckets = bucketQueryRepository.searchPublicBuckets(keyword, category, sort);
        return buckets.stream()
                .map(bucket -> {
                    long likeCount = bucketLikeRepository.countByBucket(bucket);
                    return BucketResponseDto.from(bucket, likeCount, false);
                })
                .toList();
    }

    @Override
    public void toggleLike(Long bucketId, User user) {
        Bucket bucket = bucketRepository.findById(bucketId)
                .orElseThrow(() -> new IllegalArgumentException("버킷이 존재하지 않습니다."));

        Optional<BucketLike> existingLike = bucketLikeRepository.findByUserAndBucket(user, bucket);

        if (existingLike.isPresent()) {
            bucketLikeRepository.delete(existingLike.get());
        } else {
            BucketLike like = BucketLike.builder()
                    .user(user)
                    .bucket(bucket)
                    .build();
            bucketLikeRepository.save(like);
        }
    }

    @Override
    public long getLikeCount(Long bucketId) {
        Bucket bucket = bucketRepository.findById(bucketId)
                .orElseThrow(() -> new IllegalArgumentException("버킷이 존재하지 않습니다."));
        return bucketLikeRepository.countByBucket(bucket);
    }

    @Override
    public boolean hasLiked(Long bucketId, User user) {
        Bucket bucket = bucketRepository.findById(bucketId)
                .orElseThrow(() -> new IllegalArgumentException("버킷이 존재하지 않습니다."));
        return bucketLikeRepository.findByUserAndBucket(user, bucket).isPresent();
    }
}
