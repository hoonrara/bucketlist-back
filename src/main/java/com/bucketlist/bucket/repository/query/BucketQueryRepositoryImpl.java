package com.bucketlist.bucket.repository.query;

import com.bucketlist.bucket.domain.Bucket;
import com.bucketlist.bucket.domain.Category;
import com.bucketlist.bucket.domain.QBucket;
import com.bucketlist.bucket.domain.QBucketLike;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BucketQueryRepositoryImpl implements BucketQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Bucket> searchPublicBuckets(String keyword, Category category, String sort) {
        QBucket bucket = QBucket.bucket;
        QBucketLike like = QBucketLike.bucketLike;

        // ✅ 기본 쿼리: 공개된 버킷만 조회 + 좋아요 조인 (나중에 count 위해)
        JPQLQuery<Bucket> query = queryFactory
                .select(bucket)
                .from(bucket)
                .leftJoin(bucket.bucketLikes, like) // 좋아요 수 계산용 조인
                .where(bucket.isPublic.isTrue())    // 공개된 버킷만 필터
                .groupBy(bucket.id);                // 좋아요 수 계산하려면 groupBy 필요

        // ✅ 제목 or 설명에 키워드 포함된 경우 필터
        if (keyword != null && !keyword.isBlank()) {
            query.where(
                    bucket.title.containsIgnoreCase(keyword)
                            .or(bucket.description.containsIgnoreCase(keyword))
            );
        }

        // ✅ 카테고리 필터 (예: TRAVEL, BOOK 등)
        if (category != null) {
            query.where(bucket.category.eq(category));
        }

        // ✅ 정렬 조건 처리
        switch (sort) {
            case "oldest" -> query.orderBy(bucket.createdAt.asc()); // 오래된 순
            case "likes" -> query.orderBy(like.count().desc());     // 좋아요 많은 순
            default -> query.orderBy(bucket.createdAt.desc());      // 최신순 (기본값)
        }

        // ✅ 최종 실행
        return query.fetch();
    }
}
