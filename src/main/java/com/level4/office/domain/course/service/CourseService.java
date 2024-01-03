package com.level4.office.domain.course.service;

import com.level4.office.domain.comment.dto.CommentResponseDto;
import com.level4.office.domain.comment.entity.Comment;
import com.level4.office.domain.comment.repository.CommentRepository;
import com.level4.office.domain.course.dto.CourseRequestDto;
import com.level4.office.domain.course.dto.CourseResponseDto;
import com.level4.office.domain.course.entity.Course;
import com.level4.office.domain.course.entity.CourseCategory;
import com.level4.office.domain.course.repository.CourseRepository;
import com.level4.office.domain.instructor.dto.InstructorResponseDto;
import com.level4.office.domain.instructor.entity.Instructor;
import com.level4.office.domain.instructor.repository.InstructorRepository;
import com.level4.office.domain.like.repository.LikeRepository;
import com.level4.office.exception.CustomException;
import com.level4.office.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final LikeRepository likeRepository;
    private final CourseRepository courseRepository;
    private final CommentRepository commentRepository;
    private final InstructorRepository instructorRepository;

    /*강의 등록*/
    public CourseResponseDto registerCourse(CourseRequestDto courseRequestDto) {
        Instructor instructor = validateGetInstructor(courseRequestDto.getInstructorId());
        InstructorResponseDto instructorResponseDto = new InstructorResponseDto(instructor);
        CourseCategory category = convertToCourseCategory(String.valueOf(courseRequestDto.getCategory()));
        Course course = new Course(instructor, courseRequestDto, category);
        Course savedCourse = courseRepository.save(course);
        return new CourseResponseDto(savedCourse, instructorResponseDto);
    }

    /*강의 조회 및 댓글 가져오기*/
    public CourseResponseDto getCourseDetails(Long courseId) {
        Course course = validateGetCourse(courseId);
        Instructor instructor = course.getInstructor();
        InstructorResponseDto instructorResponseDto = new InstructorResponseDto(instructor);

        List<Comment> comments = commentRepository.findByCourseId(courseId);
        List<CommentResponseDto> commentResponseDtos = comments.stream()
                .map(comment -> new CommentResponseDto(comment.getId(), comment.getComment()))
                .toList();
        long likesCount = likeRepository.countByCourse(course);
        return new CourseResponseDto(course, instructorResponseDto, commentResponseDtos, likesCount);
    }

    /*강사별 강의 조회*/
    public List<CourseResponseDto> getCoursesFromSelectedInstructor(Long instructorId) {
        Instructor instructor = validateGetInstructor(instructorId);
        List<Course> courses = courseRepository.findByInstructorOrderByRegistrationDateDesc(instructor);
        return convertToCourseResponseDtoList(courses);
    }

    /*카테고리별 강의 조회*/
    public List<CourseResponseDto> getCoursesByCategory(CourseCategory category, String sortBy, String direction) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        List<Course> courses = courseRepository.findByCategory(category, sort);
        return convertToCourseResponseDtoList(courses);
    }

    public CourseResponseDto reviseCourseDetails(Long courseId, CourseRequestDto courseRequestDto) {
        Instructor instructor = validateGetInstructor(courseRequestDto.getInstructorId());
        InstructorResponseDto instructorResponseDto = new InstructorResponseDto(instructor);
        Course course = validateGetCourse(courseId);
        course.updateCourseDetails(courseRequestDto);
        Course updatedCourse = courseRepository.save(course);
        return new CourseResponseDto(updatedCourse, instructorResponseDto);
    }

    public void deleteCourse(Long courseId) {
        Course course = validateGetCourse(courseId);
        courseRepository.delete(course);
    }

    private Course validateGetCourse(Long courseId) {
        return courseRepository.findById(courseId).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_COURSE));
    }

    private Instructor validateGetInstructor(Long instructorId) {
        return instructorRepository.findById(instructorId).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_INSTRUCTOR));
    }

    private CourseCategory convertToCourseCategory(String categoryStr) {
        return CourseCategory.getEnumIgnoreCase(categoryStr)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_CATEGORY));
    }

    private List<CourseResponseDto> convertToCourseResponseDtoList(List<Course> courses) {
        return courses.stream()
                .map(CourseResponseDto::new)
                .toList();
    }

}
