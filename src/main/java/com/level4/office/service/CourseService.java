package com.level4.office.service;

import com.level4.office.dto.course.CourseResponseDto;
import com.level4.office.entity.Course;
import com.level4.office.repository.CourseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;

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
}
