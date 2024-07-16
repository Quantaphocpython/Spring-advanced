package spring.jpa.test.devetiadb.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.metadata.ConstraintDescriptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import spring.jpa.test.devetiadb.dto.request.ApiResponse;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

@ControllerAdvice
@Slf4j
public class GlobalException {

    private static final String MIN_ATTRIBUTE = "min";

    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiResponse> handlingRuntimeException(RuntimeException runtimeException) {
        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        apiResponse.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());
        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse> handlingAppException(AppException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());

        return ResponseEntity
                .status(errorCode.getStatusCode())
                .body(apiResponse);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ApiResponse> handlingAccessDeniedException() {
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;

        return ResponseEntity
                .status(errorCode.getStatusCode())
                .body(ApiResponse
                        .builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse<?>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new LinkedHashMap<>();
        ErrorCode errorCode = ErrorCode.INVALID_KEY;

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            ConstraintViolation<?> constraintViolation = error.unwrap(ConstraintViolation.class);

            // Lấy ra các attribute được truyền vào trong annotation, từ đây có thể lấy ra giá trị của chúng
            Map<String, Object> attributes = constraintViolation
                    .getConstraintDescriptor()
                    .getAttributes();

            errors.put(
                    error.getField(),
                    mapAttribute((ErrorCode.valueOf(error
                            .getDefaultMessage()))
                            .getMessage(), attributes)
            );
            log.info(attributes.toString());
        });


        return ResponseEntity
                .badRequest()
                .body(ApiResponse
                        .builder()
                        .code(errorCode.getCode())
                        .result(errors)
                        .build()
                );
    }

    //Hàm map attribute min vào dob còn nếu như lỗi ở password thì nó sẽ ko map và trả về message
    private String mapAttribute(String message, Map<String, Object> attributes) {
        Object minValue = attributes.get(MIN_ATTRIBUTE);
        if (Objects.isNull(minValue)) {
            return message;
        }
        return message.replace("{" + MIN_ATTRIBUTE + "}", minValue.toString());
    }
}
