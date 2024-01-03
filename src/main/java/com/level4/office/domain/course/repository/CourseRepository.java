package com.level4.office.domain.course.repository;

import com.level4.office.domain.course.entity.Course;
import com.level4.office.domain.course.entity.CourseCategory;
import com.level4.office.domain.instructor.entity.Instructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findByInstructorOrderByRegistrationDateDesc(Instructor instructor);
    List<Course> findByCategoryOrderByRegistrationDateDesc(CourseCategory category);
    List<Course> findByCategory(CourseCategory category, Sort sort);
    void deleteByInstructorId(Long instructorId);

}
