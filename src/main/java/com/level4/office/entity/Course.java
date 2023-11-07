package com.level4.office.entity;

import com.level4.office.dto.course.CourseRequestDto;
import com.level4.office.entity.enumType.CategoryTypeEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Table(name = "courses")
@Entity
@NoArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseId;

    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private String courseInfo;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDate regDate;

    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private CategoryTypeEnum category;

    @Column(name = "instructor_name")
    private String instructorName;

    @OneToMany(mappedBy = "course", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Comment> comments = new HashSet<>(); // 초기화

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Like> likes = new HashSet<>(); // 초기화

    public Course(CourseRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.price = requestDto.getPrice();
        this.courseInfo = requestDto.getCourseInfo();
        this.category = requestDto.getCategory();
        this.instructorName = requestDto.getInstructorName(); // 강사 이름 설정
    }

    public void update(CourseRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.price = requestDto.getPrice();
        this.courseInfo = requestDto.getCourseInfo();
        this.category = requestDto.getCategory();
    }

    // 좋아요 수를 반환하는 메소드 (좋아요 수 필드가 없는 경우)
    public long getLikesCount() {
        return likes.stream().filter(Like::isLiked).count();
    }
}