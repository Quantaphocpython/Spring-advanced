package spring.jpa.test.devetiadb.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;
import spring.jpa.test.devetiadb.constraint.annotation.DobConstraint;
import spring.jpa.test.devetiadb.entity.Role;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    String password;
    String firstName;
    String lastName;

    @DobConstraint(min = 18, message = "INVALID_DOB")
    LocalDate dob;
    List<String> roles;
}
