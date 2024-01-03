package com.level4.office.domain.course.controller;

import com.level4.office.domain.course.dto.CourseRequestDto;
import com.level4.office.domain.course.dto.CourseResponseDto;
import com.level4.office.domain.course.entity.CourseCategory;
import com.level4.office.domain.course.service.CourseService;
import com.level4.office.global.dto.SuccessMessageDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    @PostMapping("/register")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public CourseResponseDto registerCourse(@RequestBody CourseRequestDto courseRequestDto) {
        return courseService.registerCourse(courseRequestDto);
    }

    @GetMapping("/{courseId}")
    public CourseResponseDto getCourse(@PathVariable Long courseId) {
        return courseService.getCourseDetails(courseId);
    }

    @GetMapping("/{instructorId}/allCourses")
    public List<CourseResponseDto> getCoursesByInstructor(@PathVariable Long instructorId) {
        return courseService.getCoursesFromSelectedInstructor(instructorId);
    }

    /*카테고리별 강의 목록 조회*/
    @GetMapping("/category/sorted")
    public List<CourseResponseDto> getCoursesByCategory(@RequestParam("category") CourseCategory category,
                                                        @RequestParam("sortBy") Optional<String> sortBy,
                                                        @RequestParam("direction") Optional<String> direction) {

        String sortField = sortBy.filter(field -> List.of("title", "price", "registrationDate").contains(field))
                .orElse("registrationDate");
        String sortDir = direction.filter(dir -> List.of("asc", "desc").contains(dir))
                .orElse("desc");
        return courseService.getCoursesByCategory(category, sortField, sortDir);
    }

    @PatchMapping("/{courseId}/update")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public CourseResponseDto reviseCourseDetails(@PathVariable Long courseId,
                                                 @Valid @RequestBody CourseRequestDto courseRequestDto) {
        return courseService.reviseCourseDetails(courseId, courseRequestDto);
    }

    @DeleteMapping("/{courseId}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public SuccessMessageDto deleteCourse(@PathVariable Long courseId) {
        courseService.deleteCourse(courseId);
        return new SuccessMessageDto("해당 강의가 삭제되었습니다.");
    }

}
