package pl.olpinski.stickynotes.web.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import pl.olpinski.stickynotes.data.dto.UserCreationDto;

@Component
public class NewUserDtoValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return UserCreationDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserCreationDto userCreationDto = (UserCreationDto) target;
        if(!userCreationDto.getPassword().equals(userCreationDto.getConfPassword())){
            errors.rejectValue("confPassword", "password.notMatch");
        }
    }
}
