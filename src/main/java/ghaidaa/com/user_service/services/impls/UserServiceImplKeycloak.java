package ghaidaa.com.user_service.services.impls;


import ghaidaa.com.user_service.dtos.request.UserLoginRequest;
import ghaidaa.com.user_service.dtos.request.UserRegisterRequest;
import ghaidaa.com.user_service.dtos.request.UserUpdateRequest;
import ghaidaa.com.user_service.dtos.response.LoginResponse;
import ghaidaa.com.user_service.dtos.response.PageResponse;
import ghaidaa.com.user_service.dtos.response.UserResponse;
import ghaidaa.com.user_service.entities.User;
import ghaidaa.com.user_service.exceptions.DuplicateResourceException;
import ghaidaa.com.user_service.mappers.UserMapper;
import ghaidaa.com.user_service.repos.UserRepo;
import ghaidaa.com.user_service.services.interfaces.UserService;
import ghaidaa.com.user_service.utils.EncodeUtil;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.UUID;

@Service
public class UserServiceImplKeycloak implements UserService {

    private final Keycloak keycloak;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.client-id}")
    private String clientId;

    public UserServiceImplKeycloak(Keycloak keycloak) {
        this.keycloak = keycloak;
    }
    @Autowired
    private UserRepo userRepository;

    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional
    public UserResponse register(UserRegisterRequest request) {
        // 1. Check for duplicates in your database
        if (userRepository.existsByEmail(request.email())) {
            throw new DuplicateResourceException("Email already in use");
        }
        if (userRepository.existsByPhone(request.phone())) {
            throw new DuplicateResourceException("Phone already in use");
        }
        if (userRepository.existsByNationalId(request.nationalId())) {
            throw new DuplicateResourceException("National ID already in use");
        }

        // 2. Create user in Keycloak first
        String keycloakUserId = createKeycloakUser(request);

        // 3. Create user in your database
        User user = userMapper.toEntity(request);
        user.setPassword(EncodeUtil.hashPassword(request.password())); // encode password
        // user.setKeycloakId(UUID.fromString(keycloakUserId)); // store Keycloak ID for reference

        User savedUser = userRepository.save(user);

        return userMapper.toResponse(savedUser);

    }

    @Override
    public LoginResponse login(UserLoginRequest request) {
        return null;
    }

    @Override
    public UserResponse updateProfile(UUID userId, UserUpdateRequest request) {
        return null;
    }

    @Override
    public UserResponse getProfile(UUID userId) {
        return null;
    }

    @Override
    public PageResponse<UserResponse> getAllUsers(int page, int size) {
        return null;
    }

    @Override
    public UserResponse getUserById(UUID id) {
        return null;
    }

    @Override
    public void deleteUser(UUID id) {

    }

    @Override
    public UserResponse changeRole(UUID id, String role) {
        return null;
    }



    private String createKeycloakUser(UserRegisterRequest request) {
        RealmResource realmResource = keycloak.realm(realm);
        UsersResource usersResource = realmResource.users();

        // Create user in Keycloak
        UserRepresentation userRep = new UserRepresentation();
        userRep.setUsername(request.email());
        userRep.setEmail(request.email());
        userRep.setFirstName(extractFirstName(request.fullName()));
        userRep.setLastName(extractLastName(request.fullName()));
        userRep.setEnabled(true);
        userRep.setEmailVerified(false);

        // Set user attributes for additional data
        userRep.singleAttribute("phone", request.phone());
        userRep.singleAttribute("nationalId", request.nationalId());
        userRep.singleAttribute("dateOfBirth", request.dateOfBirth().toString());
        if (request.address() != null) {
            userRep.singleAttribute("address", request.address());
        }

        // Set credentials
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(request.password());
        credential.setTemporary(false);
        userRep.setCredentials(Collections.singletonList(credential));

        // Create user in Keycloak
        Response response = usersResource.create(userRep);

        if (response.getStatus() == 201) {
            // Extract user ID from location header
            String location = response.getLocation().getPath();
            return location.substring(location.lastIndexOf('/') + 1);
        } else {
            String errorMessage = "Failed to create user in Keycloak: " + response.getStatusInfo();
            if (response.getStatus() == 409) {
                errorMessage = "User already exists in Keycloak";
            }
            throw new RuntimeException(errorMessage);
        }
    }

    private String extractFirstName(String fullName) {
        return fullName.split(" ")[0];
    }

    private String extractLastName(String fullName) {
        String[] parts = fullName.split(" ");
        return parts.length > 1 ? parts[parts.length - 1] : "";
    }

}
