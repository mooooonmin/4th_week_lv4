package com.level4.office.controller;

import com.level4.office.security.UserDetailsImpl;
import com.level4.office.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/courses/{courseId}/likes")
@RequiredArgsConstructor
public class LikeController {

    private final CourseService courseService;

    // 좋아요 토글(온/오프)
    @PostMapping
    public ResponseEntity<Void> toggleCourseLike(@PathVariable Long courseId, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Long userId = userDetails.getUser().getUserId();
        courseService.toggleLike(courseId, userId);
        return ResponseEntity.ok().build();
    }
}
