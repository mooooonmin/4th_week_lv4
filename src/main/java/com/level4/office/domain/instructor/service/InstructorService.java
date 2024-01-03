package com.level4.office.domain.instructor.service;

import com.level4.office.domain.instructor.dto.InstructorRequestDto;
import com.level4.office.domain.instructor.dto.InstructorResponseDto;
import com.level4.office.domain.instructor.entity.Instructor;
import com.level4.office.domain.instructor.repository.InstructorRepository;
import com.level4.office.global.exception.CustomException;
import com.level4.office.global.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InstructorService {

    private final InstructorRepository instructorRepository;

    public InstructorResponseDto registerInstructor(InstructorRequestDto instructorRequestDto) {
        validatePhoneNumberOnCreate(instructorRequestDto.getPhoneNumber());

        Instructor instructor = new Instructor(instructorRequestDto);
        Instructor registeredInstructor = instructorRepository.save(instructor);
        return new InstructorResponseDto(registeredInstructor);
    }

    public InstructorResponseDto getInstructorDetails(Long instructorId) {
        Instructor instructor = validateGetInstructor(instructorId);
        return new InstructorResponseDto(instructor);
    }

    @Transactional
    public InstructorResponseDto reviseInstructorDetails(Long instructorId, InstructorRequestDto instructorRequestDto) {
        Instructor instructor = validateGetInstructor(instructorId);
        validatePhoneNumberOnUpdate(instructorId, instructorRequestDto.getPhoneNumber());

        instructor.updateInstructorDetails(instructorRequestDto);
        Instructor updatedInstructor = instructorRepository.save(instructor);
        return new InstructorResponseDto(updatedInstructor);
    }

    @Transactional
    public void deleteInstructor(Long instructorId) {
        Instructor instructor = validateGetInstructor(instructorId);
        instructorRepository.delete(instructor);
    }

    private Instructor validateGetInstructor(Long instructorId) {
        return instructorRepository.findById(instructorId).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_INSTRUCTOR));
    }

    private void validatePhoneNumberOnCreate(String phoneNumber) {
        if (phoneNumber != null && instructorRepository.existsByPhoneNumber(phoneNumber)) {
            throw new CustomException(ErrorCode.PHONENUMBER_ALREADY_EXISTS);
        }
    }

    private void validatePhoneNumberOnUpdate(Long instructorId, String phoneNumber) {
        instructorRepository.findByPhoneNumber(phoneNumber)
                .ifPresent(instructor -> {
                    if (!instructor.getId().equals(instructorId)) {
                        throw new CustomException(ErrorCode.PHONENUMBER_ALREADY_EXISTS);
                    }
                });
    }

}
