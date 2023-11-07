package com.level4.office.service;

import com.level4.office.dto.comment.CommentRequestDto;
import com.level4.office.dto.comment.CommentResponseDto;
import com.level4.office.entity.Comment;
import com.level4.office.entity.Course;
import com.level4.office.entity.User;
import com.level4.office.exception.CustomException;
import com.level4.office.exception.ErrorMessage;
import com.level4.office.repository.CommentRepository;
import com.level4.office.repository.CourseRepository;
import com.level4.office.repository.UserRepository;
import com.level4.office.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {

    private final CommentRepository commentRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    // 댓글 등록 (대댓글 기능 포함)
    @Transactional
    public CommentResponseDto addComment(Long courseId, CommentRequestDto commentRequestDto) {

        // 사용자 ID를 인증 정보에서 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Long userId = userDetails.getUser().getUserId();

        // 강의 ID를 사용하여 강의 엔티티 찾기
        Course course = courseRepository.findById(courseId)
                .orElseThrow(CustomException.CourseNotFoundException::new);

        // 사용자 ID를 사용하여 사용자 엔티티 찾기
        User user = userRepository.findById(userId)
                .orElseThrow(CustomException.UserNotFoundException::new);

        // 대댓글인 경우 부모 댓글 찾기
        Comment parentComment = null;
        if (commentRequestDto.getParentId() != null) {
            parentComment = commentRepository.findById(commentRequestDto.getParentId())
                    .orElseThrow(() -> new CustomException(ErrorMessage.DATA_NOT_FOUND.getMessage()));
        }

        // 댓글 엔티티를 생성하면서 대댓글인 경우 부모 설정
        Comment comment = Comment.builder()
                .course(course)
                .user(user)
                .parent(parentComment) // 대댓글인 경우 부모 설정
                .content(commentRequestDto.getContent())
                .build();

        // 댓글 엔티티를 저장
        Comment savedComment = commentRepository.save(comment);

        // 저장된 댓글 엔티티를 기반으로 응답 DTO를 생성
        return new CommentResponseDto(savedComment);
    }

    @Transactional
    // 댓글 수정
    public CommentResponseDto updateComment(Long courseId, Long commentId, CommentRequestDto commentRequestDto) {
        // 인증 객체를 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Long authenticatedUserId = userDetails.getUser().getUserId();

        // 댓글 엔티티를 찾기
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorMessage.DATA_NOT_FOUND.getMessage()));

        // 현재 로그인한 사용자가 댓글 작성자와 같은지 확인
        if (!comment.getUser().getUserId().equals(authenticatedUserId)) {
            throw new CustomException(ErrorMessage.FORBIDDEN.getMessage());
        }

        // 댓글이 요청된 강의 ID에 속하는지 확인
        if (!comment.getCourse().getCourseId().equals(courseId)) {
            throw new CustomException(ErrorMessage.DATA_NOT_FOUND.getMessage());
        }

        // 댓글 내용 업데이트
        comment.setContent(commentRequestDto.getContent());
        Comment updatedComment = commentRepository.save(comment);

        // 응답 DTO 생성
        return new CommentResponseDto(updatedComment);
    }


    // 댓글 삭제
    @Transactional
    public void deleteComment(Long courseId, Long commentId) {
        // 인증 객체를 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Long authenticatedUserId = userDetails.getUser().getUserId();

        // 댓글 엔티티를 찾기
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorMessage.DATA_NOT_FOUND.getMessage()));

        // 현재 로그인한 사용자가 댓글 작성자와 같은지 확인
        if (!comment.getUser().getUserId().equals(authenticatedUserId)) {
            throw new CustomException(ErrorMessage.FORBIDDEN.getMessage());
        }

        // 댓글이 요청된 강의 ID에 속하는지 확인
        if (!comment.getCourse().getCourseId().equals(courseId)) {
            throw new CustomException(ErrorMessage.DATA_NOT_FOUND.getMessage());
        }

        // 댓글 삭제
        commentRepository.delete(comment);
    }
}