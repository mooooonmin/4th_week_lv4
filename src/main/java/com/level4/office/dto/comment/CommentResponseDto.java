package com.level4.office.dto.comment;

import com.level4.office.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CommentResponseDto {
    private String courseTitle; // 댓글이 달린 강의의 제목
    private Long userId; // 댓글을 단 사용자의 ID
    private String content; // 댓글 내용
    private LocalDateTime createdDate; // 댓글 작성 시간

    public CommentResponseDto(Comment comment) {
        this.courseTitle = comment.getCourse().getTitle();
        this.userId = comment.getUser().getUserId();
        this.content = comment.getContent();
        this.createdDate = comment.getCreatedDate();
    }
}
