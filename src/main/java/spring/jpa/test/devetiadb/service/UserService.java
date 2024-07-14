package spring.jpa.test.devetiadb.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import spring.jpa.test.devetiadb.dto.request.UserCreationRequest;
import spring.jpa.test.devetiadb.dto.request.UserUpdateRequest;
import spring.jpa.test.devetiadb.dto.response.UserResponse;
import spring.jpa.test.devetiadb.entity.User;
import spring.jpa.test.devetiadb.enums.Role;
import spring.jpa.test.devetiadb.exception.AppException;
import spring.jpa.test.devetiadb.exception.ErrorCode;
import spring.jpa.test.devetiadb.mapper.UserMapper;
import spring.jpa.test.devetiadb.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;

    public User createUser(UserCreationRequest request) {
        if(userRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        User user = userMapper.toUser(request);
        HashSet<String> roles = new HashSet<>();
        roles.add(Role.USER.name());
//        user.setRoles(roles);

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        return userRepository.save(user);
    }

    public UserResponse getMyInfo() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository
                .findByName(name)
                .map(userMapper::toUserResponse)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getUsers() {
        log.info("In method get user");
        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }

    @PostAuthorize("returnObject.name == authentication.name || hasRole('ADMIN')")
    public UserResponse getUserById(String userId) {
        log.info("In method get user by id");
        return userMapper.toUserResponse(userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found")));
    }

    public UserResponse updateUserById(String userId, UserUpdateRequest userUpdateRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userMapper.updateUser(user, userUpdateRequest);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public void deleteUserById(String userId) {
        userRepository.deleteById(userId);
    }
}
