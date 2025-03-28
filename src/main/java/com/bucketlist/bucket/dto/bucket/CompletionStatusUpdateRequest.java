package com.bucketlist.bucket.dto.bucket;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CompletionStatusUpdateRequest {
    private boolean isCompleted;
    private LocalDate completedAt;
}
