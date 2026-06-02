package com.naveen.ecommerce_backend.dto.userProfile;

import com.naveen.ecommerce_backend.model.user.User;

public class ProfileMapper {

    private ProfileMapper() {}

    public static ProfileResponseDto toDto(User user) {

        return ProfileResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .profileImageUrl(user.getProfileImageUrl())
                .dateOfBirth(user.getDateOfBirth())
                .gender(user.getGender())
                .bio(user.getBio())
                .build();
    }
}
