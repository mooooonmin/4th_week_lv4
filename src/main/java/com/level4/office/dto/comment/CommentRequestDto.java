package com.level4.office.dto.comment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDto {
    private String content; // 댓글 내용
    private Long courseId; // 댓글이 달릴 강의의 ID -> 응답은 강의명으로 함
    private Long userId; // 댓글을 달 사용자의 ID TODO 나중에 사용자 닉네임으로 바꾸기
}
