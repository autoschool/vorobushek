package ru.qatools.school.vorobushek.web;

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
import ru.qatools.school.vorobushek.Pages.IndexPage;
import ru.qatools.school.vorobushek.Pages.NewPost;
import ru.qatools.school.vorobushek.service.DatabaseProvider;
import ru.yandex.qatools.allure.annotations.Step;

import java.io.File;

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


        driver = new PhantomJSDriver(dcaps);
        driver.manage().window().setPosition(new Point(0,0));
        driver.manage().window().setSize(new Dimension(1200,800));

        DatabaseProvider.openConnection();
    }

    @Step("Open homepage.")
    public void openIndex() {
        driver.get("http://localhost:9183/login?code="+USER_NAME);
        index = new IndexPage(driver);
    }

    @Step("Check count of posts.")
    public void checkCountPosts(){
        assertThat(!index.getPostItemAll().isEmpty(), is(true));
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
        driver.get("http://localhost:9183/login?code="+USER_NAME);
        driver.get("http://localhost:9183/post/new");
        newPost = new NewPost(driver);
    }

    @Step("Create post")
    public void createPost(){
        newPost.setTitle("test post title");
        newPost.setBody("test post body");
        newPost.savePost();
    }

    @After
    public void killWebDriver() {
        driver.quit();
    }

    @Test
    public void testIndexPage(){
        openIndex();
        checkUserName();
        checkCountPosts();
        logout();
        checkLogout();
    }

    @Test
    public void testCreatePost(){
//        openCreatePost();
//        createPost();
    }

}
