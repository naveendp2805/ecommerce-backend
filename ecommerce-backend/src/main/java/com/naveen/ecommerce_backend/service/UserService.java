package com.naveen.ecommerce_backend.service;

import com.naveen.ecommerce_backend.dto.User.LoginRequest;
import com.naveen.ecommerce_backend.dto.User.RegisterRequest;
import com.naveen.ecommerce_backend.dto.User.UserMapper;
import com.naveen.ecommerce_backend.dto.User.UserResponseDto;
import com.naveen.ecommerce_backend.model.User;
import com.naveen.ecommerce_backend.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepo userRepo;

    public Page<UserResponseDto> getAllUsers(int page, int size) {

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("name").ascending());

        return userRepo.findAll(pageRequest).map(UserMapper::toDto);
    }

    public UserResponseDto getUserById(Long id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found!"));

        return UserMapper.toDto(user);
    }

    public UserResponseDto updateUserById(Long id, RegisterRequest registerRequest) {

        User user = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found!"));

        user.setName(registerRequest.getName());
        user.setEmail(registerRequest.getEmail());

        userRepo.save(user);

        return UserMapper.toDto(user);
    }

    public String deleteUserById(Long id) {

        if(!userRepo.existsById(id))
            throw new RuntimeException("User doesn't exist!");

        userRepo.deleteById(id);

        return "User deleted successfully.";
    }

    public String updatePasswordByEmail(LoginRequest loginRequest) {

        User user = userRepo.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found!"));

        user.setPassword(passwordEncoder.encode(loginRequest.getPassword()));

        userRepo.save(user);

        return "Password updated successfully.";
    }
}
