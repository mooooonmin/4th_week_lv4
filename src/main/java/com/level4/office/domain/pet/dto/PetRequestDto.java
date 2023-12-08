package com.level4.office.domain.pet.dto;

import com.level4.office.global.image.validation.FileExtension;
import com.level4.office.global.image.validation.FileSize;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PetRequestDto {

    private String petName;
    private String birthday;
    private String petInfo;

    @FileSize(max = 1048576, message = "이미지 파일은 1MB 이하이어야 합니다")
    @FileExtension(ext = "png,jpg,jpeg", message = "이미지 파일은 png, jpg, jpeg 형식이어야 합니다")
    private MultipartFile imageUrl;

    @Builder
    public PetRequestDto(String petName, String birthday, String petInfo, MultipartFile imageUrl) {
        this.petName = petName;
        this.birthday = birthday;
        this.petInfo = petInfo;
        this.imageUrl = imageUrl;
    }
}
