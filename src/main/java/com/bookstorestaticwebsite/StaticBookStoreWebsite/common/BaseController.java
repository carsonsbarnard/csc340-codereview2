package com.bookstorestaticwebsite.StaticBookStoreWebsite.common;
import com.bookstorestaticwebsite.StaticBookStoreWebsite.admin.User;
import com.bookstorestaticwebsite.StaticBookStoreWebsite.admin.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;
public abstract class BaseController {

    @Autowired
    private UserService userService;

    @ModelAttribute
    public void addUserToModel(@AuthenticationPrincipal org.springframework.security.core.userdetails.User user, Model model){
        if(user != null){
            User admin = userService.getUserByEmail(user.getUsername());
            model.addAttribute("admin", admin);
        }
    }

}
