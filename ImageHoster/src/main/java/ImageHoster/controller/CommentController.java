package ImageHoster.controller;
/*CommentController.java -
 ** The program is created in Controller Package to address the issues related to
 ** the Comment.java class.
 ***/
import ImageHoster.model.Comment;
import ImageHoster.model.Image;
import ImageHoster.model.User;

import ImageHoster.service.ImageService;
import ImageHoster.service.CommentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private ImageService imageService;

    /*@Autowired
    private UserService userService;*/


    /*postComments() Method -
     **Below method is coded to create an instance for  the Comment class.
     ** The newComment instance is mapped to User who has lodged the comment and to the Image that was commented
     ** The newComment instance is then passed to imageService.submitComment() method to create a record of this comment in the database.
     */
    @RequestMapping(value = "/image/{id}/{title}/comments", method = RequestMethod.POST)
    public String postComments(@PathVariable("id") Integer imageId, @PathVariable("title") String imageTitle, @RequestParam(name = "comment") String cText, HttpSession session) {

        Comment newComment = new Comment();

        newComment.setCommentText(cText);
        newComment.setDate(new Date());

        //retrieving the logged user details from httpSession
        User user = (User)session.getAttribute("loggeduser");
        newComment.setUser(user);

        //retrieving image details from service method.
        Image image = imageService.getImageByTitle(imageId,imageTitle);
        newComment.setImage(image);

        /*  Service Logic*/
        commentService.submitComment(newComment);


        return "redirect:/images/"+imageId+"/"+imageTitle;
    }
}


