package com.bookstorestaticwebsite.StaticBookStoreWebsite.admin;


import com.bookstorestaticwebsite.StaticBookStoreWebsite.book.BookService;
import com.bookstorestaticwebsite.StaticBookStoreWebsite.common.BaseController;
import com.bookstorestaticwebsite.StaticBookStoreWebsite.customer.Customer;
import com.bookstorestaticwebsite.StaticBookStoreWebsite.customer.CustomerService;
import com.bookstorestaticwebsite.StaticBookStoreWebsite.order.BookOrderService;
import com.bookstorestaticwebsite.StaticBookStoreWebsite.review.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//@RestController
@Controller
@RequestMapping("/admin")
public class UserCotroller extends BaseController {
    @Autowired
    private UserService userService;
    @Autowired
    private BookService bookService;

    @Autowired
    private BookOrderService bookOrderService;
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private CustomerService customerService;


//Project Part

    @GetMapping("/")
    public String index( Model model){

        model.addAttribute("topBooksWithLatestReview", bookService.getLastestReviewBooks());
        model.addAttribute("recentOrders", bookOrderService.getRecentOrders());
        //statistic
        model.addAttribute("totalUsers", userService.getTotalUsers());
        model.addAttribute("totalBooks", bookService.getTotalBooks());
        model.addAttribute("totalOrders", bookOrderService.getTotalOrders());
        model.addAttribute("totalCustomers", customerService.getTotalCustomers());
        model.addAttribute("totalReviews", reviewService.getTotalReviews());

        return "admin/index";
    }



    @GetMapping("/all")
    public String getAllUsers(Model model){
        List<User> user_list =  userService.getAllUsers();
        model.addAttribute("userList",user_list);
        model.addAttribute("title", "User List");
        return  "admin/user-list";
    }

    @GetMapping("/createUserForm")
    public String createNewUser(Model model){
        model.addAttribute("title", "Create New User");
        return "admin/user-form";
    }

    @PostMapping("/createUser")
    public  String createNewUser(@ModelAttribute("user") User user){
        userService.createNewUser(user);
        return "redirect:/admin/all";
    }

    @PostMapping("/edit/{userId}")
    public String updateUser(@PathVariable long userId, @ModelAttribute User user) {
        userService.updateUser(user, userId);
        return "redirect:/admin/all";
    }


    @GetMapping("/edit/{userId}")
    public String editUser(@PathVariable long userId, Model model){
        //Get user by and display it first
        model.addAttribute("user", userService.getUserById(userId));
        model.addAttribute("title", "Edit User");
        return "admin/user-edit";
    }

    @GetMapping("/delete/{userId}")
    public String deleteUser(@PathVariable long userId){
        userService.deleteUserById(userId);
        return "redirect:/admin/all";
    }

    //API Part
//    //List all users
//    @GetMapping("/all")
//    public List<User> getAllUsers(Model model){
//        List<User> user_list =  userService.getAllUsers();
//        return  user_list;
//    }
//    //create
//    @PostMapping("/create")
//    public List<User> createNewUser(@RequestBody User user){
//        userService.createNewUser(user);
//        return userService.getAllUsers();
//    }
//
//    //edit
//    @PutMapping("edit/{uId}")
//    public User updateUser(@PathVariable int uId, @RequestBody User user){
//        userService.updateUser(user, uId);
//        return userService.getUserById(uId);
//    }
//    //delete
//    @DeleteMapping("delete/{uId}")
//    public List<User> deleteUser(@PathVariable int uId){
//        userService.deleteUserById(uId);
//        return userService.getAllUsers();
//    }
//
//
//    //search by Id
//    @GetMapping("/search/{uId}")
//    public User getUserById(@PathVariable int uId){
//        return userService.getUserById(uId);
//    }



}
