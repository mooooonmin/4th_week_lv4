package com.level4.office.domain.course.dto;

import com.level4.office.domain.course.entity.CourseCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CourseRequestDto {

    @NotBlank(message = "강의명을 입력해주세요.")
    private String title;

    @NotNull(message = "가격을 입력해주세요.")
    @PositiveOrZero(message = "올바른 가격을 입력해주세요")
    private Integer price;

    @NotBlank(message = "강의를 소개해주세요.")
    @Size(max = 1000, message = "강의 소개는 1000자를 초과할 수 없습니다.")
    private String description;

    @NotNull(message = "카테고리는 필수입니다.")
    private CourseCategory category;

    @NotNull(message = "강사 ID를 입력해주세요.")
    private Long instructorId;

}
