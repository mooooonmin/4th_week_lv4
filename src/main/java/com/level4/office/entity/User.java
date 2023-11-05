package com.level4.office.entity;

import com.level4.office.dto.UserJoinRequestDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userid;

    @NotBlank
    @Email
    @Column(unique = true)
    private String email;

    @NotBlank
    @Size(min = 8, max = 15)
    private String password;

    @NotBlank
    @Column(unique = true)
    private String nickName;

    @Enumerated(EnumType.STRING)
    private UserRoleEnum role;

    @NotBlank
    private String gender;

    @NotBlank
    private String phoneNum;

    @NotBlank
    private String address;

    public User(UserJoinRequestDto requestDto) {
    }

}
