package com.level4.office.domain.pet.dto;

import com.level4.office.domain.pet.entity.Pet;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PetResponseDto {

    private Long petId;
    private Long userId;
    private String petName;
    private String birthday;
    private String petInfo;
    private String imageUrl;

    @Builder
    public PetResponseDto(Long petId, Long userId, String petName, String birthday, String petInfo, String imageUrl) {
        this.petId = petId;
        this.userId = userId;
        this.petName = petName;
        this.birthday = birthday;
        this.petInfo = petInfo;
        this.imageUrl = imageUrl;
    }

    public static PetResponseDto from(Pet pet) {
        return PetResponseDto.builder()
                .petId(pet.getPetId())
                .userId(pet.getUser().getUserId())
                .petName(pet.getPetName())
                .birthday(pet.getBirthday())
                .petInfo(pet.getPetInfo())
                .imageUrl(pet.getImageUrl())
                .build();
    }
}
