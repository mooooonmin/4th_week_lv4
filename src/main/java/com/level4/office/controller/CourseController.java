package com.level4.office.controller;

import com.level4.office.dto.course.CourseRequestDto;
import com.level4.office.dto.course.CourseResponseDto;
import com.level4.office.entity.enumType.CategoryTypeEnum;
import com.level4.office.service.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.level4.office.jwt.JwtUtil.logger;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;


    // 강의 등록
    @Secured("ROLE_ADMIN")
    @PostMapping("/course")
    public ResponseEntity<CourseResponseDto> createCourse(@RequestBody CourseRequestDto requestDto) {
        CourseResponseDto responseDto = courseService.createCourse(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    // 강의 조회
    @Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_GUEST"})
    @GetMapping("/course/{title}")
    public ResponseEntity<CourseResponseDto> getCourseByTitle(@PathVariable String title) {
        CourseResponseDto course = courseService.getCourseByTitle(title);
        return new ResponseEntity<>(course, HttpStatus.OK);
    }

    // 강사의 강의까지 조회
    @Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_GUEST"})
    @GetMapping("/courses")
    public ResponseEntity<List<CourseResponseDto>> getAllCourses() {
        List<CourseResponseDto> courses = courseService.getAllCourses();
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    // 선택 강의 수정
    @Secured("ROLE_ADMIN")
    @PutMapping("/course/{title}")
    public ResponseEntity<?> updateCourse(@PathVariable String title,
                                          @RequestBody CourseRequestDto requestDto) {
        try {
            courseService.updateCourse(title, requestDto);
            return ResponseEntity.ok("강의 정보가 성공적으로 수정되었습니다.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 선택 강의 삭제
    @Secured("ROLE_ADMIN")
    @DeleteMapping("/course/{title}")
    public ResponseEntity<?> deleteCourse(@PathVariable String title) {
        try {
            courseService.deleteCourse(title);
            return ResponseEntity.ok().body("강의 정보가 삭제되었습니다.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 강의 카테고리별 조회
    @Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_GUEST"})
    @GetMapping("/courses/category/{category}")
    public ResponseEntity<List<CourseResponseDto>> getCoursesByCategory(
            @RequestParam("category") String category,
            @RequestParam(value = "sortField", defaultValue = "title") String sortField,
            @RequestParam(value = "sortOrder", defaultValue = "asc") String sortOrder) {

        CategoryTypeEnum categoryType;
        try {
            categoryType = CategoryTypeEnum.valueOf(category.toUpperCase());
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<CourseResponseDto> courses = courseService.getCoursesByCategory(
                categoryType,
                sortField,
                sortOrder);
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }
}
