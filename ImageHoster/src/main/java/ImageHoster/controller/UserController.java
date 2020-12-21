package ImageHoster.controller;

import ImageHoster.model.Image;
import ImageHoster.model.User;
import ImageHoster.model.UserProfile;
import ImageHoster.service.ImageService;
import ImageHoster.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;

    //This controller method is called when the request pattern is of type 'users/registration'
    //This method declares User type and UserProfile type object
    //Sets the user profile with UserProfile type object
    //Adds User type object to a model and returns 'users/registration.html' file
    @RequestMapping("users/registration")
    public String registration(Model model) {
        User user = new User();
        UserProfile profile = new UserProfile();
        user.setProfile(profile);
        model.addAttribute("User", user);
        return "users/registration";
    }

    //This controller method is called when the request pattern is of type 'users/registration' and also the incoming request is of POST type
    //This method calls the business logic and after the user record is persisted in the database, directs to login page
   /* @RequestMapping(value = "users/registration", method = RequestMethod.POST)
    public String registerUser(User user) {
        userService.registerUser(user);
        return "redirect:/users/login";
    }*/
   /*Following changes are made to the above method registerUser()-
   ** 1. The method is invoked when the user clicks the register button on the screen registration.html
   ** 2. Changes are made to implement password correction when the user enters inappropriate password.
   ** 3. The method invokes checkUserProfile(user) inorder to receive error message if any
   ** 4. If there is no error message, the new user is registered.
   * */
    @RequestMapping(value = "users/registration", method = RequestMethod.POST)
    public String registerUser(User user, Model model, HttpSession session, RedirectAttributes redirectAtt) {
        //check the password before registering the user.
        //throw passwordTypeError for incorrect password and do not register the user.
        if(checkPassword(user))  {
            userService.registerUser(user);
            return "redirect:/users/login";
        }
        else {
            String error = "Password must contain atleast 1 alphabet, 1 number & 1 special character";
            redirectAtt.addAttribute("passwordTypeError", error).addFlashAttribute("passwordTypeError", error);
            return "redirect:/users/registration";
        }
    }

    /*checkPassword method-
    ** The checkPassword method is passed the new user details
    ** This method gets the password entered by the user from model class of User.
    ** It then checks if the password contatins atleast one character, one numeric and one special character.
    ** If yes, it returns true.
    ** else returns false.
    ** */

    public static boolean checkPassword(User user) {
        //get password from User model class.
        String password = user.getPassword();

        if (password.isEmpty())
            return false;

        boolean hasChar = false;
        boolean hasNumeric = false;
        boolean hasSpl = false;

        for (int i = 0; i < password.length(); i++) {
            //type casting ascii value of the character.
            int j = password.charAt(i);

            //checks for special character
            if((j>=32 && j<=47) || (j>=58 && j<=64) || (j>=91 && j<=96) || (j>=123 && j<=126))
                hasSpl = true;
            //checks for numeric
            if(j>=48 && j<=57)
                hasNumeric = true;
            //checks for characters (A-Z), (a-z)
            if((j>=65 && j<=90) || (j>=97 && j<=122))
                hasChar = true;

            if(hasSpl && hasChar && hasNumeric)
                break;
        }

        if(hasSpl && hasChar && hasNumeric)
            return true;
        else
            return false;
    }


    //This controller method is called when the request pattern is of type 'users/login'
    @RequestMapping("users/login")
    public String login() {
        return "users/login";
    }

    //This controller method is called when the request pattern is of type 'users/login' and also the incoming request is of POST type
    //The return type of the business logic is changed to User type instead of boolean type. The login() method in the business logic checks whether the user with entered username and password exists in the database and returns the User type object if user with entered username and password exists in the database, else returns null
    //If user with entered username and password exists in the database, add the logged in user in the Http Session and direct to user homepage displaying all the images in the application
    //If user with entered username and password does not exist in the database, redirect to the same login page
    @RequestMapping(value = "users/login", method = RequestMethod.POST)
    public String loginUser(User user, HttpSession session) {
        User existingUser = userService.login(user);
        if (existingUser != null) {
            session.setAttribute("loggeduser", existingUser);
            return "redirect:/images";
        } else {
            return "users/login";
        }
    }

    //This controller method is called when the request pattern is of type 'users/logout' and also the incoming request is of POST type
    //The method receives the Http Session and the Model type object
    //session is invalidated
    //All the images are fetched from the database and added to the model with 'images' as the key
    //'index.html' file is returned showing the landing page of the application and displaying all the images in the application
    @RequestMapping(value = "users/logout", method = RequestMethod.POST)
    public String logout(Model model, HttpSession session) {
        session.invalidate();

        List<Image> images = imageService.getAllImages();
        model.addAttribute("images", images);
        return "index";
    }
}
