package com.level4.office.global.config;

import com.level4.office.domain.course.entity.CourseCategory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToCourseCategoryConverter implements Converter<String, CourseCategory> {
    
    @Override
    public CourseCategory convert(String source) {
        try {
            return CourseCategory.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

}
