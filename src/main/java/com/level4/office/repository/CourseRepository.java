package com.level4.office.repository;

import com.level4.office.entity.Course;
import com.level4.office.entity.enumType.CategoryTypeEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query("SELECT c FROM Course c WHERE c.instructor.instructorName = :instructorName")
    List<Course> findCoursesByInstructorName(@Param("instructorName") String instructorName);
    // 조건 정성
    Page<Course> findAll(Pageable pageable);

    void deleteByInstructorName(String name);


    boolean existsById(Long id);

    List<Course> findByCategory(CategoryTypeEnum category, Sort sort);

    // 강사 이름으로 강의를 찾기
    List<Course> findByInstructorName(String instructorName);

}
