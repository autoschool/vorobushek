package ru.qatools.school.vorobushek.web;

import com.sun.jndi.url.iiop.iiopURLContext;
import net.anthavio.phanbedder.Phanbedder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import ru.qatools.school.vorobushek.Pages.IndexPage;
import ru.qatools.school.vorobushek.service.DatabaseProvider;
import ru.yandex.qatools.allure.annotations.Step;

import java.io.File;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

/**
 * Created by yurik
 * 10.01.15.
 */
public class ITindexTest {

    private static final String USER_NAME = "IvanGoncharov";

    private IndexPage index;

    public WebDriver driver;



    @Before
    public void loadStartPage() {

        //http://blog.anthavio.net/2014/04/phantomjs-embedder-for-selenium.html

        File phantomjs = Phanbedder.unpack();
        DesiredCapabilities dcaps = new DesiredCapabilities();
        dcaps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, phantomjs.getAbsolutePath());

        driver = new PhantomJSDriver(dcaps);

        DatabaseProvider.openConnection();

        try {
            driver.get("http://localhost:8080/login?code="+USER_NAME);

        }
        catch (Exception e){
            DatabaseProvider.getLogger().error("Cant get home link", e.getMessage());
        }
    }

    @Test
    public void testIndexPage(){
        open();
        //checkUserName();
        checkCountPosts();
        //logout();
        //checkLogout();
    }

    @Step("Open homepage.")
    public void open() {
        index = new IndexPage(driver);
    }

    @Step("Check count of posts.")
    public void checkCountPosts(){
        assertThat(!index.getPostItemAll().isEmpty(), is(true));
    }

    @Step("Check user name.")
    public void checkUserName(){
        index.getCurrentUserName();
        //assertThat(index.getCurrentUserName().contains(USER_NAME), is(true));
    }

    @Step("Logout.")
    public void logout(){
        index.logout();
    }

    @Step("Check logout.")
    public void checkLogout(){
        assertThat(index.isSignIn(), is(false));
    }

    @After
    public void killWebDriver() {
        driver.quit();
    }


}
