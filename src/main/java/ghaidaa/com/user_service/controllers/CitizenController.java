package ghaidaa.com.user_service.controllers;

import ghaidaa.com.user_service.dtos.ApiResponse;
import ghaidaa.com.user_service.dtos.request.UserLoginRequest;
import ghaidaa.com.user_service.dtos.request.UserRegisterRequest;
import ghaidaa.com.user_service.dtos.request.UserUpdateRequest;
import ghaidaa.com.user_service.dtos.response.LoginResponse;
import ghaidaa.com.user_service.dtos.response.UserResponse;
import ghaidaa.com.user_service.services.interfaces.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/citizen")
@Tag(name = "Users", description = "Citizin Users management API")
public class CitizenController {

    @Autowired
    private UserService userService;

    @Operation(summary = "register new user")
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponse>> register(@Valid @RequestBody UserRegisterRequest request) {
        return ResponseEntity.ok(ApiResponse.success("User registered successfully", userService.register(request)));
    }

    @Operation(summary = "login")
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody UserLoginRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Login successful", userService.login(request)));
    }

    @Operation(summary = "Update User Profile")
    @PutMapping("/profile/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> updateProfile(
            @PathVariable UUID id,
            @Valid @RequestBody UserUpdateRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Profile updated successfully", userService.updateProfile(id, request)));
    }

    @Operation(summary = "Get user profile")
    @GetMapping("/profile/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getProfile(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.success("Profile retrieved successfully", userService.getProfile(id)));
    }
}
