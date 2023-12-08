package com.level4.office.domain.review.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReviewResponseDto {

    private Long reviewId;
    private Long userId;
    private String nickname;
    private String comment;
    private int likeCount;
    private LocalDateTime createdAt;

    @Builder
    public ReviewResponseDto(Long reviewId, Long userId, String nickname,
                             String comment, int likeCount, LocalDateTime createdAt) {
        this.reviewId = reviewId;
        this.userId = userId;
        this.nickname = nickname;
        this.comment = comment;
        this.likeCount = likeCount;
        this.createdAt = createdAt;
    }
}