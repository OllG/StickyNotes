package pl.olpinski.stickynotes.dto;

import javax.validation.constraints.NotNull;

public class NewUserDto {

    @NotNull
    private String login;
    @NotNull
    private String password;
    @NotNull
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
