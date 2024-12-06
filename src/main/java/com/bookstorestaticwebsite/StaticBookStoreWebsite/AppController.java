package com.bookstorestaticwebsite.StaticBookStoreWebsite;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {


    @GetMapping("/login")
    public String login(){
        return "login";
    }
    @GetMapping("/error")
    public String showError(){
        return "error";
    }
    @GetMapping("/403")
    public String _403(){
        return "403";
    }

}