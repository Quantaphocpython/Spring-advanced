package spring.jpa.test.devetiadb.config;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.fasterxml.jackson.databind.ObjectMapper;

import spring.jpa.test.devetiadb.dto.request.ApiResponse;
import spring.jpa.test.devetiadb.exception.ErrorCode;

public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    // xảy ra khi có 1 exception xảy ra trong quá trình authenticated
    // thường xảy ra khi ta authentication ko thành công
    public void commence(
            HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        ErrorCode errorCode = ErrorCode.UNAUTHENTICATED;

        response.setStatus(errorCode.getStatusCode().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ApiResponse<?> apiResponse = ApiResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();

        // objectmapper dùng để chuyển object thành string
        ObjectMapper obj = new ObjectMapper();
        response.getWriter().write(obj.writeValueAsString(apiResponse));
        response.flushBuffer(); // trả dữ liệu về cho client
    }
}
