package spring.jpa.test.devetiadb.constraint.annotation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.*;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import spring.jpa.test.devetiadb.constraint.validator.DobValidator;

@Target({FIELD}) // chỉ định nơi có hiệu lực
@Retention(RUNTIME) // chỉ định khi xử lí, ở đây là runtime
@Constraint(validatedBy = {DobValidator.class}) // 1 annotation có thể có nhiều validator
public @interface DobConstraint {
    // 3 thuộc tính cơ bản của annotation
    String message() default "Invalid date of birth";

    int min();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
