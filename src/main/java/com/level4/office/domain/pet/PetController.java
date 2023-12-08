package com.level4.office.domain.pet;

import com.level4.office.domain.pet.dto.PetRequestDto;
import com.level4.office.domain.pet.dto.PetResponseDto;
import com.level4.office.domain.user.entity.User;
import com.level4.office.global.dto.ApiResponseDto;
import com.level4.office.global.tool.LoginAccount;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PetController {

    private final PetService petService;

    // 마이페이지 반려동물 조회
    @GetMapping("/pets/mypage/{userId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponseDto<List<PetResponseDto>>> getAllPets(@PathVariable("userId") Long userId) {
        ApiResponseDto<List<PetResponseDto>> response = petService.getAllPetsForUser(userId);
        return ResponseEntity.ok(response);
    }

    // 반려동물 등록
    @PostMapping("/user/{userId}/pets")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> registerPet(
            @LoginAccount User user,
            @RequestParam("petName") String petName,
            @RequestParam("birthday") String birthday,
            @RequestParam("petInfo") String petInfo,
            @RequestParam("imageUrl") MultipartFile image) throws IOException {

        PetRequestDto requestDto = new PetRequestDto(petName, birthday, petInfo, image);
        petService.registerPet(user.getUserId(), requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // 반려동물 상세 조회
    @GetMapping("/user/{userId}/pets/{petId}")
    public ResponseEntity<PetResponseDto> getPet(@PathVariable("userId") Long userId,
                                                 @PathVariable("petId") Long petId) {
        PetResponseDto petResponse = petService.getPet(userId, petId);
        return ResponseEntity.ok(petResponse);
    }

    // 반려동물 수정
    @PutMapping("/user/{userId}/pets/{petId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> updatePet(
            @LoginAccount User user,
            @PathVariable("petId") Long petId,
            @RequestParam("petName") String petName,
            @RequestParam("birthday") String birthday,
            @RequestParam("petInfo") String petInfo,
            @RequestParam("imageUrl") MultipartFile image) throws IOException {

        PetRequestDto requestDto = new PetRequestDto(petName, birthday, petInfo, image);
        petService.updatePet(user.getUserId(), petId, requestDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
