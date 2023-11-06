package com.level4.office.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.level4.office.dto.course.CourseRequestDto;
import com.level4.office.dto.course.CourseResponseDto;
import com.level4.office.entity.enumType.CategoryTypeEnum;
import com.level4.office.service.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CourseControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private CourseService courseService;

    @InjectMocks
    private CourseController courseController;

    @BeforeEach
    public void setup(WebApplicationContext context) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity()) // Spring Security를 적용한 테스트 설정
                .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    @WithMockUser(roles = "ADMIN") // ROLE_ADMIN을 가진 가상의 사용자로 테스트
    public void createCourseTest() throws Exception {
        // given
        CourseRequestDto requestDto = new CourseRequestDto();
        requestDto.setTitle("New Course");
        requestDto.setPrice(100);
        requestDto.setCourseInfo("This is a new course.");
        requestDto.setCategory(CategoryTypeEnum.SPRING);
        requestDto.setInstructorName("John Doe");

        CourseResponseDto responseDto = new CourseResponseDto();
        responseDto.setCourseId(1L);
        responseDto.setTitle(requestDto.getTitle());
        responseDto.setPrice(requestDto.getPrice());
        responseDto.setCourseInfo(requestDto.getCourseInfo());
        responseDto.setCategory(requestDto.getCategory());

        given(courseService.createCourse(requestDto)).willReturn(responseDto);

        // when & then
        mockMvc.perform(post("/api/course")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.courseId").value(responseDto.getCourseId()))
                .andExpect(jsonPath("$.title").value(requestDto.getTitle()))
                .andExpect(jsonPath("$.price").value(requestDto.getPrice()))
                .andExpect(jsonPath("$.courseInfo").value(requestDto.getCourseInfo()))
                .andExpect(jsonPath("$.category").value(requestDto.getCategory().toString()));
    }
}
