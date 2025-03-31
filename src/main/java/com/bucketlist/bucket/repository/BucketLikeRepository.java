package com.bucketlist.bucket.repository;

import com.bucketlist.bucket.domain.Bucket;
import com.bucketlist.bucket.domain.BucketLike;
import com.bucketlist.bucket.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BucketLikeRepository extends JpaRepository<BucketLike, Long> {

    // ✅ 특정 유저가 특정 버킷에 좋아요 눌렀는지 조회
    Optional<BucketLike> findByUserAndBucket(User user, Bucket bucket);

    // ✅ 특정 버킷의 좋아요 수
    long countByBucket(Bucket bucket);

    // ✅ 특정 유저가 누른 모든 좋아요 조회 (필요 시 확장)
    // List<BucketLike> findAllByUser(User user);
}