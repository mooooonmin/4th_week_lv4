package com.level4.office.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "likes", uniqueConstraints = { @UniqueConstraint(columnNames = {"user_id", "course_id"})})
@NoArgsConstructor
public class Like {

    // 한 사용자가 같은 강의에 대해 여러 번 좋아요 불가능
    // 한 강의에 여러 사용자가 좋아요 가능

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @Column(name = "is_liked")
    private boolean isLiked = false;

    public Like(User user, Course course) {
        this.user = user;
        this.course = course;
    }
}
