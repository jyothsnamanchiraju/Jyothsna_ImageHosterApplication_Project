package ImageHoster.service;
/*CommentService.java -
 ** This method is coded to implement service logic related to Comment.java class.
 * */
import ImageHoster.model.Comment;
import ImageHoster.model.Image;

import ImageHoster.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    //Call the getAllCommentsForImage() method in the Repository
    // to obtain and return the List of all comments associated with the image_id from the database
   public List<Comment> getAllCommentsForImage(Integer imageId) {
           return commentRepository.getAllComments(imageId);
    }

    //The method calls the submitComment() method in the Repository and passes the Comment to be updated in the database
    public void submitComment(Comment newComment) {
       commentRepository.submitComment(newComment);
    }

}
