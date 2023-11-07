package com.level4.office.repository;

import com.level4.office.entity.Course;
import com.level4.office.entity.Like;
import com.level4.office.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    // 특정 강의에 대한 좋아요가 눌린 수를 카운트
    long countByCourseAndIsLikedTrue(Course course);


    // 특정 강의와 특정 사용자에 대한 좋아요 엔티티 찾기
    Optional<Like> findByCourseAndUser(Course course, User user);
}
