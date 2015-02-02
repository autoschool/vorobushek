package ru.qatools.school.vorobushek.Pages;

import org.javalite.activejdbc.LazyList;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ru.qatools.school.vorobushek.models.Post;
import ru.qatools.school.vorobushek.web.Resources;
import ru.yandex.qatools.htmlelements.element.Button;
import ru.yandex.qatools.htmlelements.element.TextInput;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;

import javax.ws.rs.POST;
import java.util.Random;

/**
 * Created by yurik
 * 10.01.15.
 */
public class NewPost {

    private static final String BASE_ADDRESS="/post/new";

    private WebDriver driver;

    private String postTitle;
    private String postBody;

    @FindBy(id = "title")
    private TextInput titleInput;

    @FindBy(id = "body")
    private TextInput bodyInput;

    @FindBy(id = "save-post-button")
    private Button saveButton;

    public NewPost(WebDriver driver){
        PageFactory.initElements(new HtmlElementDecorator(driver), this);
        this.driver = driver;
    }

    private String getNewTitle(String user){
        postTitle = Resources.getRandomMessage(user);
        return postTitle;
    }

    private String getNewBody(String user){
        postBody = Resources.getRandomMessage(user);
        return postBody;
    }


    public void setTitle(String user){
        titleInput.clear();
        titleInput.sendKeys(getNewTitle(user));
    }


    public void setBody(String user){
        bodyInput.clear();
        bodyInput.sendKeys(getNewBody(user));
    }

    public Post savePost(){

        if (postTitle != null && !postTitle.isEmpty()
                && postBody != null && !postBody.isEmpty()) {

//            Post post = new Post();
//            post.setTitle(postTitle);
//            post.setBody(postBody);
//            post.saveIt();

            saveButton.click();

            LazyList<Post> posts = Post.findAll();

            return posts.size() == 0 ? null : posts.get(0);
        }
        else return null;


    }

    public static String getBaseAddress() { return BASE_ADDRESS; }
}
