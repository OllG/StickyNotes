package pl.olpinski.stickynotes.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import pl.olpinski.stickynotes.dto.NewUserDto;

@Component
public class NewUserDtoValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return NewUserDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        NewUserDto newUserDto = (NewUserDto) target;
        if(!newUserDto.getPassword().equalsIgnoreCase(newUserDto.getConfPassword())){
            errors.rejectValue("confPassword", "password.notMatch");
        }
    }
}
