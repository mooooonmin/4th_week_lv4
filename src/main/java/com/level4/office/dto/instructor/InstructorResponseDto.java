package com.level4.office.dto.instructor;

import com.level4.office.entity.Instructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class InstructorResponseDto {
    private Long instructorId;
    private String instructorName;
    private String career;
    private String company;
    private String phoneNum;
    private String info;
    private List<InstructorResponseDto> courses; // 강의 목록

    public InstructorResponseDto(Instructor instructor) {
        this.instructorId = instructor.getInstructorId();
        this.instructorName = instructor.getInstructorName();
        this.career = instructor.getCareer();
        this.company = instructor.getCompany();
        this.phoneNum = instructor.getPhoneNum();
        this.info = instructor.getInfo();
    }
}
