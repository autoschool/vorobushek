package ru.qatools.school.vorobushek.web;

import mx4j.tools.config.DefaultConfigurationBuilder;
import net.anthavio.phanbedder.Phanbedder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import ru.qatools.school.vorobushek.Elements.PostItem;
import ru.qatools.school.vorobushek.Pages.IndexPage;
import ru.qatools.school.vorobushek.Pages.NewPost;
import ru.qatools.school.vorobushek.models.Post;
import ru.qatools.school.vorobushek.service.DatabaseProvider;
import ru.yandex.qatools.allure.annotations.Step;

import java.io.File;
import java.util.List;
import java.util.Random;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

/**
 * Created by yurik
 * 10.01.15.
 */
public class ITTest {

    private static final String USER_NAME = "IvanGoncharov";

    private IndexPage index;
    private NewPost newPost;

    public WebDriver driver;

    @Before
    public void loadStartPage() {

        //http://blog.anthavio.net/2014/04/phantomjs-embedder-for-selenium.html

        File phantomjs = Phanbedder.unpack();
        DesiredCapabilities dcaps = new DesiredCapabilities();
        dcaps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, phantomjs.getAbsolutePath());

        DatabaseProvider.openConnection();

        driver = new PhantomJSDriver(dcaps);
        driver.manage().window().setPosition(new Point(0,0));
        driver.manage().window().setSize(new Dimension(1200,800));
        driver.get(IndexPage.getBaseAddres());

    }

    @Step("login.")
    public void login() {
        index = new IndexPage(driver);
        index.login(USER_NAME);
    }

    @Step("Check count of posts.")
    public void checkCountPosts(){
        assertThat(index.getPostItemAll().isEmpty(), is(true));
    }

    @Step("Check user name.")
    public void checkUserName(){
        assertThat(index.getCurrentUserName().contains(USER_NAME), is(true));
    }

    @Step("Logout.")
    public void logout(){
        index.logout();
    }

    @Step("Check logout.")
    public void checkLogout(){
        assertThat(index.isSignIn(), is(false));
    }

    @Step("Open create post.")
    public void openCreatePost(){
        driver.get(IndexPage.getBaseAddres());
        index = new IndexPage(driver);
        index.login(USER_NAME);
        driver.get(IndexPage.getBaseAddres() + NewPost.getBaseAddress());
        newPost = new NewPost(driver);
    }

    @Step("Create post")
    public Post createPost(){
        newPost.setTitle(USER_NAME);
        newPost.setBody(USER_NAME);
        return  newPost.savePost();
    }

    @Step("Delete post")
    public void deletePost(Post post){
        driver.get(IndexPage.getBaseAddres());
        index = new IndexPage(driver);
        index.login(USER_NAME);

        List<PostItem> posts = index.getPostItemAll();

        for (PostItem postItem: posts){
            if (postItem.getBody().equalsIgnoreCase(post.getBody()))
                postItem.Delete();
        }
    }

    @After
    public void killWebDriver() {
        driver.quit();
    }

    @Test
    public void testIndexPage(){
        login();
        checkUserName();
        checkCountPosts();
        logout();
        checkLogout();
    }

//    @Test
//    public void testCreatePost(){
//        openCreatePost();
//        Post newpost = createPost();
//
//        assertThat(newpost != null, is(true));
//
//        deletePost(newpost);
//
//    }

}
