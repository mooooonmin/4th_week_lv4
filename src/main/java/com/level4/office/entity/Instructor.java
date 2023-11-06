package com.level4.office.entity;

import com.level4.office.dto.instructor.InstructorRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Table(name = "instructor")
@Entity
@NoArgsConstructor
public class Instructor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long instructorId;

    @Column(name = "instructor_name", nullable = false, unique = true)
    private String instructorName;

    @Column(name = "career", nullable = false)
    private String career;

    @Column(name = "company", nullable = false)
    private String company;

    @Column(name = "phone_num", nullable = false)
    private String phoneNum;

    @Column(name = "info", nullable = false)
    private String info;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true) // 강사가없는 강의는 자동삭제
    @JoinColumn(name = "instructor_id") // fk 지정
    private List<Course> courses = new ArrayList<>();

    public Instructor(InstructorRequestDto requestDto) {
        this.instructorName = requestDto.getInstructorName();
        this.career = requestDto.getCareer();
        this.company = requestDto.getCompany();
        this.phoneNum = requestDto.getPhoneNum();
        this.info = requestDto.getInfo();
    }

    public void update(InstructorRequestDto requestDto) {
        this.instructorName = requestDto.getInstructorName();
        this.career = requestDto.getCareer();
        this.company = requestDto.getCompany();
        this.phoneNum = requestDto.getPhoneNum();
        this.info = requestDto.getInfo();
    }
}
