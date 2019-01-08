package pl.olpinski.stickynotes.web.validation;

import pl.olpinski.stickynotes.service.UserService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueLoginValidator implements ConstraintValidator<UniqueLogin, String> {

    private UserService userService;

    public UniqueLoginValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean isValid(String login, ConstraintValidatorContext context) {
        return !userService.isLoginTaken(login);
    }

    @Override
    public void initialize(UniqueLogin constraintAnnotation) {
    }
}
