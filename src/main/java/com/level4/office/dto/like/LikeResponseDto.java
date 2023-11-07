package com.level4.office.dto.like;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikeResponseDto {
    private Long courseId;
    private Long userId;
    private boolean isLiked;

    public LikeResponseDto(Long courseId, Long userId, boolean isLiked) {
        this.courseId = courseId;
        this.userId = userId;
        this.isLiked = isLiked;
    }
}