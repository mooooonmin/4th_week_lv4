package com.level4.office.service;

import com.level4.office.dto.course.CourseRequestDto;
import com.level4.office.dto.course.CourseResponseDto;
import com.level4.office.entity.Course;
import com.level4.office.entity.enumType.CategoryTypeEnum;
import com.level4.office.exception.CustomException;
import com.level4.office.repository.CommentRepository;
import com.level4.office.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    // 강의 목록 조회
    public List<CourseResponseDto> getCourses(String sortField, String sortOrder) {
        // 정렬 조건 생성
        Sort sort = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        PageRequest pageable = PageRequest.of(0, 3, sort); // 3개 반환

        // 쿼리 실행
        Page<Course> coursesPage = courseRepository.findAll(pageable);

        // 결과 반환
        return coursesPage.stream()
                .map(CourseResponseDto::new)
                .collect(Collectors.toList());
    }

    // 강의 등록
    @Transactional
    public CourseResponseDto createCourse(CourseRequestDto requestDto) {
        Course course = new Course(requestDto);
        courseRepository.save(course);
        return new CourseResponseDto(course);
    }

    // 선택 강의 조회 - 댓글 포함
    @Transactional(readOnly = true)
    public CourseResponseDto getCourseByTitle(String title) {
        Course course = courseRepository.findByTitle(title)
                .orElseThrow(CustomException.CourseNotFoundException::new);
        return new CourseResponseDto(course); // 댓글 포함 생성자 사용
    }

    // 전체 강의 조회 - 댓글 제외
    @Transactional(readOnly = true)
    public List<CourseResponseDto> getAllCourses() {
        List<Course> courses = courseRepository.findAll(Sort.by(Sort.Direction.DESC, "regDate"));
        return courses.stream()
                .map(course -> new CourseResponseDto(
                        course.getCourseId(),
                        course.getTitle(),
                        course.getPrice(),
                        course.getCategory(),
                        course.getCourseInfo() // 댓글 제외 생성자 사용
                ))
                .collect(Collectors.toList());
    }

    // 강의 카테고리 조회 - 댓글 제외
    @Transactional(readOnly = true)
    public List<CourseResponseDto> getCoursesByCategory(CategoryTypeEnum category, String sortField, String sortOrder) {
        Sort sort = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        List<Course> courses = courseRepository.findByCategory(category, sort);
        return courses.stream()
                .map(course -> new CourseResponseDto(
                        course.getCourseId(),
                        course.getTitle(),
                        course.getPrice(),
                        course.getCategory(),
                        course.getCourseInfo() // 댓글 제외 생성자 사용
                ))
                .collect(Collectors.toList());
    }


    // 선택 강의 수정
    @Transactional
    public void updateCourse(String title, CourseRequestDto requestDto) {
        Course course = courseRepository.findByTitle(title)
                .orElseThrow(CustomException.CourseNotFoundException::new);
        course.update(requestDto);
        //courseRepository.save(course);
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
