package ghaidaa.com.user_service.services.interfaces;

import ghaidaa.com.user_service.dtos.request.*;
import ghaidaa.com.user_service.dtos.response.*;

import java.util.UUID;

public interface UserService {

    // Citizen actions
    UserResponse register(UserRegisterRequest request);
    LoginResponse login(UserLoginRequest request);
    UserResponse updateProfile(UUID userId, UserUpdateRequest request);
    UserResponse getProfile(UUID userId);

    // Admin actions
    PageResponse<UserResponse> getAllUsers(int page, int size);
    UserResponse getUserById(UUID id);
    void deleteUser(UUID id);
    UserResponse changeRole(UUID id, String role);
}
