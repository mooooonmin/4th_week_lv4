package com.level4.office.domain.course.entity;

import java.util.Arrays;
import java.util.Optional;

public enum CourseCategory {
    SPRING, REACT, NODE;

    public static Optional<CourseCategory> getEnumIgnoreCase(String value) {
        return Arrays.stream(CourseCategory.values())
                .filter(e -> e.name().equalsIgnoreCase(value))
                .findFirst();
    }

}
