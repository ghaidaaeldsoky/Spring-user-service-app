package ghaidaa.com.user_service.controllers;

import ghaidaa.com.user_service.dtos.ApiResponse;
import ghaidaa.com.user_service.dtos.request.UserLoginRequest;
import ghaidaa.com.user_service.dtos.request.UserRegisterRequest;
import ghaidaa.com.user_service.dtos.request.UserUpdateRequest;
import ghaidaa.com.user_service.dtos.response.LoginResponse;
import ghaidaa.com.user_service.dtos.response.UserResponse;
import ghaidaa.com.user_service.services.impls.UserServiceImpl;
import ghaidaa.com.user_service.services.impls.UserServiceImplKeycloak;
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

//    @Autowired
    private UserService userService;

    public CitizenController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @Operation(summary = "Update User Profile")
    @PutMapping("/profile/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> updateProfile(
            @PathVariable UUID id,
            @Valid @RequestBody UserUpdateRequest request) {
        System.out.println("At controller");
        return ResponseEntity.ok(ApiResponse.success("Profile updated successfully", userService.updateProfile(id, request)));
    }

    @Operation(summary = "Get user profile")
    @GetMapping("/profile/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getProfile(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.success("Profile retrieved successfully", userService.getProfile(id)));
    }
}
