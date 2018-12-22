package pl.olpinski.stickynotes.validation;

import pl.olpinski.stickynotes.service.UserService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueMailValidator implements ConstraintValidator<UniqueMail, String> {

    private UserService userService;

    public UniqueMailValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean isValid(String mail, ConstraintValidatorContext context) {
        return !userService.isMailRegistered(mail);
    }

    @Override
    public void initialize(UniqueMail constraintAnnotation) {
    }
}
