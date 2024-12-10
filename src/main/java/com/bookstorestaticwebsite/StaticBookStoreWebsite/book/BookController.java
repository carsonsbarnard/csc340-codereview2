package com.bookstorestaticwebsite.StaticBookStoreWebsite.book;


import com.bookstorestaticwebsite.StaticBookStoreWebsite.category.Category;
import com.bookstorestaticwebsite.StaticBookStoreWebsite.category.CategoryService;
import com.bookstorestaticwebsite.StaticBookStoreWebsite.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
//@RestController
@Controller
@RequestMapping("admin/book")
public class BookController extends BaseController {

    @Autowired
    private BookService service;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/all")
    public String getAllBooks(Model model){
        List<Book> bookList = service.getAllBooks();
        bookList.forEach(book ->{
            System.out.println(book.getImagePath());
        });
        model.addAttribute("bookList",bookList);
        model.addAttribute("title", "Book List");
        return  "admin/book-list";
    }

    @PostMapping("/createBook")
    public String createBook(@ModelAttribute("book") Book book){
        service.createNewBook(book);
        return "redirect:/admin/book/all";
    }
    @GetMapping("/createBookForm")
    public String createNewBook(Model model){
        List<Category> categoryList = categoryService.getAllCategories();
        model.addAttribute("title", "Create New Book");
        model.addAttribute("categoryList", categoryList);
        return "admin/book-form"; // book-form.html file in admin folder
    }


    @PostMapping("/edit/{bId}")
    public String updateCategory(@PathVariable int bId, @ModelAttribute Book book) {
        service.updateBook(book, bId);
        return "redirect:/admin/book/all";
    }


    @GetMapping("/edit/{bId}")
    public String editBook(@PathVariable int bId, Model model){
        Book book = service.getBookById(bId);
        String imagePath = book.getImagePath();
        String imageName = Paths.get(imagePath).getFileName().toString();
        List<Category> categoryList = categoryService.getAllCategories();
        model.addAttribute("book",book);
        model.addAttribute("imageName", imageName);
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("title", "Edit Book");
        return "admin/book-edit";
    }
    @GetMapping("/delete/{bId}")
    public String deleteBook(@PathVariable int bId){
        service.deleteBookById(bId);
        return "redirect:/admin/book/all";
    }

    //For Api
//    @GetMapping("/all")
//    public List<Book> getAllBooks(){
//        return service.getAllBooks();
//    }
//    //search by Id
//    @GetMapping("/search/{bId}")
//    public Book getBookById(@PathVariable int bId){
//        return service.getBookById(bId);
//    }
//    //Search by category
//    @GetMapping("search/category/{categoryName}")
//    public  List<Book> getBooksByCategory(@PathVariable String categoryName){
//        return service.findBookByCategoryName(categoryName);
//    }
//
//    //create
//    @PostMapping("/create")
//    public List<Book> createNewBook(@RequestBody Book book){
//        service.createNewBook(book);
//        return service.getAllBooks();
//    }
//
//    //edit
//    @PutMapping("/edit/{bId}")
//    public Book updateBook(@RequestBody Book book, @PathVariable int bId){
//        service.updateBook(book, bId);
//        return service.getBookById(bId);
//    }
//
//    //delete
//    @DeleteMapping("/delete/{bId}")
//    public List<Book> deleteBook(@PathVariable int bId){
//        service.deleteBookById(bId);
//        return service.getAllBooks();
//    }



}
