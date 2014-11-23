package ru.qatools.school.vorobushek.tests;



import net.anthavio.phanbedder.Phanbedder;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import ru.qatools.school.vorobushek.service.DatabaseProvider;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

import java.io.File;


/**
 * Created by yurik
 * 23.11.14.
 */
public class IndexResourceTest  {

    private PhantomJSDriver driver;

    private String baseUrl = "http://localhost:8080";

    @Before
    public void openHomePage() {
        File phantomjs = Phanbedder.unpack();
        DesiredCapabilities dcaps = new DesiredCapabilities();
        dcaps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, phantomjs.getAbsolutePath());
        driver = new PhantomJSDriver(dcaps);


    }

    @Test
    public void test() {

        driver.get(baseUrl);

        final String hello = driver.getPageSource();
        DatabaseProvider.getLogger().info(hello);
    }
}
