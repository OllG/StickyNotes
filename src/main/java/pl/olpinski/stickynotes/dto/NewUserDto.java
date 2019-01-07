package pl.olpinski.stickynotes.dto;

import pl.olpinski.stickynotes.validation.UniqueLogin;
import pl.olpinski.stickynotes.validation.UniqueMail;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

public class NewUserDto {

    @UniqueLogin
    @Size(min=3, max=30, message = "Login must contain between 3 and 30 characters")
    private String login;

    //confirmation password
    @Size(min=6, message = "Password must have at least 6 characters")
    private String password;
    private String confPassword;

    @Email
    @UniqueMail
    @Size(min=1, message = "E-mail cannot be empty")
    private String mail;

    @Size(min = 3, max = 30, message = "First name should contain between 3 and 30 characters")
    private String firstName;
    @Size(min = 3, max = 30, message = "Last name should contain between 3 and 30 characters")
    private String lastName;

    public NewUserDto(String login, String password, String confPassword, String mail, String firstName, String lastName) {
        this.login = login;
        this.password = password;
        this.confPassword = confPassword;
        this.mail = mail;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getConfPassword() {
        return confPassword;
    }

    public String getMail() {
        return mail;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
