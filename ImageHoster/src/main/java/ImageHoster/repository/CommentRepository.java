package ImageHoster.repository;
/*CommentRepository.java -
** This method is coded to execute the SQL statements
** pertaining to the comment_tbl table in the PostgreSQL database.
** This table is referred as Comment.java class in this application.
* */
import ImageHoster.model.Comment;
import ImageHoster.model.Image;
import ImageHoster.model.User;

import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.*;

@Repository
public class CommentRepository {
    @PersistenceUnit(unitName = "imageHoster")
    private EntityManagerFactory emf;

    /*submitComment() Method-
       The method receives Comment record from the ImageService class
       starts a transaction
       the transaction is committed if it is successful
       the transaction is rolled back in case of unsuccessful transaction.
   */
    public void submitComment(Comment newComment) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            em.persist(newComment);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
    }

    /*get AllComments() method returns list of comments associated with the given Image_id*/
   public List<Comment> getAllComments(Integer imageId) {
       EntityManager em = emf.createEntityManager();

        try {
            TypedQuery<Comment> typedQuery = em.createQuery("SELECT c from Comment c where c.image.id =:imageId", Comment.class);
            typedQuery.setParameter("imageId",imageId );
            List<Comment>  commentList  =  typedQuery.getResultList();
            return commentList;
        } catch (NoResultException nre) {
            return null;
        }
    }
}


