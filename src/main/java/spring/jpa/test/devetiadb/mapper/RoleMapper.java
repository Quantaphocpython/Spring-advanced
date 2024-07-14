package spring.jpa.test.devetiadb.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import spring.jpa.test.devetiadb.dto.request.RoleRequest;
import spring.jpa.test.devetiadb.dto.response.RoleResponse;
import spring.jpa.test.devetiadb.entity.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true) // nó sẽ bỏ qua permissions khi map
    Role toRole(RoleRequest request);
    RoleResponse toRoleResponse(Role role);
}