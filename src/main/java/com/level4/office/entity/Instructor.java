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

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "career", nullable = false)
    private String career;

    @Column(name = "company", nullable = false)
    private String company;

    @Column(name = "phone_num", nullable = false)
    private String phoneNum;

    @Column(name = "info", nullable = false)
    private String info;

    @OneToMany(mappedBy = "instructor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Course> courses = new ArrayList<>();


    public Instructor(InstructorRequestDto requestDto) {
        this.name = requestDto.getName();
        this.career = requestDto.getCareer();
        this.company = requestDto.getCompany();
        this.phoneNum = requestDto.getPhoneNum();
        this.info = requestDto.getInfo();
    }

    public void update(InstructorRequestDto requestDto) {
        this.name = requestDto.getName();
        this.career = requestDto.getCareer();
        this.company = requestDto.getCompany();
        this.phoneNum = requestDto.getPhoneNum();
        this.info = requestDto.getInfo();
    }
}
