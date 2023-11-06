package com.level4.office.dto.course;

import com.level4.office.entity.Course;
import com.level4.office.entity.enumType.CategoryTypeEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CourseResponseDto {

    private Long courseId;
    private String title;
    private int price;
    private String courseInfo;
    private CategoryTypeEnum category;

    public CourseResponseDto(Course course) {
        this.courseId = course.getCourseId();
        this.title = course.getTitle();
        this.price = course.getPrice();
        this.courseInfo = course.getCourseInfo();
        this.category = course.getCategory();
    }

    // 강사 이름을 제외
    public CourseResponseDto(Long courseId, String title, int price, CategoryTypeEnum category, String courseInfo) {
        this.courseId = courseId;
        this.title = title;
        this.price = price;
        this.category = category;
        this.courseInfo = courseInfo;
    }
}
