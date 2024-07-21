package spring.jpa.test.devetiadb.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import spring.jpa.test.devetiadb.dto.request.UserCreationRequest;
import spring.jpa.test.devetiadb.dto.response.UserResponse;
import spring.jpa.test.devetiadb.service.UserService;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest // init framework spring, connect với dbms,  init các bean cần thiết, khởi động ngữ cảnh ứng dụng spring
@AutoConfigureMockMvc // giúp tạo 1 mock request tới controller
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
class UserControllerTest {
    @Autowired
    MockMvc mockMvc; // gọi đến api của chúng ta

    @MockBean
    UserService userService; // mock userService vào đ sử dụng

    UserCreationRequest request;
    UserResponse userResponse;
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
    }

    @Test
    public void createUser_validRequest_success() throws Exception {
        //Given
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // jackson hiêểu dduojc và sealize dc lovalDate
        String content = objectMapper.writeValueAsString(request);

        Mockito
                .when(userService.createUser(ArgumentMatchers.any())) // nó sẽ ko gọi hàm createUser mà sẽ trả về trực tiếp lun
                .thenReturn(userResponse);

        //when, then
        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .post("/users")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(content)
                )
                .andExpect(
                        MockMvcResultMatchers
                                .status()
                                .isOk()
                )
                .andExpect(
                        MockMvcResultMatchers
                                .jsonPath("code")
                                .value(1000)
                )
                .andExpect(
                        MockMvcResultMatchers
                                .jsonPath("result.id")
                                .value("bad5d857-561c-491f-b482-281122a91a24")
                )
        ;
    }

    @Test
    public void createUser_usernameInvalid_fail() throws Exception {
        //Given
        request.setName("sfv");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // jackson hiêểu dduojc và sealize dc lovalDate
        String content = objectMapper.writeValueAsString(request);

        //when, then
        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .post("/users")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(content)
                )
                .andExpect(
                        MockMvcResultMatchers
                                .status()
                                .isBadRequest()
                )
                .andExpect(
                        MockMvcResultMatchers
                                .jsonPath("code")
                                .value(1001)
                )
                .andExpect(
                        MockMvcResultMatchers
                                .jsonPath("result.name")
                                .value("User name must be at least 6 char")
                )
        ;
    }
}