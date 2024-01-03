package com.level4.office.domain.comment.controller;

import com.level4.office.domain.comment.dto.CommentRequestDto;
import com.level4.office.domain.comment.service.CommentService;
import com.level4.office.domain.user.entity.User;
import com.level4.office.global.dto.SuccessMessageDto;
import com.level4.office.security.impl.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/courses")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{courseId}/comments")
    public SuccessMessageDto addComment(@PathVariable Long courseId,
                                        @RequestBody CommentRequestDto commentRequestDto,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        commentService.addCommentToCourse(courseId, user.getId(), commentRequestDto);
        return new SuccessMessageDto("댓글이 등록되었습니다!");
    }

}
