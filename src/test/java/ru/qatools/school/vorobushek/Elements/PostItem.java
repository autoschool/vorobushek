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
public class PostItem {

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

    public String getBody(){
        String elementId = "body-"+post.getId().toString();
        return driver.findElement(By.id(elementId)).getText();
    }

    public PostItem(Post post, WebDriver driver){
        this.post = post;
        this.driver = driver;
    }

    public void Delete(){
        String elementId = "delete-button-"+post.getId().toString();
        driver.findElement(By.id(elementId)).click();

        //getDeleteButton().click();
    }

    public void Edit(){
        getEditButton().click();
    }
}
