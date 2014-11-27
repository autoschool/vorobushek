package ru.qatools.school.vorobushek.models;

import ru.qatools.school.vorobushek.service.DatabaseProvider;

import javax.ws.rs.NotAuthorizedException;
import java.util.List;

/**
 * Created by yurik
 * 22.11.14.
 */
public class UserContext {

    private final String dineAccessMessage;

    private User currentUser;

    private Post lastShownPost;
    private Post lastEditedPost;


    public UserContext(String token) {
        currentUser = DatabaseProvider.getYandexUser(token);
        dineAccessMessage = "You Don't Have Permission";
    }

    public UserContext() {
        currentUser = null;
        dineAccessMessage = "You Don't Have Permission";
    }

    public boolean hasUser(){
        return currentUser != null;
    }

    public String getCurrentUserString(){

        return currentUser == null
                ? ""
                : currentUser.getDisplayName();
    }

    public Post createPost(String postTitle, String postBody) {

        if (currentUser == null){
            throw new NotAuthorizedException(dineAccessMessage);
        }

        Post post = new Post();
        post.setTitle(postTitle);
        post.setBody(postBody);
        post.setUser(currentUser);
        post.saveIt();

        return post;
    }

    public Post updatePost(Object postId, String postTitle, String postBody) {

        if (currentUser == null){
            throw new NotAuthorizedException(dineAccessMessage);
        }

        Post post = Post.findById(postId);
        post.setTitle(postTitle);
        post.setBody(postBody);
        post.saveIt();

        return post;
    }

    public void deletePost(String id){
        if (currentUser == null){
            throw new NotAuthorizedException(dineAccessMessage);
        }

        Post.delete("id = ?", id);
    }
    
    public Post addCommentToPost(String commentBody, String postId){

        if (currentUser == null){
            throw new NotAuthorizedException(dineAccessMessage);
        }

        Post post = Post.findById(postId);

        if (post != null) {
            Comment comment = new Comment();
            comment.setBody(commentBody);
            comment.setParent(post);
            comment.setUser(currentUser);
            comment.saveIt();
        }

        return post;
    }

    public List<Post> getPosts(){
        List<Post> postList = Post.findAll().orderBy("created_at desc");

        if (hasUser()){
            for (Post post : postList) {
                post.setCanEdit(currentUser);
            }
        }

        return postList;
    }

    public String getUserUrl(){
        return currentUser == null
               ? "https://oauth.yandex.ru/authorize?response_type=code&client_id=" + DatabaseProvider.YANDEX_CLIEND_ID
               : "https://passport.yandex.ru/passport?mode=passport";
    }

    public Post getLastEditedPost() {
        return lastEditedPost;
    }

    public void setLastEditedPost(String id) {
        this.lastEditedPost = Post.findById(id);
    }

    public Post getLastShownPost() {
        return lastShownPost;
    }

    public void setLastShownPost(String id) {
        this.lastShownPost = Post.findById(id);
    }

    public String getLog() {
        return DatabaseProvider.Log;
    }

    public void setLog(String log) {
        DatabaseProvider.Log = log;
    }
}
