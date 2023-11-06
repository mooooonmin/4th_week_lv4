package com.level4.office.entity;

import com.level4.office.dto.course.CourseRequestDto;
import com.level4.office.entity.enumType.CategoryTypeEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Getter
@Setter
@Table(name = "course")
@Entity
@NoArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseId;

    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false)
    private int price;

    private String courseInfo;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDate regDate;

    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private CategoryTypeEnum category;

    @Column(name = "instructor_name")
    private String instructorName;


    public Course(CourseRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.price = requestDto.getPrice();
        this.courseInfo = requestDto.getCourseInfo();
        this.category = requestDto.getCategory();
    }

    public void update(CourseRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.price = requestDto.getPrice();
        this.courseInfo = requestDto.getCourseInfo();
        this.category = requestDto.getCategory();
    }
}