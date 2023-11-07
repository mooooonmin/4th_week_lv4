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

    // 강사 단일 조회 -> 강사의 강의까지 같이 조회
    @Transactional(readOnly = true)
    public InstructorResponseDto getInstructorByName(String instructorName) {
        return instructorRepository.findByInstructorName(instructorName)
                .map(this::buildInstructorResponseDto)
                .orElseThrow(() -> new NoSuchElementException(ErrorMessage.DATA_NOT_FOUND.getMessage()));
    }

    private InstructorResponseDto buildInstructorResponseDto(Instructor instructor) {
        List<CourseResponseDto> courseDtos = courseRepository.findByInstructorName(instructor.getInstructorName())
                .stream()
                .map(courseEntity -> new CourseResponseDto(
                        courseEntity.getCourseId(),
                        courseEntity.getTitle(),
                        courseEntity.getPrice(),
                        courseEntity.getCategory(),
                        courseEntity.getCourseInfo(),
                        courseEntity.getLikesCount()
                        // 강사 이름을 제외하고 객체를 생성
                ))
                .collect(Collectors.toList());

        // 강사 정보를 담은 dto 생성
        InstructorResponseDto responseDto = new InstructorResponseDto(instructor);

        // 강의 목록 설정
        responseDto.setCourses(courseDtos);
        return responseDto;
    }

    // 선택 강사 수정
    @Transactional
    public void updateInstructor(InstructorRequestDto requestDto, String instructorName) {
        Instructor instructor = instructorRepository.findByInstructorName(instructorName)
                .orElseThrow(() -> new NoSuchElementException(ErrorMessage.DATA_NOT_FOUND.getMessage()));

        instructor.update(requestDto);
    }

    // 강사 삭제 -> 단방향 설정이라 강의 직접삭제 부분을 추가해줘야함
    @Transactional
    public void deleteInstructor(String instructorName) {
        // 강사의 이름으로 존재하는지 확인 후
        if (!instructorRepository.existsByInstructorName(instructorName)) {
            throw new NoSuchElementException(ErrorMessage.DATA_NOT_FOUND.getMessage());
        }

        // 연관된 강의들을 먼저 삭제
        courseRepository.deleteByInstructorName(instructorName);

        // 강사 정보 삭제
        instructorRepository.deleteByInstructorName(instructorName);
    }
}