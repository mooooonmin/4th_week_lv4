package com.level4.office.domain.instructor.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InstructorRequestDto {

    @NotBlank(message = "이름을 입력해주세요.")
    private String name;

    @NotNull
    @Min(value = 1, message = "경력은 1년 이상이어야 합니다.")
    private int experience;

    @NotBlank(message = "회사명을 입력해주세요.")
    private String company;


    @NotBlank(message = "전화번호를 입력해주세요.")
    @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$",
            message = "전화번호 형식이 올바르지 않습니다. (예: 010-1234-5678)")
    private String phoneNumber;

    @Size(max = 500, message = "소개는 500자 이내여야 합니다.")
    private String bio;

}
