package com.level4.office.controller;

import com.level4.office.dto.comment.CommentRequestDto;
import com.level4.office.dto.comment.CommentResponseDto;
import com.level4.office.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/courses/{courseId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 강의에 대한 댓글 등록
    @PostMapping
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<CommentResponseDto> addComment(
            @PathVariable Long courseId,
            @RequestBody CommentRequestDto commentRequestDto) {
        CommentResponseDto commentResponse = commentService.addComment(courseId, commentRequestDto);
        return new ResponseEntity<>(commentResponse, HttpStatus.CREATED);
    }

    // 강의에 대한 댓글 수정
    @PutMapping("/{commentId}")
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<CommentResponseDto> updateComment(
            @PathVariable Long courseId,
            @PathVariable Long commentId,
            @RequestBody CommentRequestDto commentRequestDto) {
        CommentResponseDto updatedComment = commentService.updateComment(courseId, commentId, commentRequestDto);
        return new ResponseEntity<>(updatedComment, HttpStatus.OK);
    }

    // 강의에 대한 댓글 삭제
    @DeleteMapping("/{commentId}")
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<?> deleteComment(
            @PathVariable Long courseId,
            @PathVariable Long commentId) {
        commentService.deleteComment(courseId, commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}