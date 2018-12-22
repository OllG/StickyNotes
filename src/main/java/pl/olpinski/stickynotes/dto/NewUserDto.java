package pl.olpinski.stickynotes.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

public class NewUserDto {

    @Size(min=3, max=30, message = "Login must contain between 3 and 30 characters")
    private String login;

    @Size(min=6, message = "Password must have at least 6 characters")
    private String password;

    @Email
    @Size(min=1, message = "E-mail cannot be empty")
    private String mail;

    public NewUserDto(String login, String password, String mail) {
        this.login = login;
        this.password = password;
        this.mail = mail;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getMail() {
        return mail;
    }
}
