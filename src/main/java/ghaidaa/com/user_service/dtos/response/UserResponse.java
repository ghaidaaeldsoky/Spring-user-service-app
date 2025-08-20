package ghaidaa.com.user_service.dtos.response;

import ghaidaa.com.user_service.enums.Role;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserResponse(
        UUID id,
        String fullName,
        String email,
        String phone,
        String nationalId,
        java.time.LocalDate dateOfBirth,
        String address,
        Role role,
        LocalDateTime createdAt
) {
}
