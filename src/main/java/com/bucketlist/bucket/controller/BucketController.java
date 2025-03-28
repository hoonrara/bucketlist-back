package com.bucketlist.bucket.controller;


import com.bucketlist.bucket.domain.User;
import com.bucketlist.bucket.dto.bucket.*;
import com.bucketlist.bucket.service.BucketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/buckets")
@RequiredArgsConstructor
public class BucketController {

    private final BucketService bucketService;

    // ✅ 버킷 생성
    @PostMapping
    public ResponseEntity<BucketResponseDto> createBucket(@RequestBody BucketRequestDto request) {
        // TODO: 로그인 연동 전까지는 임시 유저 사용
        User user = dummyUser();
        return ResponseEntity.ok(bucketService.createBucket(user, request));
    }

    // ✅ 버킷 전체 조회 (선택적 완료 여부 필터링)
    @GetMapping
    public ResponseEntity<List<BucketResponseDto>> getBuckets(@RequestParam Long userId,
                                                              @RequestParam(required = false) Boolean completed) {
        return ResponseEntity.ok(bucketService.getBuckets(userId, completed));
    }

    // ✅ 버킷 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<BucketResponseDto> getBucket(@PathVariable Long id) {
        return ResponseEntity.ok(bucketService.getBucket(id));
    }

    // ✅ 제목 수정
    @PatchMapping("/{id}/title")
    public ResponseEntity<Void> updateTitle(@PathVariable Long id,
                                            @RequestBody TitleUpdateRequest request) {
        bucketService.updateTitle(id, request);
        return ResponseEntity.ok().build();
    }

    // ✅ 설명 수정
    @PatchMapping("/{id}/description")
    public ResponseEntity<Void> updateDescription(@PathVariable Long id,
                                                  @RequestBody DescriptionUpdateRequest request) {
        bucketService.updateDescription(id, request);
        return ResponseEntity.ok().build();
    }

    // ✅ 후기 수정
    @PatchMapping("/{id}/review")
    public ResponseEntity<Void> updateReview(@PathVariable Long id,
                                             @RequestBody ReviewUpdateRequest request) {
        bucketService.updateReview(id, request);
        return ResponseEntity.ok().build();
    }

    // ✅ 완료 여부 + 날짜 수정
    @PatchMapping("/{id}/completion")
    public ResponseEntity<Void> updateCompletion(@PathVariable Long id,
                                                 @RequestBody CompletionStatusUpdateRequest request) {
        bucketService.updateCompletion(id, request);
        return ResponseEntity.ok().build();
    }

    // ✅ 버킷 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBucket(@PathVariable Long id) {
        bucketService.deleteBucket(id);
        return ResponseEntity.ok().build();
    }

    // ❗ 로그인 연동 전 임시 유저 주입
    private User dummyUser() {
        return User.builder().id(1L).build();
    }
}