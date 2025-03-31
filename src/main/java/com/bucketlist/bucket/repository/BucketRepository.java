package com.bucketlist.bucket.repository;

import com.bucketlist.bucket.domain.Bucket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BucketRepository extends JpaRepository<Bucket, Long> {

    // 특정 유저의 버킷리스트 조회용 (추후 로그인 연동 시 사용 가능)
    List<Bucket> findAllByUserId(Long userId);

    // 완료 여부로 필터링
    List<Bucket> findAllByUserIdAndIsCompleted(Long userId, boolean isCompleted);

    List<Bucket> findAllByIsPublicTrue();

}

