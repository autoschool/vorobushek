package ru.qatools.school.vorobushek.models;

import org.javalite.activejdbc.Model;


/**
 * Created by yurik on 19.11.14.
 */


public class User extends Model {

    public void setLogin(String login) {
        setString("login", login);
    }

    public void setEmail(String email) {
        setString("email", email);
    }

    public String getDisplayName() {
        return getString("displayName");
    }

    public void setDisplayName(String displayName) {
        setString("displayName", displayName);
    }
}
