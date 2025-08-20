package ghaidaa.com.user_service.dtos.response;

import ghaidaa.com.user_service.enums.Role;

import java.util.UUID;

public record LoginResponse(
        UUID userId,
        Role role,
        String token  // placeholder; add JWT later
) {
}
