package com.naveen.ecommerce_backend.controller;

import com.naveen.ecommerce_backend.dto.userProfile.ProfileResponseDto;
import com.naveen.ecommerce_backend.dto.userProfile.UpdateProfileRequest;
import com.naveen.ecommerce_backend.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/profile")
public class UserProfileController {

    private final UserProfileService userProfileService;

    @GetMapping
    public ResponseEntity<ProfileResponseDto> getProfile() {
        return ResponseEntity.ok(userProfileService.getProfile());
    }

    @PutMapping
    public ResponseEntity<ProfileResponseDto> updateProfile(@RequestBody UpdateProfileRequest request) {
        return ResponseEntity.ok(userProfileService.updateProfile(request));
    }

    @PostMapping("/image")
    public ResponseEntity<ProfileResponseDto> uploadProfileImage(@RequestParam MultipartFile image) throws IOException {
        return ResponseEntity.ok(userProfileService.uploadProfileImage(image));
    }

    @DeleteMapping("/image")
    public ResponseEntity<String> deleteProfileImage() throws IOException{
        return ResponseEntity.ok(userProfileService.deleteProfileImage());
    }
}
