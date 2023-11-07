package com.level4.office.repository;

import com.level4.office.entity.Comment;
import com.level4.office.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    // 강의에 해당하는 모든 댓글 삭제
    void deleteByCourse(Course course);
}
