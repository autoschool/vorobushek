package ru.qatools.school.vorobushek.resources;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

import java.net.MalformedURLException;

/**
 * Created by Andrey on 30.11.2014.
 */

public class ITHomePageElementsTest {

    private WebDriverSteps steps;


    @Before
    public void openHomePageAndLogin() {
        steps = new WebDriverSteps(new PhantomJSDriver());
    }

    @Test
    public void homeButtonTest() throws MalformedURLException {
        steps.openHomePage();
        steps.clickOnHomeButton();
        steps.makeScreenshot();
        steps.currentUrlShouldBeHomePage();
    }

    @After
    public void quit() {
        steps.quit();
    }
}
