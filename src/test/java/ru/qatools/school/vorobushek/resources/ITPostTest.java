package ru.qatools.school.vorobushek.resources;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.phantomjs.PhantomJSDriver;


/**
 * Created by Andrey on 22.11.2014.
 */

public class ITPostTest {

    private String postTitle = "test post title";
    private String postBody = "test post message";
    private String postComment = "test comment";

    private WebDriverSteps steps;


    @Before
    public void createDriverAndSignIn() {
        steps = new WebDriverSteps(new PhantomJSDriver());
        steps.signIn();
    }

    @Test
    public void addPostTest() {
        steps.openHomePage();
        steps.addNewPost(postTitle, postBody);
        steps.goToPostByTitle(postTitle);
        steps.makeScreenshot();
        steps.postShouldBe(postTitle, postBody);
    }

    @Test
    public void addCommentTest() {
        String title = postTitle + " with comment";

        steps.openHomePage();
        steps.addNewPost(title, postBody);
        steps.goToPostByTitle(title);
        steps.addComment(postComment);
        steps.openHomePage();
        steps.goToPostByTitle(title);
        steps.makeScreenshot();
        steps.commentShouldBe(postComment);
    }

    @Test
    public void deletePostTest() {
        String title = this.postTitle + " (delete)";

        steps.openHomePage();
        steps.addNewPost(title, postBody);
        steps.findPostActionButtonAndClick(title, WebDriverSteps.DELETE);
        steps.makeScreenshot();
        steps.postShouldBeDeleted(title);
    }

    @Test
    public void editPostTest() {
        String title = this.postTitle + " (edit)";
        String newTitle = this.postTitle + " edited";
        String newBody = postBody + " edited";

        steps.openHomePage();
        steps.addNewPost(title, postBody);
        steps.findPostActionButtonAndClick(title, WebDriverSteps.EDIT);
        steps.editPost(newTitle, newBody);
        steps.openHomePage();
        steps.goToPostByTitle(newTitle);
        steps.makeScreenshot();
        steps.postShouldBe(newTitle, newBody);
    }

    @After
    public void quit() {
        steps.logout();
        steps.quit();
    }

}

