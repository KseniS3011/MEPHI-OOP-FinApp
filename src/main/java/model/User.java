package model;

public class User {
    String login;


    public User(String login) {
        this.login = login;
    }

    public User() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

}
