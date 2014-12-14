package ru.qatools.school.vorobushek.models;

import ru.qatools.school.vorobushek.service.DatabaseProvider;

import javax.ws.rs.NotAuthorizedException;
import java.util.List;

/**
 * Created by yurik
 * 22.11.14.
 */
public class UserContext {

    private String dineAccessMessage;

    private final User currentUser;

    private Post lastShownPost;
    private Post lastEditedPost;

    private String currentProjectVersion;
    private String yandexSpeechKitKey;
    private String yandexClientId;


    private void initContextVariables(){
        currentProjectVersion = DatabaseProvider.getProjectBuildNumber();
        yandexSpeechKitKey = DatabaseProvider.getYandexSpeechKitKey();
        yandexClientId = DatabaseProvider.getYandexClientId();
        dineAccessMessage = "You Don't Have Permission";
    }

    public UserContext(String token) {
        currentUser = DatabaseProvider.getYandexUser(token);
        initContextVariables();
    }

    public UserContext(User tester) {
        currentUser = tester;
        initContextVariables();
    }

    public UserContext() {
        currentUser = null;
        initContextVariables();
    }


    public boolean hasUser(){
        return currentUser != null;
    }

    public String getCurrentUserString(){

        return currentUser == null
                ? ""
                : currentUser.getLogin();
    }

    public User getCurrentUser(){
        return currentUser;
    }

    public Post createPost(String postTitle, String postBody, int freq) {

        if (currentUser == null){
            throw new NotAuthorizedException(dineAccessMessage);
        }

        Post post = new Post();
        post.setTitle(postTitle);
        post.setBody(postBody);
        post.setUser(currentUser);
        post.setFreq(freq);
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
    
    public void deleteComment(String id) {
        if (currentUser == null){
            throw new NotAuthorizedException(dineAccessMessage);
        }

        Comment.delete("id = ?", id);
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
               ? "https://oauth.yandex.ru/authorize?response_type=code&client_id=" + yandexClientId
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

    public String getCurrentProjectVersion(){
        return currentProjectVersion;
    }

    public String getYandexSpeechKitKey() { return yandexSpeechKitKey; }
    
    public User getUser() {
        return currentUser;
    }
}
