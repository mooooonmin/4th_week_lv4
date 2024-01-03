package com.level4.office.domain.like.service;

import com.level4.office.domain.course.entity.Course;
import com.level4.office.domain.course.repository.CourseRepository;
import com.level4.office.domain.like.entity.Like;
import com.level4.office.domain.like.repository.LikeRepository;
import com.level4.office.domain.user.entity.User;
import com.level4.office.domain.user.repository.UserRepository;
import com.level4.office.global.exception.CustomException;
import com.level4.office.global.exception.ErrorCode;
import com.level4.office.global.dto.SuccessMessageDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    @Transactional
    public SuccessMessageDto like(Long courseId, Long userId) {
        User user = validateUser(userId);
        Course course = validateCourse(courseId);
        Optional<Like> existingLike = likeRepository.findByUserAndCourse(user, course);
        return likeStatus(existingLike, user, course);
    }

    private SuccessMessageDto likeStatus(Optional<Like> existingLike, User user, Course course) {
        if (existingLike.isPresent()) {
            likeRepository.delete(existingLike.get());
            return new SuccessMessageDto("좋아요 취소 완료");
        } else {
            likeRepository.save(new Like(user, course));
            return new SuccessMessageDto("좋아요 완료");
        }
    }

    private User validateUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        return user;
    }

    private Course validateCourse(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_COURSE));
        return course;
    }

}
