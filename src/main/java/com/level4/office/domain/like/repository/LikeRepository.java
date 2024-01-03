package com.level4.office.domain.like.repository;

import com.level4.office.domain.course.entity.Course;
import com.level4.office.domain.like.entity.Like;
import com.level4.office.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUserAndCourse(User user, Course course);

    long countByCourse(Course course);

}
