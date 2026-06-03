package com.naveen.ecommerce_backend.dto.userProfile;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.naveen.ecommerce_backend.model.user.Gender;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProfileResponseDto {

    private Long id;

    private String name;

    private String email;

    private String phoneNumber;

    private String profileImageUrl;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    private Gender gender;

    private String bio;
}
