package com.level4.office.dto.course;

import com.level4.office.entity.enumType.CategoryTypeEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseRequestDto {
    private String title;
    private int price;
    private String courseInfo;
    private CategoryTypeEnum category;
    private String instructorName;
}
