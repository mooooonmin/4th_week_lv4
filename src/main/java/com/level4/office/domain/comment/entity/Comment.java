package com.level4.office.domain.comment.entity;

import com.level4.office.domain.comment.dto.CommentRequestDto;
import com.level4.office.domain.course.entity.Course;
import com.level4.office.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comments")
public class Comment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Comment(Course course, User user, CommentRequestDto comment) {
        this.course = course;
        this.user = user;
        this.comment = comment.getComment();
    }

}
