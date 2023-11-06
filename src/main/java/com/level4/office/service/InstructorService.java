package com.level4.office.service;

import com.level4.office.dto.course.CourseResponseDto;
import com.level4.office.dto.instructor.InstructorRequestDto;
import com.level4.office.dto.instructor.InstructorResponseDto;
import com.level4.office.entity.Instructor;
import com.level4.office.exception.ErrorMessage;
import com.level4.office.repository.CourseRepository;
import com.level4.office.repository.InstructorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InstructorService {

    private final InstructorRepository instructorRepository;
    private final CourseRepository courseRepository;

    // 강사 등록
    @Transactional
    public InstructorResponseDto createInstructor(InstructorRequestDto requestDto) {
        Instructor instructor = new Instructor(requestDto);
        instructorRepository.save(instructor);
        return new InstructorResponseDto(instructor);
    }

    // 모든 강사 조회 (강의 정보 반화 X)
    @Transactional(readOnly = true)
    public List<InstructorResponseDto> getAllInstructors() {
        List<Instructor> instructors = instructorRepository.findAll();
        return instructors.stream()
                .map(InstructorResponseDto::new)
                .collect(Collectors.toList());
    }

    // 강사 단일 조회 -> 강사의 강의까지 조회
    @Transactional(readOnly = true)
    public InstructorResponseDto getInstructorByName(String name) {
        return instructorRepository.findByName(name)
                .map(this::buildInstructorResponseDto)
                .orElseThrow(() -> new NoSuchElementException(ErrorMessage.DATA_NOT_FOUND.getMessage()));
    }


    private InstructorResponseDto buildInstructorResponseDto(Instructor instructor) {
        List<CourseResponseDto> courses = courseRepository.findByInstructorNameOrderByRegDateDesc(instructor.getName())
                .stream()
                .map(CourseResponseDto::new)
                .collect(Collectors.toList());

        InstructorResponseDto responseDto = new InstructorResponseDto(instructor);
        responseDto.setCourses(courses);
        return responseDto;
    }

    // 선택 강사 수정
    @Transactional
    public void updateInstructor(InstructorRequestDto requestDto, String name) {
        Instructor instructor = instructorRepository.findByName(name)
                .orElseThrow(() -> new NoSuchElementException(ErrorMessage.DATA_NOT_FOUND.getMessage()));

        instructor.update(requestDto);
    }

    // 강사 삭제 TODO 강사삭제시 강사의 강의 모두삭제
    @Transactional
    public void deleteInstructor(String name) {
        // 강사의 이름으로 존재하는지 확인 후
        if (!instructorRepository.existsByName(name)) {
            throw new NoSuchElementException(ErrorMessage.DATA_NOT_FOUND.getMessage());
        }
        // 강사 정보 삭제
        instructorRepository.deleteByName(name);
    }
}