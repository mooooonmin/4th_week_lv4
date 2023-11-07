package com.level4.office.entity;

import com.level4.office.dto.comment.CommentRequestDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    // TODO 자기참조 확인하기

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent; // 부모댓글

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> children = new ArrayList<>();

    private String content;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdDate; // 댓글 단 시각

    @UpdateTimestamp
    private LocalDateTime updatedDate; // 댓글 수정 시각

    public Comment(CommentRequestDto requestDto, User user, Course course, Comment parent) {
        this.content = requestDto.getContent();
        this.user = user;
        this.course = course;
        this.parent = parent;
    }

    // 자식 댓글 초기화
    public List<Comment> getChildren() {
        if (this.children == null) {
            this.children = new ArrayList<>();
        }
        return this.children;
    }

}
