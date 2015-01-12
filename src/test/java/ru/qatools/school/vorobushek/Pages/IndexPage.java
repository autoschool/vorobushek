package ru.qatools.school.vorobushek.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ru.qatools.school.vorobushek.Elements.PostItem;
import ru.qatools.school.vorobushek.models.Post;
import ru.qatools.school.vorobushek.service.DatabaseProvider;
import ru.yandex.qatools.htmlelements.element.HtmlElement;
import ru.yandex.qatools.htmlelements.element.Link;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yurik
 * 10.01.15.
 */
public class IndexPage {
    private WebDriver driver;


    @FindBy(className = "postBody")
    private List<WebElement> postItems;

    @FindBy(id = "user-name")
    private WebElement userLink;

    @FindBy(id = "logout")
    private HtmlElement logoutLink;

    public IndexPage(WebDriver webDriver){
        this.driver = webDriver;
        PageFactory.initElements(new HtmlElementDecorator(webDriver), this);
    }

    public void logout(){
        userLink.click();
        logoutLink.click();
    }

    public String getCurrentUserName(){
        return userLink.getText();
    }


    public boolean isSignIn(){

        boolean hasSignInLink = false;

        try {
            WebElement link = driver.findElement(By.id("sign-in"));
            hasSignInLink = link != null;
        }
        catch (Exception e){
            DatabaseProvider.getLogger().error("Cannot find signIn link. It means that user have login to site", e.getMessage());
        }

//        DatabaseProvider.getLogger().info(driver.getPageSource());

        return !hasSignInLink;
    }


    public List<PostItem> getPostItemAll(){

        List<PostItem> PostItems = new ArrayList<>();

        for (WebElement postItem: postItems){

            String currentId  = postItem.getAttribute("id").replace("body-","");

            Post post = Post.findById(currentId);

            if (post == null) continue;

            PostItems.add(new PostItem(post, driver));

        }

        return PostItems;
    }

}
