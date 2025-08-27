package ghaidaa.com.user_service.controllers;


import ghaidaa.com.user_service.dtos.ApiResponse;
import ghaidaa.com.user_service.dtos.request.ChangeRoleRequest;
import ghaidaa.com.user_service.dtos.response.PageResponse;
import ghaidaa.com.user_service.dtos.response.UserResponse;
import ghaidaa.com.user_service.services.impls.UserServiceImplKeycloak;
import ghaidaa.com.user_service.services.interfaces.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin/users")
@Tag(name = "Users", description = "Citizin Users management API")
public class AdminController {

//    @Autowired
    private UserService userService;

    public AdminController(UserServiceImplKeycloak userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<UserResponse>>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(ApiResponse.success("Users retrieved successfully", userService.getAllUsers(page, size)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.success("User retrieved successfully", userService.getUserById(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(ApiResponse.success("User deleted successfully", null));
    }

    @PutMapping("/{id}/role")
    public ResponseEntity<ApiResponse<UserResponse>> changeRole(@PathVariable UUID id,
                                                                @Valid @RequestBody ChangeRoleRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Role updated successfully", userService.changeRole(id, request.role())));
    }

}
