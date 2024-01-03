package com.level4.office.domain.like.controller;

import com.level4.office.domain.like.service.LikeService;
import com.level4.office.global.dto.SuccessMessageDto;
import com.level4.office.global.security.impl.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/courses")
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/{courseId}/likes")
    public SuccessMessageDto like(@PathVariable Long courseId,
                                  @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return likeService.like(courseId, userDetails.getUser().getId());
    }

}
