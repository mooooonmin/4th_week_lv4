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
    private long likesCount; // 좋아요 수

    public CourseResponseDto(Course course) {
        this.courseId = course.getCourseId();
        this.title = course.getTitle();
        this.price = course.getPrice();
        this.courseInfo = course.getCourseInfo();
        this.category = course.getCategory();
        this.comments = course.getComments().stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
        this.likesCount = course.getLikes().stream().filter(Like::isLiked).count(); // 좋아요 수 계산하여 초기화
    }


    // 강사 이름을 제외
    public CourseResponseDto(Long courseId,
                             String title,
                             int price,
                             CategoryTypeEnum category,
                             String courseInfo,
                             long likesCount) {
        this.courseId = courseId;
        this.title = title;
        this.price = price;
        this.category = category;
        this.courseInfo = courseInfo;
        this.likesCount = likesCount;
    }
}
