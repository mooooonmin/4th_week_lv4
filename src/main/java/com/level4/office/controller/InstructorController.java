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


    // TODO 되는데 왜 get 권한이 필요하지?

    // 모든 강사 조회
    @Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_GUEST"})
    @GetMapping("/instructors")
    public ResponseEntity<List<InstructorResponseDto>> getAllInstructors() {
        List<InstructorResponseDto> instructors = instructorService.getAllInstructors();
        return new ResponseEntity<>(instructors, HttpStatus.OK);
    }

    // 강사 조회
    @Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_GUEST"})
    @GetMapping("/instructor/{instructorName}")
    public ResponseEntity<InstructorResponseDto> getInstructorByName(@PathVariable String instructorName) {
        InstructorResponseDto instructor = instructorService.getInstructorByName(instructorName);
        return new ResponseEntity<>(instructor, HttpStatus.OK);
    }

    // 강사 수정
    @Secured("ROLE_ADMIN")
    @PatchMapping("/instructor/{instructorName}")
    public ResponseEntity<String> updateInstructor(@PathVariable String instructorName,
                                                   @RequestBody InstructorRequestDto requestDto) {
        instructorService.updateInstructor(requestDto, instructorName);
        return ResponseEntity.ok("강사 정보가 수정되었습니다.");
    }

    // 강사 삭제
    @Secured("ROLE_ADMIN")
    @DeleteMapping("/instructor/{instructorName}")
    public ResponseEntity<String> deleteInstructor(@PathVariable String instructorName) {
        instructorService.deleteInstructor(instructorName);
        return ResponseEntity.ok().body("강사 정보가 삭제되었습니다.");
    }
}

