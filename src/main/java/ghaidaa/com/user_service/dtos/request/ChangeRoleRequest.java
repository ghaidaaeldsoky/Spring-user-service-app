package ghaidaa.com.user_service.dtos.request;

import ghaidaa.com.user_service.enums.Role;
import jakarta.validation.constraints.NotBlank;

public record ChangeRoleRequest(
        @NotBlank(message = "Role cannot be blank")
        String role
        ) {
}
