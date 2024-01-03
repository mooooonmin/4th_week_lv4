package com.level4.office.domain.comment.service;

import com.level4.office.domain.comment.dto.CommentRequestDto;
import com.level4.office.domain.comment.entity.Comment;
import com.level4.office.domain.comment.repository.CommentRepository;
import com.level4.office.domain.course.entity.Course;
import com.level4.office.domain.course.repository.CourseRepository;
import com.level4.office.domain.user.entity.User;
import com.level4.office.domain.user.repository.UserRepository;
import com.level4.office.global.exception.CustomException;
import com.level4.office.global.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    @Transactional
    public void addCommentToCourse(Long courseId, Long userId, CommentRequestDto comment) {
        Course course = validateCourse(courseId);
        User user = validateUser(userId);

        Comment savedComment = new Comment(course, user, comment);
        commentRepository.save(savedComment);
    }

    private User validateUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
    }

    private Course validateCourse(Long courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_COURSE));
    }

}
