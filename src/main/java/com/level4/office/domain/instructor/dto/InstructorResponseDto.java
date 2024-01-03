package com.level4.office.domain.instructor.dto;

import com.level4.office.domain.instructor.entity.Instructor;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InstructorResponseDto {

    private Long id;
    private String name;
    private int experience;
    private String company;
    private String bio;

    public InstructorResponseDto(Instructor saveInstructor) {
        this.id = saveInstructor.getId();
        this.name = saveInstructor.getName();
        this.experience = saveInstructor.getExperience();
        this.company = saveInstructor.getCompany();
        this.bio = saveInstructor.getBio();
    }

}
