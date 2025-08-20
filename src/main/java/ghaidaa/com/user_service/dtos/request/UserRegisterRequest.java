package ghaidaa.com.user_service.dtos.request;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record UserRegisterRequest(
        @NotBlank @Size(min=3, max=100) String fullName,
        @NotBlank @Email @Size(max=150) String email,
        @NotBlank @Size(min=8, max=100) String password,
        @NotBlank @Pattern(regexp="^[0-9]{10,15}$") String phone,
        @NotBlank @Pattern(regexp="^[0-9]{14}$") String nationalId,
        @NotNull @Past LocalDate dateOfBirth,
        @Size(max=255) String address
) {
}
