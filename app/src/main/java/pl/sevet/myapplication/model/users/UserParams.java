package pl.sevet.myapplication.model.users;

import pl.sevet.myapplication.model.BaseModel;

public class UserParams implements BaseModel {
    private String login;
    private String password;

    public UserParams(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
