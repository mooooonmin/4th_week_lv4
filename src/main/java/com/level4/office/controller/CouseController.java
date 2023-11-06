package com.level4.office.controller;

import com.level4.office.dto.course.CourseRequestDto;
import com.level4.office.dto.course.CourseResponseDto;
import com.level4.office.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CouseController {

    private final CourseService courseService;

    // 강의 등록
    @Secured("ROLE_ADMIN")
    @PostMapping("/course")
    public ResponseEntity<CourseResponseDto> createCourse(@RequestBody CourseRequestDto requestDto) {
        CourseResponseDto responseDto = courseService.createCourse(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    // 강의 조회
    @GetMapping("/course/{id}")
    public ResponseEntity<CourseResponseDto> getCourseById(@PathVariable Long id) {
        CourseResponseDto course = courseService.getCourseById(id);
        return new ResponseEntity<>(course, HttpStatus.OK);
    }

    // 강의의 강의까지 조회
    @Secured({"ROLE_STAFF", "ROLE_MANAGER"})
    @GetMapping("/courses")
    public ResponseEntity<List<CourseResponseDto>> getAllCourses() {
        List<CourseResponseDto> courses = courseService.getAllCourses();
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    // 선택 강의 수정
    @Secured("ROLE_ADMIN")
    @PutMapping("/course/{id}")
    public ResponseEntity<?> updateCourse(@PathVariable Long id,
                                          @RequestBody CourseRequestDto requestDto) {
        try {
            courseService.updateCourse(id, requestDto);
            return ResponseEntity.ok("강의 정보가 성공적으로 수정되었습니다");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 선택 강의 삭제
    @Secured("ROLE_ADMIN")
    @DeleteMapping("/course/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable Long id) {
        try {
            courseService.deleteCourse(id);
            return ResponseEntity.ok().body("강의 정보가 삭제되었습니다.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
