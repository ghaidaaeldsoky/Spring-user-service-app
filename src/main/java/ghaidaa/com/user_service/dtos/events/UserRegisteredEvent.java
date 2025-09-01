package ghaidaa.com.user_service.dtos.events;

import java.util.UUID;

public record UserRegisteredEvent(
        String eventType,   // "USER_REGISTERED"
        UUID userId,
        String email,
        String fullName
) {
}
