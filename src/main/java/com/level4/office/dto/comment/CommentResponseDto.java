package com.level4.office.dto.comment;

import com.level4.office.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class CommentResponseDto {
    private Long commentId; // 댓글 ID
    private String courseTitle; // 댓글이 달린 강의의 제목
    private Long userId; // 댓글을 단 사용자의 ID
    private String content; // 댓글 내용
    private LocalDateTime createdDate; // 댓글 작성 시간
    private Long parentCommentId; // 부모 댓글의 ID (대댓글일 경우)
    private List<CommentResponseDto> childComments; // 대댓글 리스트

    public CommentResponseDto(Comment comment) {
        this.commentId = comment.getCommentId();
        this.courseTitle = comment.getCourse().getTitle();
        this.userId = comment.getUser().getUserId();
        this.content = comment.getContent();
        this.createdDate = comment.getCreatedDate();
        this.parentCommentId = comment.getParent() != null ? comment.getParent().getCommentId() : null; // 부모 댓글이 있으면 그 ID를, 없으면 null을 설정
        // 대댓글 리스트를 설정하는 방식
        this.childComments = comment.getChildren().stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
    }
}
