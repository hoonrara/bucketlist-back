package com.bucketlist.bucket.dto.bucket;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DescriptionUpdateRequest {
    private String description;
}