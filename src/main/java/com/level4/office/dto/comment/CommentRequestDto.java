package com.level4.office.dto.comment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDto {
    private Long parentId; // 대댓글을 위한 부모 댓글의 ID
    private String content;
}
