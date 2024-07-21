package spring.jpa.test.devetiadb.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.*;
import lombok.experimental.FieldDefaults;
import spring.jpa.test.devetiadb.constraint.annotation.DobConstraint;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(
        level = AccessLevel.PRIVATE) // nếu không xác định phạm vi cụ thể của field thì nó sẽ là private ->  ta có theer
// xoóa private đi
public class UserCreationRequest {
    @Size(min = 6, message = "USERNAME_INVALID")
    String name;

    //    @Size(min = 8, message = "Password must be at least 8 characters")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "PASSWORD_INVALID")
    String password;

    String firstName;
    String lastName;

    @DobConstraint(min = 18, message = "INVALID_DOB")
    LocalDate dob;
}
