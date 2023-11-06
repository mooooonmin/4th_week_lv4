package com.level4.office.service;

import com.level4.office.dto.comment.CommentRequestDto;
import com.level4.office.dto.comment.CommentResponseDto;
import com.level4.office.entity.Comment;
import com.level4.office.entity.Course;
import com.level4.office.entity.User;
import com.level4.office.exception.CustomException;
import com.level4.office.repository.CommentRepository;
import com.level4.office.repository.CourseRepository;
import com.level4.office.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public CommentResponseDto addComment(CommentRequestDto commentRequestDto) {
        // 요청된 DTO에서 강의 ID와 사용자 ID를 가져오기
        Long courseId = commentRequestDto.getCourseId();
        Long userId = commentRequestDto.getUserId();

        // 강의 ID와 사용자 ID를 사용하여 각각의 엔티티 찾기
        Course course = courseRepository.findById(courseId)
                .orElseThrow(CustomException.CourseNotFoundException::new);
        User user = userRepository.findById(userId)
                .orElseThrow(CustomException.UserNotFoundException::new);

        // 댓글 엔티티를 생성
        Comment comment = Comment.builder()
                .course(course)
                .user(user)
                .content(commentRequestDto.getContent())
                .build();

        // 댓글 엔티티를 저장
        Comment savedComment = commentRepository.save(comment);

        // 저장된 댓글 엔티티를 기반으로 응답 DTO를 생성
        return new CommentResponseDto(savedComment);
    }

}