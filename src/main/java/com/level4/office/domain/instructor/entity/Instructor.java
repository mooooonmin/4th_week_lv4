package com.level4.office.domain.instructor.entity;

import com.level4.office.domain.course.entity.Course;
import com.level4.office.domain.instructor.dto.InstructorRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "instructors")
public class Instructor {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int experience;

    @Column(nullable = false)
    private String company;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false, length = 1000)
    private String bio;

    @OneToMany(mappedBy = "instructor", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Course> courses;

    public Instructor(InstructorRequestDto instructorRequestDto) {

        this.name = instructorRequestDto.getName();
        this.experience = instructorRequestDto.getExperience();
        this.company = instructorRequestDto.getCompany();
        this.phoneNumber = instructorRequestDto.getPhoneNumber();
        this.bio = instructorRequestDto.getBio();
    }

    public void updateInstructorDetails(InstructorRequestDto requestDto) {
        if (requestDto.getName() != null) {
            this.name = requestDto.getName();
        }
        if (requestDto.getExperience() >= 0) {
            this.experience = requestDto.getExperience();
        }
        if (requestDto.getCompany() != null) {
            this.company = requestDto.getCompany();
        }
        if (requestDto.getPhoneNumber() != null) {
            this.phoneNumber = requestDto.getPhoneNumber();
        }
        if (requestDto.getBio() != null) {
            this.bio = requestDto.getBio();
        }
    }

}
