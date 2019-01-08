package pl.olpinski.stickynotes.web.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import pl.olpinski.stickynotes.data.dto.NewUserDto;

@Component
public class NewUserDtoValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return NewUserDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        NewUserDto newUserDto = (NewUserDto) target;
        // zasada Demeter 1 kropki
        if(!newUserDto.getPassword().equalsIgnoreCase(newUserDto.getConfPassword())){
            errors.rejectValue("confPassword", "password.notMatch");
        }
    }
}
