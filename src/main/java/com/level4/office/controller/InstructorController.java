package com.level4.office.controller;

import com.level4.office.dto.instructor.InstructorRequestDto;
import com.level4.office.dto.instructor.InstructorResponseDto;
import com.level4.office.service.InstructorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class InstructorController {

    private final InstructorService instructorService;

    // 강사 등록
    @Secured("ROLE_ADMIN")
    @PostMapping("/instructor")
    public ResponseEntity<InstructorResponseDto> createInstructor(@RequestBody InstructorRequestDto requestDto) {
        InstructorResponseDto responseDto = instructorService.createInstructor(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    // 강사 조회
    @Secured({"ROLE_STAFF", "ROLE_MANAGER"})
    @GetMapping("/instructor/{id}")
    public ResponseEntity<InstructorResponseDto> getInstructorById(@PathVariable String name) {
        InstructorResponseDto instructor = instructorService.getInstructorByName(name);
        return new ResponseEntity<>(instructor, HttpStatus.OK);
    }

    // 모든 강사 조회
    @Secured({"ROLE_STAFF", "ROLE_MANAGER"})
    @GetMapping("/instructors")
    public ResponseEntity<List<InstructorResponseDto>> getAllInstructors() {
        List<InstructorResponseDto> instructors = instructorService.getAllInstructors();
        return new ResponseEntity<>(instructors, HttpStatus.OK);
    }

    // 강사 수정
    @Secured("ROLE_ADMIN")
    @PatchMapping("/instructor/{id}")
    public ResponseEntity<String> updateInstructor(@PathVariable String name,
                                                   @RequestBody InstructorRequestDto requestDto) {
        instructorService.updateInstructor(requestDto, name);
        return ResponseEntity.ok("강사 정보가 수정되었습니다.");
    }

    // 강사 삭제
    @Secured("ROLE_ADMIN")
    @DeleteMapping("/instructor/{id}")
    public ResponseEntity<String> deleteInstructor(@PathVariable String name) {
        instructorService.deleteInstructor(name);
        return ResponseEntity.ok().body("강사 정보가 삭제되었습니다.");
    }
}

