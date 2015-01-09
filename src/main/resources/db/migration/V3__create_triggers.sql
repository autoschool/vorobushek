delimiter //


CREATE TRIGGER delete_comments_after_delete_post AFTER DELETE on posts
FOR EACH ROW
BEGIN

  DELETE FROM comments
      WHERE comments.post_id = old.id;

END//

CREATE TRIGGER delete_posts_comments_after_delete_user AFTER DELETE on users
FOR EACH ROW
BEGIN

  DELETE FROM posts
      WHERE posts.user_id = old.id;

  DELETE FROM comments
      WHERE comments.user_id = old.id;

END//
