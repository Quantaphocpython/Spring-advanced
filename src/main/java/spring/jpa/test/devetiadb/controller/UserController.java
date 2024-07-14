package spring.jpa.test.devetiadb.controller;


import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import spring.jpa.test.devetiadb.dto.request.ApiResponse;
import spring.jpa.test.devetiadb.dto.request.UserCreationRequest;
import spring.jpa.test.devetiadb.dto.request.UserUpdateRequest;
import spring.jpa.test.devetiadb.dto.response.UserResponse;
import spring.jpa.test.devetiadb.entity.User;
import spring.jpa.test.devetiadb.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;

    @PostMapping
    ApiResponse<User> createUser(@RequestBody @Valid UserCreationRequest request) {
        ApiResponse<User> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.createUser(request));
        return apiResponse;
    }

    @GetMapping
    ApiResponse<List<UserResponse>> getUsers() {
        return ApiResponse.<List<UserResponse>>builder()
                .result(userService.getUsers())
                .build();
    }

    @GetMapping("/myinfo")
    ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getMyInfo())
                .build();
    }

    @GetMapping("/{userId}")
    ApiResponse<UserResponse> getUserById(@PathVariable String userId) {
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.getUserById(userId));
        return apiResponse;
    }

    @PutMapping("/{userId}")
    UserResponse updateUserById(@PathVariable String userId, @RequestBody UserUpdateRequest userUpdateRequest) {
        return userService.updateUserById(userId, userUpdateRequest);
    }

    @DeleteMapping("/{userId}")
    String deleteUserById(@PathVariable String userId) {
        userService.deleteUserById(userId);
        return "User has been deleted";
    }

}
