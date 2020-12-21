package ImageHoster.model;
/*Comment.java -
** This program creates Comment class and Comment_tbl in the database.
**
*  The Comment_tbl table is mapped with the Images table via image_id
** One Imagecan have multiple comments, while each comment is mapped to only one image.
*
** The Comment_tbl is mapped with Users table via user_id
** Many comments can be posted by One user for one image.
**/

import ImageHoster.model.Image;
import ImageHoster.model.User;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "Comment_tbl")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) //primary key column
    @Column(name = "id")
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String commentText;

    @Column(name = "date")
    private Date date;

    //Many comments can be posted by one user. Therefore we need Many to One mapping
    //but one comment belongs to one user.
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")           //joined with users table by user_id, foreign key
    private User user;

    //One Comment belongs to One Image. Therefore, we need to use One to One mapping.
    //but one Image can have many comments.
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "image_id")          //joined with images table by image_id, foreign key
    private Image image;

    /*Getter and Setter methods fot the class variables*/
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
