package com.level4.office.service;

import com.level4.office.dto.course.CourseRequestDto;
import com.level4.office.dto.course.CourseResponseDto;
import com.level4.office.entity.Course;
import com.level4.office.entity.enumType.CategoryTypeEnum;
import com.level4.office.exception.CustomException;
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

    // 강의 목록 조회
    public List<CourseResponseDto> getCourses(String sortField, String sortOrder) {
        // 정렬 조건 생성
        Sort sort = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        PageRequest pageable = PageRequest.of(0, 10, sort);

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

    // 선택 강의 조회
    @Transactional(readOnly = true)
    public CourseResponseDto getCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new CustomException.CourseNotFoundException());
        return new CourseResponseDto(course);
    }

    // 전체 강의 조회
    @Transactional(readOnly = true)
    public List<CourseResponseDto> getAllCourses() {
        List<Course> courses = courseRepository.findAll(Sort.by(Sort.Direction.DESC, "regDate"));
        return courses.stream()
                .map(CourseResponseDto::new)
                .collect(Collectors.toList());
    }

    // 강의 카테고리 조회 - 정렬 기준과 정렬 순서 추가
    @Transactional(readOnly = true)
    public List<CourseResponseDto> getCoursesByCategory(CategoryTypeEnum category, String sortField, String sortOrder) {
        Sort sort = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        List<Course> courses = courseRepository.findByCategory(category, sort);
        return courses.stream()
                .map(course -> new CourseResponseDto(
                        course.getCourseId(),
                        course.getTitle(),
                        course.getPrice(),
                        course.getCategory()
                        // 강사 정보는 여기서 제외
                ))
                .collect(Collectors.toList());
    }


    // 선택 강의 수정
    @Transactional
    public void updateCourse(Long id, CourseRequestDto requestDto) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new CustomException.CourseNotFoundException());
        course.update(requestDto);
        //courseRepository.save(course);
    }

    // 선택 강의 삭제
    @Transactional
    public void deleteCourse(Long id) {
        if(!courseRepository.existsById(id)) {
            throw new CustomException.CourseNotFoundException();
        }
        courseRepository.deleteById(id);
    }

}
