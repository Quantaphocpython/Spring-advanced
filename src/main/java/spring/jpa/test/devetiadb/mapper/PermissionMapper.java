package spring.jpa.test.devetiadb.mapper;

import org.mapstruct.Mapper;
import spring.jpa.test.devetiadb.dto.request.PermissionRequest;
import spring.jpa.test.devetiadb.dto.response.PermissionResponse;
import spring.jpa.test.devetiadb.entity.Permission;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);
    PermissionResponse toPermissionResponse(Permission permission);
}
