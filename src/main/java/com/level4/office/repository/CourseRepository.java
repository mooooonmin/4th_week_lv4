package com.level4.office.repository;

import com.level4.office.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CourseRepository {

    // 조건 정렬
    Page<Course> findAll(Pageable pageable);

    // 강의 제목으로 강의 조회
    Optional<Course> findByTitle(String title);

    // 강사 이름으로 강의 목록 조회
    List<Course> findByInstructorName(String instructorName);

    // 카테고리로 강의 목록 조회
    List<Course> findByCategory(String category);
}
}
