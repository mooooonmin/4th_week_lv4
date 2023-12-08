package com.level4.office.domain.pet;

import com.level4.office.domain.pet.dto.PetRequestDto;
import com.level4.office.domain.pet.dto.PetResponseDto;
import com.level4.office.domain.pet.entity.Pet;
import com.level4.office.domain.user.UserRepository;
import com.level4.office.domain.user.entity.User;
import com.level4.office.global.dto.ApiResponseDto;
import com.level4.office.global.exception.CustomException;
import com.level4.office.global.exception.ErrorCode;
import com.level4.office.global.image.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PetService {

    private final UserRepository userRepository;
    private final PetRepository petRepository;
    private final ImageService imageService;

    // 반려동물 등록
    @Transactional
    public PetResponseDto registerPet(Long userId, PetRequestDto requestDto) throws IOException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.ACCOUNT_NOT_FOUND));

        String imageUrl = imageService.uploadImage(requestDto.getImageUrl());

        Pet pet = Pet.of(requestDto, user, imageUrl);
        pet = petRepository.save(pet);

        return PetResponseDto.from(pet);
    }

    // 마이페이지 반려동물 조회
    public ApiResponseDto<List<PetResponseDto>> getAllPetsForUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.ACCOUNT_NOT_FOUND));

        List<PetResponseDto> pets = petRepository.findByUser(user).stream()
                .map(PetResponseDto::from)
                .collect(Collectors.toList());

        String message = pets.isEmpty() ? "아직 등록된 반려동물이 없습니다" : "반려동물 목록 조회 성공";
        return new ApiResponseDto<>(message, pets);
    }

    // 반려동물 상세 조회
    @Transactional(readOnly = true)
    public PetResponseDto getPet(Long userId, Long petId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.ACCOUNT_NOT_FOUND));

        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new CustomException(ErrorCode.PET_NOT_FOUND));

        return PetResponseDto.from(pet);
    }

    // 반려동물 수정
    @Transactional
    public PetResponseDto updatePet(Long userId, Long petId, PetRequestDto requestDto) throws IOException {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new CustomException(ErrorCode.PET_NOT_FOUND));

        if (!pet.getUser().getUserId().equals(userId)) {
            throw new CustomException(ErrorCode.ACCOUNT_NOT_FOUND);
        }

        String updatedImageUrl = imageService.updateImage(requestDto.getImageUrl(), pet.getImageUrl());
        pet.updatePetDetails(
                requestDto.getPetName(),
                requestDto.getBirthday(),
                requestDto.getPetInfo(),
                updatedImageUrl);

        return PetResponseDto.from(pet);
    }
}
