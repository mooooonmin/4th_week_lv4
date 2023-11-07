package com.level4.office.repository;

import com.level4.office.entity.Course;
import com.level4.office.entity.enumType.CategoryTypeEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query("SELECT c FROM Course c WHERE c.instructorName = :instructorName")
    List<Course> findByInstructorName(@Param("instructorName") String instructorName);

    @Modifying
    @Query("DELETE FROM Course c WHERE c.title = :title")
    void deleteByTitle(String title);

    Page<Course> findAll(Pageable pageable);

    // 강사 이름 기준으로 삭제
    void deleteByInstructorName(String name);

    // 강의명으로 찾기
    Optional<Course> findByTitle(String title);

    //List<Course> findByCategory(CategoryTypeEnum category, Sort sort);

    boolean existsByTitle(String title);

    // 페이지네이션
    Page<Course> findByCategory(CategoryTypeEnum category, Pageable pageable);

}
