package com.naveen.ecommerce_backend.dto.User;

import com.naveen.ecommerce_backend.model.User;

public class UserMapper {

    public static UserResponseDto toDto(User user) {

        UserResponseDto dto = UserResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();

        return dto;
    }

}
