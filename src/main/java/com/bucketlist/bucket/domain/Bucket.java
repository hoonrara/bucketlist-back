package com.bucketlist.bucket.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "buckets")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Bucket {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    private Category category;

    private boolean isCompleted;

    private LocalDate completedAt;

    private String review;

    private LocalDateTime createdAt;

    @Column(nullable = false)
    private boolean isPublic = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "bucket")
    @Builder.Default
    private List<BucketLike> bucketLikes = new ArrayList<>();


    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // 📌 필드별 수정 메서드 (프론트 구조에 맞춤)
    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateDescription(String description) {
        this.description = description;
    }

    public void updateCategory(Category category) {
        this.category = category;
    }

    public void updateReview(String review) {
        this.review = review;
    }

    public void markCompleted(LocalDate date) {
        this.isCompleted = true;
        this.completedAt = date;
    }

    public void markNotCompleted() {
        this.isCompleted = false;
        this.completedAt = null;
    }

    public void updatePublic(boolean isPublic) {
        this.isPublic = isPublic;
    }
}
