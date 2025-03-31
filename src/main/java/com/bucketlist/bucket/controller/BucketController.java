package com.bucketlist.bucket.controller;

import com.bucketlist.bucket.domain.Category;
import com.bucketlist.bucket.dto.bucket.*;
import com.bucketlist.bucket.security.CustomUserDetails;
import com.bucketlist.bucket.service.BucketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/buckets")
@RequiredArgsConstructor
public class BucketController {

    private final BucketService bucketService;

    @PostMapping
    public ResponseEntity<BucketResponseDto> createBucket(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                          @RequestBody BucketRequestDto request) {
        return ResponseEntity.ok(bucketService.createBucket(userDetails.getUser(), request));
    }

    @GetMapping
    public ResponseEntity<List<BucketResponseDto>> getBuckets(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                              @RequestParam(required = false) Boolean completed) {
        return ResponseEntity.ok(bucketService.getBuckets(userDetails.getUser().getId(), completed));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BucketResponseDto> getBucket(@PathVariable Long id,
                                                       @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(bucketService.getBucket(id, userDetails.getUser()));
    }

    @PatchMapping("/{id}/title")
    public ResponseEntity<Void> updateTitle(@PathVariable Long id,
                                            @RequestBody TitleUpdateRequest request,
                                            @AuthenticationPrincipal CustomUserDetails userDetails) {
        bucketService.updateTitle(id, request, userDetails.getUser().getId());
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/description")
    public ResponseEntity<Void> updateDescription(@PathVariable Long id,
                                                  @RequestBody DescriptionUpdateRequest request,
                                                  @AuthenticationPrincipal CustomUserDetails userDetails) {
        bucketService.updateDescription(id, request, userDetails.getUser().getId());
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/review")
    public ResponseEntity<Void> updateReview(@PathVariable Long id,
                                             @RequestBody ReviewUpdateRequest request,
                                             @AuthenticationPrincipal CustomUserDetails userDetails) {
        bucketService.updateReview(id, request, userDetails.getUser().getId());
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/completion")
    public ResponseEntity<Void> updateCompletion(@PathVariable Long id,
                                                 @RequestBody CompletionStatusUpdateRequest request,
                                                 @AuthenticationPrincipal CustomUserDetails userDetails) {
        bucketService.updateCompletion(id, request, userDetails.getUser().getId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBucket(@PathVariable Long id,
                                             @AuthenticationPrincipal CustomUserDetails userDetails) {
        bucketService.deleteBucket(id, userDetails.getUser().getId());
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/publicity")
    public ResponseEntity<Void> updatePublicity(@PathVariable Long id,
                                                @RequestBody PublicityUpdateRequest request,
                                                @AuthenticationPrincipal CustomUserDetails userDetails) {
        bucketService.updatePublicity(id, request.isPublic(), userDetails.getUser().getId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/public")
    public ResponseEntity<List<BucketResponseDto>> getPublicBuckets() {
        return ResponseEntity.ok(bucketService.getPublicBuckets());
    }

    @GetMapping("/public/search")
    public ResponseEntity<List<BucketResponseDto>> searchPublicBuckets(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Category category,
            @RequestParam(required = false, defaultValue = "latest") String sort
    ) {
        return ResponseEntity.ok(bucketService.searchPublicBuckets(keyword, category, sort));
    }

    // ✅ 좋아요 토글 (POST /buckets/{id}/like)
    @PostMapping("/{id}/like")
    public ResponseEntity<Void> toggleLike(@PathVariable Long id,
                                           @AuthenticationPrincipal CustomUserDetails userDetails) {
        bucketService.toggleLike(id, userDetails.getUser());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/like-count")
    public ResponseEntity<Long> getLikeCount(@PathVariable Long id) {
        long count = bucketService.getLikeCount(id);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/{id}/liked")
    public ResponseEntity<Boolean> hasLiked(@PathVariable Long id,
                                            @AuthenticationPrincipal CustomUserDetails userDetails) {
        boolean liked = bucketService.hasLiked(id, userDetails.getUser());
        return ResponseEntity.ok(liked);
    }

}
