package ru.qatools.school.vorobushek.Pages;

import org.openqa.selenium.WebDriver;
import ru.qatools.school.vorobushek.models.Post;

/**
 * Created by yurik
 * 10.01.15.
 */
public class EditPost extends NewPost {

    private Post post;

    public EditPost(WebDriver driver, int postId) {
        super(driver);
        this.post = Post.findById(postId);
    }

    public String getTitle(){
        return post.getTitle();
    }

    public String getBody(){
        return post.getBody();
    }
}
