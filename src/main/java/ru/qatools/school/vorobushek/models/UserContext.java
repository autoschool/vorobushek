package ru.qatools.school.vorobushek.models;

import ru.qatools.school.vorobushek.service.DatabaseProvider;

import javax.ws.rs.NotAuthorizedException;
import java.util.List;

/**
 * Created by yurik
 * 22.11.14.
 */
public class UserContext {

    private User currentUser;

    private Post lastPost;

    public UserContext(String token) {
        currentUser = DatabaseProvider.getYandexUser(token);
    }

    public UserContext() {
        currentUser = null;
    }

    public boolean hasUser(){
        return currentUser != null;
    }

    public String getCurrentUserString(){

        return currentUser == null
                ? ""
                : currentUser.getDisplayName();
    }

    public void setLastPost(String postId){
        lastPost = Post.findById(postId);
    }

    public Post getLastPost(){
        return lastPost;
    }

    public Post createPost(String postTitle, String postBody) {

        if (currentUser == null)
            throw new NotAuthorizedException("You Don't Have Permission");

        Post post = new Post();
        post.setTitle(postTitle);
        post.setBody(postBody);
        post.setUser(currentUser);
        post.saveIt();

        lastPost = post;

        return post;
    }

    public Post addCommentToPost(String commentBody, String postId){

        if (currentUser == null)
            throw new NotAuthorizedException("You Don't Have Permission");

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

    public static List<Post> getPosts(){
        return Post.findAll();
    }

    public String getUserUrl(){
        return currentUser == null
               ? "https://oauth.yandex.ru/authorize?response_type=code&client_id=" + DatabaseProvider.YANDEX_CLIEND_ID
               : "https://passport.yandex.ru/passport?mode=passport";
    }

}
