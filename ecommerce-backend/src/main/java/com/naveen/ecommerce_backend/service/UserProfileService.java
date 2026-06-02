package com.naveen.ecommerce_backend.service;

import com.naveen.ecommerce_backend.constants.CloudinaryFolders;
import com.naveen.ecommerce_backend.dto.product.CloudinaryResponse;
import com.naveen.ecommerce_backend.dto.userProfile.ProfileMapper;
import com.naveen.ecommerce_backend.dto.userProfile.ProfileResponseDto;
import com.naveen.ecommerce_backend.dto.userProfile.UpdateProfileRequest;
import com.naveen.ecommerce_backend.exception.ResourceNotFoundException;
import com.naveen.ecommerce_backend.model.user.User;
import com.naveen.ecommerce_backend.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserRepo userRepo;

    private final CloudinaryService cloudinaryService;

    private User getCurrentUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        return userRepo.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found!!"));
    }

    public ProfileResponseDto getProfile() {
        return ProfileMapper.toDto(getCurrentUser());
    }

    public ProfileResponseDto updateProfile(UpdateProfileRequest request) {

        User user = getCurrentUser();

        user.setName(request.getName());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setDateOfBirth(request.getDateOfBirth());
        user.setGender(request.getGender());
        user.setBio(request.getBio());

        userRepo.save(user);

        return ProfileMapper.toDto(user);
    }

    public ProfileResponseDto uploadProfileImage(MultipartFile image) throws IOException {

        User user = getCurrentUser();

        if(user.getProfileImagePublicId() != null)
            cloudinaryService.deleteImage(user.getProfileImagePublicId());

        CloudinaryResponse imageResponse = cloudinaryService.uploadImage(image, CloudinaryFolders.PROFILES);

        user.setProfileImagePublicId(imageResponse.getPublicId());
        user.setProfileImageUrl(imageResponse.getImageUrl());

        userRepo.save(user);

        return ProfileMapper.toDto(user);
    }

    public String deleteProfileImage() throws IOException {

        User user = getCurrentUser();

        if(user.getProfileImagePublicId() != null)
            cloudinaryService.deleteImage(user.getProfileImagePublicId());

        user.setProfileImageUrl(null);
        user.setProfileImagePublicId(null);

        userRepo.save(user);

        return "Profile image deleted successfully";
    }
}
