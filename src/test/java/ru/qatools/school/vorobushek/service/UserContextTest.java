package ru.qatools.school.vorobushek.service;

import junit.framework.TestCase;
import org.javalite.activejdbc.LazyList;
import org.junit.*;
import org.junit.runners.MethodSorters;
import ru.qatools.school.vorobushek.Server;
import ru.qatools.school.vorobushek.models.Comment;
import ru.qatools.school.vorobushek.models.Post;
import ru.qatools.school.vorobushek.models.User;
import ru.qatools.school.vorobushek.models.UserContext;
import ru.yandex.qatools.allure.annotations.Step;

import java.util.List;

import static java.lang.String.format;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

/**
 * Created by Sergey on 14.01.2015.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserContextTest {

    static private User testUser;
    static private UserContext testUserContext;
    static private Post testPost;

    @Before
    public void openHomePage() {
        new Server();
    }

    @Step("Create user")
    public void createUser() {
        String login = "testUser";
        String email = login + "@yandex.ru";
        String displayName = login.toUpperCase();
        String id = "0";
        testUser = DatabaseProvider.getUser(login, email, displayName, id);
    }

    @Step("Create userContext")
    public void createUserContext() {
        testUserContext = new UserContext(testUser);
    }

    @Step("Create post")
    public void createPost() {
        testPost = testUserContext.createPost("testTitle", "test body for testing", 0);
    }

    @Step("Check post")
    public void checkPost() {
        String title = testPost.getTitle();
        long user_id = (long)testPost.getUser().getId();
        LazyList<Post> posts = Post.find("title=? and user_id=?", title, user_id);
        assertThat(!posts.isEmpty(),is(true));
    }

    @Step("Add comment")
    public void addComment() {
        testUserContext.addCommentToPost("Test comment", String.valueOf(testPost.getId()));
    }

    @Step("Delete comment")
    public void deleteComment() {
        LazyList<Comment> comments = Comment.find("body=? and post_id=?", "Test comment", testPost.getId());
        assertThat(comments.size(),is(1));
        long commentId = (long)comments.get(0).getId();
        testUserContext.deleteComment(String.valueOf(commentId));
    }

    @Step("Get current user")
    public void getCurrentUser() {
        assertThat(testUserContext.getCurrentUser(), is(testUser));
    }

    @Step("Has user")
    public void hasUser() {
        assertThat(testUserContext.hasUser(),is(true));
    }

    @Step("Update post")
    public void updatePost() {
        testPost = testUserContext.updatePost(testPost.getId(),"updatedTestTitle","Updated test body");
        assertThat(testPost.getTitle(),is("updatedTestTitle"));
        assertThat(testPost.getBody(),is("Updated test body"));
    }

    @Step("Delete post")
    public void deletePost() {
        testPost.delete();
        LazyList<Post> posts = Post.find("title=? and body=?", "updatedTestTitle","Updated test body");
        assertThat(posts.isEmpty(),is(true));
    }

    @Step("Delete user")
    public void deleteUser() {
        testUser.delete();
        LazyList<User> users = User.find("login=? and email=?", "testUser","testUser@yandex.ru");
        assertThat(users.isEmpty(),is(true));
    }

    @Test
    public void test1UserAndUserContextCreation() {
        createUser();
        createUserContext();
    }

    @Test
    public void test2PostCreation() {
        createPost();
        checkPost();
    }

    @Test
    public void test3CommentCreation() {
        addComment();
    }

    @Test
    public void test4CommentDeletion() {
        deleteComment();
    }

    @Test
    public void test5GetCurrentUser() {
        getCurrentUser();
        hasUser();
    }

    @Test
    public void test6UpdatePost() {
        updatePost();
    }

    @Test
    public void test7PostDeletion() {
        deletePost();
    }

    @Test
    public void test8UserDeletion() {
        deleteUser();
    }
}