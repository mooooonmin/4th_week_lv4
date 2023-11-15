package com.level4.office.service;

import com.level4.office.dto.course.CourseRequestDto;
import com.level4.office.dto.course.CourseResponseDto;
import com.level4.office.entity.Course;
import com.level4.office.entity.Like;
import com.level4.office.entity.User;
import com.level4.office.entity.enumType.CategoryTypeEnum;
import com.level4.office.exception.CustomException;
import com.level4.office.repository.CommentRepository;
import com.level4.office.repository.CourseRepository;
import com.level4.office.repository.LikeRepository;
import com.level4.office.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;

    // 강의 등록
    @Transactional
    public CourseResponseDto createCourse(CourseRequestDto requestDto) {
        Course course = new Course(requestDto);
        courseRepository.save(course);
        return new CourseResponseDto(course);
    }

    // 좋아요 토글(온/오프) 메서드
    @Transactional
    public void toggleLike(Long courseId, Long userId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(CustomException.CourseNotFoundException::new);
        User user = userRepository.findById(userId)
                .orElseThrow(CustomException.UserNotFoundException::new);

        Like like = likeRepository.findByCourseAndUser(course, user)
                .orElseGet(() -> new Like(user, course));
        like.setLiked(!like.isLiked());
        likeRepository.save(like);
    }

    // 선택 강의 조회 - 댓글,좋아요 포함
    @Transactional(readOnly = true)
    public CourseResponseDto getCourseByTitle(String title) {
        Course course = courseRepository.findByTitle(title)
                .orElseThrow(CustomException.CourseNotFoundException::new);
        CourseResponseDto responseDto = new CourseResponseDto(course);

        // 좋아요 수 추가
        long likesCount = likeRepository.countByCourseAndIsLikedTrue(course);
        responseDto.setLikesCount(likesCount);

        return responseDto;
    }

    // 전체 강의 조회 - 댓글,좋아요 수 포함
    @Transactional(readOnly = true)
    public List<CourseResponseDto> getAllCourses() {
        List<Course> courses = courseRepository.findAll(Sort.by(Sort.Direction.DESC, "regDate"));
        return courses.stream()
                .map(course -> {
                    CourseResponseDto dto = new CourseResponseDto(
                            course.getCourseId(),
                            course.getTitle(),
                            course.getPrice(),
                            course.getCategory(),
                            course.getCourseInfo(),
                            course.getLikesCount()
                    );
                    long likesCount = likeRepository.countByCourseAndIsLikedTrue(course);
                    dto.setLikesCount(likesCount);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    // 강의 카테고리 조회 - 댓글 제외
    // TODO 페이지네이션 학습하기
    @Transactional(readOnly = true)
    public Page<CourseResponseDto> getCoursesByCategory(
            CategoryTypeEnum category,
            int page,
            int size,
            String sortField,
            String sortOrder) {

        // PageRequest 생성을 통한 페이징 처리
        Sort.Direction direction = Sort.Direction.fromString(sortOrder);
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortField));

        // 카테고리와 페이징 정보를 기반으로 강의 조회
        Page<Course> coursesPage = courseRepository.findByCategory(category, pageable);

        // DTO 변환 후 반환
        return coursesPage.map(CourseResponseDto::new);
    }

    /*// 강의 목록 조회 (항상 상위 * 개의 목록 반환을 위해 사용)
    public List<CourseResponseDto> getCourses(String sortField, String sortOrder) {
        // 정렬 조건 생성
        Sort sort = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        PageRequest pageable = PageRequest.of(0, 3, sort); // 3개 반환 (지정가능)

        // 쿼리 실행
        Page<Course> coursesPage = courseRepository.findAll(pageable);

        // 결과 반환
        return coursesPage.stream()
                .map(CourseResponseDto::new)
                .collect(Collectors.toList());
    }*/


    // 선택 강의 수정
    @Transactional
    public void updateCourse(String title, CourseRequestDto requestDto) {
        Course course = courseRepository.findByTitle(title)
                .orElseThrow(CustomException.CourseNotFoundException::new);
        course.update(requestDto);
    }

    // 선택 강의 삭제
    @Transactional
    public void deleteCourse(String title) {
        Course course = courseRepository.findByTitle(title)
                .orElseThrow(CustomException.CourseNotFoundException::new);

        // 댓글 먼저 삭제
        commentRepository.deleteByCourse(course);

        // 이제 강의 삭제 가능
        courseRepository.delete(course);
    }

}
