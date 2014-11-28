package ru.qatools.school.vorobushek.models;

import org.javalite.activejdbc.Model;

/**
 * Created by yurik
 * 19.11.14.
 */


public class User extends Model {

    public void setLogin(String login) {
        setString("login", login);
    }

    public String getLogin() {
        return getString("login");
    }

    public void setEmail(String email) {
        setString("email", email);
    }

    public String getEmail() {
        return getString("email");
    }

    public String getDisplayName() {
        return getString("displayName");
    }

    public void setDisplayName(String displayName) {
        setString("displayName", displayName);
    }

    @Override
    public int hashCode(){
        return getLogin().hashCode()
                ^ getEmail().hashCode()
                ^ getDisplayName().hashCode();
    }


    @Override
    public boolean equals(Object user){

        User u = (user instanceof User ? (User)user : null);

        if (u == null) {
            return false;
        }

        return hashCode() == u.hashCode();
    }

    @Override
    public String toString(){
        return getLogin();
    }
}
