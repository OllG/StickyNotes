package pl.olpinski.stickynotes.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueLoginValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueLogin {
    String message() default "This login is already taken";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
