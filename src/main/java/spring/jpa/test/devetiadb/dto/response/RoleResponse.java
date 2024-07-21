package spring.jpa.test.devetiadb.dto.response;

import java.util.Set;

import lombok.*;
import lombok.experimental.FieldDefaults;
import spring.jpa.test.devetiadb.entity.Permission;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleResponse {
    String name;
    String description;
    Set<Permission> permissions;
}
