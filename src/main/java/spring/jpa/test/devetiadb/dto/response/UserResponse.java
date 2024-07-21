package spring.jpa.test.devetiadb.dto.response;

import java.time.LocalDate;
import java.util.Set;

import lombok.*;
import lombok.experimental.FieldDefaults;
import spring.jpa.test.devetiadb.entity.Role;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
// @JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {
    String id;
    String name;
    String firstName;
    String lastName;
    LocalDate dob;
    Set<Role> roles;
}
