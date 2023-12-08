package com.level4.office.domain.pet.entity;

import com.level4.office.domain.pet.dto.PetRequestDto;
import com.level4.office.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "pets")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long petId;

    @Column(nullable = false)
    private String petName;

    @Column(nullable = false)
    private String birthday;

    @Column(nullable = false)
    private String petInfo;

    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Pet(User user, String petName, String birthday, String petInfo, String imageUrl) {
        this.user = user;
        this.petName = petName;
        this.birthday = birthday;
        this.petInfo = petInfo;
        this.imageUrl = imageUrl;
    }

    public static Pet of(PetRequestDto requestDto, User user, String imageUrl) {
        return Pet.builder()
                .user(user)
                .petName(requestDto.getPetName())
                .birthday(requestDto.getBirthday())
                .petInfo(requestDto.getPetInfo())
                .imageUrl(imageUrl)
                .build();
    }

    public void updatePetDetails(String petName, String birthday, String petInfo, String imageUrl) {
        this.petName = petName;
        this.birthday = birthday;
        this.petInfo = petInfo;
        this.imageUrl = imageUrl;
    }
}
