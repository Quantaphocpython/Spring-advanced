package spring.jpa.test.devetiadb.service;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import spring.jpa.test.devetiadb.dto.request.UserCreationRequest;
import spring.jpa.test.devetiadb.dto.response.UserResponse;
import spring.jpa.test.devetiadb.entity.User;
import spring.jpa.test.devetiadb.exception.AppException;
import spring.jpa.test.devetiadb.exception.ErrorCode;
import spring.jpa.test.devetiadb.repository.UserRepository;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
class UserServiceTest {
    @Autowired
    UserService userService;

    @MockBean
    UserRepository userRepository;

    UserCreationRequest request;
    UserResponse userResponse;
    User user;
    LocalDate dob;

    @BeforeEach // chạy trước các func test
    public void initData() {
        dob = LocalDate.of(1990, 1, 1);

        request = UserCreationRequest
                .builder()
                .name("kaitoudads")
                .firstName("Trần")
                .lastName("Quân")
                .password("12345678Quan@")
                .dob(dob)
                .build();

        userResponse = UserResponse
                .builder()
                .id("bad5d857-561c-491f-b482-281122a91a24")
                .name("kaitoudads")
                .firstName("Trần")
                .lastName("Quân")
                .dob(dob)
                .build();

        user = User
                .builder()
                .id("bad5d857-561c-491f-b482-281122a91a24")
                .name("kaitoudads")
                .firstName("Trần")
                .lastName("Quân")
                .dob(dob)
                .build();
    }

    @Test
    public void createUser_validRequest_success() {
        // GIVEN
        when(userRepository.existsByName(anyString())).thenReturn(false);
        when(userRepository.save(any())).thenReturn(user);

        //WHEN
        var response = userService.createUser(request);

        //THEN
        Assertions.assertThat(response.getId()).isEqualTo("bad5d857-561c-491f-b482-281122a91a24");
        Assertions.assertThat(response.getName()).isEqualTo("kaitoudads");

    }

    @Test
    public void createUser_userExisted_fail() {
        // GIVEN
        when(userRepository.existsByName(anyString())).thenReturn(true);

        //WHEN
        var exception = assertThrows(AppException.class, () -> userService.createUser(request));

        //Then
        Assertions.assertThat(exception.getErrorCode().getCode()).isEqualTo(ErrorCode.USER_EXISTED.getCode());
    }
}