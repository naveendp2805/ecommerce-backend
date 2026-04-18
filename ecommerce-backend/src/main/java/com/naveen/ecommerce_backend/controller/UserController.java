package com.naveen.ecommerce_backend.controller;

import com.naveen.ecommerce_backend.dto.User.LoginRequest;
import com.naveen.ecommerce_backend.dto.User.RegisterRequest;
import com.naveen.ecommerce_backend.dto.User.UserResponseDto;
import com.naveen.ecommerce_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    
    private final UserService userService;

    @GetMapping
    public ResponseEntity<Page<UserResponseDto>> getAllUsers(@RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "5") int size) {
        return ResponseEntity.ok(userService.getAllUsers(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUserById(@PathVariable Long id,
                                                          @RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(userService.updateUserById(id, registerRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.deleteUserById(id));
    }

    @PutMapping
    public ResponseEntity<String> updatePasswordByEmail(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(userService.updatePasswordByEmail(loginRequest));
    }

}
