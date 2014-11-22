package ru.qatools.school.vorobushek.models;

import java.security.Principal;
import org.javalite.activejdbc.Model;


/**
 * Created by yurik on 19.11.14.
 */


public class User extends Model {

    public String getLogin() {
        return getString("login");
    }

    public void setLogin(String login) {
        setString("login", login);
    }

    public String getEmail() {
        return getString("email");
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
