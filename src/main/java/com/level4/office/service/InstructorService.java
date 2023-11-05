package com.level4.office.service;

import com.level4.office.dto.instructor.InstructorRequestDto;
import com.level4.office.dto.instructor.InstructorResponseDto;
import com.level4.office.entity.Instructor;
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

    // 선택한 강사 정보 조회 TODO 이름으로 찾기로 변경
    @Transactional(readOnly = true)
    public InstructorResponseDto getInstructorById(Long id) {
        Instructor instructor = instructorRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 강사 정보를 찾을 수 없습니다"));
        return new InstructorResponseDto(instructor);
    }

    // 선택 강사 수정 TODO 이름으로 찾기로 변경
    @Transactional
    public void updateInstructor(Long id, InstructorRequestDto requestDto) {
        // 강사 정보 찾고 수정 - 강사 아이디로 찾기
        Instructor instructor = instructorRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 강사 정보를 찾을 수 없습니다"));

        instructor.update(requestDto);
    }

    // 선택 강사 삭제 TODO 이름으로 찾기로 변경하기
    @Transactional
    public void deleteInstructor(Long id) {
        // 강사 정보가 있는지 확인
        if (!instructorRepository.existsById(id)) {
            throw new NoSuchElementException("해당 강사 정보를 찾을 수 없습니다");
        }
        // 강사 정보 삭제
        instructorRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public InstructorResponseDto getInstructorByName(String name) {
        Instructor instructor = instructorRepository.findByName(name)
                .orElseThrow(() -> new NoSuchElementException("해당 강사 정보를 찾을 수 없습니다"));
        return buildInstructorResponseDto(instructor);
    }

    @Transactional(readOnly = true)
    public List<InstructorResponseDto> getAllInstructors() {
        List<Instructor> instructors = instructorRepository.findAll();
        return instructors.stream()
                .map(this::buildInstructorResponseDto)
                .collect(Collectors.toList());
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
}