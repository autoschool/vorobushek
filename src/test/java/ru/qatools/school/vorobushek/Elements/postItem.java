package ru.qatools.school.vorobushek.Elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.qatools.school.vorobushek.models.Post;
import ru.yandex.qatools.htmlelements.element.Button;
import ru.yandex.qatools.htmlelements.element.HtmlElement;
import ru.yandex.qatools.htmlelements.element.TextBlock;


/**
 * 10.01.15.
 * Created by yurik
 */
public class PostItem extends HtmlElement {

    private WebDriver driver;

    private Post post;

    private Button getDeleteButton(){
        String elementId = "delete-"+post.getId().toString();
        return (Button) driver.findElement(By.id(elementId));
    }

    private Button getEditButton(){
        String elementId = "edit-"+post.getId().toString();
        return (Button) driver.findElement(By.id(elementId));
    }

    private TextBlock getBody(){
        String elementId = "body-"+post.getId().toString();
        return (TextBlock)driver.findElement(By.id(elementId));
    }

    public PostItem(Post post, WebDriver driver){
        this.post = post;
        this.driver = driver;
    }

    public String getBodyText(){
        return getBody().getText();
    }

    public void Delete(){
        getDeleteButton().click();
    }

    public void Edit(){
        getEditButton().click();
    }
}
