package spring.jpa.test.devetiadb.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;
import spring.jpa.test.devetiadb.entity.Role;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {
    String id;
    String name;
    String firstName;
    String lastName;
    LocalDate dob;
    Set<Role> roles;
}
