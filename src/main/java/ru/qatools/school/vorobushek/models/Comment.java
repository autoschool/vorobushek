package ru.qatools.school.vorobushek.models;

import org.javalite.activejdbc.Model;

/**
 * Created by yurik on 19.11.14.
 */
public class Comment extends Model {

    public String getBody() {
        return getString("body");
    }

    public void setBody(String body) {
        setString("body", body);
    }
}
