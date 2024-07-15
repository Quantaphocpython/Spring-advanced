package spring.jpa.test.devetiadb.constraint.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import spring.jpa.test.devetiadb.constraint.annotation.DobConstraint;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class DobValidator implements ConstraintValidator<DobConstraint, LocalDate> {

    int min;

    @Override
    //Đây l bước nó lấy giá trị min mà người dùng nhập vào dể validate, hàm này diễn ra trước hàm isValid
    public void initialize(DobConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        min = constraintAnnotation.min();
    }

    @Override
    // chạy sau hàm init
    // best practice khi viết annotation là mỗi validation chỉ nên chịu trách nhiệm cho 1 validation cụ thể thôi
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        if (Objects.isNull(value))
            return true;
        long years = ChronoUnit.YEARS.between(value, LocalDate.now());
        return years >= min;
    }
}
