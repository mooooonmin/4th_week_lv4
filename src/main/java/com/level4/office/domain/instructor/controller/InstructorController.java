package com.level4.office.domain.instructor.controller;

import com.level4.office.domain.instructor.dto.InstructorRequestDto;
import com.level4.office.domain.instructor.dto.InstructorResponseDto;
import com.level4.office.domain.instructor.service.InstructorService;
import com.level4.office.global.dto.SuccessMessageDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/instructors")
public class InstructorController {

    private final InstructorService instructorService;

    /*강사 등록*/
    @PostMapping("/register")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public InstructorResponseDto registerInstructor(@Valid @RequestBody InstructorRequestDto instructorRequestDto) {
        return instructorService.registerInstructor(instructorRequestDto);
    }

    /*강의 조회*/
    @GetMapping("/{instructorId}")
    public InstructorResponseDto getInstructor(@PathVariable Long instructorId) {
        return instructorService.getInstructorDetails(instructorId);
    }

    /*강사 수정*/
    @PatchMapping("/{instructorId}/update")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public InstructorResponseDto reviseInstructorDetails(@PathVariable Long instructorId, @Valid @RequestBody InstructorRequestDto instructorRequestDto) {
        return instructorService.reviseInstructorDetails(instructorId, instructorRequestDto);
    }

    /*강사 삭제*/
    @DeleteMapping("/{instructorId}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public SuccessMessageDto deleteInstructor(@PathVariable Long instructorId) {
        instructorService.deleteInstructor(instructorId);
        return new SuccessMessageDto("해당 강사와 강의들이 삭제되었습니다");
    }

}
