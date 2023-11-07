package com.level4.office.dto.course;

import com.level4.office.dto.comment.CommentResponseDto;
import com.level4.office.entity.Course;
import com.level4.office.entity.enumType.CategoryTypeEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class CourseResponseDto {

    private Long courseId;
    private String title;
    private int price;
    private String courseInfo;
    private CategoryTypeEnum category;
    private List<CommentResponseDto> comments; // 댓글

    public CourseResponseDto(Course course) {
        this.courseId = course.getCourseId();
        this.title = course.getTitle();
        this.price = course.getPrice();
        this.courseInfo = course.getCourseInfo();
        this.category = course.getCategory();
        this.comments = course.getComments().stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
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
