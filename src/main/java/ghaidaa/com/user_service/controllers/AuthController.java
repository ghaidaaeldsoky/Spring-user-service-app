package ghaidaa.com.user_service.controllers;


import ghaidaa.com.user_service.dtos.ApiResponse;
import ghaidaa.com.user_service.dtos.events.UserRegisteredEvent;
import ghaidaa.com.user_service.dtos.request.UserLoginRequest;
import ghaidaa.com.user_service.dtos.request.UserRegisterRequest;
import ghaidaa.com.user_service.dtos.response.LoginResponse;
import ghaidaa.com.user_service.dtos.response.UserResponse;
import ghaidaa.com.user_service.services.impls.UserServiceImplKeycloak;
import ghaidaa.com.user_service.services.interfaces.UserService;
import ghaidaa.com.user_service.services.kafka.UserRegisterProducer;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private UserService userService;

    @Autowired
    private UserRegisterProducer userRegisterProducer;

    public AuthController(@Qualifier("userServiceImplKeycloak") UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "register new user")
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponse>> register(@Valid @RequestBody UserRegisterRequest request) {
        UserResponse userResponse = userService.register(request);
        userRegisterProducer.send(new UserRegisteredEvent(
                "USER_REGISTERED",
                userResponse.id(),
                userResponse.email(),
                userResponse.fullName()));
        return ResponseEntity.ok(ApiResponse.success("User registered successfully", userResponse));
    }

    @Operation(summary = "login")
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody UserLoginRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Login successful", userService.login(request)));
    }
}
