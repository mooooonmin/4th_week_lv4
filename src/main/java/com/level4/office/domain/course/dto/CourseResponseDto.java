package com.level4.office.domain.course.dto;

import com.level4.office.domain.comment.dto.CommentResponseDto;
import com.level4.office.domain.course.entity.Course;
import com.level4.office.domain.course.entity.CourseCategory;
import com.level4.office.domain.instructor.dto.InstructorResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
public class CourseResponseDto {

    private Long id;
    private String title;
    private Integer price;
    private long likesCount;
    private String description;
    private CourseCategory category;
    private LocalDate registrationDate;
    private InstructorResponseDto instructor;
    private List<CommentResponseDto> comment;

    public CourseResponseDto(Course savedCourse, InstructorResponseDto instructor) {
        this.id = savedCourse.getId();
        this.title = savedCourse.getTitle();
        this.price = savedCourse.getPrice();
        this.description = savedCourse.getDescription();
        this.category = savedCourse.getCategory();
        this.registrationDate = savedCourse.getRegistrationDate();
        this.instructor = instructor;
    }

    public CourseResponseDto(Course course) {
        this.id = course.getId();
        this.title = course.getTitle();
        this.price = course.getPrice();
        this.description = course.getDescription();
        this.category = course.getCategory();
        this.registrationDate = course.getRegistrationDate();
    }

    public CourseResponseDto(Course course, InstructorResponseDto instructor, List<CommentResponseDto> comments, Long likesCount) {
        this.id = course.getId();
        this.title = course.getTitle();
        this.price = course.getPrice();
        this.description = course.getDescription();
        this.category = course.getCategory();
        this.registrationDate = course.getRegistrationDate();
        this.instructor = instructor;
        this.comment = comments;
        this.likesCount = likesCount;
    }

}
