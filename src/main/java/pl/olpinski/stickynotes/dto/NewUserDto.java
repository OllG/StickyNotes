package pl.olpinski.stickynotes.dto;

public class NewUserDto {

    private String login;
    private String password;
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
