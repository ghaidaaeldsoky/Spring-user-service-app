package ghaidaa.com.user_service.dtos.request;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record UserUpdateRequest(
        @NotBlank @Size(min=3, max=100) String fullName,
        @NotBlank @Pattern(regexp="^[0-9]{10,15}$") String phone,
        @Size(max=255) String address,
        @NotNull @Past LocalDate dateOfBirth
) {
}
