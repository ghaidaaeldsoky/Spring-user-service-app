package ghaidaa.com.user_service.mappers;


import ghaidaa.com.user_service.dtos.request.UserRegisterRequest;
import ghaidaa.com.user_service.dtos.request.UserUpdateRequest;
import ghaidaa.com.user_service.dtos.response.UserResponse;
import ghaidaa.com.user_service.entities.User;
import org.mapstruct.*;


@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)

public interface UserMapper {
    // Register Request → Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", constant = "CITIZEN") // default role
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "password", ignore = true) // will encode in service
    User toEntity(UserRegisterRequest request);

    // Update Request → Update existing entity
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(UserUpdateRequest request, @MappingTarget User user);

    // Entity → Response
    UserResponse toResponse(User user);

}
