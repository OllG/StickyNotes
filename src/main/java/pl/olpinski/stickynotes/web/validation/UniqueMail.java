package pl.olpinski.stickynotes.web.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueMailValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueMail {
    String message() default "This mail is already registered";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
