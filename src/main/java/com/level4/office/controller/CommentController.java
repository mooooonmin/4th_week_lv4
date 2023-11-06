package com.level4.office.controller;

import com.level4.office.dto.comment.CommentRequestDto;
import com.level4.office.dto.comment.CommentResponseDto;
import com.level4.office.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    @PreAuthorize("hasAuthority('USER')") // 'USER' 권한을 가진 사용자만 댓글을 달 수 있음
    public ResponseEntity<CommentResponseDto> addComment(@RequestBody CommentRequestDto commentRequestDto) {
        CommentResponseDto commentResponse = commentService.addComment(commentRequestDto);
        return new ResponseEntity<>(commentResponse, HttpStatus.CREATED);
    }

}