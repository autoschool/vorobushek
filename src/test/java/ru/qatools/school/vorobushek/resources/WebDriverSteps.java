package ru.qatools.school.vorobushek.resources;

import org.openqa.selenium.*;
import ru.yandex.qatools.allure.annotations.Attachment;
import ru.yandex.qatools.allure.annotations.Step;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/**
 * Created by Andrey on 30.11.2014.
 */
public class WebDriverSteps {

    private static final String baseUrl = "http://localhost:8080";
    private static final String logoutURL = "/logout";
    private static final String testerLoginURL = "/testlogin/tester/password/tester@test.test";


    private static final String logoutID = "logout";
    private static final String newPostButtonId = "new-post";
    private static final String titleInputId = "title";
    private static final String newPostBodyTextareaId = "new-post-body-textarea";
    private static final String editBodyTextareaId = "edit-body-textarea";
    private static final String newCommentTextareaId = "new-comment-textarea";
    private static final String editTitleInputId = "edit-title-input";
    private static final String saveButtonId = "save-post-button";
    private static final String homeLinkId = "home-link";
    private static final String addCommentButtonId = "add-comment-button";
    private static final String saveChangesButtonId = "save-button";
    private static final String postTitleId = "post-title";
    private static final String postBodyId = "post-body";

    private static final String commentClassName = "well";

    private static final Dimension windowDimension = new Dimension(1200, 800);

    public static final String DELETE = "delete";
    public static final String EDIT = "edit";

    private WebDriver driver;

    public WebDriverSteps(WebDriver driver) {
        this.driver = driver;
        driver.manage().window().setSize(windowDimension);
    }

    public void quit() {
        driver.quit();
        driver = null;
    }


    @Step("Open homepage.")
    public void openHomePage() {
        driver.get(baseUrl);
    }

    @Step("Tester sign in.")
    public void signIn() {
        driver.get(baseUrl + testerLoginURL);
    }

    @Step("Tester logout.")
    public void logout() {
        driver.get(baseUrl + logoutURL);
    }

    @Step("Add new post with title = '{0}' and body = '{1}'.")
    public void addNewPost(String title, String body) {
        driver.findElement(By.id(newPostButtonId)).click();
        driver.findElement(By.id(titleInputId)).sendKeys(title);
        driver.findElement(By.id(newPostBodyTextareaId)).sendKeys(body);
        driver.findElement(By.id(saveButtonId)).click();
        driver.get(baseUrl);
    }

    @Step("Add new comment \''{0}'\' to the opened post.")
    public void addComment(String comment) {
        driver.findElement(By.id(newCommentTextareaId)).sendKeys(comment);
        driver.findElement(By.id(addCommentButtonId)).click();
    }

    @Step("Open the post with title = '{0}'.")
    public void goToPostByTitle(String title) {
        driver.findElement(By.linkText(title)).click();
    }

    @Step("Click on \''{1}'\' button of the post with \''{0}'\' title.")
    public void findPostActionButtonAndClick(String title, String action) {
        WebElement postLink = driver.findElement(By.linkText(title));
        String postId = postLink.getAttribute("id");

        WebElement button = driver.findElement(By.id(String.format("%s-button-%s", action, postId)));
        button.click();
    }

    @Step("Change post title on '{0}', post body on '{1}'.")
    public void editPost(String newTitle, String newBody) {
        WebElement input = driver.findElement(By.id(editTitleInputId));
        input.clear();
        input.sendKeys(newTitle);
        input = driver.findElement(By.id(editBodyTextareaId));
        input.clear();
        input.sendKeys(newBody);
        driver.findElement(By.id(saveChangesButtonId)).click();
    }

    @Step("Click on 'Home' button.")
    public void clickOnHomeButton() {
        driver.findElement(By.id(homeLinkId)).click();
    }

    @Attachment(type = "image/png")
    public byte[] makeScreenshot() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    //asserts
    @Step("Equal post comment.")
    public void commentShouldBe(String comment) {
        String actualComment = driver.findElement(By.className(commentClassName)).getText();
        assertThat(actualComment, startsWith(comment));
    }

    @Step("Equal post title and body.")
    public void postShouldBe(String title, String body) {
        String actualTitle = driver.findElement(By.id(postTitleId)).getText();
        String actualMessage = driver.findElement(By.id(postBodyId)).getText();

        assertThat(actualTitle, equalTo(title));
        assertThat(actualMessage, equalTo(body));
    }

    @Step("Is there link with text = \''{0}'\'?")
    public void postShouldBeDeleted(String title) {
        List<WebElement> actualPosts = driver.findElements(By.xpath("//a/h3"));
        List<String> actualPostTitles = new ArrayList<>();
        for (WebElement actualPost : actualPosts) {
            actualPostTitles.add(actualPost.getText());
        }
        assertThat(actualPostTitles, not(hasItem(title)));
    }

    @Step()
    public void currentUrlShouldBeHomePage() {
        assertThat(driver.getCurrentUrl(), equalTo(baseUrl + "/"));
    }
}
