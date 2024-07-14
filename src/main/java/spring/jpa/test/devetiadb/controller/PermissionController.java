package spring.jpa.test.devetiadb.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.jpa.test.devetiadb.dto.request.ApiResponse;
import spring.jpa.test.devetiadb.dto.request.PermissionRequest;
import spring.jpa.test.devetiadb.dto.response.PermissionResponse;
import spring.jpa.test.devetiadb.service.PermissionService;

@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PermissionController {
    PermissionService permissionService;

    @PostMapping
    ApiResponse<PermissionResponse> create(@RequestBody PermissionRequest request) {
        return ApiResponse
                .<PermissionResponse>builder()
                .result(permissionService.create(request))
                .build();
    }

    ApiResponse<>
}
