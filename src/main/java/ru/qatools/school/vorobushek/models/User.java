package ru.qatools.school.vorobushek.models;


import java.security.Principal;

/**
 * Created by yurik on 19.11.14.
 */
public class User implements Principal {

    private String login;

    private String email;

    private String displayName;


    public User(String login, String email, String displayName){
        this.login = login;
        this.email = email;
        this.displayName = displayName;
    }

    public String getEmail(){ return email; }
    public String getLogin(){ return login; }
    public String getDisplayName(){ return displayName; }

    @Override
    public String getName() {
        return displayName;
    }
}
