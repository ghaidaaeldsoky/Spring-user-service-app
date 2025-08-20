package ghaidaa.com.user_service.services.impls;

import ghaidaa.com.user_service.dtos.request.UserLoginRequest;
import ghaidaa.com.user_service.dtos.request.UserRegisterRequest;
import ghaidaa.com.user_service.dtos.request.UserUpdateRequest;
import ghaidaa.com.user_service.dtos.response.LoginResponse;
import ghaidaa.com.user_service.dtos.response.PageResponse;
import ghaidaa.com.user_service.dtos.response.UserResponse;
import ghaidaa.com.user_service.entities.User;
import ghaidaa.com.user_service.enums.Role;
import ghaidaa.com.user_service.exceptions.BadRequestException;
import ghaidaa.com.user_service.exceptions.DuplicateResourceException;
import ghaidaa.com.user_service.exceptions.NotFoundException;
import ghaidaa.com.user_service.mappers.UserMapper;
import ghaidaa.com.user_service.repos.UserRepo;
import ghaidaa.com.user_service.services.interfaces.UserService;
import ghaidaa.com.user_service.utils.EncodeUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private  UserRepo userRepository;

    @Autowired
    private  UserMapper userMapper;

    // User
    @Override
    @Transactional
    public UserResponse register(UserRegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new DuplicateResourceException("Email already in use");
        }
        if (userRepository.existsByPhone(request.phone())) {
            throw new DuplicateResourceException("Phone already in use");
        }
        if (userRepository.existsByNationalId(request.nationalId())) {
            throw new DuplicateResourceException("National ID already in use");
        }

        User user = userMapper.toEntity(request);
        user.setPassword(EncodeUtil.hashPassword(request.password())); // encode password
        userRepository.save(user);

        return userMapper.toResponse(user);
    }

    @Override
    public LoginResponse login(UserLoginRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new NotFoundException("Invalid email or password"));

        if (!user.getPassword().equals(EncodeUtil.hashPassword(request.password()))) {
            throw new NotFoundException("Invalid email or password");
        }

        return new LoginResponse(user.getId(), user.getRole(), "dummy-token");
    }

    @Override
    @Transactional
    public UserResponse updateProfile(UUID userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        userMapper.updateEntityFromDto(request, user);
        userRepository.save(user);

        return userMapper.toResponse(user);
    }

    @Override
    public UserResponse getProfile(UUID userId) {
        return userRepository.findById(userId)
                .map(userMapper::toResponse)
                .orElseThrow(()-> new NotFoundException("User not Found"));
    }


    // Admin
    @Override
    public PageResponse<UserResponse> getAllUsers(int page, int size) {
        Page<User> users = userRepository.findAll(PageRequest.of(page, size));
        return new PageResponse<>(
                users.map(userMapper::toResponse).getContent(),
                users.getNumber(),
                users.getSize(),
                users.getTotalElements(),
                users.getTotalPages()
        );
    }

    @Override
    public UserResponse getUserById(UUID id) {
        return userRepository.findById(id)
                .map(userMapper::toResponse)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Override
    @Transactional
    public void deleteUser(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException("User not found");
        }
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public UserResponse changeRole(UUID id, String roleStr) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
        Role role;
        try{
           role = Role.valueOf(roleStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid role. Allowed values: " + Arrays.toString(Role.values()));
        }
        user.setRole(role);
        userRepository.save(user);
        return userMapper.toResponse(user);
    }


}
