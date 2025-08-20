package ghaidaa.com.user_service.dtos.request;

import jakarta.validation.constraints.*;

public record UserLoginRequest(
        @NotBlank @Email String email,
        @NotBlank String password
) {
}
