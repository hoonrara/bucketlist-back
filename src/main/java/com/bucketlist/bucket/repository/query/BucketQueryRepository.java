package com.bucketlist.bucket.repository.query;

import com.bucketlist.bucket.domain.Bucket;
import com.bucketlist.bucket.domain.Category;

import java.util.List;

public interface BucketQueryRepository {
    List<Bucket> searchPublicBuckets(String keyword, Category category, String sort);
}
