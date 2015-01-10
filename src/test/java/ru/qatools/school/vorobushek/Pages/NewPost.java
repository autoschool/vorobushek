package ru.qatools.school.vorobushek.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ru.yandex.qatools.htmlelements.element.Button;
import ru.yandex.qatools.htmlelements.element.TextInput;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;

/**
 * Created by yurik
 * 10.01.15.
 */
public class NewPost {
    private WebDriver driver;

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

    public void setTitle(String title){
        titleInput.clear();
        titleInput.sendKeys(title);
    }

    public void setBody(String body){
        bodyInput.clear();
        bodyInput.sendKeys(body);
    }

    public void  savePost(){
        saveButton.click();
    }
}
