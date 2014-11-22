package ru.qatools.school.vorobushek.models;

import org.javalite.activejdbc.Model;

import java.sql.Timestamp;
import java.util.List;

/**
 * eroshenkoam
 * 15/11/14
 */
public class Post extends Model {

    public String getTitle() {
        return getString("title");
    }

    public void setTitle(String title) {
        setString("title", title);
    }

    public String getBody() {
        return getString("body");
    }

    public void setBody(String body) {
        setString("body", body);
    }

    public Timestamp getUpdatedAt() {
        return getTimestamp("updated_at");
    }

    public Timestamp getCreatedAt() {
        return getTimestamp("created_at");
    }

    public void addComment(Comment comment){
        this.add(comment);
    }

    public List<Comment> getComments(){
        return this.getAll(Comment.class);
    }

    public int getCommentsCount(){
        return this.getAll(Comment.class).size();
    }

    public void setUser(User user){
        this.setParent(user);
    }

    public User getUser(){
        return this.parent(User.class);
    }
}
