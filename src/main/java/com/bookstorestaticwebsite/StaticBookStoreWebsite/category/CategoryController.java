package com.bookstorestaticwebsite.StaticBookStoreWebsite.category;


import com.bookstorestaticwebsite.StaticBookStoreWebsite.admin.User;
import com.bookstorestaticwebsite.StaticBookStoreWebsite.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//@RestController
@Controller
@RequestMapping("/admin/category")
public class CategoryController extends BaseController {
    @Autowired
    private CategoryService service;

    @GetMapping("/all")
    public String getAllCategory(Model model){
        List<Category> categoryList = service.getAllCategories();
        model.addAttribute("categoryList",categoryList);
        model.addAttribute("title", "Category List");
        return  "admin/category-list";
    }

    @PostMapping("/createCategory")
    public String createCategory(Category category){
        service.createNewCategory(category);
        return "redirect:/admin/category/all";
    }

    @GetMapping("/createCategoryForm")
    public String createNewUser(Model model){
        model.addAttribute("title", "Create New Category");
        return "admin/category-form";
    }

    @PostMapping("/edit/{cId}")
    public String updateCategory(@PathVariable int cId, @ModelAttribute Category category) {
        service.updateCategory(category, cId);
        return "redirect:/admin/category/all";
    }


    @GetMapping("/edit/{cId}")
    public String editCategory(@PathVariable int cId, Model model){
        //Get user by and display it first
        model.addAttribute("category", service.getCategoryById(cId));
        model.addAttribute("title", "Edit Category");
        return "admin/category-edit";
    }
    @GetMapping("/delete/{cId}")
    public String deleteCategory(@PathVariable int cId){
        service.deleteCategoryById(cId);
        return "redirect:/admin/category/all";
    }
    //For Api

//    //list all
//    @GetMapping("/all")
//    public List<Category> getAllCategories(){
//        return service.getAllCategories();
//    }
//
//    //search by id
//    @GetMapping("/search/{caId}")
//    public Category getCategoryById(@PathVariable int caId){
//        return service.getCategoryById(caId);
//    }
//
//    //create
//    @PostMapping("/create")
//    public List<Category> createNewCategory(@RequestBody Category category){
//        service.createNewCategory(category);
//        return service.getAllCategories();
//    }
//
//    //edit
//    @PutMapping("/edit/{caId}")
//    public Category updateCategory(@RequestBody Category category, @PathVariable int caId){
//        service.updateCategory(category, caId);
//        return service.getCategoryById(caId);
//    }
//
//    //delete
//    @DeleteMapping("/delete/{caId}")
//    public List<Category> deleteCategory(@PathVariable int caId){
//        service.deleteCategoryById(caId);
//        return service.getAllCategories();
//    }
//
//




}
